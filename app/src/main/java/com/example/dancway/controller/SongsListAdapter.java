package com.example.dancway.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dancway.R;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongsList;

import java.util.List;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {

    private SongsList listOfSongs;
    private RecyclerViewInterface recyclerViewInterface;

    public SongsListAdapter(SongsList listOfSongs, RecyclerViewInterface recyclerViewInterface) {
        this.listOfSongs = listOfSongs;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView songTitle;
        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle_In_ListView);
            itemView.setOnClickListener(new View.OnClickListener() {
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
        }}


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_view_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(SongsListAdapter.ViewHolder holder, int position) {
        Song myData = listOfSongs.getSongAt(position);
        holder.songTitle.setText(myData.getTitle() + " - " + myData.getArtist().getName());
    }

    @Override
    public int getItemCount() {
        return listOfSongs.getSize();
    }
}
