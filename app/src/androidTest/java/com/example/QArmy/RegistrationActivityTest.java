/*
 * RegistrationActivityTest
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

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.RegistrationActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the RegistrationActivity.
 * @version 1.0
 * @author Kai Luedemann
 */
public class RegistrationActivityTest {
    private Solo solo;
    private Database database;
    private QArmy app;

    private final User empty = new User("");
    private final String enlist = "Enlist";
    private final int timeout = 1000;

    @Rule
    public ActivityTestRule<RegistrationActivity> rule = new ActivityTestRule<>(RegistrationActivity.class, true, false);

    /**
     * Initialize database for tests.
     */
    @Before
    public void setUp() {
        app = (QArmy) ApplicationProvider.getApplicationContext();
        database = app.model.db;
    }

    /**
     * Test adding a user with valid email.
     */
    @Test
    public void testNewUserEmail() {
        app.setUser(empty);
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        String testUsername = "notexistinguser";
        solo.assertCurrentActivity("Wrong Activity", RegistrationActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "testemail@gmail.com");

        solo.enterText((EditText) solo.getView(R.id.username), testUsername);

        solo.clickOnButton(enlist);
        assertTrue(solo.waitForActivity(MainActivity.class, timeout));
        database.deleteUser(new User("notexistinguser"), task -> {});
    }

    /**
     * Test opening the app when already registered.
     */
    @Test
    public void testRegistered() {
        app.setUser(new User("test"));
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        assertTrue(solo.waitForText("Your Platoon",1, timeout));
    }

    /**
     * Test adding a user that already exists in the database.
     */
    @Test
    public void testExistingUser() {
        app.setUser(empty);
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", RegistrationActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "5875555555");
        solo.enterText((EditText) solo.getView(R.id.username), "existinguser");

        solo.clickOnButton(enlist);
        assertFalse(solo.waitForActivity(MainActivity.class, timeout));
    }

    /**
     * Test registering with empty input.
     */
    @Test
    public void testEmptyInput() {
        app.setUser(empty);
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.clickOnButton(enlist);
        assertFalse(solo.waitForActivity(MainActivity.class, timeout));
    }

    /**
     * Test registering with invalid username.
     */
    @Test
    public void testInvalidUsername() {
        app.setUser(empty);
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "5875555555");
        solo.enterText((EditText) solo.getView(R.id.username), "f/d");

        solo.clickOnButton(enlist);
        assertFalse(solo.waitForActivity(MainActivity.class, timeout));
    }

    /**
     * Test registering with an invalid contact info.
     */
    @Test
    public void testInvalidContact() {
        app.setUser(empty);
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "notanemailorphonenumber");
        solo.enterText((EditText) solo.getView(R.id.username), "validusername");

        solo.clickOnButton(enlist);
        assertFalse(solo.waitForActivity(MainActivity.class, timeout));
    }

    /**
     * Close activity after tests.
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

}
