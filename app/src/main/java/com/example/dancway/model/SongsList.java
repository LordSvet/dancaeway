package com.example.dancway.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * The class for working with the list of songs contained in the database
 */
public class SongsList {
    private ArrayList<Song> songsList;

    /**
     * creates an object which holds the Array List with songs
     */
    public SongsList() {
        this.songsList = new ArrayList<>();
    }

    /**
     * @return the object with the list of all available songs
     */
    public SongsList getList() {
        return this;
    }

    /**
     * @return the list of songs contained in the database
     */
    public ArrayList<Song> getArrayList(){ return songsList;}

    /**
     * @param list used to set the list of songs
     */
    public void setList(ArrayList<Song> list){songsList = list;}

    /**
     * @return the number of songs in database
     */
    public int getSize() {
        return songsList.size();
    }

    /**
     * @param track chooses the certain song in array list of songs in database
     * @return the certain song from database
     */
    public Song getSongAt(int track) {
        return songsList.get(track);
    }

    /**
     * @param newSong gets added to the list of songs in database
     */
    public void addSong(Song newSong){songsList.add(newSong);}

    /**
     * @param i gets removed from the list of songs in database
     */
    public void removeSongAt(int i){songsList.remove(i);}
}