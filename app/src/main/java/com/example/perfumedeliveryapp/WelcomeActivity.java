package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize the button from the layout
        Button getStartedButton = findViewById(R.id.getStartedButton);

        // Set the click listener for the button
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to HomeActivity
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent); // Start HomeActivity
            }
        });
    }
}
