package com.example.QArmy;


import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.CaptureAct;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.qrcodes.CommentsActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.osmdroid.views.MapView;

import static org.junit.Assert.*;

public class CommentsActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<CommentsActivity> rule = new ActivityTestRule<>(CommentsActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void testNavigation() {
        assertTrue(solo.waitForText("Only"));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}