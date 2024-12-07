package com.example.perfumedeliveryapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox notificationCheckBox;
    private Button saveSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);  // Make sure activity_settings.xml is correct

        // Initialize the views
        notificationCheckBox = findViewById(R.id.notificationCheckBox);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);

        // Load the current settings from SharedPreferences
        loadSettings();

        // Save the settings when the user presses the Save button
        saveSettingsButton.setOnClickListener(v -> {
            boolean notificationsEnabled = notificationCheckBox.isChecked();

            // Save the setting to SharedPreferences
            saveSettings(notificationsEnabled);

            // Provide feedback to the user
            Toast.makeText(SettingsActivity.this, "Settings saved successfully!", Toast.LENGTH_SHORT).show();
        });
    }

    // Load settings from SharedPreferences
    private void loadSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true);

        // Set the checkbox state based on the stored preference
        notificationCheckBox.setChecked(notificationsEnabled);
    }

    // Save settings to SharedPreferences
    private void saveSettings(boolean notificationsEnabled) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the notifications setting
        editor.putBoolean("notifications_enabled", notificationsEnabled);

        // Commit the changes
        editor.apply();
    }
}
