package com.example.pramusaku.Tools.ScoutingAct;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.R;

public class CampingActivity extends AppCompatActivity {

    private ImageView logoImageView;
    private TextView titleTextView;
    private TextView subtitleTextView;
    private ImageView smallTentImageView1;
    private ImageView smallTentImageView2;
    private ImageView largeTentImageView1;
    private ImageView largeTentImageView2;
    private ImageView largeTentImageView3;
    private ImageView largeTentImageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camping); // Replace with the actual name of your XML file

        // Bind views from the XML layout
        logoImageView = findViewById(R.id.logoImageView);
        titleTextView = findViewById(R.id.titleTextView);
        subtitleTextView = findViewById(R.id.subtitleTextView);
        smallTentImageView1 = findViewById(R.id.imageViewsmalltent);
        smallTentImageView2 = findViewById(R.id.imageViewsmalltent2);
        largeTentImageView1 = findViewById(R.id.imageViewlargetent);
        largeTentImageView2 = findViewById(R.id.imageViewlargetent2);
        largeTentImageView3 = findViewById(R.id.imageViewlargetent3);
        largeTentImageView4 = findViewById(R.id.imageViewlargetent4);

        // Optional: Set programmatic content if needed
        titleTextView.setText("Camping");
        subtitleTextView.setText("Set Up Your Camp!");
    }
}
