package com.example.QArmy;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.RegistrationActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegistrationActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<RegistrationActivity> rule = new ActivityTestRule<>(RegistrationActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void checkRegistrationActivity() {
        solo.assertCurrentActivity("Wrong Activity", RegistrationActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_or_phone), "5875555555");
        solo.enterText((EditText) solo.getView(R.id.username), "test123");
        //solo.enterText((EditText) solo.getView(R.id.password), "test");

        solo.clickOnButton("Register");
    }

}
