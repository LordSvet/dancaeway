package com.example.dancway.model;

/**
 * This class holds the information of a session
 */
public class Session {  // Most of session will be implemented in later increments
    private String id;
    private User user;

    /**
     * Constructor
     * @param id id of session
     * @param user user that created it
     */
    public Session(String id, User user){
        this.id = id;
        this.user = user;
    }

    public Session() {

    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser1(User user) {
        this.user = user;
    }

}
