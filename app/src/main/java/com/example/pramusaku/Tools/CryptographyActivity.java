package com.example.pramusaku.Tools;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import com.example.pramusaku.R;

public class CryptographyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up button click listeners
        Button semaphoreButton = findViewById(R.id.semaphoreButton);
        semaphoreButton.setOnClickListener(v -> {
            // Add functionality here
        });

        Button morseButton = findViewById(R.id.morseButton);
        morseButton.setOnClickListener(v -> {
            // Add functionality here
        });

        Button codeButton = findViewById(R.id.codeButton);
        codeButton.setOnClickListener(v -> {
            // Add functionality here
        });

        Button footprintButton = findViewById(R.id.footprintButton);
        footprintButton.setOnClickListener(v -> {
            // Add functionality here
        });
    }
}