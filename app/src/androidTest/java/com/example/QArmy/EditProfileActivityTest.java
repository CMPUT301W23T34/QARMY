/*
 * EditProfileActivityTest
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

import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.profile.EditProfileActivity;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the EditProfileActivity
 * @version 1.0
 * @author Kai Luedemann
 */
public class EditProfileActivityTest {
    private Solo solo;
    private QArmy app;
    private final String save = "Save";
    private final int timeout = 1000;

    @Rule
    public ActivityTestRule<EditProfileActivity> rule = new ActivityTestRule<>(EditProfileActivity.class, true, true);

    /**
     * Set up tests.
     */
    @Before
    public void setUp() {
        app = (QArmy) ApplicationProvider.getApplicationContext();
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Test updating phone and email values.
     */
    @Test
    public void testUpdate() {
        String email = "newemail@gmail.com";
        String phone = "5875555555";
        solo.enterText((EditText) solo.getView(R.id.edit_email), email);
        solo.enterText((EditText) solo.getView(R.id.edit_phone), phone);
        solo.clickOnButton(save);
        solo.waitForText("textdoesnotexist", 1, timeout);

        User user = app.model.user;
        assertEquals(user.getEmail(), email);
        assertEquals(user.getPhone(), phone);
    }

    /**
     * Test invalid email.
     */
    @Test
    public void testInvalidEmail() {
        String email = "notavalidemail";
        solo.enterText((EditText) solo.getView(R.id.edit_email), email);
        solo.clickOnButton(save);
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);


        User user = app.model.user;
        assertNotEquals(user.getEmail(), email);
    }

    /**
     * Test invalid phone.
     */
    @Test
    public void testInvalidPhone() {
        String email = "validemail@gmail.com";
        String phone = "notavalidphonenumber";
        solo.enterText((EditText) solo.getView(R.id.edit_email), email);
        solo.enterText((EditText) solo.getView(R.id.edit_phone), phone);
        solo.clickOnButton(save);
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);

        User user = app.model.user;
        assertNotEquals(user.getPhone(), phone);
    }

    /**
     * Test preset text appears and no changes.
     */
    @Test
    public void testPresetText() {
        String email = "testemail@gmail.com";
        String phone = "5875555555";
        app.setUser(new User("test", email, phone));
        rule.finishActivity();
        rule.launchActivity(new Intent());

        assertTrue(solo.waitForText(email, 1, timeout));
        assertTrue(solo.waitForText(phone, 1, timeout));

        solo.clickOnButton(save);
        solo.waitForText("textdoesnotexist", 1, timeout);

        User user = app.model.user;
        assertEquals(user.getEmail(), email);
        assertEquals(user.getPhone(), phone);
        app.setUser(new User("test"));
    }

    /**
     * Test updating with only email.
     */
    @Test
    public void testOnlyEmail() {
        String email = "validemail@gmail.com";
        solo.enterText((EditText) solo.getView(R.id.edit_email), email);
        solo.clickOnButton(save);
        solo.waitForText("textdoesnotexist", 1, timeout);

        User user = app.model.user;
        assertEquals(user.getEmail(), email);
        app.setUser(new User("test"));
    }

    /**
     * Test only setting phone number. Should not update.
     */
    @Test
    public void testOnlyPhone() {
        String phone = "5875555555";
        solo.enterText((EditText) solo.getView(R.id.edit_phone), phone);
        solo.clickOnButton(save);
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);
        solo.waitForText("textdoesnotexist", 1, timeout);

        User user = app.model.user;
        assertNotEquals(user.getPhone(), phone);
    }

    /**
     * Close activity after tests.
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

}

