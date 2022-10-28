package com.example.dancway.controller;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.dancway.model.Song;

import java.io.IOException;

public class MusicPlayerController {    //Class uses MediaPlayer as our audio player
    private MediaPlayer mediaPlayer;

    public MusicPlayerController(Song song) throws IOException {    //Passing song to be played when creating player
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();    //Initiating it
            mediaPlayer.setAudioAttributes(     //setting the appropriate configuration for Music streaming
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            try {
                mediaPlayer.setDataSource(song.getUrl());   //Setting the music source via Url
            } catch (Exception e) {
                Log.i("Error: ", e.getMessage());
            }
            mediaPlayer.prepare();  //Preparing the player and once prepared calls play()
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    play();
                }
            });
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

    public void stopPlayer() {     //calls releasePlayer
        releasePlayer();
    }

    private void releasePlayer() {   //Releases resources used by player
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
