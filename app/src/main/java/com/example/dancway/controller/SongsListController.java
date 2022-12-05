package com.example.dancway.controller;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dancway.model.Artist;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongsList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SongsListController {
    static private SongsList songsList;
    private Activity context;

    public SongsListController(Activity context){
        this.context = context;
    }

    public static SongsList getSongsList() {
        return songsList.getList();
    }

    public Activity getContext(){return context;}

    public void setContext(Activity newContext){context = newContext;}

    public void setSongsList(SongsList newList) {
        songsList.setList(newList.getArrayList());
    }

    /**
     * Populate songsList with songs from DB. This should be used before operating on songsList.
     */
    public static void populateSongs() {
        songsList = new SongsList();
        DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference().child("SongsListRepository");
        databaseRoot.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot it : dataSnapshot.getChildren()){  //On success the JSON array is stored in dataSnapshot. Each JSON object is in iterator it
                    Song temp = new Song(String.valueOf(it.child("name").getValue()),     (long)it.child("duration").getValue(),
                            new Artist(String.valueOf(it.child("artist").getValue())),  String.valueOf(it.child("url").getValue()));
                    songsList.addSong(temp);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {   //On failure logs the error message on Logcat
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ",e.getMessage());
            }
        });
    }
}
