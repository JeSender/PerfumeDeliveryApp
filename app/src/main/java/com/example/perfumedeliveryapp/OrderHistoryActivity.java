package com.example.perfumedeliveryapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private ListView orderListView;
    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize the ListView
        orderListView = findViewById(R.id.orderListView);

        // Load order history directly from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int orderCount = sharedPreferences.getInt("order_count", 0);

        // Check if there are no orders
        if (orderCount == 0) {
            Toast.makeText(this, "No orders found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Loop through the saved orders and load them
        for (int i = 0; i < orderCount; i++) {
            String orderDetails = sharedPreferences.getString("order_" + i, null);
            if (orderDetails != null) {
                String[] orderParts = orderDetails.split("\\|"); // Split using the pipe separator

                // Ensure the order data is valid (we expect at least 6 parts)
                if (orderParts.length < 6) {
                    Toast.makeText(this, "Order data is incomplete or malformed.", Toast.LENGTH_SHORT).show();
                    continue;
                }

                // Initialize lists to hold product details
                List<String> productNames = new ArrayList<>();
                List<String> productPrices = new ArrayList<>();
                List<Integer> productQuantities = new ArrayList<>();

                // Parse the product details
                String[] products = orderParts[0].split(",");
                for (String product : products) {
                    String[] productInfo = product.split(",");
                    if (productInfo.length == 3) {
                        productNames.add(productInfo[0]); // Product name
                        productPrices.add(productInfo[1]); // Product price
                        productQuantities.add(Integer.parseInt(productInfo[2])); // Product quantity
                    }
                }

                // Extract recipient details and other order information
                String recipientName = orderParts[1]; // Recipient name
                String recipientAddress = orderParts[2]; // Recipient address
                String recipientPhone = orderParts[3]; // Recipient phone
                String totalPrice = orderParts[4]; // Total price

                // Create an Order object and add it to the list
                Order order = new Order(productNames, productPrices, productQuantities, recipientName, recipientAddress, recipientPhone, totalPrice);
                orderList.add(order);
            }
        }

        // Set the Adapter for the ListView
        orderAdapter = new OrderAdapter(this, orderList);
        orderListView.setAdapter(orderAdapter);

        // Empty view for the ListView when no orders are available
        orderListView.setEmptyView(findViewById(R.id.emptyOrderTextView));
    }
}
