package com.example.dancway.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ArtistTest {
    private Artist exampleArtist;

    @Before
    public void createArtistExample(){

        exampleArtist = new Artist("Snoop Dogg");
    }

    // Test of getName
    @Test
    public void testGetName(){
        String artistName = exampleArtist.getName();
        assertEquals(exampleArtist.getName(),artistName);
    }

    // Test of setName
    @Test
    public void testSetName(){
        Artist artistName = new Artist("Snoop Dog");
        exampleArtist.setName("Snoop Dog");
        assertEquals(exampleArtist.getName(),artistName.getName());
    }




}