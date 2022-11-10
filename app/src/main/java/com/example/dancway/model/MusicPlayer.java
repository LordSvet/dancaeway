package com.example.dancway.model;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    private Song currentSong;

    public MusicPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();    //Initiating it
            mediaPlayer.setAudioAttributes(     //setting the appropriate configuration for Music streaming
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
        }
    }

    private void preparePlayerConc(){       //As preparing the player might be slow it might crash the UI thread. So it's executed concurrently
        Thread thread = new Thread() {  //Implementing run from Thread to use function concurrently
            @Override
            public void run() {
                preparePlayer();
            }
        };
        thread.start();
    }

    public Song getCurrentSong(){
        if(currentSong != null)
        return currentSong;
        return new Song("",69,new Artist("BabaTi"),""); //TODO: Change
    }


    public void preparePlayer(){    //Prepares and plays song that is set to mediaPlayer
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.i("Error: ", e.getMessage());
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                play();
            }
        });
    }

    public void changeSong(Song newSong){   //Stops current song and switches to new one
        try {
            mediaPlayer.setDataSource(newSong.getUrl());
            currentSong = newSong;
        } catch (IOException e) {
            Log.i("Error: ", e.getMessage());
        }
        preparePlayerConc();
    }

    public void playSong(Song song){    //Plays song thats passed as argument
        try{
            if(mediaPlayer.isPlaying()) reInitialize();    //If mediaPlayer is currently playing gets sent back to initialized state again so it can start prepare for new song
            mediaPlayer.setDataSource(song.getUrl());
            currentSong = song;
            preparePlayerConc();
        } catch (IOException e) {
            Log.i("Error: ", e.getMessage());
        }
    }
    public void play() {     //plays song
        mediaPlayer.start();
    }

    public void pause() {    //pauses song
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void reInitialize(){
        mediaPlayer.reset();
    }

    public void stopPlayer() {     //calls releasePlayer. TODO: Make override onStop to release player resources!
        releasePlayer();
    }

    private void releasePlayer() {   //Releases resources used by player
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
