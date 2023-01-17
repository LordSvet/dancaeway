package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.UserController;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Password pattern based on REGEX. Accepts min 8 characters,
     * at least one number, one uppercase, and one special character,
     */
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
    private TextView register_tittle, registerUser, login_button;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextRepeatPassword;
    private UserController userController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // only a singular instance of userController class gets created
        userController = UserController.getInstance(this);

        TextView banner = (TextView) findViewById(R.id.register_banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextRepeatPassword = (EditText) findViewById(R.id.repeat_password);

        // making Log in clickable and moving from register screen to login screen
        login_button = (TextView) findViewById(R.id.login_button);

        /**
         * When Login button is clicked, opens LoginActivity
         */
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
    }

    /**
     * @param view an instance of View is passed
     * Implements the Register logic.
     * Checks if fields like, Name, Email, and Password are empty.
     * Checks for email and password if they match the patterns specified.
     * Password pattern: 8 minimum characters, at least one uppercase, one number, and one special character
     * @return to the method, if they're empty, or they don't match the patterns
     */
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

        } if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        } if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            editTextPassword.setError("Use 8 chars, at least one uppercase, number and special chars");
            editTextPassword.requestFocus();
            return;
        }
        else if (repeat_password.isEmpty()) {
            editTextRepeatPassword.setError("Your password doesn't match ");
            editTextRepeatPassword.requestFocus();
            return;
        }

        if (view.getId() == R.id.registerUser) {
            userController.register(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextRepeatPassword.getText().toString(), editTextFullName.getText().toString());
            startActivity(new Intent(this, ModeSelectionActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
        }
    }
}


