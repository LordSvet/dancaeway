package com.example.dancway.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.dancway.controller.SongsListController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PartyMode extends Session {    //Will implement online session later in next increments

    private String codeGenerator ;
    private List<User> connectedUsers ;
    private ArrayList<Song> songList;
    private SongQueue songQueue;

    public PartyMode(){
        SessionCodeGen gen = new SessionCodeGen();
        codeGenerator = gen.getCode();
        connectedUsers = new ArrayList<>();
        songList = new ArrayList<>();
        songQueue = new SongQueue(100);

        currentSession();
    }

    public void currentSession(){
        //TODO: make sure after the session is ended , we delete the branch

        //make a ref to db and make a ref to the root which has a child named Session
        DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference().child("Session");

        Map<String, Map<String, String>> sessionBranch = new HashMap<>();

        Map<String, String> userList = new HashMap<>();
        for(int i = 0; i< connectedUsers.size();i++){
            userList.put(connectedUsers.get(i).getUsername(),"");
        }
        sessionBranch.put("UserList",userList);

        Map<String, String> songs = new HashMap<>();
        for(int i = 0; i< songList.size();i++){
            songs.put(songList.get(i).getTitle(), songList.get(i).getUrl());

        }
        sessionBranch.put("SongsList",songs);

        Map<String, String> queue = new HashMap<>();

        Iterator<Song> iterate = songQueue.getQueue().iterator();

        while(iterate.hasNext()){
            queue.put(songQueue.getQueue().iterator().next().getTitle(),"");
        }

        sessionBranch.put("SongsQueue",queue);


        databaseRoot.child(codeGenerator).setValue(sessionBranch);

    }

    public String getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(String codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

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
