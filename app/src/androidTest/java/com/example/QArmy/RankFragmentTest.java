package com.example.QArmy;


import android.app.Activity;
import android.widget.EditText;
import android.widget.SearchView;

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

        // now type in other user and make sure they show up with their correct rank
        SearchView searchView = (SearchView) solo.getView(R.id.search_text);
        EditText searchEditText = (EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        solo.clickOnView(searchView);
        solo.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery("Joe", true);
            }
        });
        solo.goBack();
        assertTrue(solo.waitForText("Joe"));
        assertTrue(solo.waitForText("8"));

    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}