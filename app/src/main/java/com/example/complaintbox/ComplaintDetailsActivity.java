package com.example.complaintbox;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ComplaintDetailsActivity extends AppCompatActivity {

    private ComplaintDbHelper dbHelper;
    private Complaint complaint;
    private TextView complaintIdText;
    private TextView titleText;
    private TextView categoryText;
    private TextView descriptionText;
    private TextView dateText;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);

        dbHelper = new ComplaintDbHelper(this);
        complaintIdText = findViewById(R.id.complaintIdText);
        titleText = findViewById(R.id.titleText);
        categoryText = findViewById(R.id.categoryText);
        descriptionText = findViewById(R.id.descriptionText);
        dateText = findViewById(R.id.dateText);
        statusText = findViewById(R.id.statusText);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button shareButton = findViewById(R.id.shareButton);
        Button emailButton = findViewById(R.id.emailButton);

        String complaintId = getIntent().getStringExtra(MyComplaintsActivity.EXTRA_COMPLAINT_ID);
        if (complaintId == null) {
            Toast.makeText(this, "Complaint not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        complaint = dbHelper.getComplaintByComplaintId(complaintId);
        if (complaint == null) {
            Toast.makeText(this, "Complaint not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        renderComplaint();

        deleteButton.setOnClickListener(v -> {
            if (dbHelper.deleteComplaint(complaint.getComplaintId())) {
                Toast.makeText(this, "Complaint deleted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });

        shareButton.setOnClickListener(v -> shareComplaint());
        emailButton.setOnClickListener(v -> emailAdministration());
    }

    private void renderComplaint() {
        complaintIdText.setText(complaint.getComplaintId());
        titleText.setText(complaint.getTitle());
        categoryText.setText(complaint.getCategory());
        descriptionText.setText(complaint.getDescription());
        dateText.setText(complaint.getDate());
        statusText.setText(complaint.getStatus());
    }

    private void shareComplaint() {
        String message = "Complaint ID: " + complaint.getComplaintId()
                + "\nTitle: " + complaint.getTitle()
                + "\nCategory: " + complaint.getCategory()
                + "\nStatus: " + complaint.getStatus();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(shareIntent, "Share Complaint"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No app found to share", Toast.LENGTH_SHORT).show();
        }
    }

    private void emailAdministration() {
        String subject = "Complaint ID " + complaint.getComplaintId();
        String body = "Dear Administration,\n\n"
                + "Please review this complaint:\n\n"
                + "Complaint ID: " + complaint.getComplaintId() + "\n"
                + "Title: " + complaint.getTitle() + "\n"
                + "Category: " + complaint.getCategory() + "\n"
                + "Description: " + complaint.getDescription() + "\n"
                + "Date: " + complaint.getDate() + "\n"
                + "Status: " + complaint.getStatus() + "\n";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:admin@college.edu"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(intent, "Email Administration"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }
}
