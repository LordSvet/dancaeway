package com.example.dancway.model;

import java.util.ArrayList;

public class SinglePlaylist {
    private ArrayList<Song> playlist;
    private String name;

    public SinglePlaylist(String name, ArrayList<Song> playlist){
        this.playlist = playlist;
        this.name = name;
    }

    public void addSong(Song song){
        for(Song temp: playlist){
            if(temp.getTitle().equals(song.getTitle())){
                return;
            }
        }
        playlist.add(song);
    }

    public String getName(){return name;}

    public void removeSongAt(int index){
        playlist.remove(index);
    }

    public ArrayList<Song> getPlaylist(){
        return playlist;
    }

    public Song getSongAt(int index){
        return playlist.get(index);
    }

    public boolean songExistsInPlaylist(String songTitle){
        for(Song song: playlist){
            if(song.getTitle().equals(songTitle)){
                return true;
            }
        }
        return false;
    }

    public int getIndexFromTitle(String songTitle){
        for(int i = 0; i < playlist.size(); i++){
            if(playlist.get(i).getTitle().equals(songTitle)){
                return i;
            }
        }
        return 0;
    }

}
