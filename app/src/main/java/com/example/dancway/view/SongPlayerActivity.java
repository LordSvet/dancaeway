package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.Song;

public class SongPlayerActivity extends AppCompatActivity {

    MusicPlayerControllerSingleton musicPlayerController;
    ImageView profileButton;
    TextView artistName;
    TextView songName;
    TextView seekbarEnd;
    SeekBar seekBar;
    Handler handler = new Handler();
    Runnable runnable;
    ImageView previousSong;
    ImageView pausePlay;
    ImageView nextSong;
    int songPosition;
    Song currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        artistName = findViewById(R.id.artistName);
        songName = findViewById(R.id.songTittle);
        seekBar = findViewById(R.id.seekBar);
        seekbarEnd = findViewById(R.id.seekbarEnd);
        previousSong = findViewById(R.id.previousSong);
        nextSong = findViewById(R.id.nextSong);
        pausePlay = findViewById(R.id.pausePlay);
        musicPlayerController = MusicPlayerControllerSingleton.getInstance();


        profileButton = (ImageView) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongPlayerActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songPosition --;
                if(songPosition < 0){
                    songPosition = SongsListController.getSongsList().getSize()-1; //If it was first song it goes back around
                }
                currentSong = SongsListController.getSongsList().getSongAt(songPosition);
                musicPlayerController.changeSong(currentSong);
                updateViews(currentSong);
            }
        });

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songPosition ++;
                if(songPosition >= SongsListController.getSongsList().getSize()){
                    songPosition = 0; //If it was last song it goes back around
                }
                currentSong = SongsListController.getSongsList().getSongAt(songPosition);
                musicPlayerController.changeSong(currentSong);
                updateViews(currentSong);
            }
        });

        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicPlayerController.getMusicPlayer().isPlaying()){
                   musicPlayerController.pausePlayer();
                   pausePlay.setImageResource(R.drawable.play_button);
                }else if(!musicPlayerController.getMusicPlayer().isPlaying()){
                    musicPlayerController.unPausePlayer();
                    pausePlay.setImageResource(R.drawable.pause_button);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            songPosition = extras.getInt("pos", -1);
            if (songPosition != -1) {
                currentSong = SongsListController.getSongsList().getSongAt(songPosition);

                updateViews(currentSong);


                musicPlayerController.playSong(currentSong);
                updateSeekbar();

                musicPlayerController.getMusicPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        songPosition++;
                        currentSong = SongsListController.getSongsList().getSongAt(songPosition);
                        musicPlayerController.changeSong(currentSong);
                        updateViews(currentSong);
                        updateSeekbar();

                    }
                });
            }
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean fromUser) {
                if(fromUser){       //Check is user clicked this or if it was done in the Handler
                    musicPlayerController.seekTo(position*1000);    //No idea why it has to be multiplied by a thousand but its the only way it works
                    seekBar.setProgress(position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //We don't really need to do this but it has to be here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  //We don't really need to do this but it has to be here

            }
        });

    }

    public void updateSeekbar(){
        int currentPosition = musicPlayerController.getMusicPlayer().getCurrentPosition();
        seekBar.setProgress(currentPosition/1000);

        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        handler.postDelayed(runnable,1000);
    }

    public void updateViews(Song song){
        seekBar.setProgress(0);
        seekBar.setMax((int) song.getDuration());
        String seekbarEndString = "";
        seekbarEndString += song.getDuration()/60 + ":";
        if(song.getDuration()%60 < 10){
            seekbarEndString+="0";
        }
        seekbarEndString += song.getDuration()%60;
        seekbarEnd.setText(seekbarEndString);

        artistName.setText(song.getArtist().getName());
        songName.setText(song.getTitle());
    }

}