package com.example.QArmy;


import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.CaptureAct;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.OtherUserProfile;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class OtherUserProfileTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.sleep(5000);
        solo.scrollToSide(Solo.RIGHT);
    }

    @Test
    public void testRankFragment() {

        // MUST CHANGE HARDCODED NAMES/RANKS TO MOCK STUFF

        assertTrue(solo.waitForView(R.id.rank_list));
        // asser that search shows up
        assertTrue(solo.waitForText("Search"));
        // assert that when you click on search and type in letter, user name comes up and correct rank
        assertTrue(solo.waitForText("nmelo"));
        assertTrue(solo.waitForText("15"));

        // make sure other user profile fragment pops up
        ListView rankList = (ListView)solo.getView(R.id.rank_list);
        solo.clickOnView(rankList.getChildAt(1));
        assertTrue(solo.waitForText("Max"));

    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}