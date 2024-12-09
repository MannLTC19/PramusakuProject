package com.example.pramusaku;

import static android.app.Activity.RESULT_OK;
import static java.util.Base64.*;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static final int CAPTURE_IMAGE_CODE = 100;
    private static final int CHOOSE_IMAGE = 102;
    private static final int CAMERA_PERMISSION_CODE = 101;
    private FirebaseFirestore firestore;
    private Uri imageUri;

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
        fetchUserdata();
        updateBtn.setOnClickListener((v ->{
            updateBtnClick();
        }));
        profilePicture.setOnClickListener(v -> showImageOptions());
        logoutBtn.setOnClickListener(v -> logoutUser());
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        return view;
    }

    private void showImageOptions(){
        String[] options = {"Camera", "Gallery"};
        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Choose Profile Picture").setItems(options, (dialog, which)->{
                    if(which == 0){
                        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
                        }else {
                            openCamera();
                        }
                    }else {
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CHOOSE_IMAGE);
                    }
                }).show();
    }

    private void openCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getActivity().getPackageManager())!=null){
            try {
                File pictureFile = createPictureFile();
                if(pictureFile!=null){
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE_CODE);
                }
            }catch (IOException ex){
                Toast.makeText(getContext(), "Error Creating File", Toast.LENGTH_SHORT).show();
            }
        }
    }

   private File createPictureFile()throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(null);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageUri = FileProvider.getUriForFile(getContext(), getActivity().getApplicationContext().getPackageName() + ".fileprovider", image);
        return image;
   }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data!=null){
            Uri selectedImageUri = data.getData();
            profilePicture.setImageURI(selectedImageUri);
            imageUri = selectedImageUri;
        } else if (requestCode == CAPTURE_IMAGE_CODE && resultCode == RESULT_OK) {
            profilePicture.setImageURI(imageUri);
        }else {
            Toast.makeText(getContext(), "Fail to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBtnClick(){
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();

        FirebaseUser user = auth.getCurrentUser();
        if(user!= null){
            String userId = user.getUid();
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", username);
            userData.put("email", email);

            if(imageUri!=null){
                try {
                    byte[]imageData = getImageData(imageUri);
                    String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
                    userData.put("profileImage", base64Image);
                }catch (IOException e){
                    Toast.makeText(getContext(), "Error processing" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            firestore.collection("users").document(userId)
                    .set(userData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Profile saved", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error saving profile: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }else {
            Toast.makeText(getContext(), "No authenticated user", Toast.LENGTH_SHORT).show();
        }
    }

    void fetchUserdata(){
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            String userId = currentUser.getUid();

            DocumentReference documentReference = firestore.collection("users").document(userId);
            documentReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    if(task.getResult()!=null && task.getResult().exists()){
                        String username = task.getResult().getString("username");
                        String email = task.getResult().getString("email");
                        String profileImageBase64 = task.getResult().getString("profileImage");
                        txtUsername.setText(username);
                        txtEmail.setText(email);
                        if(profileImageBase64!=null){
                            byte[]decodeString = Base64.decode(profileImageBase64, Base64.DEFAULT);
                            Bitmap decodeByte = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
                            profilePicture.setImageBitmap(decodeByte);
                        }
                    }else {
                        Toast.makeText(getContext(),"User doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Error handling user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ProfileFragment", "Error loading user data", task.getException());
                }
            });
        }else {
            Toast.makeText(getContext(), "No authenticated user", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[]getImageData(Uri imageUri)throws IOException{
        InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

        while (byteArrayOutputStream.toByteArray().length > 1024*1024){
            byteArrayOutputStream.reset();
            quality -=10;
            if (quality<0){
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream);
        }

        return byteArrayOutputStream.toByteArray();
    }

   private void logoutUser(){
        auth.signOut();
        Toast.makeText(getContext(), "Logged out succesfully", Toast.LENGTH_SHORT).show();

        //Redirect to login page
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }


}


