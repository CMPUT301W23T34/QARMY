/*
 * CommentsActivityTest
 *
 * Version: 1.0
 *
 * Date: 2023-04-02
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy;


import android.content.Intent;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.qrcodes.CommentsActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

/**
 * Test the CommentsActivity class.
 * @version 1.0
 * @author Kai Luedemann
 */
public class CommentsActivityTest {
    private Solo solo;
    private final int timeout = 1000;

    @Rule
    public ActivityTestRule<CommentsActivity> rule = new ActivityTestRule<>(CommentsActivity.class, true, false);

    /**
     * Launch the activity with a QRCode.
     */
    @Before
    public void setUp() {
        QRCode qrCode = new QRCode("CommentTest", new User("test"), null, new Date());
        Intent intent = new Intent();
        intent.putExtra("QRCode", qrCode);


        rule.launchActivity(intent);
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Test that comments appear.
     */
    @Test
    public void testCommentsAppear() {
        assertTrue(solo.waitForText("other comment", 1, timeout));
        assertTrue(solo.waitForText("nottest", 1, timeout));
        assertTrue(solo.waitForView(R.id.deleteButton, 1, timeout));
    }

    /**
     * Test adding and deleting new comment.
     */
    @Test
    public void testAddDeleteComments() {
        solo.enterText((EditText) solo.getView(R.id.commentEditText), "New comment");
        solo.clickOnView(solo.getView(R.id.submitButton));
        assertTrue(solo.waitForText("New comment", 1, timeout));
        solo.clickOnView(solo.getView(R.id.deleteButton, 0));
        assertFalse(solo.waitForText("New comment",1, timeout));
    }

    /**
     * Test that an empty comment is not added.
     */
    @Test
    public void testEmptyComment() {
        solo.clickOnView(solo.getView(R.id.submitButton));
        assertFalse(solo.waitForText("test", 2, timeout));
    }

    /**
     * Ensure that other users' comments cannot be deleted.
     */
    @Test
    public void testDeleteOtherUserComment() {
        solo.clickOnView(solo.getView(R.id.deleteButton, 0));
        assertTrue(solo.waitForText("other comment", 1, timeout));
    }

    /**
     * Finish activity after tests.
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}