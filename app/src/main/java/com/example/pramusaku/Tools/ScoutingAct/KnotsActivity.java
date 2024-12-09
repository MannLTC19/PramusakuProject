package com.example.pramusaku.Tools.ScoutingAct;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.R;

public class KnotsActivity extends AppCompatActivity {

    private ImageView simpulJangkarImageView, ikatanCanggahImageView,
            simpulLasoImageView, simpulPangkalImageView,
            simpulKembarImageView, simpulMatiImageView, simpulHidupImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knots);

        simpulJangkarImageView = findViewById(R.id.imageViewSimpulJangkar);
        ikatanCanggahImageView = findViewById(R.id.imageViewIkatanCanggah);
        simpulLasoImageView = findViewById(R.id.imageViewSimpulLaso);
        simpulPangkalImageView = findViewById(R.id.imageViewSimpulPangkal);
        simpulKembarImageView = findViewById(R.id.imageViewSimpulKembar);
        simpulMatiImageView = findViewById(R.id.imageViewSimpulMati);
        simpulHidupImageView = findViewById(R.id.imageViewSimpulHidup);

        // Set click listeners to handle interactivity
        setupClickListeners();
    }

    private void setupClickListeners() {
        simpulJangkarImageView.setOnClickListener(v ->
                showToast("Simpul Jangkar clicked!")
        );

        ikatanCanggahImageView.setOnClickListener(v ->
                showToast("Ikatan Canggah clicked!")
        );

        simpulLasoImageView.setOnClickListener(v ->
                showToast("Simpul Laso clicked!")
        );

        simpulPangkalImageView.setOnClickListener(v ->
                showToast("Simpul Pangkal clicked!")
        );

        simpulKembarImageView.setOnClickListener(v ->
                showToast("Simpul Kembar clicked!")
        );

        simpulMatiImageView.setOnClickListener(v ->
                showToast("Simpul Mati clicked!")
        );

        simpulHidupImageView.setOnClickListener(v ->
                showToast("Simpul Hidup clicked!")
        );
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}