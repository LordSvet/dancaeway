package com.example.dancway.model;

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

}
