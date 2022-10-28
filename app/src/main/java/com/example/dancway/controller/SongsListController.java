package com.example.dancway.controller;

import android.app.Activity;
import android.util.Log;

import com.example.dancway.model.SongsList;

import java.io.IOException;

public class SongsListController {
    private SongsList list;
    private MusicPlayerController player;
    private Activity context;

    public SongsListController(Activity context){
        this.context = context;
        list = new SongsList();
    }

    public void playSong() {
        try {
            player = new MusicPlayerController(list.getSongAt(1));
        }catch(IOException e){  //Prints error message to Log in case of IOException
            Log.i("Error: ", e.getMessage());
        }
    }
}
