package com.example.dancway.model;

import com.google.firebase.auth.FirebaseUser;

public class User {

    private String username;
    private FirebaseUser firebaseUser;
    private PartyRole partyRole;
    private static User currentUser;

    private User(FirebaseUser firebaseUser, String username){
        this.firebaseUser = firebaseUser;
        this.username = username;
    }

    public static synchronized User getCurrentUser(){
        return currentUser;
    }

    public static synchronized User getCurrentUser(FirebaseUser user, String username){
        if(currentUser == null){
            currentUser = new User(user, username);
        }
        return currentUser;
    }

    public void setPartyRole(PartyRole role){
        partyRole = role;
    }

    public PartyRole getPartyRole(){ return partyRole;}

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
