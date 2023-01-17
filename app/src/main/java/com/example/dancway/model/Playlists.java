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

    public static Playlists getInstance(){
        if(listOfPlaylists == null){
            listOfPlaylists = new Playlists();
        }
        return listOfPlaylists;
    }


    public int getNoOfPlaylists(){return playlistsList.size();}

    public void setPlaylistsList(ArrayList<SinglePlaylist> playlistsList){
        this.playlistsList = playlistsList;
    }

    public SinglePlaylist getPlaylistAt(int indexOfPlaylist){return playlistsList.get(indexOfPlaylist);}

    public void addPlaylist(SinglePlaylist newList){
        for(SinglePlaylist playlist : playlistsList){
            if(playlist.getName().equals(newList.getName())){
                return;
            }
        }
        playlistsList.add(newList);
        namesOfLists.add(newList.getName());
    }

    public void addSongToPlaylist(Song song, int indexOfPlaylist){
        playlistsList.get(indexOfPlaylist).addSong(song);
    }

    public ArrayList<SinglePlaylist> getPlaylistsList(){return playlistsList;}

    public void removePlaylist(int index){
        namesOfLists.remove(playlistsList.get(index).getName());
        playlistsList.remove(index);
    }

    public ArrayList<String> getNames(){
        return namesOfLists;
    }


}
