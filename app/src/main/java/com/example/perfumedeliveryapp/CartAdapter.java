package com.example.perfumedeliveryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private ArrayList<CartItem> cartItems;
    private Context context;

    public CartAdapter(Context context, ArrayList<CartItem> cartItems) {
        super(context, R.layout.cart_item, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the cart item layout if it doesn't exist yet
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        // Get the CartItem object for this position
        CartItem cartItem = getItem(position);

        // Bind the views
        ImageView cartItemImage = convertView.findViewById(R.id.cartItemImage);
        TextView cartItemName = convertView.findViewById(R.id.cartItemName);
        TextView cartItemQuantity = convertView.findViewById(R.id.cartItemQuantity);
        TextView cartItemPrice = convertView.findViewById(R.id.cartItemPrice);
        Button removeButton = convertView.findViewById(R.id.cartItemRemoveButton);

        // Set product data to the views
        cartItemName.setText(cartItem.getProductName());
        cartItemQuantity.setText("Quantity: " + cartItem.getQuantity());
        // Set the price using getPriceAsFloat()
        cartItemPrice.setText("Price: " + cartItem.getPriceAsFloat() * cartItem.getQuantity() + " PHP");
        cartItemImage.setImageResource(cartItem.getImageResourceId());  // Use getImageResourceId() for image

        // Handle the "remove" button click
        removeButton.setOnClickListener(v -> {
            // Remove the item from the cart list
            cartItems.remove(position);
            // Notify the adapter that the data has changed
            notifyDataSetChanged();
        });

        return convertView;
    }
}
