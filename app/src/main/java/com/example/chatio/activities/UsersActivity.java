package com.example.chatio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;

import com.example.chatio.R;
import com.example.chatio.adapters.UsersAdapters;
import com.example.chatio.databinding.ActivityUsersBinding;
import com.example.chatio.models.User;
import com.example.chatio.utilitis.Constants;
import com.example.chatio.utilitis.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }

    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No user is availabe"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);

    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

 /*   private void setListeners(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true *//* enabled by default *//*) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        binding.imageBack.setOnClickListener(v -> callback.handleOnBackPressed());
    }
*/

    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if(task.isSuccessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();

                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if(currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.username = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.phoneNumber = queryDocumentSnapshot.getString(Constants.KEY_PHONE);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            users.add(user);
                        }
                        if(users.size() > 0){
                            UsersAdapters usersAdapters = new UsersAdapters(users);
                            binding.usersRecyclerView.setAdapter(usersAdapters);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        }else{
                            showErrorMessage();
                        }
                    }else{
                        Log.e("UsersActivity", "Error fetching users", task.getException()); // Log the error
                        showErrorMessage();
                    }

                });
    }

    private void loading(Boolean isLoading){
        if(isLoading)
        {
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);

        }
    }
}