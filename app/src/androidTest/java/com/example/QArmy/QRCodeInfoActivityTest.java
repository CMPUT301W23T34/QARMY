package com.example.QArmy;


import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.qrcodes.QRCodeInfoActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class QRCodeInfoActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<QRCodeInfoActivity> rule = new ActivityTestRule<>(QRCodeInfoActivity.class, true, false);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        Intent intent = new Intent();
        intent.putExtra("Object", "testname, testhash");
        rule.launchActivity(intent);
    }

    @Test
    public void testNavigation() {
        assertTrue(solo.waitForText("Lat"));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}