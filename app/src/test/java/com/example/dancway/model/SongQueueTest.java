package com.example.dancway.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;

public class SongQueueTest {

    private SongQueue exampleSongQueue;
    private Song exampleSong;

    @Before
    public void createExample(){

        exampleSongQueue = new SongQueue(5);
        exampleSong = new Song("Smoke weed evey day", 420, new Artist("Snoop Dogg"), "exampleURL");

    }


    // Test of addSong
    @Test
    public void testAddSong(){

        Boolean song = exampleSongQueue.addSong(exampleSong);
        assertEquals(exampleSongQueue.addSong(exampleSong),song);
    }

        //Test of Clear
    @Test
    public void testClear(){

          exampleSongQueue.clear();
          assertTrue(exampleSongQueue.getQueue().isEmpty());

    }
    // Test of getQueue
    @Test
    public void testGetQueue(){

        PriorityQueue<Song>  queue = exampleSongQueue.getQueue();
        assertEquals(exampleSongQueue.getQueue(),queue);

    }

    // Test of setQueue
    @Test
    public void testSetQueue(){

        PriorityQueue<Song>  queue = new PriorityQueue<Song>();
        exampleSongQueue.setQueue(queue);
        assertEquals(exampleSongQueue.getQueue(),queue);
    }







}