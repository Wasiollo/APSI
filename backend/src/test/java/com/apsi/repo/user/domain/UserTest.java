package com.apsi.repo.user.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;

    @Before
    public void setUser(){
        user = new User();
    }

    @Test
    public void testInitialPoints() throws Exception {
        assertEquals(user.getPoints(), Integer.valueOf(0));
    }

    @Test
    public void testAddedPoints() throws Exception {
        user.addPoints(10);
        assertEquals(user.getPoints(), Integer.valueOf(10));
    }
}
