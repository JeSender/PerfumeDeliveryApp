package com.example.perfumedeliveryapp;

import java.util.List;

public class Order {
    private List<String> productNames;
    private List<String> productPrices;
    private List<Integer> productQuantities;
    private String recipientName;
    private String recipientAddress;
    private String recipientPhone;
    private String totalPrice;

    public Order(List<String> productNames, List<String> productPrices, List<Integer> productQuantities,
                 String recipientName, String recipientAddress, String recipientPhone, String totalPrice) {
        this.productNames = productNames;
        this.productPrices = productPrices;
        this.productQuantities = productQuantities;
        this.recipientName = recipientName;
        this.recipientAddress = recipientAddress;
        this.recipientPhone = recipientPhone;
        this.totalPrice = totalPrice;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public List<String> getProductPrices() {
        return productPrices;
    }

    public List<Integer> getProductQuantities() {
        return productQuantities;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
