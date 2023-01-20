package com.example.dancway.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.Playlists;
import com.example.dancway.model.SinglePlaylist;

/**
 * Activity which offers a list of all songs. User can select multiple songs that they want added to the playlist
 */
public class SelectSongsActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    boolean[] checkedItems;
    int positionOfOGList;
    SinglePlaylist currentPlaylist;

    @Override
    public void onBackPressed() {
        if(listView != null) {
            SparseBooleanArray selectedItems = listView.getCheckedItemPositions();
            for (int i = 0; i < selectedItems.size(); i++) {
                int position = selectedItems.keyAt(i);
                if (selectedItems.valueAt(i)) {
                    Playlists.getInstance().addSongToPlaylist(SongsListController.getSongsList().getSongAt(position), positionOfOGList);
                }

                if(!selectedItems.valueAt(i) && currentPlaylist.songExistsInPlaylist(SongsListController.getSongsList().getSongAt(position).getTitle())){
                    int indexToDelete = currentPlaylist.getIndexFromTitle(SongsListController.getSongsList().getSongAt(position).getTitle());
                    currentPlaylist.removeSongAt(indexToDelete);
                }
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_songs);
        listView = findViewById(R.id.add_songs_list);

        positionOfOGList = getIntent().getIntExtra("position", 0);
        currentPlaylist = Playlists.getInstance().getPlaylistAt(positionOfOGList);
        checkedItems = getIntent().getBooleanArrayExtra("songsThatAreChecked");





        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, SongsListController.getSongsList().getNamesList());

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        for(int i = 0; i < checkedItems.length;i++){
            if(checkedItems[i]){
                listView.setItemChecked(i, true);
            }
        }

    }



}