package com.example.pramusaku.Tools;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.R;

public class NationalDataActivity extends AppCompatActivity {

    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView pancasilaTitleTextView;
    private TextView pancasilaContentTextView;
    private TextView uud1945TitleTextView;
    private TextView uud1945ContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_national_data); // Replace with the actual name of your XML file

        // Bind views from the XML layout
        iconImageView = findViewById(R.id.iconImageView); // Assuming you add an id to the ImageView
        titleTextView = findViewById(R.id.NationalDataTitle);
        pancasilaTitleTextView = findViewById(R.id.pancasila);
        pancasilaContentTextView = findViewById(R.id.pancasilaContent); // Add this id in XML for content
        uud1945TitleTextView = findViewById(R.id.uud_1945);
        uud1945ContentTextView = findViewById(R.id.uud1945Content);
    }
}