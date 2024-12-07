package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView orderConfirmationMessage, orderDetailsTextView, totalPriceTextView, paymentMethodTextView;
    private Button goToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Initialize views
        orderConfirmationMessage = findViewById(R.id.orderConfirmationMessage);
        orderDetailsTextView = findViewById(R.id.orderDetailsTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        paymentMethodTextView = findViewById(R.id.paymentMethodTextView);
        goToHomeButton = findViewById(R.id.goToHomeButton);

        // Get the data passed from ReceiptActivity
        Intent intent = getIntent();
        ArrayList<CartItem> cartItems = intent.getParcelableArrayListExtra("cartItems");
        float totalPrice = intent.getFloatExtra("totalPrice", 0.0f);

        // Display the confirmation message
        orderConfirmationMessage.setText("Thank you for your order!");

        // Display order details (product names, quantities, and prices)
        StringBuilder orderDetails = new StringBuilder("Order Details:\n");
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                orderDetails.append(item.getProductName())
                        .append(" x")
                        .append(item.getQuantity())
                        .append(" - PHP ")
                        .append(item.getQuantity() * item.getPriceAsFloat())
                        .append("\n");
            }
        }
        orderDetailsTextView.setText(orderDetails.toString());

        // Display the total price
        totalPriceTextView.setText("Total: PHP " + totalPrice);

        // Display the payment method (Cash on Delivery)
        paymentMethodTextView.setText("Payment Method: Cash on Delivery");

        // Go to Home button action
        goToHomeButton.setOnClickListener(v -> {
            // Navigate to HomeActivity
            Intent homeIntent = new Intent(OrderConfirmationActivity.this, HomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear all activities and start a new HomeActivity
            startActivity(homeIntent);
            finish(); // Finish the current activity to prevent users from coming back to it
        });
    }
}
