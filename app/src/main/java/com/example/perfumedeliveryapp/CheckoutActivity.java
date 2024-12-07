package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private TextView checkoutSummaryTextView;
    private TextView totalPriceTextView;
    private ArrayList<CartItem> cartItems;
    private float totalPrice;
    private Button checkoutButton;
    private EditText recipientNameEditText, recipientAddressEditText, recipientPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Get the cart items and total price passed from CartActivity
        cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        // Initialize UI elements
        checkoutSummaryTextView = findViewById(R.id.checkoutSummaryTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        checkoutButton = findViewById(R.id.checkoutButton);
        recipientNameEditText = findViewById(R.id.recipientNameEditText);
        recipientAddressEditText = findViewById(R.id.recipientAddressEditText);
        recipientPhoneEditText = findViewById(R.id.recipientPhoneEditText);

        // Calculate the total price of the cart
        totalPrice = 0;
        StringBuilder summary = new StringBuilder("Checkout Summary:\n\n");

        for (CartItem cartItem : cartItems) {
            // Calculate the price for each cart item
            float itemTotalPrice = cartItem.getPriceAsFloat() * cartItem.getQuantity();
            summary.append(cartItem.getProductName())
                    .append(" x").append(cartItem.getQuantity())
                    .append(" - PHP ").append(itemTotalPrice)
                    .append("\n");

            // Add to total price
            totalPrice += itemTotalPrice;
        }

        // Set the checkout summary and total price
        checkoutSummaryTextView.setText(summary.toString());
        totalPriceTextView.setText("Total: PHP " + totalPrice);

        // Set up the checkout button to validate the form and proceed to payment
        checkoutButton.setOnClickListener(v -> {
            if (isFormValid()) {
                // If the form is valid, show a success message and proceed to the ReceiptActivity
                Toast.makeText(CheckoutActivity.this, "Delivery address confirmed! Proceeding to Receipt.", Toast.LENGTH_SHORT).show();

                // Prepare the data to pass to the ReceiptActivity
                String recipientName = recipientNameEditText.getText().toString().trim();
                String recipientAddress = recipientAddressEditText.getText().toString().trim();
                String recipientPhone = recipientPhoneEditText.getText().toString().trim();

                // Create an Intent to move to the ReceiptActivity
                Intent intent = new Intent(CheckoutActivity.this, ReceiptActivity.class);

                // Pass the necessary data to ReceiptActivity
                intent.putExtra("recipientName", recipientName);
                intent.putExtra("recipientAddress", recipientAddress);
                intent.putExtra("recipientPhone", recipientPhone);
                intent.putParcelableArrayListExtra("cartItems", cartItems);
                intent.putExtra("totalPrice", totalPrice);

                // Start the ReceiptActivity
                startActivity(intent);
                finish(); // Optionally close CheckoutActivity to prevent the user from returning
            }
        });
    }

    // Form validation method
    private boolean isFormValid() {
        String recipientName = recipientNameEditText.getText().toString().trim();
        String recipientAddress = recipientAddressEditText.getText().toString().trim();
        String recipientPhone = recipientPhoneEditText.getText().toString().trim();

        // Validate recipient name
        if (recipientName.isEmpty()) {
            recipientNameEditText.setError("Recipient Name is required");
            return false;
        }

        // Validate delivery address
        if (recipientAddress.isEmpty()) {
            recipientAddressEditText.setError("Delivery Address is required");
            return false;
        }

        // Validate phone number
        if (recipientPhone.isEmpty() || !recipientPhone.matches("\\d{10}")) {
            recipientPhoneEditText.setError("Valid Phone Number is required (10 digits)");
            return false;
        }

        // If all fields are valid
        return true;
    }
}
