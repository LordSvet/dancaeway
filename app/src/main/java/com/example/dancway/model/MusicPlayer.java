package com.example.dancway.model;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * This class represents the music player of the app
 */
public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    private Song currentSong;

    /**
     * Constructor initializes mediaPlayer if it is null and sets it up for Music streaming.
     */
    public MusicPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();    // initiating it
            mediaPlayer.setAudioAttributes(     // setting the appropriate configuration for Music streaming
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
        }
    }

    /**
     * @param position Determines the position in time
     * Calls mediaplayer's seekTo() method to skip to the certain position in time
     *
     */
    public void changePosition(int position){
        mediaPlayer.seekTo(position);
    }

    /**
     * Returns the MediaPlayer
     * @return returns MediaPlayer object
     */
    public MediaPlayer getPlayer(){return mediaPlayer;}

    /**
     * As preparing the player might be slow it might crash the UI thread. So it's executed concurrently
     */
    private void preparePlayerConc() {
        Thread thread = new Thread() {  // Implementing run from Thread to use function concurrently
            @Override
            public void run() {
                preparePlayer();
            }
        };
        thread.start();
    }

    /**
     *Returns current song
     * @return returns the current song if it is not null
     */
    public Song getCurrentSong(){
        if(currentSong != null)
        return currentSong;
        return new Song("",69,new Artist("BabaTi"),""); // TODO: Change
    }

    /**
     * This method prepares and plays song that is set to mediaPlayer
     */
    public void preparePlayer(){
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

    /**
     * This method stops current song and switches to new one
     * @param newSong the nexsong from the playlist
     */
    public void changeSong(Song newSong){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(newSong.getUrl());
            currentSong = newSong;
        } catch (IOException e) {
            Log.i("Error: ", e.getMessage());
        }
        preparePlayerConc();
    }

    /**
     * Plays song thats passed as argument
     * @param song selected song
     */
    public void playSong(Song song){
        try{
            if(mediaPlayer.isPlaying()) reInitialize();    // If mediaPlayer is currently playing gets sent back to initialized state again so it can start prepare for new song
            try {   // When spamming the play button it might give IllegalStateException if it loads too slow so this fixes it
                mediaPlayer.setDataSource(song.getUrl());
            } catch (IllegalStateException e){
                return;
            }
            currentSong = song;
            preparePlayerConc();
        } catch (IOException e) {
            Log.i("Error: ", e.getMessage());
        }
    }
    /**
     * Starts the player
     */
    public void play() {     //plays song
        mediaPlayer.start();
    }

    /**
     * Pauses current song
     */
    public void pause() {   // pauses song
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     *Resets the player
     */
    public void reInitialize(){
        mediaPlayer.reset();
    }

    /**
     * Calls {@link #releasePlayer() releasePlayer()} method
     */
    public void stopPlayer() {     //calls releasePlayer
        releasePlayer();
    }

    /**
     * Releases resources used by player
     */
    private void releasePlayer() {   // Releases resources used by player
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Unpauses player
     */
    public void unpausePlayer() {
        mediaPlayer.start();
    }
    
}
