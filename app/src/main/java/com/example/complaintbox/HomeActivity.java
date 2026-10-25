package com.example.complaintbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);

        ImageView logoView = findViewById(R.id.homeLogoView);
        TextView titleText = findViewById(R.id.homeTitleText);
        TextView subtitleText = findViewById(R.id.homeSubtitleText);
        TextView welcomeText = findViewById(R.id.welcomeText);
        MaterialCardView submitCard = findViewById(R.id.submitCard);
        MaterialCardView complaintsCard = findViewById(R.id.complaintsCard);
        MaterialCardView settingsCard = findViewById(R.id.settingsCard);

        logoView.setImageResource(R.mipmap.ic_launcher);
        titleText.setText(getString(R.string.home_title));
        subtitleText.setText(getString(R.string.home_subtitle));

        String studentName = preferences.getString(MainActivity.KEY_STUDENT_NAME, "Student");
        welcomeText.setText("Welcome, " + studentName);

        submitCard.setOnClickListener(v -> startActivity(new Intent(this, SubmitComplaintActivity.class)));
        complaintsCard.setOnClickListener(v -> startActivity(new Intent(this, MyComplaintsActivity.class)));
        settingsCard.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
}
