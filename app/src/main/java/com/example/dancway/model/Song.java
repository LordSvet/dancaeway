package com.example.dancway.model;

/**
 * This Song class holds all data that are relevant for the songs
 */
public class Song {
    private String title;
    private long duration;
    private Artist artist;
    private String url;
    private int nrOfLikes;


    public Song(String title, long duration, Artist artist, String url){
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.url = url;
        this.nrOfLikes = 0; // default value
    }

    public Song(String title, String url){
        this.title = title;
        this.url = url;
        nrOfLikes = 0;
    }

    public Artist getArtist(){return artist;}

    public void setArtist(Artist newArtist){artist = newArtist;}

    public void setTitle(String newTitle){title = newTitle;}

    public String getTitle(){return title;}

    public void setUrl(String newUrl){url = newUrl;}

    public String getUrl(){return url;}

    public void setDuration(long newDuration){duration = newDuration;}

    public long getDuration(){return duration;}

    public int getNrOfLikes() {
        return nrOfLikes;
    }

    public void setNrOfLikes(int nrOfLikes) {
        this.nrOfLikes = nrOfLikes;
    }

}
