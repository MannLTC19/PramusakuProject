package com.example.pramusaku.Tools;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.R;

public class ScoutCodeActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleText;
    private TextView triSatyaTitle;
    private TextView triSatyaContent;
    private TextView dasaDarmaTitle;
    private TextView dasaDarmaContent;
    private TextView dwiDarmaTitle;
    private TextView dwiDarmaContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_code); // Replace with the actual name of your XML file

        // Bind views from the XML layout
        imageView = findViewById(R.id.imageView);
        titleText = findViewById(R.id.titleText);
        triSatyaTitle = findViewById(R.id.triSatyaTitle); // Add these IDs to XML if not already present
        triSatyaContent = findViewById(R.id.triSatyaContent);
        dasaDarmaTitle = findViewById(R.id.dasaDarmaTitle);
        dasaDarmaContent = findViewById(R.id.dasaDarmaContent);
        dwiDarmaTitle = findViewById(R.id.dwiDarmaTitle);
        dwiDarmaContent = findViewById(R.id.dwiDarmaContent);
    }
}