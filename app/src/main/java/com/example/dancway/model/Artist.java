package com.example.dancway.model;
import java.util.ArrayList;

/**
 * This is the Class that holds Artist's info
 */
public class Artist {       // Very basic for now will add functionalities in later increment
    private String name;
    private ArrayList<Song> listOfTracks;

    /**
     * Constructor which loads artist's tracks
     * @param name name of artist
     */
    public Artist(String name){
        this.name = name;
        listOfTracks = loadArtistTracks();
    }

    /**
     *
     * @return the name of the artist
     */
    public String getName(){return name;}

    public void setName(String newName){name = newName;}

    /**
     * @return the total number of songs belong to that artist
     */
    public int getNumberOfTracks(){return listOfTracks.size();}

    /**
     * @return the songs belong to a artist
     */
    private ArrayList<Song> loadArtistTracks(){return listOfTracks;} //TODO: Method will be finished in later increment
}
