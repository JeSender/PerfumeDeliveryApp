package com.example.perfumedeliveryapp;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private String productName;
    private String productDescription;
    private String productPrice;
    private int quantity;
    private int imageResourceId;

    // Constructor
    public CartItem(String productName, String productDescription, String productPrice, int imageResourceId) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.quantity = 1; // Initialize quantity to 1 when added to cart
        this.imageResourceId = imageResourceId;
    }

    // Getters
    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    // Method to increase the quantity of the item
    public void increaseQuantity() {
        quantity++;
    }

    // Method to set quantity explicitly if needed
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to get price as float (strip the currency part)
    public float getPriceAsFloat() {
        try {
            // Remove the "PHP" currency and any extra spaces
            return Float.parseFloat(productPrice.replace(" PHP", "").trim());
        } catch (NumberFormatException e) {
            // If there's an error in the price format, return 0.0f
            return 0.0f;
        }
    }

    // Parcelable implementation
    protected CartItem(Parcel in) {
        productName = in.readString();
        productDescription = in.readString();
        productPrice = in.readString();
        quantity = in.readInt();
        imageResourceId = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productDescription);
        dest.writeString(productPrice);
        dest.writeInt(quantity);
        dest.writeInt(imageResourceId);
    }
}
