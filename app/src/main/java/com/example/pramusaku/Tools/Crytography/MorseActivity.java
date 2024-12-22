package com.example.pramusaku.Tools.Crytography;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pramusaku.MainActivity;
import com.example.pramusaku.R;
import com.example.pramusaku.Tools.ScoutingAct.CompassActivity;

import java.util.HashMap;

public class MorseActivity extends AppCompatActivity {

    private EditText etNormalText;
    private Button btnTranslate;
    private TextView tvMorseCode;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MorsePrefs";
    private static final String LAST_TRANSLATION_KEY = "lastTranslation";

    private static final HashMap<Character, String> morseCodeMap = new HashMap<>();

    static {
        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put(' ', "/"); // Space between words
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

        // Initialize UI components
        etNormalText = findViewById(R.id.etNormalText);
        btnTranslate = findViewById(R.id.btnTranslate);
        tvMorseCode = findViewById(R.id.tvMorseCode);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnMain = findViewById(R.id.btnMain);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MorseActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lastTranslation = sharedPreferences.getString(LAST_TRANSLATION_KEY, "");
        if (!lastTranslation.isEmpty()) {
            tvMorseCode.setText(lastTranslation);
        }


        btnTranslate.setOnClickListener(v -> {
            String normalText = etNormalText.getText().toString().toUpperCase();
            if (TextUtils.isEmpty(normalText)) {
                tvMorseCode.setText("Please enter text to translate.");
                return;
            }
            String morseCode = translateToMorse(normalText);
            tvMorseCode.setText(morseCode);
            saveTranslationToSharedPreferences(morseCode);
        });
    }

    private String translateToMorse(String text) {
        StringBuilder morseBuilder = new StringBuilder();
        for (char character : text.toCharArray()) {
            String morseChar = morseCodeMap.get(character);
            if (morseChar != null) {
                morseBuilder.append(morseChar).append(" ");
            } else {
                morseBuilder.append("? "); // Unknown characters
            }
        }
        return morseBuilder.toString().trim();
    }

    private void saveTranslationToSharedPreferences(String translation) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_TRANSLATION_KEY, translation);
        editor.apply();
    }
}

