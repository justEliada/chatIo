package com.example.chatio.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.example.chatio.databinding.ActivityLoginBinding;

public class login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){
        binding.RegisterButton.setOnClickListener(v->
                startActivity(new Intent(getApplicationContext(), register.class)));
    }

























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