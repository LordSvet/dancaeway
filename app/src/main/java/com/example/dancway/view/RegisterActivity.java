package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.UserController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register_tittle, registerUser;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextRepeatPassword;
    private UserController userController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userController = new UserController(this);

        TextView banner = (TextView) findViewById(R.id.register_banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextRepeatPassword = (EditText) findViewById(R.id.repeat_password);


    }

    @Override
    public void onClick(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeat_password = editTextRepeatPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;

        } else if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

            switch (view.getId()) {
                case R.id.register_banner:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.registerUser:
                    userController.register(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextRepeatPassword.getText().toString(),  editTextFullName.getText().toString());
                    break;

            }

        }


    }
