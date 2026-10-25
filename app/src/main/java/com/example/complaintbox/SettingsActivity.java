package com.example.complaintbox;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText studentNameInput;
    private Spinner educationSpinner;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);

        studentNameInput = findViewById(R.id.studentNameInput);
        educationSpinner = findViewById(R.id.educationSpinner);
        Button saveButton = findViewById(R.id.saveButton);
        Button clearButton = findViewById(R.id.clearButton);
        Button contactButton = findViewById(R.id.contactButton);
        Button websiteButton = findViewById(R.id.websiteButton);
        Button locationButton = findViewById(R.id.locationButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.education_levels,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        educationSpinner.setAdapter(adapter);

        studentNameInput.setText(preferences.getString(MainActivity.KEY_STUDENT_NAME, ""));
        setSpinnerSelection(preferences.getString("education_level", "Select Education Level"));

        saveButton.setOnClickListener(v -> savePreferences());
        clearButton.setOnClickListener(v -> clearPreferences());
        contactButton.setOnClickListener(v -> contactAdministration());
        websiteButton.setOnClickListener(v -> openWebsite());
        locationButton.setOnClickListener(v -> openLocation());
    }

    private void setSpinnerSelection(String value) {
        ArrayAdapter adapter = (ArrayAdapter) educationSpinner.getAdapter();
        int position = adapter.getPosition(value);
        if (position >= 0) {
            educationSpinner.setSelection(position);
        }
    }

    private void savePreferences() {
        String studentName = studentNameInput.getText().toString().trim();
        String educationLevel = educationSpinner.getSelectedItem().toString();

        preferences.edit()
                .putString(MainActivity.KEY_STUDENT_NAME, studentName)
                .putString("education_level", educationLevel)
                .apply();

        Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show();
    }

    private void clearPreferences() {
        preferences.edit().clear().apply();
        studentNameInput.setText("");
        educationSpinner.setSelection(0);
        Toast.makeText(this, "Preferences cleared", Toast.LENGTH_SHORT).show();
    }

    private void contactAdministration() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:admin@college.edu"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "College Complaint Box");

        try {
            startActivity(Intent.createChooser(intent, "Contact Administration"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No browser found", Toast.LENGTH_SHORT).show();
        }
    }

    private void openLocation() {
        Uri uri = Uri.parse("geo:0,0?q=college+campus");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No maps app found", Toast.LENGTH_SHORT).show();
        }
    }
}
