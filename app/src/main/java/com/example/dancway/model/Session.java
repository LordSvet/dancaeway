package com.example.dancway.model;

public class Session {
    private String id;
    User user1 = new User ();

    public String getId() {
        return id;
    }

    public User getUser1() {
        return user1;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

}
