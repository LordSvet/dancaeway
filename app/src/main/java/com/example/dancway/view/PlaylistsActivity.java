package com.example.dancway.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dancway.R;
import com.example.dancway.model.Playlists;

/**
 * Activity that shows a list of all playlists and user has the option to add or delete playlists
 */
public class PlaylistsActivity extends AppCompatActivity {
    Button buttonCreateList;
    ArrayAdapter<String> adapter;



    @Override
    protected void onPause() {


        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        adapter = new ArrayAdapter<>(this, R.layout.playlists_list_view_item, Playlists.getInstance().getNames());
        ListView listView = findViewById(R.id.playlistsListView);

        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        buttonCreateList = findViewById(R.id.playlistsCreateButton);
        buttonCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlaylistsActivity.this, SinglePlaylistActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("name",Playlists.getInstance().getPlaylistsList().get(i).getName());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
    }

    /**
     * Opens menu with option to delete the playlist
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.playlist_options_menu, menu);
    }

    /**
     * Deletes the playlist that user selected to remove
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if (item.getItemId() == R.id.delete_playlist_menu_item) {
            Playlists.getInstance().removePlaylist(index);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Loads dialog to create playlist when user clicks the add playlist button
     */
    public void showPopup() {
        CreatePlaylistDialog dialog = new CreatePlaylistDialog();
        dialog.show(getSupportFragmentManager(),"Create Playlist");
    }
}