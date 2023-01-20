package com.example.dancway.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.controller.RecyclerViewInterface;
import com.example.dancway.controller.SongsListAdapter;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.Playlists;
import com.example.dancway.model.SinglePlaylist;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongsList;

import java.util.ArrayList;

/**
 * Shows selected playlist. User has the option to choose which songs are in it and can add songs to queue or play the song
 */
public class SinglePlaylistActivity extends AppCompatActivity implements RecyclerViewInterface {
    SongsListAdapter listAdapter;
    RecyclerView songsListView;
    SinglePlaylist songs;
    MusicPlayerControllerSingleton musicPlayerController;
    TextView nameView;
    Button addSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_playlist);

        nameView = findViewById(R.id.playlist_name);
        songsListView = findViewById(R.id.playlistsRecyclerView);
        addSong = findViewById(R.id.add_song_to_playlist);

        int positionOfList = getIntent().getIntExtra("position", 0);
        String name = getIntent().getStringExtra("name");

        nameView.setText(name);
        songs = Playlists.getInstance().getPlaylistAt(positionOfList);
        musicPlayerController = MusicPlayerControllerSingleton.getInstance();

        addSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SinglePlaylistActivity.this, SelectSongsActivity.class);
                intent.putExtra("position", positionOfList);
                boolean[] addedSongs = new boolean[SongsListController.getSongsList().getSize()];
                for (int i = 0; i < songs.getPlaylist().size(); i++) {
                    addedSongs[SongsListController.getIndexOfSong(songs.getPlaylist().get(i).getTitle())] = true;
                }
                intent.putExtra("songsThatAreChecked", addedSongs);
                startActivity(intent);

            }
        });

        setListAdapter();

    }

    @Override
    protected void onResume() {
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    private void setListAdapter() {

        SongsList list = new SongsList(songs.getPlaylist());
        listAdapter = new SongsListAdapter(list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        songsListView.setLayoutManager(layoutManager);
        songsListView.setAdapter(listAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        Song chosenSong = songs.getSongAt(position);
        Intent songPlayerIntent = new Intent(this, SongPlayerActivity.class);
        if (ModeSelectionActivity.upcomingSongs.isEmpty()) {
            for (int i = position; i < songs.getPlaylist().size(); i++) {
                ModeSelectionActivity.upcomingSongs.add(songs.getSongAt(i));
            }
            songPlayerIntent.putExtra("pos", 0);
        } else {
            Song currentSong = musicPlayerController.getSong();
            if (currentSong == null) {
                songPlayerIntent.putExtra("pos", 0);
            }
            for (int i = 0; i < ModeSelectionActivity.upcomingSongs.size(); i++) {
                if (currentSong != null && currentSong.getTitle().equals(ModeSelectionActivity.upcomingSongs.get(i).getTitle())) {
                    ModeSelectionActivity.upcomingSongs.add(i + 1, chosenSong);
                    songPlayerIntent.putExtra("pos", i + 1);
                    break;
                }
            }
        }
        startActivity(songPlayerIntent);
    }

}