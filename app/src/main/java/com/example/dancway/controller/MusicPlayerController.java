package com.example.dancway.controller;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.dancway.model.MusicPlayer;
import com.example.dancway.model.Song;

import java.io.IOException;

public class MusicPlayerController {    //Class uses MediaPlayer as our audio player
    private MusicPlayer musicPlayer;

    public MusicPlayerController() {    //Passing song to be played when creating player
        musicPlayer = new MusicPlayer();
    }

    public Song getSong(){
        return musicPlayer.getCurrentSong();
    }

    public void playSong(Song song){
        musicPlayer.playSong(song);
    }

    public void changeSong(Song newSong){
        musicPlayer.changeSong(newSong);
    }
    public void stopPlayer(){
        musicPlayer.stopPlayer();
    }

    public void pausePlayer(){
        musicPlayer.pause();
    }

    public void reInitializePlayer(){
        musicPlayer.reInitialize();
    }


}
