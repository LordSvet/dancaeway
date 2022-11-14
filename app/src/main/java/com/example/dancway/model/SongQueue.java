package com.example.dancway.model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SongQueue {

    private PriorityQueue<Song> queue;
    private int initialCapacity;

    public SongQueue(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        queue = new PriorityQueue<>(initialCapacity, new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                if (song1.getNrOfLikes() < song2.getNrOfLikes()) {
                    return 1;
                } else if (song1.getNrOfLikes() > song2.getNrOfLikes()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public boolean addSong(Song song) {
        return queue.offer(song);
    }

    public void clear() {
        queue.clear();
    }

    public PriorityQueue<Song> getQueue() {
        return queue;
    }

    public void setQueue(PriorityQueue<Song> queue) {
        this.queue = queue;
    }

}
