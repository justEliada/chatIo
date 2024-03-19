package com.example.chatio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatio.R;
import com.example.chatio.databinding.ActivityRegisterBinding;

public class register extends AppCompatActivity {

    //Data binding; simplier way of connecting views with each other
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_register);
        setListeners();
    }


    private void setListeners() {
        binding.RegisterButtonRegisterScreen.setOnClickListener(
                v -> onBackPressed()); // doesnt work

    }

}