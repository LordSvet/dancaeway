package com.example.dancway.model;
import java.util.ArrayList;

/**
 * The class holds information about the Artist of a song
 */
public class Artist {
    private String name;
    private ArrayList<Song> listOfTracks;

    /**
     * @param name Sets the artist names
     */
    public Artist(String name){
        this.name = name;
        listOfTracks = loadArtistTracks();
    }

    /**
     * @return name of the artist
     */
    public String getName(){return name;}

    /**
     * @param newName assigns a new name for existing artist object
     */
    public void setName(String newName){name = newName;}

    /**
     * @return the number of songs in the list of songs
     */
    public int getNumberOfTracks(){return listOfTracks.size();}

    /**
     * @return the list of tracks
     */
    private ArrayList<Song> loadArtistTracks(){return listOfTracks;} //Method will be finished in later increment
}