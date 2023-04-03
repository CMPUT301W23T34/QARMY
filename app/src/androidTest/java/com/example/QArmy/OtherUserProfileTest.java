package com.example.QArmy;


import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class OtherUserProfileTest {
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
        solo.sleep(1000);
        solo.scrollToSide(Solo.RIGHT);
    }

    /**
     * tests to make sure other user profile shows up when click on from rank fragment
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

        // make sure other user profile fragment pops up
        ListView rankList = (ListView)solo.getView(R.id.rank_list);
        solo.clickOnView(rankList.getChildAt(1));
        assertTrue(solo.waitForText("Total", 1, 1000));

        database.deleteUser(testUser2,task -> {});
        database.deleteUser(testUser,task -> {});

    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}