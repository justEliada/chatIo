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

    // testing the connection is working
/*    private void addDataToFirestore(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> data = new HashMap<>();
        data.put("first_name", "Eliada");
        data.put("last_name", "Ballazhi");
        database.collection("users")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(),"Data Inserted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }*/
























    /*//declaring the buttons
    private Button loginButton;
  *//*  private Button registerButton;*//*
    EditText usernameInput, passwordInput;
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Casting
        registerButton = (Button) findViewById(R.id.RegisterButton);
        loginButton = (Button) findViewById(R.id.LoginButton);
        //Make text Clickable

        TextView textView = findViewById(R.id.PasswordResetText);
        tx = (TextView) findViewById(R.id.PasswordResetText);
        loginButton = (Button) findViewById(R.id.LoginButton);
        String text = "Reset Email Address";
        SpannableString ss = new SpannableString(text);

        // getting login inputs
        usernameInput=(EditText) findViewById(R.id.UserName);
        passwordInput=(EditText) findViewById(R.id.Password);

        ClickableSpan clickableSpan = new ClickableSpan(){
            @Override
            public void onClick(View view) {
                openAuthenticaiton();
            }


            // TO-DO RESUOURSES COLOR.
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 0, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        // Set an OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });

        // Set an OnClickListener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openRegisterPage();
            }
        });


    }

  *//*  private void setListeners() {
        binding.RegisterButton.setOnClickListener(v ->
                startActivity(new Intent()));
    }*//*
    private void openRegisterPage() {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    private void openHomePage() {
        Intent intent = new Intent(this, inbox.class);
        startActivity(intent);
    }

    private void openAuthenticaiton() {
        Intent intent = new Intent(this, authentication.class);
        startActivity(intent);
    }*/


}