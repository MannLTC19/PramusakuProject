package com.example.pramusaku.Tools.Crytography;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pramusaku.R;

public class CodeActivity extends AppCompatActivity {

    private ImageView imageView; // Top ImageView
    private TextView titleText; // Title TextView
    private LinearLayout orangeBox; // Orange box layout
    private ImageView centerImage; // Image in the orange box

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code); // Make sure the layout file name matches this

        // Initialize UI elements
        imageView = findViewById(R.id.imageView);
        titleText = findViewById(R.id.titleText);
        orangeBox = findViewById(R.id.orangeBox);
        centerImage = findViewById(R.id.centerImage);

        // Example of setting a title dynamically
        titleText.setText("Pigpen Code");

        // Example of changing the image dynamically (if needed)
        centerImage.setImageResource(R.drawable.pigpen);

        // Additional functionality can be added here
    }
}