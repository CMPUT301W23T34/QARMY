package com.example.QArmy;


import android.app.Activity;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.CaptureAct;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class RankFragmentTest {
    private Solo solo;
    private Database database;
    private QArmy app;
    private User testUser;
    private User testUser2;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        app = (QArmy) ApplicationProvider.getApplicationContext();
        database = app.model.db;
        testUser = new User("testX");
        testUser2 = new User("testY");
        testUser.setScore(123);
        testUser2.setScore(456);
        app.setUser(testUser);
        database.addUser(testUser2,task -> {});

        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.sleep(5000);
        solo.scrollToSide(Solo.RIGHT);
    }

    /**
    Tests search functionality and rank fragment functionality
     */
    @Test
    public void testRankFragment() {
        assertTrue(solo.waitForView(R.id.rank_list));
        // asser that search shows up
        assertTrue(solo.waitForText("Search"));
        // assert that when you click on search and type in letter, user name comes up and correct rank
        assertTrue(solo.waitForText("testX"));
        assertTrue(solo.waitForText("123"));
        assertTrue(solo.waitForText(Integer.toString(testUser.getRank())));

        // now type in other user and make sure they show up with their correct rank
        SearchView searchView = (SearchView) solo.getView(R.id.search_text);
        EditText searchEditText = (EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        solo.clickOnView(searchView);
        solo.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery("testY", true);
            }
        });
        assertTrue(solo.waitForText("testY"));
        assertTrue(solo.waitForText("456"));
        assertTrue(solo.waitForText(Integer.toString(testUser2.getRank())));
        database.deleteUser(testUser2,task -> {});
        database.deleteUser(testUser,task -> {});
        solo.goBack();

    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}