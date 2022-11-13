package com.example.dancway.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dancway.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UserController {
    private User user;
    private FirebaseAuth auth;
    private Activity context;

    public UserController(Activity context){
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void setUser(User newUser){user = newUser;}

    public User getUser(){return user;}

    public void setActivity(Activity newContext){context = newContext;}

    public Activity getActivityContext(){return context;}

    public void register(String email, String password, String repeat_password, String username){
        registerUser(email, password, repeat_password, username);
    }

    private void registerUser(String email, String password, String repeat_password ,String username) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = new User(Objects.requireNonNull(auth.getCurrentUser())); //Stores FirebaseUser in user if its not null
                            Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(String email, String password){loginUser(email,password);}

    private void loginUser(String email, String password){

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    user = new User(Objects.requireNonNull(auth.getCurrentUser())); //Stores FirebaseUser in user if its not null
                    Toast.makeText(context, "User logged in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
