package com.example.dancway.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.controller.RecyclerViewInterface;
import com.example.dancway.controller.SongsListAdapter;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.Song;

/**
 * This is the main activity
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView songsListView;
    SongsListAdapter listAdapter;
    MusicPlayerControllerSingleton musicPlayerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicPlayerController = MusicPlayerControllerSingleton.getInstance();

        try {
            setListAdapter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registerForContextMenu(songsListView);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);
        ModeSelectionActivity.upcomingSongs.add(SongsListController.getSongsList().getSongAt(item.getGroupId()));
        return true;
    }

    public void setListAdapter() throws InterruptedException {
        listAdapter = new SongsListAdapter(SongsListController.getSongsList(),this);
        songsListView = findViewById(R.id.main_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        songsListView.setLayoutManager(layoutManager);
        songsListView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        // Start SongPlayer activity and pass the position of clicked song
        Song chosenSong = SongsListController.getSongsList().getSongAt(position);
        Intent songPlayerIntent = new Intent(MainActivity.this, SongPlayerActivity.class);
        if(ModeSelectionActivity.upcomingSongs.isEmpty()) {
            for (int i = position; i < SongsListController.getSongsList().getSize(); i++) {
                ModeSelectionActivity.upcomingSongs.add(SongsListController.getSongsList().getSongAt(i));
            }
            songPlayerIntent.putExtra("pos",0);
        }else {
            Song currentSong = musicPlayerController.getSong();
            for(int i = 0; i < ModeSelectionActivity.upcomingSongs.size();i++){
                if(currentSong.getTitle().equals(ModeSelectionActivity.upcomingSongs.get(i).getTitle())){
                    ModeSelectionActivity.upcomingSongs.add(i+1, chosenSong);
                    songPlayerIntent.putExtra("pos", i+1);
                    break;
                }
            }
        }
        MainActivity.this.startActivity(songPlayerIntent);
    }
}