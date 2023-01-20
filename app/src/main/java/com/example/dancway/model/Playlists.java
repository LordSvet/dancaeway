package com.example.dancway.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

//Object has ArrayList of ArrayList of Songs which means it's a list of all playlists
public class Playlists {
    private ArrayList<SinglePlaylist> playlistsList;  //List of playlists of current user
    private static Playlists listOfPlaylists;
    private ArrayList<String> namesOfLists;

    private Playlists(){
        playlistsList = new ArrayList<>();
        namesOfLists = new ArrayList<>();
    }

    /**
     * Gets the one instance of the Playlists class. Runs as a singleton
     * @return Returns the one running instance
     */
    public static Playlists getInstance(){
        if(listOfPlaylists == null){
            listOfPlaylists = new Playlists();
        }
        return listOfPlaylists;
    }


    /**
     *
     * @return Returns number of Playlists
     */
    public int getNoOfPlaylists(){return playlistsList.size();}

    /**
     * Returns a playlist at a certain index
     * @param indexOfPlaylist index of palylist to return
     * @return Returns playlist at index indexOfPlaylist
     */
    public SinglePlaylist getPlaylistAt(int indexOfPlaylist){return playlistsList.get(indexOfPlaylist);}

    /**
     * Adds a new playlist to the list
     * @param newList  new playlist to be added
     */
    public void addPlaylist(SinglePlaylist newList){
        for(SinglePlaylist playlist : playlistsList){
            if(playlist.getName().equals(newList.getName())){
                return;
            }
        }
        playlistsList.add(newList);
        namesOfLists.add(newList.getName());
    }

    /**
     * Adds a song to a certain playlist at index indexOfPlaylist
     * @param song  song to add
     * @param indexOfPlaylist index of playlist to add it at
     */
    public void addSongToPlaylist(Song song, int indexOfPlaylist){
        playlistsList.get(indexOfPlaylist).addSong(song);
    }

    /**
     *
     * @return Returns list of Playlists
     */
    public ArrayList<SinglePlaylist> getPlaylistsList(){return playlistsList;}

    /**
     * Removes playlist at certain index
     * @param index index of playlist to remove
     */
    public void removePlaylist(int index){
        namesOfLists.remove(playlistsList.get(index).getName());
        playlistsList.remove(index);
    }

    /**
     *
     * @return Returns names of all lists
     */
    public ArrayList<String> getNames(){
        return namesOfLists;
    }


}
