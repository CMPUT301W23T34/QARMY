package com.example.QArmy;

import static org.junit.Assert.assertEquals;

import com.example.QArmy.model.UserComments;

import org.junit.Test;

public class TestUserComments {
    @Test
    public void testUserCommentSetters() {
        UserComments testComment = new UserComments("TestUsername","TestComment", "TestID");
        assertEquals("TestUsername", testComment.getUser());
        assertEquals("TestComment",testComment.getText());
        testComment.setUser("NewUsername");
        testComment.setText("NewMessage");
        assertEquals("NewUsername",testComment.getUser());
        assertEquals("NewMessage",testComment.getText());
    }
}
