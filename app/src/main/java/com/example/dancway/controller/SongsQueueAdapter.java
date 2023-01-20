package com.example.dancway.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dancway.R;
import com.example.dancway.model.PartyMode;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongQueue;
import com.example.dancway.view.JoinPartyActivity;
import com.example.dancway.view.ModeSelectionActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SongsQueueAdapter extends BaseAdapter {
    private List<Song> arrayToSet;
    private Context context;

    boolean shouldBeDisabled = false;

    PartyMode partyMan = JoinPartyActivity.partyMode;


    /**
     * Constructor
     * @param listToSet list that will be used to display in the list view
     * @param context context is passed in order to make changes to the Activity, such as toasts
     */
    public SongsQueueAdapter(List<Song> listToSet, Context context) {
        this.arrayToSet = listToSet;
        this.context = context;
    }

    /**
     *
     * @return Returns size of list
     */
    @Override
    public int getCount() {
        return arrayToSet.size();
    }

    /**
     *
     * @param i position of item
     * @return  Returns item that was called for at position i
     */
    @Override
    public Object getItem(int i) {
        return arrayToSet.get(i);
    }

    /**
     *
     * @param i position of item
     * @return  returns position of the item
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Overriden method getView is called every time a new view is loaded or interacted with.
     * @param position  position of loaded view
     * @param convertView  view that was selected
     * @param parent group of views
     * @return  returns the view that is to be updated on the UI
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_sheet_queue_item, parent, false);

        ImageView upvoteButton = view.findViewById(R.id.upvoteEmojiButton);

        ImageView downvoteButton = view.findViewById(R.id.downvoteEmojiButton);
        if(shouldBeDisabled) {
            downvoteButton.setEnabled(false);
            downvoteButton.setAlpha(.5f);
            upvoteButton.setEnabled(false);
            upvoteButton.setAlpha(.5f);
        }
        TextView textView = view.findViewById(R.id.queueSheetTextView);
        String toSet = arrayToSet.get(position).getTitle() + " | \tLikes: " + arrayToSet.get(position).getNrOfLikes();
        textView.setText(toSet);

        upvoteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Upvotes song when the button is clicked and disables song liking for 4 minutes
             * @param view view that was clicked
             */
            @Override
            public void onClick(View view) {
                Song upVoted = arrayToSet.get(position);
                int positionToVote = SongQueue.findPosInUpcomingQueue(upVoted.getTitle());
                ModeSelectionActivity.upcomingSongs.get(positionToVote).incrementLikes();
                sortListByVotes(arrayToSet);

                if(partyMan != null && partyMan.isParty()) {
                    shouldBeDisabled = true;
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            shouldBeDisabled = false;
                        }
                    }, 240000); //User can vote once every 4 minutes
                }

                notifyDataSetChanged();
            }
        });

        downvoteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Downvotes song when the button is clicked and disables song liking for 4 minutes
             * @param view view that was clicked
             */
            @Override
            public void onClick(View view) {
                Song downVoted = arrayToSet.get(position);
                int positionToVote = SongQueue.findPosInUpcomingQueue(downVoted.getTitle());
                ModeSelectionActivity.upcomingSongs.get(positionToVote).decrementLikes();
                sortListByVotes(arrayToSet);

                if(partyMan != null && partyMan.isParty()) {
                    shouldBeDisabled = true;
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            shouldBeDisabled = false;
                        }
                    }, 240000);
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }

    /**
     * Sets the current list to a new one and updates the ListView on the UI
     * @param list new list to be set
     */
    public void updateDataChanged(List<Song> list) {
        arrayToSet = list;
        notifyDataSetChanged();
    }

    /**
     * Static method that sorts a passed array list by the songs' votes
     * @param arrayList arrayList to be sorted
     */
    public static void sortListByVotes(List<Song> arrayList){
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

