package com.example.dancway.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class holds information about partymode
 */
public class PartyMode extends Session {    // Will implement online session later in next increments

    private String codeGenerator ;
    private List<String> connectedUsers ;
    private ArrayList<Song> songList;
    private SongQueue songQueue;


    public PartyMode(User guestUser, String code){
        if(guestUser.getPartyRole() == PartyRole.GUEST) {
            connectedUsers = new ArrayList<>();
            songList = new ArrayList<>();
            songQueue = new SongQueue(100);
            connectToParty(guestUser, code);
        }
    }

    public PartyMode(User master) {
        if (master.getPartyRole() == PartyRole.MASTER) {
            SessionCodeGen gen = new SessionCodeGen();
            codeGenerator = gen.getCode();
            connectedUsers = new ArrayList<>();
            connectedUsers.add(master.getUsername().split("@")[0]); // TODO: JUST FOR TEST PURPOSES!!!!!!!!!!!
            songList = new ArrayList<>();
            songQueue = new SongQueue(100);
            createPartySession();
        }
    }

    /**
     * This method connects to a party
     * @param guest who is not party master
     * @param seshCode session code of that party
     */
    private void connectToParty(User guest, String seshCode){
        DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference().child("Session").child(seshCode);
        databaseRoot.child("UserList").child(guest.getUsername().split("@")[0]).setValue(""); // TODO: For now value is empty, add UID later on
        updateFromDB(databaseRoot);
    }

    /**
     * Updating the info from firebase database
     * @param databaseReference an instace of the database reference
     */
    public void updateFromDB(DatabaseReference databaseReference){
        Thread updateFromDB = new Thread(new Runnable() {
            @Override
            public void run() {
                takeNewInfo(databaseReference);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateFromDB.start();
    }

    /**
     * This method takes information from the referenced firebase realtime database
     * @param databaseReference an instance of database reference
     */
    private void takeNewInfo(DatabaseReference databaseReference) {
        databaseReference.child("UserList").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot it : dataSnapshot.getChildren()){  // On success the JSON array is stored in dataSnapshot. Each JSON object is in iterator it
    //              User temp = new User(it.getKey());
                    connectedUsers.add(it.getKey());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {   // On failure logs the error message on Logcat
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ",e.getMessage());
            }
        });
        databaseReference.child("SongsList").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot it : dataSnapshot.getChildren()){  //On success the JSON array is stored in dataSnapshot. Each JSON object is in iterator it
                    Song temp = new Song(it.getKey(), (String) it.getValue());
                    songList.add(temp);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {   // On failure logs the error message on Logcat
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ",e.getMessage());
            }
        });
        databaseReference.child("SongsQueue").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot it : dataSnapshot.getChildren()){  // On success the JSON array is stored in dataSnapshot. Each JSON object is in iterator it
                    Song temp = new Song(it.getKey(), (String) it.getValue());
                    songQueue.addSong(temp);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {   // On failure logs the error message on Logcat
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ",e.getMessage());
            }
        });
    }

    /**
     * This method creates a party session and
     * puts the information of users', songs list and songs queue in the firebase realtime database
     */
    private void createPartySession(){
        // TODO: make sure after the session is ended , we delete the branch

        // make a ref to db and make a ref to the root which has a child named Session
        DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference().child("Session");

        Map<String, Map<String, String>> sessionBranch = new HashMap<>();

        Map<String, String> userList = new HashMap<>();
        for(int i = 0; i< connectedUsers.size();i++){
            userList.put(connectedUsers.get(i),"");
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

        updateFromDB(databaseRoot.child(codeGenerator));
    }

    public String getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(String codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    public List<String> getConnectedUsers(){
        return connectedUsers;
    }

    public void addUser(User user) {
        // probably we want to add not the whole user object, but only some props from it
        connectedUsers.add(user.getUsername());
    }

    public void removeUser(User user) {
        connectedUsers.remove(user);
    }

}
