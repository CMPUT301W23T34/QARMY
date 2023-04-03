package com.example.QArmy;


import android.app.Activity;
import android.content.Intent;

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
    public ActivityTestRule<CommentsActivity> rule = new ActivityTestRule<>(CommentsActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("Object", "nmelo9a7cd5efda286fbcdd26f89e64a360c560208248b301ff49ad670cb5552790ff");
        rule.launchActivity(intent);
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void testNavigation() {
        assertTrue(solo.waitForText("nick"));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}