package com.example.dancway.model;

import com.example.dancway.R;
import com.example.dancway.view.ModeSelectionActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PartyGuest extends PartyMode {

    private User user;
    private String seshCode;
    private ArrayList<User> connectedUsers;
    private ArrayList<Song> songList;
    private SongQueue songQueue;
    ArrayList<Song> listToPass;


    public PartyGuest(String codeGenerator, ArrayList<User> connectedUsers, ArrayList<Song> songList, SongQueue songQueue){
        super(codeGenerator,connectedUsers,songList,songQueue);
        seshCode = codeGenerator;
        user = User.getCurrentUser();
        listToPass = new ArrayList<>(ModeSelectionActivity.upcomingSongs);
        connectToParty(user, seshCode);
    }

    public void setListToPass(List<Song> list){
        listToPass.clear();
        listToPass.addAll(list);
    }


    //Methods to be used in child classes
    public void connectToParty(User guest, String seshCode) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Session").child(seshCode);
        databaseReference.child("UserList").child(user.getUid()).setValue(user.getUsername());
        updateFromDatabase(databaseReference, listToPass);
    }

    //Calls base class function
    public void updateFromDatabase(DatabaseReference databaseRoot, ArrayList<Song> list) {
        super.updateFromDatabase(databaseRoot, list);
    }

    /**
     * Calls super class's voteForSong
     * @param vote true means upVote, false means downVote
     * @param title Song Title of song to vote for
     */
    public void voteForSong(boolean vote, String title) {   //vote == true for upvote, false for downvote
        super.voteForSong(vote, title);
    }

    /**
     * Calls super class's updateSongListInDB
     * @param list List of songs to put in the database
     */
    public void updateSongListInDB(ArrayList<Song> list){
        super.updateSongListInDB(list);
    }

    /**
     *
     * @return Returns false
     */
    public boolean isMaster(){return false;}

}
