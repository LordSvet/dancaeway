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
import java.util.List;
import java.util.Map;

public class PartyMaster extends PartyMode{

    private User user;
    private String seshCode;
    private ArrayList<User> connectedUsers;
    private ArrayList<Song> songList;
    private SongQueue songQueue;
    ArrayList<Song> listToPass;
    private SongsListController songsListController;


    public PartyMaster(String codeGenerator, ArrayList<User> connectedUsers, ArrayList<Song> songList, SongQueue songQueue){
        super(codeGenerator,connectedUsers,songList,songQueue);
        seshCode = codeGenerator;
        user = User.getCurrentUser();
        connectedUsers.add(user);
        songsListController = new SongsListController();
        listToPass = new ArrayList<>(ModeSelectionActivity.upcomingSongs);
        createPartySession();
    }

    //Methods to be used in child classes

    public void setListToPass(List<Song> list){
        listToPass.clear();
        listToPass.addAll(list);
    }

    /**
     * Calls updateFromDatabase with the correct parameters
     */
    public void createPartySession() {

        // make a ref to db and make a ref to the root which has a child named Session
        DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference().child("Session");

        //1. Add user
        databaseRoot.child(seshCode).child("UserList").child(user.getUid()).setValue(user.getUsername());

        //Shuffles list before adding it
        SongPlayerActivity.shuffleQueue(ModeSelectionActivity.upcomingSongs);
        databaseRoot.child(seshCode).child("SongsList").setValue("");
        for (int i = 0; i < ModeSelectionActivity.upcomingSongs.size(); i++) {    //Loads all songs from storage onto the realtime database
            databaseRoot.child(seshCode).child("SongsList").child(String.valueOf(i)).setValue(String.valueOf(SongsListController.getIndexOfSong(ModeSelectionActivity.upcomingSongs.get(i).getTitle())));
        }//SONGS HAVE TO BE SAVED UNDER NUMBER IN QUEUE and as a value it has a child which contains the index in the OG list and that child has a value which is the likes


        updateFromDatabase(databaseRoot.child(seshCode), listToPass);
    }

    /**
     * Calls super class's updateFromDatabase
     * @param databaseRoot Reference towards the branch in the real time database
     * @param list list that will be update to and from database
     */
    public void updateFromDatabase(DatabaseReference databaseRoot, ArrayList<Song> list) {
        super.updateFromDatabase(databaseRoot, list);
    }

    public void voteForSong(boolean vote, Song songToVoteFor) { //if vote == true -> vote += 1 else -1
        super.voteForSong(vote, songToVoteFor.getTitle());
    }

    public void updateSongListInDB(ArrayList<Song> list){
        super.updateSongListInDB(list);
    }

    public boolean isMaster(){return true;}



}
