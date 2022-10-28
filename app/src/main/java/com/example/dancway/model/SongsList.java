package com.example.dancway.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SongsList {
    private ArrayList<Song> songsList;  //Holds list of songs from database
    private String[] urls; //PROTOTYPE. As there is not enough time to connect secure links with access tokens the first implementation will use this array to keep sample links
    //In next increments will update

    public SongsList() {
        this.songsList = new ArrayList<>();
        runConcurrent();    //Runs getListOfSongs() on another thread concurrently when object is initialized
        urls = new String[]{"https://firebasestorage.googleapis.com/v0/b/dancaeway.appspot.com/o/For%20Me.mp3?alt=media&token=4d754f05-3cfc-4ca6-97de-85b349f963d8",
                "https://firebasestorage.googleapis.com/v0/b/dancaeway.appspot.com/o/Mesmerizing.mp3?alt=media&token=9a082edc-13c8-49d7-be7b-ec6de85e630c",
                "https://firebasestorage.googleapis.com/v0/b/dancaeway.appspot.com/o/Tropical%20summer.mp3?alt=media&token=42a89c32-cc00-48fb-aa98-89fbcdc39cba",
                "https://firebasestorage.googleapis.com/v0/b/dancaeway.appspot.com/o/UpBeat%20Funk.mp3?alt=media&token=d50044e6-4e19-4dbb-be18-98363d5d92d0"};
    }

    public ArrayList<Song> getList() {
        return songsList;
    }

    public int getSize() {
        return songsList.size();
    }

    public Song getSongAt(int i) {
        return songsList.get(i);
    }


    public void getListOfSongs() {//Method fills Array with songs from DB
        FirebaseStorage storage = FirebaseStorage.getInstance();    //Creating reference to Firebase Storage
        StorageReference referenceForList = storage.getReference();  //Creating a reference that points to storage files
        Task<ListResult> listResultTask = referenceForList.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() { //listAll() returns all of the songs
            @Override
            public void onSuccess(ListResult listResult) {  //When it passes onSuccess we iterate and add each song to the ArrayList
                int i = 0;//i is  used for the urls array. This will be changed later.
                for (StorageReference reference : listResult.getItems()) {
                    Song temp = new Song(reference.getName(), urls[i]);
                    songsList.add(temp);
                    i++;

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override   //In case of failure error is displayed on Log
            public void onFailure(@NonNull Exception e) {
                Log.i("Error: ", e.getMessage());
            }
        });

    }

    private void runConcurrent() {
        Thread thread = new Thread() {  //Implementing run from Thread to use function concurrently
            @Override
            public void run() {
                getListOfSongs();
            }
        };
        thread.start();
    }
}


