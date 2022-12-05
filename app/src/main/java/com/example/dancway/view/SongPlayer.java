package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerController;
import com.example.dancway.controller.SongsListController;

public class SongPlayer extends AppCompatActivity {

    MusicPlayerController musicPlayerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("pos", -1);
            if (position != -1) {
                musicPlayerController = new MusicPlayerController();
                musicPlayerController.playSong(SongsListController.getSongsList().getSongAt(position));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        musicPlayerController.stopPlayer();
    }
}