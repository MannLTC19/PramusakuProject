package com.example.pramusaku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnRegister;

    // Firebase Authentication and Firestore instances
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Ensure this points to the correct layout XML file

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        txtEmail = findViewById(R.id.txtEmail);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtpassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Register button click listener
        btnRegister.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                Toast.makeText(RegisterActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(email, password, username);
        });
    }

    private void registerUser(String email, String password, String username) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            createUserDocument(user, username);
                        }
                    } else {
                        // Registration failed
                        String errorMessage;
                        if (task.getException() instanceof FirebaseAuthException) {
                            errorMessage = ((FirebaseAuthException) task.getException()).getMessage();
                        } else {
                            errorMessage = "Registration failed";
                        }
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Registration failed: " + errorMessage);
                    }
                });
    }

    private void createUserDocument(FirebaseUser user, String username) {
        String userId = user.getUid();
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("username", username);
        userData.put("profileImage", ""); // Placeholder for profile image

        firestore.collection("users")
                .document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "User document created successfully");
                    Toast.makeText(RegisterActivity.this, "User document created successfully", Toast.LENGTH_SHORT).show();
                    navigateToHomeScreen();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error creating user document", e);
                    Toast.makeText(RegisterActivity.this, "Error creating user document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToHomeScreen() {
        // Navigate to the home screen or next activity
        Intent intent = new Intent(RegisterActivity.this, HomeFragment.class);
        startActivity(intent);
        finish(); // Prevent user from returning to the registration screen
    }
}
