package com.example.dancway.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This Song class holds all data that are relevant for the songs
 */
public class Song {
    private String title;
    private long duration;
    private Artist artist;
    private String url;
    private int nrOfLikes;
    private String imageURL;
    private Bitmap bitmap;


    /**
     * Constructor. Sets initial number of likes to 0
     * @param title Song title
     * @param duration Song duration
     * @param artist Song artist
     * @param url Song url
     */
    public Song(String title, long duration, Artist artist, String url, String imageURL){
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.url = url;
        this.nrOfLikes = 0; // default value
        this.imageURL = imageURL;
    }

    public void setBitmap(String url){
        bitmap = getImageFromURL(url);
    }

    //TODO: Maybe we won't need this constructor afterall We must discuss
    public Song(String title, String url){
        this.title = title;
        this.url = url;
        nrOfLikes = 0;
    }

    /**
     *Gets the image URL
     * @return returns image URL
     */
    public String getImageURL(){return imageURL;}

    /**
     * Sets new image URL
     * @param newURL new URL to be set
     */
    public void setImageURL(String newURL){
        this.imageURL = newURL;
    }

    /**
     * Gets the artist of the song
     * @return returns artist
     */
    public Artist getArtist(){return artist;}

    /**
     * sets new Artist to a song
     * @param newArtist new artist to set
     */
    public void setArtist(Artist newArtist){artist = newArtist;}

    /**
     * Sets new title to a song
     * @param newTitle new title to set
     */
    public void setTitle(String newTitle){title = newTitle;}

    /**
     * Gets title of song
     * @return returns title of song
     */
    public String getTitle(){return title;}

    /**
     * Sets new url to a song
     * @param newUrl new url to set
     */
    public void setUrl(String newUrl){url = newUrl;}

    /**
     * Gets url of song
     * @return returns url of song
     */
    public String getUrl(){return url;}

    /**
     * Sets new duration to a song
     * @param newDuration new duration to set
     */
    public void setDuration(long newDuration){duration = newDuration;}

    /**
     * Gets duration of song
     * @return returns duration of song
     */
    public long getDuration(){return duration;}

    /**
     * Gets number of likes of song
     * @return returns number of likes of song
     */
    public int getNrOfLikes() {
        return nrOfLikes;
    }

    /**
     * Sets new number of likes to a song
     * @param nrOfLikes new number of likes to set
     */
    public void setNrOfLikes(int nrOfLikes) {
        this.nrOfLikes = nrOfLikes;
    }

    public void incrementLikes(){this.nrOfLikes++;}

    public void decrementLikes(){this.nrOfLikes--;}

    public static Bitmap getImageFromURL(String url){
        try {
            URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream iStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(iStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getBitmap(){return bitmap;}

}
