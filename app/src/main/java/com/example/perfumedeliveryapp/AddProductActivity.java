package com.example.perfumedeliveryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText productNameEditText, productDescriptionEditText, productPriceEditText;
    private ImageView productImageView;
    private Button saveProductButton, selectImageButton;
    private Uri imageUri;
    private int selectedImageResourceId = -1;  // Store the resource ID of the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize UI elements
        productNameEditText = findViewById(R.id.productNameEditText);
        productDescriptionEditText = findViewById(R.id.productDescriptionEditText);
        productPriceEditText = findViewById(R.id.productPriceEditText);
        productImageView = findViewById(R.id.productImageView);
        saveProductButton = findViewById(R.id.saveProductButton);
        selectImageButton = findViewById(R.id.selectImageButton);

        // Set OnClickListener to select an image
        selectImageButton.setOnClickListener(v -> openFileChooser());

        // Set OnClickListener to save the product
        saveProductButton.setOnClickListener(v -> saveProduct());
    }

    // Open file chooser to select an image from gallery
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle the result of the image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                // Get the image from URI and set it to ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                productImageView.setImageBitmap(bitmap);
                // Use a placeholder image resource ID here
                selectedImageResourceId = R.drawable.chanel;  // Replace with actual resource if needed
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AddProductActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Save product details to the database
    private void saveProduct() {
        String productName = productNameEditText.getText().toString();
        String productDescription = productDescriptionEditText.getText().toString();
        String productPrice = productPriceEditText.getText().toString();

        if (productName.isEmpty() || productDescription.isEmpty() || productPrice.isEmpty() || selectedImageResourceId == -1) {
            Toast.makeText(AddProductActivity.this, "Please fill in all details and select an image", Toast.LENGTH_SHORT).show();
        } else {
            // Save the product to the database
            ProductDatabase productDatabase = new ProductDatabase(AddProductActivity.this);
            Product product = new Product(productName, productDescription, productPrice, selectedImageResourceId);
            productDatabase.insertProduct(product);

            Toast.makeText(AddProductActivity.this, "Product Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
