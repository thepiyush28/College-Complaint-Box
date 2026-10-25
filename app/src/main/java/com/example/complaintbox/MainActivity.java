package com.example.complaintbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "college_complaint_box_prefs";
    public static final String KEY_STUDENT_NAME = "student_name";

    private EditText studentNameInput;
    private EditText passwordInput;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ImageView logoView = findViewById(R.id.logoView);
        studentNameInput = findViewById(R.id.studentNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);

        logoView.setImageResource(R.mipmap.ic_launcher);
        studentNameInput.setText(preferences.getString(KEY_STUDENT_NAME, ""));

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String studentName = studentNameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(studentName)) {
            studentNameInput.setError("Enter your name");
            studentNameInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Enter your password");
            passwordInput.requestFocus();
            return;
        }

        preferences.edit().putString(KEY_STUDENT_NAME, studentName).apply();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
