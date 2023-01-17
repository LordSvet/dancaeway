package com.example.dancway.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dancway.R;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongQueue;
import com.example.dancway.view.ModeSelectionActivity;
import com.example.dancway.view.SongPlayerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongsQueueAdapter extends BaseAdapter {
    private List<Song> arrayToSet;
    private Context context;


    public SongsQueueAdapter(List<Song> listToSet, Context context) {
        this.arrayToSet = listToSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayToSet.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayToSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_sheet_queue_item, parent, false);
        ImageView upvoteButton = view.findViewById(R.id.upvoteEmojiButton);
        ImageView downvoteButton = view.findViewById(R.id.downvoteEmojiButton);
        TextView textView = view.findViewById(R.id.queueSheetTextView);
        textView.setText(arrayToSet.get(position).getTitle() + " \tLikes: " + arrayToSet.get(position).getNrOfLikes());

        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Song upVoted = arrayToSet.get(position);
                int positionToVote = SongQueue.findPosInUpcomingQueue(upVoted.getTitle());
                ModeSelectionActivity.upcomingSongs.get(positionToVote).incrementLikes();
                sortListByVotes(arrayToSet);
                notifyDataSetChanged();

            }
        });

        downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Song downVoted = arrayToSet.get(position);
                int positionToVote = SongQueue.findPosInUpcomingQueue(downVoted.getTitle());
                ModeSelectionActivity.upcomingSongs.get(positionToVote).decrementLikes();
                sortListByVotes(arrayToSet);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void updateDataChanged(List<Song> list) {
        arrayToSet = list;
        notifyDataSetChanged();
    }

    public void sortListByVotes(List<Song> arrayList){
        Collections.sort(arrayList, new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                if (song1.getNrOfLikes() < song2.getNrOfLikes()) {
                    return 1;
                } else if (song1.getNrOfLikes() > song2.getNrOfLikes()) {
                    return -1;
                }
                return 0;            }
        });
    }

}

