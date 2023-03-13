package com.example.QArmy;

import static org.junit.Assert.assertEquals;

import com.example.QArmy.model.User;

import org.junit.Test;

public class TestUser {
    @Test
    public void testUserGetters() {
        User testUser = new User("TestUser", "TestEmail", "1234567890");
        assertEquals("TestUser", testUser.getName());
        assertEquals("TestEmail", testUser.getEmail());
        assertEquals("1234567890", testUser.getPhone());
    }
}
