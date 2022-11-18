package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerController;
import com.example.dancway.controller.RecyclerViewInterface;
import com.example.dancway.controller.SongsListAdapter;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.SongsList;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView songsListView;
    SongsListController songsListController;
    SongsListAdapter listAdapter;
    MusicPlayerController musicPlayerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        songsListController = new SongsListController(new SongsList(), this);
        musicPlayerController = new MusicPlayerController();

        Handler handler = new Handler();        //Songs are loaded concurrently so adapter is empty when set so I put a delay of 2 seconds
        handler.postDelayed(new Runnable() {    //This will be changed with later implementations
            @Override
            public void run() {
                setListAdapter();
            }
        }, 2000);

    }
    public void setListAdapter(){
        listAdapter = new SongsListAdapter(songsListController.getSongsList(),this);
        songsListView = findViewById(R.id.main_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        songsListView.setLayoutManager(layoutManager);
        songsListView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        musicPlayerController.playSong(songsListController.getSongsList().getSongAt(position));
    }
}