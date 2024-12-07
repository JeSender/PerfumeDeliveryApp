package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView, userEmailTextView;
    private ImageView profileImageView;
    private Button editProfileButton, orderHistoryButton, logOutButton, loginAnotherDeviceButton, addProductButton;
    private ImageButton backButton; // Changed type to ImageButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile); // Ensure profile.xml exists

        // Initialize the views
        userNameTextView = findViewById(R.id.userNameTextView);
        userEmailTextView = findViewById(R.id.userEmailTextView);
        profileImageView = findViewById(R.id.profileImageView);
        editProfileButton = findViewById(R.id.editProfileButton);
        orderHistoryButton = findViewById(R.id.orderHistoryButton);
        logOutButton = findViewById(R.id.logOutButton);
        loginAnotherDeviceButton = findViewById(R.id.logInAnotherDeviceButton);
        backButton = findViewById(R.id.backHomeButton); // Correctly cast ImageButton
        addProductButton = findViewById(R.id.addProductButton); // Added button reference

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("name", "User Name");
        String userEmail = sharedPreferences.getString("email", "user@example.com");

        // Display user data
        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);

        // Set up button click listeners
        setupClickListeners(sharedPreferences);
    }

    private void setupClickListeners(SharedPreferences sharedPreferences) {
        // Back Button Click Listener
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Prevent returning to this activity
        });

        // Edit Profile Button
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // Order History Button
        orderHistoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, OrderHistoryActivity.class);
            startActivity(intent);
        });

        // Log Out Button
        logOutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(ProfileActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Log In on Another Device Button
        loginAnotherDeviceButton.setOnClickListener(v -> {
            Toast.makeText(this, "Redirecting to Login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Add Product Button
        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AddProductActivity.class);
            startActivity(intent);
        });
    }
}
