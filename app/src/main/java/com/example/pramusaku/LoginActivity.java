package com.example.pramusaku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername, passwordTxt;
    private Button btnLogin, btnRegJump;

    // Firebase Authentication instance
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Ensure this matches your XML layout file name

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        btnRegJump = findViewById(R.id.btnRegJump);
        txtUsername = findViewById(R.id.txtUsername);
        passwordTxt = findViewById(R.id.passwordTxt);
        btnLogin = findViewById(R.id.btnLogin);

        // Login button click listener
        btnLogin.setOnClickListener(v -> {
            String email = txtUsername.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, password);
        });

        btnRegJump.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        navigateToHomeScreen();
                    } else {
                        // Login failed
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Login failed";
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToHomeScreen() {
        // Navigate to the home screen or next activity
        Intent intent = new Intent(LoginActivity.this, HomeFragment.class);
        startActivity(intent);
        finish(); // Prevent user from returning to the login screen
    }
}
