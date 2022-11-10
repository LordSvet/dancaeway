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

public class SongsList {
    private ArrayList<Song> songsList;  //Holds list of songs from database


    public SongsList() {
        this.songsList = new ArrayList<>();
       }

    public SongsList getList() {
        return this;
    }

    public ArrayList<Song> getArrayList(){ return songsList;}

    public void setList(ArrayList<Song> list){songsList = list;}

    public int getSize() {
        return songsList.size();
    }

    public Song getSongAt(int i) {
        return songsList.get(i);
    }

    public void addSong(Song newSong){songsList.add(newSong);}

    public void removeSongAt(int i){songsList.remove(i);}


}


