package com.example.complaintbox;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SubmitComplaintActivity extends AppCompatActivity {

    private final Calendar selectedDate = Calendar.getInstance();
    private EditText studentNameInput;
    private Spinner categorySpinner;
    private EditText titleInput;
    private EditText descriptionInput;
    private EditText dateInput;
    private TextView resultText;
    private ComplaintDbHelper dbHelper;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_complaint);

        preferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        dbHelper = new ComplaintDbHelper(this);

        studentNameInput = findViewById(R.id.studentNameInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        dateInput = findViewById(R.id.dateInput);
        Button pickDateButton = findViewById(R.id.pickDateButton);
        Button submitButton = findViewById(R.id.submitButton);
        resultText = findViewById(R.id.resultText);

        studentNameInput.setText(preferences.getString(MainActivity.KEY_STUDENT_NAME, ""));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.complaint_categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        dateInput.setText(formatter.format(selectedDate.getTime()));

        pickDateButton.setOnClickListener(v -> openDatePicker());
        submitButton.setOnClickListener(v -> submitComplaint());
    }

    private void openDatePicker() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            selectedDate.set(year1, month1, dayOfMonth);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            dateInput.setText(formatter.format(selectedDate.getTime()));
        }, year, month, day);
        dialog.show();
    }

    private void submitComplaint() {
        String studentName = studentNameInput.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();

        if (TextUtils.isEmpty(studentName)) {
            studentNameInput.setError("Required");
            studentNameInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(title)) {
            titleInput.setError("Required");
            titleInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionInput.setError("Required");
            descriptionInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(date)) {
            dateInput.setError("Required");
            dateInput.requestFocus();
            return;
        }

        String complaintId = generateComplaintId();
        long rowId = dbHelper.insertComplaint(
                complaintId,
                studentName,
                category,
                title,
                description,
                date,
                "Submitted"
        );

        if (rowId == -1) {
            Toast.makeText(this, "Unable to save complaint", Toast.LENGTH_SHORT).show();
            return;
        }

        preferences.edit().putString(MainActivity.KEY_STUDENT_NAME, studentName).apply();
        resultText.setText("Complaint submitted successfully.\nComplaint ID: " + complaintId);
        Toast.makeText(this, "Complaint submitted", Toast.LENGTH_SHORT).show();
        titleInput.setText("");
        descriptionInput.setText("");
    }

    private String generateComplaintId() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return "CCB-" + format.format(new Date()) + "-" + (System.currentTimeMillis() % 1000);
    }
}
