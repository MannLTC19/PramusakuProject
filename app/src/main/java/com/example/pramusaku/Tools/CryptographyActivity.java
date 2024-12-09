package com.example.pramusaku.Tools;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import com.example.pramusaku.R;
import com.example.pramusaku.Tools.Crytography.CodeActivity;
import com.example.pramusaku.Tools.Crytography.FootprintActivity;
import com.example.pramusaku.Tools.Crytography.MorseActivity;
import com.example.pramusaku.Tools.Crytography.SemaphoreActivity;

public class CryptographyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptography);

        // Set up button click listeners
        Button semaphoreButton = findViewById(R.id.semaphoreButton);
        semaphoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcode = new Intent(CryptographyActivity.this, SemaphoreActivity.class);
                startActivity(intentcode);
            }

        });

        Button morseButton = findViewById(R.id.morseButton);
        morseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcode = new Intent(CryptographyActivity.this, MorseActivity.class);
                startActivity(intentcode);
            }
        });

        Button codeButton = findViewById(R.id.codeButton);
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcode = new Intent(CryptographyActivity.this, CodeActivity.class);
                startActivity(intentcode);
            }
        });

        Button footprintButton = findViewById(R.id.footprintButton);
        footprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcode = new Intent(CryptographyActivity.this, FootprintActivity.class);
                startActivity(intentcode);
            }
        });
    }
}