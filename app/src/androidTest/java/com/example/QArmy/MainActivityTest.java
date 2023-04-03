package com.example.QArmy;


import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.CaptureAct;
import com.example.QArmy.UI.MainActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.osmdroid.views.MapView;

import static org.junit.Assert.*;

public class MainActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void testNavigation() {
        solo.clickOnView(solo.getView(R.id.navigation_map));
        assertTrue(solo.waitForView(R.id.map));
        solo.clickOnView(solo.getView(R.id.navigation_rank));
        assertTrue(solo.waitForView(R.id.rank_list));
        solo.clickOnView(solo.getView(R.id.navigation_home));
        assertTrue(solo.waitForView(R.id.qr_code_list));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}