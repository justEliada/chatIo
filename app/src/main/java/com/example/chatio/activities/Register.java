package com.example.chatio.activities;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.example.chatio.utilitis.Constants;
import com.example.chatio.databinding.ActivityRegisterBinding;
import com.example.chatio.utilitis.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    //Data binding; simplier way of connecting views with each other
    private ActivityRegisterBinding binding;
    private PreferenceManager preferenceManager;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Correct way to set content view using binding
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }


    private void setListeners() {
        binding.RegisterButtonRegisterScreen.setOnClickListener(v ->{
            if(isValidRegisterDetails()) {
                register();}
        });

        binding.layoutImage.setOnClickListener(v ->{
           Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           pickImage.launch(intent);
        });
    }

    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if(result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imageProfile.setImageBitmap(bitmap);
                            binding.textAddImage.setVisibility(View.GONE);
                            encodedImage = encodeImage(bitmap);
                        }catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void register(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, binding.UserName.getText().toString()); // Adjusted line
        user.put(Constants.KEY_PHONE, binding.PhoneNumberId.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.PasswordId.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                  preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                  preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                  preferenceManager.putString(Constants.KEY_NAME, binding.UserName.getText().toString());
                  preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(exception -> {
                });
    }


    // Check if credentials are true when registering
    private Boolean isValidRegisterDetails(){
        {
            if(encodedImage == null){
            showToast("Select Profile Image");
            return false;
        }
            if(binding.UserName.getText().toString().trim().isEmpty()) {
            showToast("Enter Name");
            return false;
        }
            else if(binding.PhoneNumberId.getText().toString().trim().isEmpty()) {
            showToast("Enter Phone Number");
            return false;
        }
            else if(!Patterns.PHONE.matcher(binding.PhoneNumberId.getText().toString()).matches()) {
            showToast("Enter Valid Phone Number");
            return false;
        }
            else if(binding.PasswordId.getText().toString().trim().isEmpty()) {
            showToast("Enter Password");
            return false;
        }
            else if(binding.ConfirmationPassword.getText().toString().trim().isEmpty()) {
            showToast("Confirm Password");
            return false;
        }
            else if(!binding.PasswordId.getText().toString().equals(binding.ConfirmationPassword.getText().toString())) {
                showToast("Passwords don't match");
                return false;

        }
            else{
                return true;
            }
        }
    }
}