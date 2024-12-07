package com.example.perfumedeliveryapp;

public class Product {
    private String productName;
    private String productDescription;
    private String productPrice;
    private int productImage; // Changed to int

    // Constructor
    public Product(String productName, String productDescription, String productPrice, int productImage) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
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

    public int getProductImage() {
        return productImage;
    }
}
