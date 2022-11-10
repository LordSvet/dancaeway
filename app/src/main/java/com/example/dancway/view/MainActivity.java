package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerController;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.SongsList;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView welcomeText;
    Button playButton;
    Button registerButton;
    Button loginButton;
    SongsListController songsListController;
    MusicPlayerController musicPlayerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        playButton = (Button) findViewById(R.id.playButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        songsListController = new SongsListController(new SongsList(), this);
        musicPlayerController = new MusicPlayerController();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = new Random().nextInt(20);
                musicPlayerController.playSong(songsListController.getSongsList().getSongAt(r));
                welcomeText.setText(musicPlayerController.getSong().getTitle() + " is now playing");
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}