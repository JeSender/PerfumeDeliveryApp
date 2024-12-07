package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EditProfileActivity extends AppCompatActivity {

    // Declare views
    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);  // Set the layout

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Retrieve current user info from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String currentName = sharedPreferences.getString("name", "");
        String currentEmail = sharedPreferences.getString("email", "");
        String currentPassword = sharedPreferences.getString("password", "");

        // Prefill the EditText fields with current data
        nameEditText.setText(currentName);
        emailEditText.setText(currentEmail);
        passwordEditText.setText(currentPassword);  // Password field for editing

        // Save Changes Button Click Listener
        saveButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString().trim();
            String newEmail = emailEditText.getText().toString().trim();
            String newPassword = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Validate fields
            if (TextUtils.isEmpty(newName) || TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(EditProfileActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(EditProfileActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(newEmail)) {
                Toast.makeText(EditProfileActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            } else {
                // Hash the password before saving
                String hashedPassword = hashPassword(newPassword);

                // Save the updated information in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", newName);
                editor.putString("email", newEmail);
                editor.putString("password", hashedPassword);  // Save the hashed password
                editor.apply();

                Toast.makeText(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();

                // Redirect to ProfileActivity
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();  // Close EditProfileActivity
            }
        });

        // Cancel Button Click Listener
        cancelButton.setOnClickListener(v -> {
            // Redirect to ProfileActivity without saving
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();  // Close EditProfileActivity
        });
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;  // Fallback to plain password (not recommended)
        }
    }
}
