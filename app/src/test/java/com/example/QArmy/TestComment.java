package com.example.QArmy;

import static org.junit.Assert.assertEquals;

import com.example.QArmy.model.Comment;

import org.junit.Test;

public class TestComment {
    @Test
    public void testUserCommentSetters() {
        Comment testComment = new Comment("TestUsername","TestComment", "TestID");
        assertEquals("TestUsername", testComment.getUser());
        assertEquals("TestComment",testComment.getText());
        testComment.setUser("NewUsername");
        testComment.setText("NewMessage");
        assertEquals("NewUsername",testComment.getUser());
        assertEquals("NewMessage",testComment.getText());
    }
}
