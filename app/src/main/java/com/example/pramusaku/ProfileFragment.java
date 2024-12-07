package com.example.pramusaku;

import static java.util.Base64.*;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.security.Permission;
import android.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    ImageView profilePicture;
    EditText txtUsername;
    EditText txtEmail;
    Button updateBtn;
    Button logoutBtn;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profilePicture);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        updateBtn = view.findViewById(R.id.updateBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        fetchUserdata();
        updateBtn.setOnClickListener((v ->{
            updateBtnClick();
        }));
        logoutBtn.setOnClickListener(v -> logoutUser());
        profilePicture.setOnClickListener(v -> cameraPermission());
        auth = FirebaseAuth.getInstance();

        return view;
    }

    private void cameraPermission(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }else {
            openCamera();
        }
    }

    private void openCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(requireActivity().getPackageManager())!=null){
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }else {
            Toast.makeText(getContext(), "Camera is not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == requireActivity().RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            profilePicture.setImageBitmap(imageBitmap);
            uploadImageToFirebase(imageBitmap);
        }
    }

    private void uploadImageToFirebase(Bitmap imageBitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

        while (baos.toByteArray().length > 1024*1024){
            baos.reset();
            quality -= 10;
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }

        byte[] compressImageData = baos.toByteArray();
        String base64Image = Base64.encodeToString(compressImageData,Base64.DEFAULT);
        // Store in Firestore
        String userId = auth.getCurrentUser().getUid();
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("profilePicture", base64Image);

        firestore.collection("users").document(userId)
                .set(profileData)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void fetchUserdata(){
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            String userId = currentUser.getUid();

            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String username = snapshot.child("username").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);

                        txtUsername.setText(username!=null ? username: "");
                        txtEmail.setText(email != null ? email: "");
                    }else {
                        Toast.makeText(getContext(), "User fata not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Fail to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getContext(), "No authenticated user", Toast.LENGTH_SHORT).show();
        }
    }
    void updateBtnClick(){
        String newUsername = txtUsername.getText().toString();
        String newEmail = txtEmail.getText().toString();
        if(newUsername.isEmpty() || newUsername.length()<3){
            txtUsername.setError("Username length should be at least 3 characters");
            return;
        }


        if (newEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            txtEmail.setError("Enter a valid email");
            return;
    }
    updateUserData(newUsername, newEmail);
    }

    void updateUserData(String newUsername, String newEmail){
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser !=null){
            String userId = currentUser.getUid();

            currentUser.updateEmail(newEmail).addOnCompleteListener(task -> {
               if(task.isSuccessful()){
                   databaseReference.child(userId).child("username").setValue(newUsername);
                   databaseReference.child(userId).child("email").setValue(newEmail)
                           .addOnCompleteListener(dbTask -> {
                               if (dbTask.isSuccessful()){
                                   Toast.makeText(getContext(), "Profile update succesfully", Toast.LENGTH_SHORT).show();
                               }else {
                                   Toast.makeText(getContext(), "Failed to update profile in database: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           });
               }else {
                   Toast.makeText(getContext(), "Failed to update email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }
            });
        }else {
            Toast.makeText(getContext(), "No authenticated user", Toast.LENGTH_SHORT).show();
        }
    }

    void logoutUser(){
        auth.signOut();
        Toast.makeText(getContext(), "Logged out succesfully", Toast.LENGTH_SHORT).show();

        //Redirect to login page
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }


}


