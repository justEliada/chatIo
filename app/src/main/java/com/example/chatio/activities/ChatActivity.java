package com.example.chatio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chatio.databinding.ActivityChataCtivityBinding;
import com.example.chatio.models.User;
import com.example.chatio.utilitis.Constants;

public class ChatActivity extends AppCompatActivity {

    private ActivityChataCtivityBinding binding;
    private User receiverUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChataCtivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();

    }

    private void loadReceiverDetails() {
        receiverUser =(User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(receiverUser.username);
    }
    private void setListeners() {
        binding.imageBack.setOnClickListener(v->onBackPressed());

    }
}