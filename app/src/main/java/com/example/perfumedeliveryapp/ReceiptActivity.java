package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity {

    private TextView userDetailsTextView, receiptSummaryTextView, totalPriceTextView;
    private RadioGroup paymentMethodRadioGroup;
    private Button confirmPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // Initialize views
        userDetailsTextView = findViewById(R.id.userDetailsTextView);
        receiptSummaryTextView = findViewById(R.id.receiptSummaryTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("name", "No Name");
        String userEmail = sharedPreferences.getString("email", "No Email");

        // Display user details
        userDetailsTextView.setText(String.format("User: %s\nEmail: %s", userName, userEmail));

        // Retrieve data passed from previous activity
        Intent intent = getIntent();
        ArrayList<CartItem> cartItems = intent.getParcelableArrayListExtra("cartItems");
        float totalPrice = intent.getFloatExtra("totalPrice", 0.0f);
        String recipientName = intent.getStringExtra("recipientName");
        String recipientAddress = intent.getStringExtra("recipientAddress");
        String recipientPhone = intent.getStringExtra("recipientPhone");

        // Display recipient details
        if (recipientName != null && recipientAddress != null && recipientPhone != null) {
            userDetailsTextView.append(String.format("\n\nRecipient: %s\nAddress: %s\nPhone: %s", recipientName, recipientAddress, recipientPhone));
        } else {
            userDetailsTextView.append("\n\nRecipient details are incomplete.");
        }

        // Display order summary
        if (cartItems != null && !cartItems.isEmpty()) {
            StringBuilder receiptSummary = new StringBuilder("Order Details:\n");
            for (CartItem item : cartItems) {
                receiptSummary.append(String.format("Product: %s x%d - PHP %.2f\n",
                        item.getProductName(), item.getQuantity(), item.getPriceAsFloat() * item.getQuantity()));
            }
            receiptSummaryTextView.setText(receiptSummary.toString());
        } else {
            receiptSummaryTextView.setText("No items in your cart.");
        }

        // Display total price
        totalPriceTextView.setText(String.format("Total: PHP %.2f", totalPrice));

        // Disable confirm button until a payment method is selected
        confirmPaymentButton.setEnabled(false);
        paymentMethodRadioGroup.setOnCheckedChangeListener((group, checkedId) -> confirmPaymentButton.setEnabled(checkedId != -1));

        // Confirm Payment button action
        confirmPaymentButton.setOnClickListener(v -> {
            int selectedId = paymentMethodRadioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.cashOnDeliveryRadioButton) {
                // Save order data to SharedPreferences
                saveOrderToSharedPreferences(cartItems, totalPrice, recipientName, recipientAddress, recipientPhone);

                // Log confirmation
                // Handle the Cash on Delivery logic
                handleCashOnDelivery(cartItems, totalPrice);
            } else {
                // Handle other payment methods if applicable (e.g., credit card, PayPal, etc.)
                Toast.makeText(this, "Payment method not supported yet.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOrderToSharedPreferences(ArrayList<CartItem> cartItems, float totalPrice,
                                              String recipientName, String recipientAddress, String recipientPhone) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve the current number of saved orders
        int orderCount = sharedPreferences.getInt("order_count", 0);

        // Build a string with order details
        StringBuilder orderDetails = new StringBuilder();
        for (CartItem item : cartItems) {
            orderDetails.append(item.getProductName()).append(",")  // Product name
                    .append(item.getPriceAsFloat()).append(",")  // Product price
                    .append(item.getQuantity()).append("|");     // Quantity
        }

        // Add recipient details and total price
        orderDetails.append(recipientName).append(",")
                .append(recipientAddress).append(",")
                .append(recipientPhone).append(",")
                .append(totalPrice)
                .append(",").append(System.currentTimeMillis());  // Add timestamp

        // Save the order details under a unique key
        editor.putString("order_" + orderCount, orderDetails.toString());

        // Increment the order count
        editor.putInt("order_count", orderCount + 1);

        // Apply changes
        editor.apply();
    }

    private void handleCashOnDelivery(ArrayList<CartItem> cartItems, float totalPrice) {
        // Before sending the notification, check and request permission if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;  // Exit if permission is not granted
            }
        }

        // Send the payment confirmation notification (popup notification)
        NotificationHelper.sendPaymentConfirmationNotification(ReceiptActivity.this);

        // Proceed with Cash on Delivery logic
        Toast.makeText(this, "Payment Confirmed. Cash on Delivery selected.", Toast.LENGTH_SHORT).show();

        // Navigate to Order Confirmation or any other relevant activity
        Intent intent = new Intent(this, OrderConfirmationActivity.class);
        intent.putParcelableArrayListExtra("cartItems", cartItems);
        intent.putExtra("totalPrice", totalPrice);
        startActivity(intent);

        // Close ReceiptActivity
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the payment confirmation notification
                NotificationHelper.sendPaymentConfirmationNotification(this);
            } else {
                // Permission denied, handle accordingly (maybe show a message to the user)
                Toast.makeText(this, "Notification permission denied. You will not receive notifications.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
