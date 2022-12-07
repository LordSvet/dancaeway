package com.example.dancway.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerController;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.User;

public class SongPlayer extends AppCompatActivity {

    MusicPlayerController musicPlayerController;
    private ImageView profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        profileButton = (ImageView) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongPlayer.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

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