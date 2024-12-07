package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private TextView totalPriceTextView;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Get cart items passed from HomeActivity
        cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        // Initialize the views using the correct IDs from the XML layout
        cartListView = findViewById(R.id.cartListView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Set up the cart adapter
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);

        // Calculate and display total price
        float totalPrice = calculateTotalPrice(); // Use method for total price calculation
        totalPriceTextView.setText("Total: PHP " + totalPrice);

        // Set up the Checkout button click listener
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the cart is empty, show a message
                if (cartItems.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to CheckoutActivity
                    Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);

                    // Pass cart items and total price to CheckoutActivity
                    checkoutIntent.putParcelableArrayListExtra("cartItems", cartItems);
                    checkoutIntent.putExtra("totalPrice", calculateTotalPrice()); // Pass total price

                    startActivity(checkoutIntent);
                }
            }
        });
    }

    // Method to calculate total price of items in the cart
    private float calculateTotalPrice() {
        float totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPriceAsFloat() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}
