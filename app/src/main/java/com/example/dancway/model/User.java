package com.example.dancway.model;

import com.google.firebase.auth.FirebaseUser;

/**
 * The class that holds user's information
 */
public class User {

    private String username;
    private FirebaseUser firebaseUser;
    private PartyRole partyRole;
    private static User currentUser;

    /**
     * Constructor
     * @param firebaseUser holds a user from Google Firebase
     * @param username holds username
     */
    private User(FirebaseUser firebaseUser, String username){
        this.firebaseUser = firebaseUser;
        this.username = username;
    }

    /**
     * @return the current user
     */
    public static synchronized User getCurrentUser(){
        return currentUser;
    }

    /**
     * @param user User whose data are fetched from firebase list
     * @param username of the user
     * @return the current user
     */
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

    /**
     * Gets username
     * @return returns username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets new username to User
     * @param username new username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets FirebaseUser object
     * @return returns firebaseUser
     */
    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    /**
     * sets new FirebaseUser to a User
     * @param firebaseUser new FirebaseUser to set
     */
    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

}
