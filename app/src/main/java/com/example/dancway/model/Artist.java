package com.example.dancway.model;
import java.util.ArrayList;

public class Artist {       //Class that holds Artist's info. Very basic for now will add functionalities in later increment
    private String name;
    private ArrayList<Song> listOfTracks;

    public Artist(String name){
        this.name = name;
        listOfTracks = loadArtistTracks();
    }

    public String getName(){return name;}

    public void setName(String newName){name = newName;}

    public int getNumberOfTracks(){return listOfTracks.size();}

    public ArrayList<Song> loadArtistTracks(){return listOfTracks;} //Method will be finished in later increment
}
