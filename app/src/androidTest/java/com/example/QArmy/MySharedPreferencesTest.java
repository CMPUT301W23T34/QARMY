package com.example.QArmy;

import static org.junit.Assert.assertTrue;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MySharedPreferencesTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void testLoadSharedPreferences() {
        User previousSharedPreferences = MySharedPreferences.loadUserProfile(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertTrue(previousSharedPreferences instanceof User);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
