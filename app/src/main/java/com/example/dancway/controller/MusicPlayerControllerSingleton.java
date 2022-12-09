package com.example.dancway.controller;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.dancway.model.MusicPlayer;
import com.example.dancway.model.Song;

import java.io.IOException;

public class MusicPlayerControllerSingleton {    //Class uses MediaPlayer as our audio player
    private MusicPlayer musicPlayer;
    private static MusicPlayerControllerSingleton instance;

    private MusicPlayerControllerSingleton() {    //Passing song to be played when creating player
        musicPlayer = new MusicPlayer();
    }

    public static synchronized MusicPlayerControllerSingleton getInstance(){
        if(instance == null){
            instance = new MusicPlayerControllerSingleton();
        }
        return instance;
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

    public void unPausePlayer(){
        musicPlayer.unpausePlayer();
    }

    public void reInitializePlayer(){
        musicPlayer.reInitialize();
    }

    public MediaPlayer getMusicPlayer(){return musicPlayer.getPlayer();}

    public void seekTo(int progress){
        musicPlayer.changePosition(progress);
    }




}
