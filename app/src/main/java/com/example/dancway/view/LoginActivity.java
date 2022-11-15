package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.UserController;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private TextView signup, forgotPassword;
    private UserController userController;
    private ImageView signGoogle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Google Sign thingie
        createRequest();


        // only to verify if the login session is already saved or not
        if (UserController.getInstance(this).autologin())
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        setContentView(R.layout.activity_login);

        userController = UserController.getInstance(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
// making Register text clickable and moving from login screen back to register activity
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        signGoogle = (ImageView) findViewById(R.id.googleSign);
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }

    @Override
    public void onClick(View view) {    //TODO: Check if needed
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        switch(view.getId()) {
            case R.id.signup:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.signIn:
                userController.login(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }
    }

