package com.example.dancway.model;

import com.google.firebase.auth.FirebaseUser;

public class User {

    private String username ;
    private int id ;
    private FirebaseUser firebaseUser;

    public User(String username, int id, FirebaseUser firebaseUser){

        this.username = username;
        this.id = id;
        this.firebaseUser = firebaseUser;


    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }
    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }





}
