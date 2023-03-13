package com.example.QArmy;

import static org.junit.Assert.assertEquals;

import com.example.QArmy.model.User;
import com.example.QArmy.model.UserComments;

import org.junit.Test;

public class TestUserComments {
    @Test
    public void testUserCommentSetters() {
        UserComments testComment = new UserComments("TestUsername","TestComment");
        assertEquals("TestUsername", testComment.getUsername());
        assertEquals("TestComment",testComment.getTextMessage());
        testComment.setUsername("NewUsername");
        testComment.setTextMessage("NewMessage");
        assertEquals("NewUsername",testComment.getUsername());
        assertEquals("NewMessage",testComment.getTextMessage());
    }
}
