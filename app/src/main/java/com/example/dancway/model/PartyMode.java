package com.example.dancway.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PartyMode extends Session {    //Will implement online session later in next increments

    private List<User> connectedUsers = new ArrayList<>();

    public List<User> getConnectedUsers(){
        return connectedUsers;
    }

    public void addUser(User user) {
        // probably we want to add not the whole user object, but only some props from it
        connectedUsers.add(user);
    }

    public void removeUser(User user) {
        connectedUsers.remove(user);
    }

    public void saveCurrentSession(){
//        DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference().child("Session").child("id").child("list");
//        sessionRef.setValue("testValue");

    }
}
