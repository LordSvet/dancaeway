package com.example.dancway.model;

import java.util.ArrayList;
import java.util.List;

public class SongQueue {
    List<Song> Songs = new ArrayList<>();

    public Song getSong(String title) {
        for (Song song : Songs) {
            if (song.getTitle().equals(title)) {
                return song;
            }
        }
        return null;
    }

    public boolean addSong(Song song) {
        return Songs.add(song);
    }

    public void changeOrder(Song song, int newIndex) {
        Songs.remove(song);
        Songs.add(newIndex, song);
    }
}
