package com.example.perfumedeliveryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.order_item, parent, false);
        }

        Order order = orderList.get(position);

        // Initialize UI elements
        TextView recipientDetailsTextView = convertView.findViewById(R.id.recipientDetailsTextView);
        TextView productDetailsTextView = convertView.findViewById(R.id.productNamesTextView);
        TextView totalPriceTextView = convertView.findViewById(R.id.totalPriceTextView);

        // Display the recipient details
        String recipientDetails = "Recipient: " + order.getRecipientName() + "\n" +
                "Address: " + order.getRecipientAddress() + "\n" +
                "Phone: " + order.getRecipientPhone();
        recipientDetailsTextView.setText(recipientDetails);

        // Display product details
        StringBuilder productDetails = new StringBuilder();
        for (int i = 0; i < order.getProductNames().size(); i++) {
            productDetails.append("Product: ").append(order.getProductNames().get(i))
                    .append(" x").append(order.getProductQuantities().get(i))
                    .append(" - PHP ").append(order.getProductPrices().get(i))
                    .append("\n");
        }
        productDetailsTextView.setText(productDetails.toString());

        // Display total price
        totalPriceTextView.setText("Total: PHP " + order.getTotalPrice());

        return convertView;
    }
}
