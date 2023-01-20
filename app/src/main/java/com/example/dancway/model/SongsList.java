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
import java.util.List;

/**
 * This class that holds, sets, gets and removes songs from database
 */
public class SongsList {
    private ArrayList<Song> songsList;  // Holds list of songs from database
    private ArrayList<String> namesList;

    /**
     * Constructor which initializes songsList
     */
    public SongsList() {
        this.songsList = new ArrayList<>();
        this.namesList = new ArrayList<>();
       }

    public SongsList(ArrayList<Song> songsList){
        this.songsList = songsList;
    }

    /**
     * Returns songsList as ArrayList<Song>
     * @return returns songsList
     */
    public ArrayList<Song> getArrayList(){ return songsList;}

    /**
     * Sets a new list of songs
     * @param list the new songsList
     */
    public void setList(ArrayList<Song> list){songsList = list;}

    /**
     * Returns the size of the list of songs
     * @return returns size of songsList
     */
    public int getSize() {
        return songsList.size();
    }

    /**
     * Returns song in list songsList at index i
     * @param i index showing which song is needed
     * @return returns song at index i
     */
    public Song getSongAt(int i) {
        return songsList.get(i);
    }

    /**
     * Adds new song to list
     * @param newSong the new song to be added
     */
    public void addSong(Song newSong){
        songsList.add(newSong);
        namesList.add(newSong.getTitle());
    }

    /**
     * Removes song from list at index i
     * @param i index showing which song is needed
     */
    public void removeSongAt(int i){
        namesList.remove(songsList.get(i).getTitle());
        songsList.remove(i);
    }

    public ArrayList<String> getNamesList() {
        return namesList;
    }

    /**
     * Uses the title of a song to find the index in the passed list of songs
     * @param songs List of Songs to search in
     * @param title Title of song it is searching for
     * @return Returns the index where the song is
     */
    public static int getIndexOfSongInList(List<Song> songs, String title){
        int i = 0;
        for(Song song: songs){
            if(song.getTitle().equals(title)){
                return i;
            }
            i++;
        }
        return i;
    }

}


