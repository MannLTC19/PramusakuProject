package com.example.pramusaku.Tools.ScoutingAct;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.R;

public class LineOfMarchActivity extends AppCompatActivity {

    // Declare UI components
    private ImageView logoImageView;
    private TextView titleTextView;

    private ImageView berderetImageView;
    private TextView berderetTextView;

    private ImageView angkareImageView;
    private TextView angkareTextView;

    private ImageView lingkaranBesarImageView;
    private TextView lingkaranBesarTextView;

    private ImageView lingkaranKecilImageView;
    private TextView lingkaranKecilTextView;

    private ImageView koloneTerbukaImageView;
    private TextView koloneTerbukaTextView;

    private ImageView koloneTertutupImageView;
    private TextView koloneTertutupTextView;

    private ImageView rodaImageView;
    private TextView rodaTextView;

    private ImageView anakPanahImageView;
    private TextView anakPanahTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_of_march);

        // Initialize UI components
        logoImageView = findViewById(R.id.logoImageView);
        titleTextView = findViewById(R.id.titleTextView);

        berderetImageView = findViewById(R.id.berderetImageView);
        berderetTextView = findViewById(R.id.berderetTextView);

        angkareImageView = findViewById(R.id.angkareImageView);
        angkareTextView = findViewById(R.id.angkareTextView);

        lingkaranBesarImageView = findViewById(R.id.lingkaranBesarImageView);
        lingkaranBesarTextView = findViewById(R.id.lingkaranBesarTextView);

        lingkaranKecilImageView = findViewById(R.id.lingkaranKecilImageView);
        lingkaranKecilTextView = findViewById(R.id.lingkaranKecilTextView);

        koloneTerbukaImageView = findViewById(R.id.koloneTerbukaImageView);
        koloneTerbukaTextView = findViewById(R.id.koloneTerbukaTextView);

        koloneTertutupImageView = findViewById(R.id.koloneTertutupImageView);
        koloneTertutupTextView = findViewById(R.id.koloneTertutupTextView);

        rodaImageView = findViewById(R.id.rodaImageView);
        rodaTextView = findViewById(R.id.rodaTextView);

        anakPanahImageView = findViewById(R.id.anakPanahImageView);
        anakPanahTextView = findViewById(R.id.anakPanahTextView);

        // Set title text (you can make changes here if needed)
        titleTextView.setText("Line of March");

        // You can set click listeners or any interaction logic here, if needed
        berderetImageView.setOnClickListener(v -> {
            // Handle click logic for Berderet
            showText("Berderet clicked!");
        });

        angkareImageView.setOnClickListener(v -> {
            // Handle click logic for Angkare
            showText("Angkare clicked!");
        });
    }

    private void showText(String message) {
        // Example method to show a message in the console or Toast
        System.out.println(message);
    }
}