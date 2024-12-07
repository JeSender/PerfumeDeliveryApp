package com.example.perfumedeliveryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {

    private DatabaseHelper dbHelper;

    // Constructor to initialize DatabaseHelper
    public ProductDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insert product into the database
    public void insertProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Adding product details to ContentValues
        values.put(DatabaseHelper.COLUMN_NAME, product.getProductName());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, product.getProductDescription());
        values.put(DatabaseHelper.COLUMN_PRICE, product.getProductPrice());
        values.put(DatabaseHelper.COLUMN_IMAGE, product.getProductImage());  // Store image as an int (resource ID)

        // Insert the product into the database
        long newRowId = db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
        if (newRowId == -1) {
            // Handle failure in inserting product
            // You could log the error or show a Toast to notify the user
        }
        db.close();
    }

    // Fetch all products from the database
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns to retrieve
        String[] columns = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_DESCRIPTION,
                DatabaseHelper.COLUMN_PRICE,
                DatabaseHelper.COLUMN_IMAGE
        };

        // Query the database for all products
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCTS, columns, null, null, null, null, null);

        // Ensure the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve values from the cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE));

                // Add the product to the list
                productList.add(new Product(name, description, price, image));
            } while (cursor.moveToNext());  // Continue to next row

            cursor.close();  // Always close the cursor
        }

        db.close();  // Close the database
        return productList;  // Return the list of products
    }
}
