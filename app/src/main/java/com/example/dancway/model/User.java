package com.example.dancway.model;

import com.google.firebase.auth.FirebaseUser;

public class User {

    private String username;
    private FirebaseUser firebaseUser;

    public User(FirebaseUser firebaseUser){
        this.firebaseUser = firebaseUser;
        username = firebaseUser.getDisplayName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }





}
