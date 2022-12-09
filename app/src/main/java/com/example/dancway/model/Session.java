package com.example.dancway.model;

/**
 * The class that holds all the data that is relevant for the current music session
 */
public class Session {  //Most of session will be implemented in later increments
    private String id;
    private User user;

    /**
     * @return the id of the session
     */
    public String getId() {
        return id;
    }

    /**
     * @return the user object of the session
     */
    public User getUser() {
        return user;
    }

    /**
     * @param id assigns a new id to the current session
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param user assigns a new user of the session
     */
    public void setUser1(User user) {
        this.user = user;
    }


}