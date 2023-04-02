package com.example.QArmy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.UI.profile.RegistrationActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationActivityTest {
    private Solo solo;
    private Database database;
    private QArmy app;
    @Rule
    public ActivityTestRule<RegistrationActivity> rule = new ActivityTestRule<>(RegistrationActivity.class, true, false);

    @Before
    public void setUp() {
        app = (QArmy) ApplicationProvider.getApplicationContext();
        //rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        database = app.model.db;
    }

    @Test
    public void testRegistrationActivity() {
        database.deleteUser(new User("test21"), task -> {});
        app.setUser(new User(""));
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        String testUsername = "test21";
        solo.assertCurrentActivity("Wrong Activity", RegistrationActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "5875555555");

        solo.enterText((EditText) solo.getView(R.id.username), testUsername);

        solo.clickOnButton("Enlist");
        assertTrue(solo.waitForActivity(MainActivity.class, 2000));
    }

    @Test
    public void testRegistered() {
        app.setUser(new User("test"));
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        assertTrue(solo.waitForText("QArmy"));
    }

    @Test
    public void testExistingUser() {
        app.setUser(new User(""));
        rule.launchActivity(new Intent(Intent.ACTION_MAIN));
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", RegistrationActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "5875555555");
        solo.enterText((EditText) solo.getView(R.id.username), "kai");

        solo.clickOnButton("Enlist");
        assertFalse(solo.waitForActivity(MainActivity.class, 2000));
    }


    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

}
