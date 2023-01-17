package com.example.dancway.model;

import com.example.dancway.view.ModeSelectionActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The class that represents song's queue
 */
public class SongQueue {

    private PriorityQueue<Song> queue;

    /**
     * Create a new song queue with the specified capacity
     * @param initialCapacity capacity of the queue
     */
    public SongQueue(int initialCapacity) {
        queue = new PriorityQueue<>(initialCapacity, (song1, song2) -> {
            if (song1.getNrOfLikes() < song2.getNrOfLikes()) {
                return 1;
            } else if (song1.getNrOfLikes() > song2.getNrOfLikes()) {
                return -1;
            }
            return 0;
        });
    }

    /**
     * Add new song to the queue
     * @param song The Song object to add
     * @return true if success; else false
     */
    public boolean addSong(Song song) {
        return queue.offer(song);
    }

    /**
     * Clear the song queue
     */
    public void clear() {
        queue.clear();
    }

    /**
     * Get the song queue
     * @return the song queue
     */
    public PriorityQueue<Song> getQueue() {
        return queue;
    }

    /**
     * set the current queue to a new queue
     * @param queue the new queue
     */
    public void setQueue(PriorityQueue<Song> queue) {
        this.queue = queue;
    }

    /**
     * Since Priority Queue cannot be updated dynamically this method uses the title of the song to get the exact instance
     * of the Song object, removes it from the queue, increments the likes by one and adds it back again at the new correct place
     * @param title title of the song that will be upvoted
     */
    public void voteUpForSong(String title){
        for (Song song : queue) {
            if (song.getTitle().equals(title)) {
                queue.remove(song);
                song.incrementLikes();
                queue.add(song);
            }
        }
    }

    /**
     * Since Priority Queue cannot be updated dynamically this method uses the title of the song to get the exact instance
     * of the Song object, removes it from the queue, decrements the likes by one and adds it back again at the new correct place
     * @param title title of the song that will be downvoted
     */
    public void voteDownForSong(String title){
        for (Song song : queue) {
            if (song.getTitle().equals(title)) {
                queue.remove(song);
                song.decrementLikes();
                queue.add(song);
            }
        }
    }

    /**
     * If song already is in queue returns true
     * @param songTitle Title of song that we are checking for
     * @return  returns true in case song exists in queue already and false if it doesn't
     */
    public boolean exists(String songTitle){
        Song[] array = (Song[]) queue.toArray();
        for (Song value : array) {
            if (value.getTitle().equals(songTitle)) {
                return true;
            }
        }
        return false;
    }

    public static void sortListByVotes(ArrayList<Song> arrayList){
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

    public static int findPosInUpcomingQueue(String songTitle){
        for(int i = 0; i < ModeSelectionActivity.upcomingSongs.size();i++){
            if(ModeSelectionActivity.upcomingSongs.get(i).getTitle().equals(songTitle)){
                return i;
            }
        }
        return 0;
    }

}
