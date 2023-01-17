package com.example.dancway.model;

import com.example.dancway.controller.SongsListController;
import com.example.dancway.view.JoinPartyActivity;
import com.example.dancway.view.ModeSelectionActivity;
import com.example.dancway.view.SongPlayerActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PartyMaster extends PartyMode{

    private User user;
    private String seshCode;
    private ArrayList<User> connectedUsers;
    private ArrayList<Song> songList;
    private SongQueue songQueue;
    private SongsListController songsListController;


    public PartyMaster(String codeGenerator, ArrayList<User> connectedUsers, ArrayList<Song> songList, SongQueue songQueue){
        super(codeGenerator,connectedUsers,songList,songQueue);
        seshCode = codeGenerator;
        user = User.getCurrentUser();
        connectedUsers.add(user);
        songsListController = new SongsListController();
        createPartySession();
    }

    //Methods to be used in child classes


    public void createPartySession() {

        // make a ref to db and make a ref to the root which has a child named Session
        DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference().child("Session");

        Map<String, Map<String, String>> sessionBranch = new HashMap<>();

        //1. Add user
        Map<String, String> userList = new HashMap<>();
        userList.put(user.getUid(), user.getUsername());

        sessionBranch.put("UserList",userList);

        if(ModeSelectionActivity.upcomingSongs.isEmpty()){  //If the queue is empty it just adds full song list to it
            ModeSelectionActivity.upcomingSongs = new ArrayList<>(SongsListController.getSongsList().getArrayList());
        }

        //Shuffles list before adding it
        SongPlayerActivity.shuffleQueue(ModeSelectionActivity.upcomingSongs);
        //2. Adds songs list
        Map<String, String> songs = new HashMap<>();    //Key is title of song and value is the original index in the RTDB of that song

        for(int i = 0; i< ModeSelectionActivity.upcomingSongs.size();i++){    //Loads all songs from storage onto the realtime database
            songs.put(ModeSelectionActivity.upcomingSongs.get(i).getTitle(), String.valueOf(SongsListController.getIndexOfSong(ModeSelectionActivity.upcomingSongs.get(i).getTitle())));
        }
        sessionBranch.put("SongsList",songs);

        databaseRoot.child(seshCode).setValue(sessionBranch);

        updateFromDatabase(databaseRoot.child(seshCode));
    }

    public void updateFromDatabase(DatabaseReference databaseRoot) {
        super.updateFromDatabase(databaseRoot);
    }

    public void voteForSong(boolean vote, Song songToVoteFor) { //if vote == true -> vote += 1 else -1
        super.voteForSong(vote, songToVoteFor.getTitle());
    }
}
