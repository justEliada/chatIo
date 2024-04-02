package com.example.chatio.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatio.databinding.ActivityLoginBinding;
import com.example.chatio.utilitis.Constants;
import com.example.chatio.utilitis.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        // if user is singed in it doesn't need to sign in again
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){
        binding.RegisterButton.setOnClickListener(v->
                startActivity(new Intent(getApplicationContext(), Register.class)));
        binding.LoginButton.setOnClickListener(v->{
            if(isValidCredentials()){
                login();
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void login(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_NAME, binding.UserName.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.Password.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful() && task.getResult() != null &&
                   task.getResult().getDocuments().size()>0){
                       DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                       preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                       preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                   }
                   else{
                       showToast("Unable to Login");
                   }
                });
    }

    private Boolean isValidCredentials()
    {
        if(binding.UserName.getText().toString().trim().isEmpty()){
        showToast("Enter Username");
        return false;

        }
        else if(binding.Password.getText().toString().trim().isEmpty()){
        showToast("Enter Password");
        return false;

        }
        else {
            return true;
        }
    }
}