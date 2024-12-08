package com.example.pramusaku.Tools;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import com.example.pramusaku.R;
import com.example.pramusaku.Tools.Crytography.CodeFragment;
import com.example.pramusaku.Tools.Crytography.FootprintFragment;
import com.example.pramusaku.Tools.Crytography.MorseFragment;
import com.example.pramusaku.Tools.Crytography.SemaphoreFragment;

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
                Intent intentsema = new Intent(CryptographyActivity.this, SemaphoreFragment.class);
            }

        });

        Button morseButton = findViewById(R.id.morseButton);
        morseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentmorse = new Intent(CryptographyActivity.this, MorseFragment.class);
            }
        });

        Button codeButton = findViewById(R.id.codeButton);
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcode = new Intent(CryptographyActivity.this, CodeFragment.class);
            }
        });

        Button footprintButton = findViewById(R.id.footprintButton);
        footprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcode = new Intent(CryptographyActivity.this, FootprintFragment.class);
            }
        });
    }
}