package com.example.dancway.model;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SongTest {


    private Song exampleSong;

    @Before
    public void createExample(){
        exampleSong = new Song("Smoke weed every day", 420, new Artist("Snoop Dogg"), "exampleURL");
    }

    @Test
    public void testGetArtist() {
        Artist exampleArtist = exampleSong.getArtist();
        assertEquals(exampleSong.getArtist(), exampleArtist);
    }

    @Test
    public void testSetArtist() {
        Artist exampleArtist = new Artist("Dr. Dre");
        exampleSong.setArtist(exampleArtist);
        assertEquals(exampleSong.getArtist(), exampleArtist);
    }

    @Test
    public void testSetTitle() {
        String newTitle = "Nuthin But a G'Thang";
        exampleSong.setTitle(newTitle);
        assertEquals(exampleSong.getTitle(), newTitle);
    }

    @Test
    public void testGetTitle() {
        String title = "Smoke weed every day";
        assertEquals(exampleSong.getTitle(), title);
    }

    @Test
    public void testSetUrl() {
        String newURL = "newExampleURL";
        exampleSong.setUrl(newURL);
        assertEquals(exampleSong.getUrl(), newURL);
    }

    @Test
    public void testGetUrl() {
        String url = "exampleURL";
        assertEquals(exampleSong.getUrl(), url);
    }

    @Test
    public void testSetDuration() {
        long newDuration = 69;
        exampleSong.setDuration(newDuration);
        assertEquals(exampleSong.getDuration(), newDuration);
    }

    @Test
    public void testGetDuration() {
        long duration = 420;
        assertEquals(exampleSong.getDuration(), duration);
    }

    @Test
    public void testGetNrOfLikes() {
        int likes = 0;
        assertEquals(exampleSong.getNrOfLikes(), likes);
    }

    @Test
    public void testSetNrOfLikes() {
        int newLikeNR = 120;
        exampleSong.setNrOfLikes(newLikeNR);
        assertEquals(exampleSong.getNrOfLikes(), newLikeNR);
    }
}