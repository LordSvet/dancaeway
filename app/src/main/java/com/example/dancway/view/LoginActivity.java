package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.UserController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is login activity
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth auth;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private TextView signup, forgotPassword;
    private UserController userController;
    private Button signGoogle;

    Animation button_scale_up, button_scale_down;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * Google sign in
         */
        auth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // only to verify if the login session is already saved or not
        if (UserController.getInstance(this).autologin())
        {
            startActivity(new Intent(LoginActivity.this, ModeSelectionActivity.class));
        }

        userController = UserController.getInstance(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        // making Register text clickable and moving from login screen back to register activity
        signup = (TextView) findViewById(R.id.signup);



        /**
        /**
         * Implement button animation


        button_scale_up = AnimationUtils.loadAnimation(this, R.anim.button_scale_up);
        button_scale_down = AnimationUtils.loadAnimation(this, R.anim.button_scale_down);

        signIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    signIn.startAnimation(button_scale_down);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP)
                    signIn.startAnimation(button_scale_up);

                return true;
            }
            }
        );

         */

        /**
         * When sign up button is clicked, RegisterActivity is called
         */
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

        /**
         * When forgot password is clicked, it goes to ChangePassword Activity
         */
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });


        /**
         * Google Sign in logic
         * When button is clicked calls the signInGoogle method
         */
        signGoogle = (Button) findViewById(R.id.login_with_google);
        signGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });
    }

    /**
     * This method opens the google Sign in page
     */
    private void signInGoogle() {

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    /**
     * If the task of Google Sign in is succesfful, it jumps to the main activity,
     * otherwise it shows the error
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                MainAct();
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * After google sign in, returns to the main Screen
     */
    private void MainAct() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    /**
     * Implements the Login logic.
     * Checks if the field, like email and password are empty.
     * Checks if password given is less than 8 characters, which are the min bassed on register password pattern.
     * @param view
     * @returns to the method if the requirements are not met.
     */
    @Override
    public void onClick(View view) {    // TODO: Check if needed
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

        if(password.length() < 8) {
            editTextPassword.setError("Min password length is 8 characters!");
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