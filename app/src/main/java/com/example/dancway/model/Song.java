
package com.example.dancway.model;

/**
 * The class that holds all data that is relevant for the song
 */
public class Song {
    private String title;
    private long duration;
    private Artist artist;
    private String url;
    private int nrOfLikes;

    /**
     * @param title sets the title of the song
     * @param duration sets the duration of the song
     * @param artist sets the artist of the song
     * @param url sets the url of the song
     */
    public Song(String title, long duration, Artist artist, String url){
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.url = url;
    }

    /**
     * @return the artist of the song
     */
    public Artist getArtist(){return artist;}

    /**
     * @param newArtist reassigns the name of the artist of the song
     */
    public void setArtist(Artist newArtist){artist = newArtist;}

    /**
     * @param newTitle reassigns the title of the song
     */
    public void setTitle(String newTitle){title = newTitle;}

    /**
     * @return the title of the song
     */
    public String getTitle(){return title;}

    /**
     * @param newUrl reassigns the url of the song
     */
    public void setUrl(String newUrl){url = newUrl;}

    /**
     * @return the url of the song
     */
    public String getUrl(){return url;}

    /**
     * @param newDuration reassigns a new duration of the song
     */
    public void setDuration(long newDuration){duration = newDuration;}

    /**
     * @return duration of the song
     */
    public long getDuration(){return duration;}

    /**
     * @return number of upvotes for the song
     */
    public int getNrOfLikes() {
        return nrOfLikes;
    }

    /**
     * @param nrOfLikes reassigns the number of upvotes for the song
     */
    public void setNrOfLikes(int nrOfLikes) {
        this.nrOfLikes = nrOfLikes;
    }

}
