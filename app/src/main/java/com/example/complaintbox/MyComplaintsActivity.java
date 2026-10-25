package com.example.complaintbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyComplaintsActivity extends AppCompatActivity {

    public static final String EXTRA_COMPLAINT_ID = "extra_complaint_id";

    private ComplaintDbHelper dbHelper;
    private ComplaintAdapter adapter;
    private ArrayList<Complaint> complaints = new ArrayList<>();
    private ListView complaintsListView;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaints);

        dbHelper = new ComplaintDbHelper(this);
        complaintsListView = findViewById(R.id.complaintsListView);
        emptyText = findViewById(R.id.emptyText);

        complaintsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Complaint complaint = complaints.get(position);
                Intent intent = new Intent(MyComplaintsActivity.this, ComplaintDetailsActivity.class);
                intent.putExtra(EXTRA_COMPLAINT_ID, complaint.getComplaintId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        complaints = dbHelper.getAllComplaints();
        adapter = new ComplaintAdapter(this, complaints);
        complaintsListView.setAdapter(adapter);
        emptyText.setVisibility(complaints.isEmpty() ? View.VISIBLE : View.GONE);
        complaintsListView.setVisibility(complaints.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
