package com.example.perfumedeliveryapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    private static final String CHANNEL_ID = "payment_confirmation_channel";
    private static final String CHANNEL_NAME = "Payment Confirmation";

    // Create Notification Channel (for Android 8.0 and above)
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notification for payment confirmation");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Method to send heads-up notification (popup notification)
    public static void sendPaymentConfirmationNotification(Context context) {
        // Create the notification with high priority
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Payment Confirmed")
                .setContentText("Your payment has been successfully processed!")
                .setSmallIcon(R.drawable.baseline_account) // Replace with your own icon
                .setPriority(NotificationCompat.PRIORITY_HIGH) // High priority for heads-up notification
                .setAutoCancel(true) // Dismiss notification when tapped
                .setDefaults(Notification.DEFAULT_ALL) // Use default sound, vibration, etc.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Ensure visibility in locked screen
                .build();

        // Send the notification to the status bar
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);  // 1 is the notification ID
    }
}
