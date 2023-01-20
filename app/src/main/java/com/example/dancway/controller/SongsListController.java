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

import java.util.ArrayList;

/**
 * The class that controls songs which reside in SongsListRepository
 */
public class SongsListController {
    static private SongsList songsList;
    private Activity context;

    /**
     * Constructor
     *
     */
    public SongsListController() {
    }

    /**
     * @return the list of songs
     */
    public static SongsList getSongsList() {
        return songsList;
    }

    /**
     * Sets new songs list
     *
     * @param newList new list to be set
     */
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
                for (DataSnapshot it : dataSnapshot.getChildren()) {  // On success the JSON array is stored in dataSnapshot. Each JSON object is in iterator it
                    Song temp = new Song(String.valueOf(it.child("name").getValue()), (long) it.child("duration").getValue(),
                            new Artist(String.valueOf(it.child("artist").getValue())), String.valueOf(it.child("url").getValue()),
                            String.valueOf(it.child("image-url").getValue()));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            temp.setBitmap(temp.getImageURL());
                        }
                    });
                    thread.start();
                    songsList.addSong(temp);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {   // On failure logs the error message on Logcat
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ", e.getMessage());
            }
        });
    }

    /**
     * Gets index of song in the original list of songs from the title
     * @param songTitle title of Song
     * @return returns index of the song in the original full song list
     */
    public static int getIndexOfSong(String songTitle) {
        ArrayList<Song> list = getSongsList().getArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().equals(songTitle)) {
                return i;
            }
        }
        return -1;  //Means song is not in list
    }

    /**
     * @return returns the context
     */
    public Activity getContext() {
        return context;
    }

    /**
     * Sets new context
     *
     * @param newContext new context to be set
     */
    public void setContext(Activity newContext) {
        context = newContext;
    }

}
