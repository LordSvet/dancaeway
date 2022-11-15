package com.example.dancway.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dancway.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText enterEmail;
    private Button sendCode;
    private TextView signUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        enterEmail = (EditText) findViewById(R.id.emailForgot);
        sendCode = (Button) findViewById(R.id.sendCode);
        signUp = (TextView) findViewById(R.id.registerForgot);
        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });




    }

    public void resetPassword() {
        String email = enterEmail.getText().toString().trim();

        if(email.isEmpty()) {
            enterEmail.setError("Email is required");
            enterEmail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ChangePasswordActivity.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}