package com.example.QArmy;
import com.example.QArmy.UI.profile.RegistrationActivity;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Provide tests for RegistrationActivity.
 * @author Jessica Emereonye
 * @version 1.0
 * @see RegistrationActivity
 */

@RunWith(AndroidJUnit4.class)
public class NewRegistrationActivityTest {

    @Rule
    public ActivityScenarioRule<RegistrationActivity> activityRule =
            new ActivityScenarioRule<>(RegistrationActivity.class);
     /**
     * Test that registration button works
     */
    @Test
    public void testRegisterButton() {
        Espresso.onView(ViewMatchers.withId(R.id.register_button))
                .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
                .perform(ViewActions.click());
    }
    /**
     * Test if user inputed anything
     */

    @Test
    public void testNoInput() {
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Please enter your email or phone number:"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    /**
     * Test if user inputed an invalid email or phone number
     */

    @Test
    public void testInvalidInput() {
        Espresso.onView(ViewMatchers.withId(R.id.email_or_phone)).perform(ViewActions.typeText("invalid_email_or_phone_number"));
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Please enter a valid email or phone number:"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    /**
     * Test if user didnt input username
     */

    @Test
    public void testNoUsername() {
        Espresso.onView(ViewMatchers.withId(R.id.email_or_phone)).perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Please choose a username:"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    /**
     * Test if user already exists
     */

    @Test
    public void testExistingUser() {
        Espresso.onView(ViewMatchers.withId(R.id.email_or_phone)).perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText("testuser"));
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Username already taken. Please choose another one."))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    /**
     * Test if registration is complete
     */

    @Test
    public void testRegistration() {
        Espresso.onView(ViewMatchers.withId(R.id.email_or_phone)).perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText("newuser"));
        Espresso.onView(ViewMatchers.withId(R.id.register_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.register_button))
                .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));
        
    }
}
