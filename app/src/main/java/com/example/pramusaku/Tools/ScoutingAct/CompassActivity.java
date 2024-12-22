package com.example.pramusaku.Tools.ScoutingAct;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.MainActivity;
import com.example.pramusaku.R;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] gravity;
    private float[] geomagnetic;
    private ImageView compassImageView;
    private float azimuth = 0f;
    private float currentAzimuth = 0f;
    private TextView directionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassImageView = findViewById(R.id.compassImageView);
        directionTextView = findViewById(R.id.directionTextView);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnMain = findViewById(R.id.btnMain);

        btnBack.setOnClickListener(v -> finish());

        btnMain.setOnClickListener(v -> {
            Intent intent = new Intent(CompassActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values.clone();
        }

        if (gravity != null && geomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360) % 360;

                animateCompass();
                updateDirectionText();
            }
        }
    }

    private void animateCompass() {
        currentAzimuth = azimuth;
        compassImageView.setRotation(currentAzimuth);
    }

    private void updateDirectionText() {
        String direction = getDirectionFromAzimuth(azimuth);
        directionTextView.setText(direction);
    }

    private String getDirectionFromAzimuth(float azimuth) {
        if (azimuth >= 337.5 || azimuth < 22.5) {
            return "North";
        } else if (azimuth >= 22.5 && azimuth < 67.5) {
            return "North-East";
        } else if (azimuth >= 67.5 && azimuth < 112.5) {
            return "East";
        } else if (azimuth >= 112.5 && azimuth < 157.5) {
            return "South-East";
        } else if (azimuth >= 157.5 && azimuth < 202.5) {
            return "South";
        } else if (azimuth >= 202.5 && azimuth < 247.5) {
            return "South-West";
        } else if (azimuth >= 247.5 && azimuth < 292.5) {
            return "West";
        } else if (azimuth >= 292.5 && azimuth < 337.5) {
            return "North-West";
        }
        return "Unknown";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
