package com.example.pramusaku.Tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.R;
import com.example.pramusaku.Tools.ScoutingAct.CampingActivity;
import com.example.pramusaku.Tools.ScoutingAct.CompassActivity;
import com.example.pramusaku.Tools.ScoutingAct.KnotsActivity;
import com.example.pramusaku.Tools.ScoutingAct.LineOfMarchActivity;

public class ScoutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting);


        LinearLayout knotsButton = findViewById(R.id.riggingButton); // Updated to "knotsButton"
        LinearLayout lineOfMarchButton = findViewById(R.id.lineOfMarchButton);
        LinearLayout campingButton = findViewById(R.id.campingButton);
        LinearLayout compassButton = findViewById(R.id.compassButton);


        knotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoutingActivity.this, KnotsActivity.class);
                startActivity(intent);
            }
        });

        lineOfMarchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoutingActivity.this, LineOfMarchActivity.class);
                startActivity(intent);
            }
        });

        campingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoutingActivity.this, CampingActivity.class);
                startActivity(intent);
            }
        });

        compassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoutingActivity.this, CompassActivity.class);
                startActivity(intent);
            }
        });
    }
}
