package com.example.dancway.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.dancway.controller.SongsListController;
import com.example.dancway.view.JoinPartyActivity;
import com.example.dancway.view.ModeSelectionActivity;
import com.example.dancway.view.SongPlayerActivity;
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


    /**
     *
     * @return Returns the code that represents the session
     */
    public String getCodeGenerator() {
        return codeGenerator;
    }

    /**
     *
     * @return Returns the songList
     */
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

    /**
     * Method calls the methods takeNewInfo and updateSongListInDB every 10 seconds
     * @param databaseReference Reference towards the branch in the real time database
     * @param list list that will be update to and from database
     */
    public void updateFromDatabase(DatabaseReference databaseReference, ArrayList<Song> list) {
        Thread updateFromDB = new Thread(new Runnable() {
            @Override
            public void run() {
                takeNewInfo(databaseReference); //Have to read first once upon connecting
                while(true){

                    takeNewInfo(databaseReference);
                    updateSongListInDB(list);
                    list.clear();   //TODO: TEST WITH TWO PHONES
                    list.addAll(songList);
                    try{
                        Thread.sleep(10000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                        Log.i("Error: ", e.getMessage());
                    }
                }
            }
        });
        updateFromDB.start();
    }


    /**
     * Vote for a song
     * @param vote true means upVote, false means downVote
     * @param songTitle Song Title of song to vote for
     */
    public void voteForSong(boolean vote, String songTitle) {   //vote == true to upvote, false to downvote
        if(vote){
            songQueue.voteUpForSong(songTitle);
        }else{
            songQueue.voteDownForSong(songTitle);
        }
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
                songList.clear();
                for(DataSnapshot it : dataSnapshot.getChildren()){
                    String index = String.valueOf(it.getValue());
                    index = index.replace("{","");
                    index = index.replace("}","");
                    String[] values = index.split("=");
                    Song temp =SongsListController.getSongsList().getSongAt(Integer.parseInt(values[0]));
                    temp.setNrOfLikes(Integer.parseInt(values[1]));
                    songList.add(temp);
                }
                setListToPass(songList);
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

    /**
     * Sets the songs list in the database to the most current one from the user's end
     * @param song List of songs to put in the database
     */
    public void updateSongListInDB(ArrayList<Song> song){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Session").child(codeGenerator).child("SongsList");

        SongQueue.sortListByVotes(song);

        databaseReference.setValue("");
        for(int i = 0; i< song.size();i++){    //Loads all songs from storage onto the realtime database
            databaseReference.child(String.valueOf(i)).child(String.valueOf(SongsListController.getIndexOfSong(song.get(i).getTitle()))).setValue(String.valueOf(song.get(i).getNrOfLikes()));//TODO: SET TWO VALUES NOT ONNNNEEEEE
        }
//        setListToPass(song);
    }


    public void setListToPass(List<Song> list){}

    /**
     *
     * @return Returns true if partyMode is active
     */
    public boolean isParty(){return true;}

    /**
     *
     * @return Returns true if current user is the master of the party
     */
    public boolean isMaster(){return false;}


}
