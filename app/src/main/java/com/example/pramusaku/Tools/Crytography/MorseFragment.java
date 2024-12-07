package com.example.pramusaku.Tools.Crytography;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pramusaku.R;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class MorseFragment extends Fragment {

    private EditText etNormalText;
    private Button btnTranslate;
    private TextView tvMorseCode;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_morse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        etNormalText = view.findViewById(R.id.etNormalText);
        btnTranslate = view.findViewById(R.id.btnTranslate);
        tvMorseCode = view.findViewById(R.id.tvMorseCode);

        // Set button click listener
        btnTranslate.setOnClickListener(v -> {
            String normalText = etNormalText.getText().toString().toUpperCase();
            if (TextUtils.isEmpty(normalText)) {
                tvMorseCode.setText("Please enter text to translate.");
                return;
            }

            // Translate to Morse code
            String morseCode = translateToMorse(normalText);
            tvMorseCode.setText(morseCode);
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
}
