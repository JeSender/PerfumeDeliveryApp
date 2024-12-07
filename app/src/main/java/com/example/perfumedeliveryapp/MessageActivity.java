package com.example.perfumedeliveryapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {

    private EditText messageEditText;
    private Button sendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Initialize views
        messageEditText = findViewById(R.id.messageEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);

        // Handle send message button
        sendMessageButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();

            if (message.isEmpty()) {
                Toast.makeText(MessageActivity.this, "Message cannot be empty!", Toast.LENGTH_SHORT).show();
            } else {
                sendMessageToSupport(message);
            }
        });
    }

    private void sendMessageToSupport(String message) {
        // Here, handle sending the message to customer support
        // This could involve Firebase, an API, or just showing a confirmation message
        Toast.makeText(this, "Message sent: " + message, Toast.LENGTH_LONG).show();

        // Optionally, clear the input field after sending
        messageEditText.setText("");
    }
}
