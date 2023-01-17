package com.example.dancway.model;

import com.google.firebase.auth.FirebaseUser;

/**
 * The class that holds user's information
 */
public class User {

    private String username;
    private FirebaseUser firebaseUser;
    private static User currentUser;
    private String uid;

    //This is only for objects that contain username and user ID for representing on the list of users during PartyMode
    public User(String username, String uid){
        this.username = username;
        this.uid = uid;
    }

    /**
     * Constructor
     * @param firebaseUser holds a user from Google Firebase
     * @param username holds username
     */
    private User(FirebaseUser firebaseUser, String username, String uid){
        this.firebaseUser = firebaseUser;
        this.username = username;
        this.uid = uid;
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
    public static synchronized User getCurrentUser(FirebaseUser user, String username, String uid){
        if(currentUser == null){
            currentUser = new User(user, username, uid);
        }
        return currentUser;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getUid(){return uid;}

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
