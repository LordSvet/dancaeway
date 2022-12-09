package com.example.dancway.model;

import static org.junit.Assert.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class SessionTest {


    private Session exampleSession;

    // We need to use before so the test class can use the Session class
    @Before
    public void createExample(){
        exampleSession = new Session("sample_id",User.getCurrentUser());
    }

    // Test of getID
    @Test
    public void testGetID(){
        String session = exampleSession.getId();
        assertEquals(exampleSession.getId(), session);

    }
    // Test of getUser
    @Test
    public void testGetUser(){
        User user = exampleSession.getUser();
        assertEquals(exampleSession.getUser(),user);
    }
    // Test of setID
    @Test
    public void testSetId(){
        String id = "ID1";
        exampleSession.setId(id);
        assertEquals(exampleSession.getId(),id);
    }

}