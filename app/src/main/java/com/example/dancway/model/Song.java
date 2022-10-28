package com.example.dancway.model;

public class Song {     //Holds all data thats relevant for the song
    private String title;
    private long duration;
    private Artist artist;
    private String url;


    public Song(String title, String url){  //Constructor being used for prototype, below constructor is what end version will be closer to
        this.title = title;
        this.url = url;
    }
    public Song(String title, long duration, Artist artist, String url){
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.url = url;
    }

    public Artist getArtist(){return artist;}

    public void setArtist(Artist newArtist){artist = newArtist;}

    public void setTitle(String newTitle){title = newTitle;}

    public String getTitle(){return title;}

    public void setUrl(String newUrl){url = newUrl;}

    public String getUrl(){return url;}

    public void setDuration(long newDuration){duration = newDuration;}

    public long getDuration(){return duration;}

}
