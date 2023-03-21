package com.example.QArmy;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.annotation.NonNull;
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
    private String username;
    @Rule
    public ActivityTestRule<RegistrationActivity> rule = new ActivityTestRule<>(RegistrationActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        database = new Database();
    }

    private void setSavedUser(String name) {
        SharedPreferences sharedPrefs = rule.getActivity().getSharedPreferences("user_profile_prefs", Context.MODE_PRIVATE);
        username = sharedPrefs.getString("name", "");
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("name", name);
        editor.commit();
    }

    @Test
    public void testRegistrationActivity() {
        setSavedUser("");
        String testUsername = "test49023840328409328";
        solo.assertCurrentActivity("Wrong Activity", RegistrationActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "5875555555");
        solo.enterText((EditText) solo.getView(R.id.username), testUsername);
        solo.enterText((EditText) solo.getView(R.id.password), "test");

        solo.clickOnButton("Register");
        assertTrue(solo.waitForActivity(MainActivity.class, 2000));
        database.deleteUser(new User(testUsername), task -> {

        });
    }

    @Test
    public void testRegistered() {
        setSavedUser("test");
        assertTrue(solo.waitForActivity(MainActivity.class));
    }

    @Test
    public void testExistingUser() {
        setSavedUser("");
        solo.assertCurrentActivity("Wrong Activity", RegistrationActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "5875555555");
        solo.enterText((EditText) solo.getView(R.id.username), "kai");
        solo.enterText((EditText) solo.getView(R.id.password), "exists");

        solo.clickOnButton("Register");
        assertFalse(solo.waitForActivity(MainActivity.class, 2000));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
        setSavedUser(username);
    }

}
