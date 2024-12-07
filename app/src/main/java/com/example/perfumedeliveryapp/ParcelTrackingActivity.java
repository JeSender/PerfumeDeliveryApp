package com.example.perfumedeliveryapp;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ParcelTrackingActivity extends AppCompatActivity {

    private ProgressBar parcelProgressBar;
    private TextView orderConfirmationMessage, orderDetailsTextView, totalPriceTextView, paymentMethodTextView;
    private TextView parcelStatusStep1, parcelStatusStep2, parcelStatusStep3, parcelStatusStep4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation); // The XML layout we created above

        // Initialize views
        parcelProgressBar = findViewById(R.id.parcelProgressBar);
        orderConfirmationMessage = findViewById(R.id.orderConfirmationMessage);
        orderDetailsTextView = findViewById(R.id.orderDetailsTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        paymentMethodTextView = findViewById(R.id.paymentMethodTextView);
        parcelStatusStep1 = findViewById(R.id.parcelStatusStep1);
        parcelStatusStep2 = findViewById(R.id.parcelStatusStep2);
        parcelStatusStep3 = findViewById(R.id.parcelStatusStep3);
        parcelStatusStep4 = findViewById(R.id.parcelStatusStep4);

        // For example, set some static data (This can be dynamically fetched)
        orderConfirmationMessage.setText("Thank you for your order!");
        orderDetailsTextView.setText("Order details will appear here");
        totalPriceTextView.setText("Total: PHP 0.00");
        paymentMethodTextView.setText("Payment Method: Cash on Delivery");

        // Simulate progress updates
        updateParcelTrackingStatus(50); // Set progress to 50% (for example)
    }

    private void updateParcelTrackingStatus(int progress) {
        parcelProgressBar.setProgress(progress);

        // Simulating dynamic status updates (you would retrieve this from your server or database)
        if (progress >= 25) {
            parcelStatusStep1.setText("1. Order Placed");
        }
        if (progress >= 50) {
            parcelStatusStep2.setText("2. Shipped");
        }
        if (progress >= 75) {
            parcelStatusStep3.setText("3. In Transit");
        }
        if (progress == 100) {
            parcelStatusStep4.setText("4. Delivered");
        }
    }
}
