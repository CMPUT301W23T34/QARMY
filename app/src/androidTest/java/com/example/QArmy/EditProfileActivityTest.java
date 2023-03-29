package com.example.QArmy;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.CaptureAct;
import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.EditProfileActivity;
import com.example.QArmy.UI.profile.UserProfileActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class EditProfileActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void testEditProfileActivity() {
        solo.clickOnView(solo.getView(R.id.action_profile));
        solo.assertCurrentActivity("Wrong activity", UserProfileActivity.class);
        solo.waitForActivity("UserProfileActivity");
        solo.clickOnView(solo.getView(R.id.edit_button));
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}

