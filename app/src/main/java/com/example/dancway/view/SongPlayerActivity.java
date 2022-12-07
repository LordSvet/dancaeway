package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerController;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.Song;

public class SongPlayerActivity extends AppCompatActivity {

    MusicPlayerController musicPlayerController;
    private ImageView profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        Song song;
        profileButton = (ImageView) findViewById(R.id.profileButton);
        ImageView backButton = findViewById(R.id.back_button);
        TextView songTitle = findViewById(R.id.songTittle);
        TextView artistName = findViewById(R.id.artistName);
        // Extract the song position from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("pos", -1);
            if (position != -1) {
                song = SongsListController.getSongsList().getSongAt(position);
                musicPlayerController = new MusicPlayerController();
                musicPlayerController.playSong(song);
                songTitle.setText(song.getTitle());
                artistName.setText(song.getArtist().getName());
            }
        }

        // Attach Listeners
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongPlayerActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        musicPlayerController.stopPlayer();
    }
}