package com.example.dancway.model;

import java.util.ArrayList;

public class SinglePlaylist {
    private ArrayList<Song> playlist;
    private String name;

    public SinglePlaylist(String name, ArrayList<Song> playlist){
        this.playlist = playlist;
        this.name = name;
    }

    /**
     * Adds song to this playlist
     * @param song Song to be added
     */
    public void addSong(Song song){
        for(Song temp: playlist){
            if(temp.getTitle().equals(song.getTitle())){
                return;
            }
        }
        playlist.add(song);
    }

    /**
     *
     * @return Returns name of playlist
     */
    public String getName(){return name;}

    /**
     * Removes song at given index
     * @param index index of where song will be removed from
     */
    public void removeSongAt(int index){
        playlist.remove(index);
    }

    /**
     *
     * @return Returns the playlist as an ArrayList
     */
    public ArrayList<Song> getPlaylist(){
        return playlist;
    }

    /**
     * Returns song at given index
     * @param index given index
     * @return Returns song at index "index"
     */
    public Song getSongAt(int index){
        return playlist.get(index);
    }

    /**
     *
     * @param songTitle Song title to checl
     * @return Returns true if song exists in playlist
     */
    public boolean songExistsInPlaylist(String songTitle){
        for(Song song: playlist){
            if(song.getTitle().equals(songTitle)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets title and uses it to find index of that song
     * @param songTitle Title of Song its searching for
     * @return Returns index of the song in the list
     */
    public int getIndexFromTitle(String songTitle){
        for(int i = 0; i < playlist.size(); i++){
            if(playlist.get(i).getTitle().equals(songTitle)){
                return i;
            }
        }
        return 0;
    }

}
