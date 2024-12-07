package com.example.perfumedeliveryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> products; // List of products to display
    private OnAddToCartClickListener onAddToCartClickListener;
    private boolean isLoggedIn; // Login state
    private Context context; // For handling intents and Toasts

    // Constructor with isLoggedIn and context
    public ProductAdapter(ArrayList<Product> products, boolean isLoggedIn, Context context) {
        this.products = products;
        this.isLoggedIn = isLoggedIn;
        this.context = context;
    }

    // Listener setter for Add to Cart
    public void setOnAddToCartClickListener(OnAddToCartClickListener listener) {
        this.onAddToCartClickListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the product item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);

        // Set product details in views
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
        holder.productDescription.setText(product.getProductDescription()); // Setting the description

        // Load product image using Glide (assuming it's a URL or resource ID)
        Glide.with(context)
                .load(product.getProductImage()) // Resource ID or URL
                .into(holder.productImage);

        // Handle Add to Cart button click
        holder.addToCartButton.setOnClickListener(v -> {
            if (!isLoggedIn) {
                // User not logged in
                Toast.makeText(context, "Please log in to add items to your cart.", Toast.LENGTH_SHORT).show();

                // Redirect to LoginActivity
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            } else {
                // User is logged in
                if (onAddToCartClickListener != null) {
                    onAddToCartClickListener.onAddToCart(product); // Notify listener
                }
                Toast.makeText(context, product.getProductName() + " added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Method to update product list (used for search functionality)
    public void updateList(ArrayList<Product> updatedProducts) {
        this.products = updatedProducts; // Update the adapter's data
        notifyDataSetChanged(); // Refresh the RecyclerView
    }

    // ViewHolder class for managing individual product items
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        Button addToCartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            productPrice = itemView.findViewById(R.id.productPriceTextView);
            productDescription = itemView.findViewById(R.id.productDescriptionTextView); // Added for description
            productImage = itemView.findViewById(R.id.productImageView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }

    // Interface for Add to Cart event
    public interface OnAddToCartClickListener {
        void onAddToCart(Product product);
    }
}
