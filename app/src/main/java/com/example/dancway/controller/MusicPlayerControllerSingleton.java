package com.example.dancway.controller;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.dancway.model.MusicPlayer;
import com.example.dancway.model.Song;

import java.io.IOException;

/**
 * The class uses MediaPlayer as the audio player. Singleton class pattern is selected
 * to avoid multiple music players playing at the same time
 */

public class MusicPlayerControllerSingleton {
    private MusicPlayer musicPlayer;
    private static MusicPlayerControllerSingleton instance;

    /**
     * Constructor initializes musicPlayer
     */
    private MusicPlayerControllerSingleton() {    //Passing song to be played when creating player
        musicPlayer = new MusicPlayer();
    }

    /**
     * @return current instance
     */
    public static synchronized MusicPlayerControllerSingleton getInstance(){
        if(instance == null){
            instance = new MusicPlayerControllerSingleton();
        }
        return instance;
    }

    /**
     * @return the current song
     */
    public Song getSong(){
        return musicPlayer.getCurrentSong();
    }

    /**
     * @param song assings a new song in the music player
     */
    public void playSong(Song song){
        musicPlayer.playSong(song);
    }

    /**
     * @param newSong changes current song to newSong
     */
    public void changeSong(Song newSong){
        musicPlayer.changeSong(newSong);
    }

    /**
     * Stops the player
     */
    public void stopPlayer(){
        musicPlayer.stopPlayer();
    }

    /**
     * Pauses the player
     */
    public void pausePlayer(){
        musicPlayer.pause();
    }

    /**
     * Unpauses the player
     */
    public void unPausePlayer(){
        musicPlayer.unpausePlayer();
    }

    /**
     * Resets the player
     */
    public void reInitializePlayer(){
        musicPlayer.reInitialize();
    }

    /**
     * @return music player
     */
    public MediaPlayer getMusicPlayer(){return musicPlayer.getPlayer();}

    /**
     * It shows the current time of playing and user can change the playing position with dragging
     * @param progress
     */
    public void seekTo(int progress){
        musicPlayer.changePosition(progress);
    }




}
