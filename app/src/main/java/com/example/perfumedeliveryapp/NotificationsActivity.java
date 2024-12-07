package com.example.perfumedeliveryapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    private ListView notificationsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize the ListView
        notificationsListView = findViewById(R.id.notificationsListView);

        // Example notifications (this can be fetched from a server or database)
        ArrayList<String> notifications = new ArrayList<>();
        notifications.add("Your order #1234 has been shipped.");
        notifications.add("Your order #1234 is out for delivery.");
        notifications.add("Exclusive Offer: 20% off on your next purchase!");

        // Set adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notifications);
        notificationsListView.setAdapter(adapter);
    }
}
