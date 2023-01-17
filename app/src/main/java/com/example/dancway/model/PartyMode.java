package com.example.dancway.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.dancway.controller.SongsListController;
import com.example.dancway.view.ModeSelectionActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class holds information about partymode
 */
public class PartyMode{    // Will implement online session later in next increments

    private String codeGenerator;
    private ArrayList<User> connectedUsers;
    private ArrayList<Song> songList;
    private SongQueue songQueue;
    SongsListController songsListController;


    //generic constructor
    public PartyMode(String codeGenerator, ArrayList<User> connectedUsers, ArrayList<Song> songList, SongQueue songQueue){
        this.codeGenerator = codeGenerator;
        this.connectedUsers = connectedUsers;
        this.songList = songList;
        this.songQueue = songQueue;
    }


    public String getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(String codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    public ArrayList<User> getConnectedUsers(){
        return connectedUsers;
    }

    public SongQueue getSongQueue(){return songQueue;}

    public ArrayList<Song> getSongList(){return songList;}

    public void addUser(User user) {
        connectedUsers.add(user);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Session").child(codeGenerator).child("UserList");
        databaseReference.child(user.getUid()).setValue(user.getUsername());
    }
    public void removeUser(User user) {
        connectedUsers.remove(user);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Session").child(codeGenerator).child("UserList");
        databaseReference.child(user.getUid()).removeValue();

    }

    //Methods to be used in child classes

    public void updateFromDatabase(DatabaseReference databaseReference) {
        Thread updateFromDB = new Thread(new Runnable() {
            @Override
            public void run() {
                takeNewInfo(databaseReference);
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    Log.i("Error: ", e.getMessage());
                }
            }
        });
        updateFromDB.start();
    }


    public void voteForSong(boolean vote, String songTitle) {   //vote == true to upvote, false to downvote
        if(vote){
            songQueue.voteUpForSong(songTitle);
        }else{
            songQueue.voteDownForSong(songTitle);
        }   //TODO: Then update RTDB
    }




    /**
     * This method takes information from the referenced firebase realtime database and updates the lists
     * @param databaseReference an instance of database reference
     */
    private void takeNewInfo(DatabaseReference databaseReference) {
        //1. Update users list
        databaseReference.child("UserList").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() != connectedUsers.size()){   //If there's no change doesn't do shit
                    connectedUsers.clear();
                    for(DataSnapshot it : dataSnapshot.getChildren()){  // On success the JSON array is stored in dataSnapshot. Each JSON object is in iterator it
                        connectedUsers.add(new User(it.getValue().toString(),it.getKey()));
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {   // On failure logs the error message on Logcat
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ",e.getMessage());
            }
        });

        //2. Update songs list
        databaseReference.child("SongsList").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot it : dataSnapshot.getChildren()){
                    // This list won't really be changed during PartyMode its just the queue
                    //TODO: Change to the static mainQueue
                    Song temp = new Song(it.getKey(), (String) it.getValue());
                    songList.add(temp);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ",e.getMessage());
            }
        });

        //3. Update songs queue
        databaseReference.child("SongsQueue").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot it : dataSnapshot.getChildren()) {
                    if (songQueue.exists(it.getKey())) {
                        Song temp = SongsListController.getSongsList().getSongAt((Integer) it.getValue());  //get exact song by index
                        songQueue.addSong(temp);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ",e.getMessage());
            }
        });
    }

    /**
     * Sets the value of the branch that is referenced to nothing. In other words deletes it
     * @param reference reference to the branch of the PartyMode session(should be basically the branch with the name of the code)
     */
    public void onDelete(DatabaseReference reference){
        reference.setValue("");
    }

    public void addSongToUpcomingQueue(Song song){
        song.setNrOfLikes(0);
        songQueue.addSong(song);
    }

}
