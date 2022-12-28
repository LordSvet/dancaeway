package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.dancway.R;
import com.example.dancway.controller.RecyclerViewInterface;
import com.example.dancway.controller.SongsListAdapter;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.service.MusicService;

/**
 * This is the main activity
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewInterface, ServiceConnection {
    RecyclerView songsListView;
    SongsListAdapter listAdapter;
    Thread populateSongsThread;
    MusicService musicService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //--- Bind Music Service ---
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        //--- End Bind Music Service ---

        // Fetch songs list from database in separate thread
        // there are too many thread creation in the App --> A better implementation could exist?!
        populateSongsThread = new Thread() {
            @Override
            public void run() {
                SongsListController.populateSongs();
            }
        };
        populateSongsThread.start();

        Handler handler = new Handler();        // Songs are loaded concurrently so adapter is empty when set so I put a delay of 2 seconds
        handler.postDelayed(new Runnable() {    // This will be changed with later implementations
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
        Intent songPlayerIntent = new Intent(MainActivity.this, SongPlayerActivity.class);
        songPlayerIntent.putExtra("pos", position);
        MainActivity.this.startActivity(songPlayerIntent);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
        musicService = binder.getService();
        Log.d("Connected", musicService + "");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService = null;
        Log.d("Disconnected", musicService + "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }
}
