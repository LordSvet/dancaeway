package com.example.dancway.model;

import com.example.dancway.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PartyGuest extends PartyMode {

    private User user;
    private String seshCode;
    private ArrayList<User> connectedUsers;
    private ArrayList<Song> songList;
    private SongQueue songQueue;


    public PartyGuest(String codeGenerator, ArrayList<User> connectedUsers, ArrayList<Song> songList, SongQueue songQueue){
        super(codeGenerator,connectedUsers,songList,songQueue);
        seshCode = codeGenerator;
        user = User.getCurrentUser();
        connectToParty(user, seshCode);
    }


    //Methods to be used in child classes
    public void connectToParty(User guest, String seshCode) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Session").child(seshCode);
        databaseReference.child("UserList").child(user.getUid()).setValue(user.getUsername());
        updateFromDatabase(databaseReference);
    }

    //Calls base class function
    public void updateFromDatabase(DatabaseReference databaseReference) {
        super.updateFromDatabase(databaseReference);
    }

    public void voteForSong(boolean vote, String title) {   //vote == true for upvote, false for downvote
        super.voteForSong(vote, title);
    }


}
