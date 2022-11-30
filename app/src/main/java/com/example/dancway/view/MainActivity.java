package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dancway.R;
import com.example.dancway.controller.RecyclerViewInterface;
import com.example.dancway.controller.SongsListAdapter;
import com.example.dancway.controller.SongsListController;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView songsListView;
    SongsListAdapter listAdapter;
    Thread populateSongsThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Fetch songs list from database in separate thread
        // there are too many thread creation in the App --> A better implementation could exist?!
        populateSongsThread = new Thread() {
            @Override
            public void run() {
                SongsListController.populateSongs();
            }
        };
        populateSongsThread.start();

        Handler handler = new Handler();        //Songs are loaded concurrently so adapter is empty when set so I put a delay of 2 seconds
        handler.postDelayed(new Runnable() {    //This will be changed with later implementations
            @Override
            public void run() {
                try {
                    setListAdapter();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }

    public void setListAdapter() throws InterruptedException {
        populateSongsThread.join();
        listAdapter = new SongsListAdapter(SongsListController.getSongsList(),this);
        songsListView = findViewById(R.id.main_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        songsListView.setLayoutManager(layoutManager);
        songsListView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        // Start SongPlayer activity and pass the position of clicked song
        Intent songPlayerIntent = new Intent(MainActivity.this, SongPlayer.class);
        songPlayerIntent.putExtra("pos", position);
        MainActivity.this.startActivity(songPlayerIntent);
    }
}