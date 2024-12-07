package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private EditText searchEditText; // Search bar
    private ImageButton viewCartButton, profileButton, settingsButton, messageButton, notificationsButton;
    private ArrayList<CartItem> cartItems;
    private ArrayList<Product> allProducts; // Original product list
    private ProductAdapter productAdapter; // Adapter for RecyclerView
    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize cartItems list
        cartItems = new ArrayList<>();

        // Retrieve logged-in state from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // Initialize Views
        profileButton = findViewById(R.id.userAccountButton);
        viewCartButton = findViewById(R.id.viewCartButton);
        settingsButton = findViewById(R.id.settingsButton);
        messageButton = findViewById(R.id.messageButton); // Added message button
        notificationsButton = findViewById(R.id.notificationButton); // Added notifications button
        searchEditText = findViewById(R.id.searchView);

        // Profile Button Setup
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Notifications Button Setup
        notificationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });

        // Message Button Setup
        messageButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MessageActivity.class);
            startActivity(intent);
        });

        // Product RecyclerView Setup
        productRecyclerView = findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch and display products
        allProducts = getProducts();
        productAdapter = new ProductAdapter(allProducts, isLoggedIn, this);
        productRecyclerView.setAdapter(productAdapter);

        // Set Add to Cart listener for each product
        productAdapter.setOnAddToCartClickListener(this::addToCart);

        // Search Functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        // View Cart Button Setup
        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            intent.putParcelableArrayListExtra("cartItems", cartItems);
            startActivity(intent);
        });

        // Settings Button Setup
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    // Method to filter products based on search query
    private void filterProducts(String query) {
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getProductName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter.updateList(filteredList); // Update adapter with filtered list
    }

    // Method to fetch product data
    private ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Chanel", "Luxury fragrance", "368 PHP", R.drawable.chanel));
        products.add(new Product("Victoria Secret", "Fresh fragrance", "350 PHP", R.drawable.victoria));
        products.add(new Product("Bench", "Affordable fragrance", "120 PHP", R.drawable.bench));
        products.add(new Product("Banana Republic", "Luxury fragrance", "300 PHP", R.drawable.banana));
        products.add(new Product("Bvlgari", "Fresh fragrance", "1510 PHP", R.drawable.bvlgari));
        products.add(new Product("Coach Perfume", "Luxury fragrance", "300 PHP", R.drawable.coach));
        products.add(new Product("Dolce Gabbana", "Luxury fragrance", "300 PHP", R.drawable.dolce));
        products.add(new Product("Nautica", "Luxury fragrance", "300 PHP", R.drawable.nautica));
        products.add(new Product("Bliss", "Luxury fragrance", "560 PHP", R.drawable.bliss));
        products.add(new Product("Eau de Parfum", "Luxury fragrance", "300 PHP", R.drawable.eau_de_parfum));
        products.add(new Product("Lavin", "Luxury fragrance", "335 PHP", R.drawable.lanvin));
        products.add(new Product("Jovan", "Luxury fragrance", "450 PHP", R.drawable.jovan));
        products.add(new Product("Rose", "Luxury fragrance", "843 PHP", R.drawable.rose));
        products.add(new Product("Man Regal", "Luxury fragrance", "670 PHP", R.drawable.man));
        products.add(new Product("Chanel No.5", "Luxury fragrance", "760 PHP", R.drawable.channel));
        products.add(new Product("Coco Mademoiselle", "Luxury fragrance", "290 PHP", R.drawable.coco));
        products.add(new Product("J'adore", "Affordable fragrance", "290 PHP", R.drawable.adore));
        products.add(new Product("Sauvage", "Luxury fragrance", "830 PHP", R.drawable.sauvage));
        products.add(new Product("Black Opium", "Fresh fragrance", "460 PHP", R.drawable.opium));
        products.add(new Product("Libre", "Fresh fragrance", "600 PHP", R.drawable.libri));
        products.add(new Product("Daisy", "Luxury fragrance", "870 PHP", R.drawable.daisy));
        products.add(new Product("Marc Jacobs Perfect", "Luxury fragrance", "810 PHP", R.drawable.marc));
        products.add(new Product("Light Blue", "Luxury fragrance", "650 PHP", R.drawable.blue));
        products.add(new Product("The One", "Fresh fragrance", "843 PHP", R.drawable.one));
        products.add(new Product("Acqua Di Gio", "Luxury fragrance", "700 PHP", R.drawable.gio));
        products.add(new Product("Si", "Luxury fragrance", "843 PHP", R.drawable.si));
        products.add(new Product("Bright Cystal", "Affordable fragrance", "550 PHP", R.drawable.bright));
        products.add(new Product("Dylan blue", "Luxury fragrance", "850 PHP", R.drawable.dylan));
        products.add(new Product("My Burberry", "Fresh fragrance", "400", R.drawable.berry));
        products.add(new Product("Brit Rhythm", "Oriental fragrance", "350 PHP", R.drawable.brit));

        return products;
    }

    // Add product to cart
    private void addToCart(Product product) {
        boolean found = false;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductName().equals(product.getProductName())) {
                cartItem.increaseQuantity();
                found = true;
                break;
            }
        }
        if (!found) {
            cartItems.add(new CartItem(product.getProductName(), product.getProductDescription(), product.getProductPrice(), product.getProductImage()));
        }
        Toast.makeText(this, product.getProductName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
