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
import static org.junit.Assert.*;

public class RankFragmentTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.scrollToSide(Solo.RIGHT);
    }

    @Test
    public void testRankFragment() {
        assertTrue(solo.waitForView(R.id.rank_list));
        assertTrue(solo.waitForText("Search"));
        // assert that when you click on search and type in letter, mock name comes up
        // assert that when it comes up, mock name matches rank in database
        // assert that pressing clear goes back to normal list (top score is back)
        // assert that user currently using activity's rank is correct at bottom
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}