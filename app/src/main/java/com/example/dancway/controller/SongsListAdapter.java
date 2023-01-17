package com.example.dancway.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dancway.R;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongsList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Custom adapter for filling every view in a RecyclerView
 */
public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {

    private SongsList listOfSongs;
    private RecyclerViewInterface recyclerViewInterface;

    /**
     * Constructor
     * @param listOfSongs list of all Songs
     * @param recyclerViewInterface Interface for implementing the onItemClicked for the custom RecyclerView
     */
    public SongsListAdapter(SongsList listOfSongs, RecyclerViewInterface recyclerViewInterface) {
        this.listOfSongs = listOfSongs;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     * Inner class ViewHolder represents each view which holds a song title for now in the TextView songTitle
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView songTitle;
        ImageView songImage;
        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle_In_ListView);
            songImage = itemView.findViewById(R.id.image_view_recycler);
            itemView.setOnClickListener(new View.OnClickListener() {

                /**
                 * Overriding onClick from our recyclerViewInterface interface
                 * @param view The view that was clicked
                 */
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClicked(position);
                        }
                    }

                }
            });

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),R.id.add_song_to_queue,0,"Add song to queue");
        }
    }

    /**
     * Inflates the view and creates new ViewHolder
     * @param parent  type of Viewgroup
     * @param viewType basically an enumeration of Viewgroup
     * @return a new viewholder
     */
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_view_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    /**
     * Setting of each view's title in ViewHolder
     * @param holder Holding the song
     * @param position The positoin of the song
     */
    @Override
    public void onBindViewHolder(SongsListAdapter.ViewHolder holder, int position) {
        Song myData = listOfSongs.getSongAt(position);
        holder.songTitle.setText(myData.getTitle() + " - " + myData.getArtist().getName());
        holder.songImage.setImageBitmap(myData.getBitmap());
    }

    /**
     * @return the size of the list
     */
    @Override
    public int getItemCount() {
        return listOfSongs.getSize();
    }


}
