package com.example.QArmy;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.UsersSameQrScanActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * The type Users same qr scan test.
 */
public class UsersSameQrScanTest {
    private Solo solo;

    /**
     * The Rule.
     */
    @Rule
    public ActivityTestRule<UsersSameQrScanActivity> rule
            = new ActivityTestRule<>(UsersSameQrScanActivity.class, true, true);

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Test fetch location activity.
     */
    @Test
    public void testFetchLocationActivity() {
        solo.clickOnView(solo.getView(R.id.listView));
        solo.assertCurrentActivity("Current Activity", UsersSameQrScanActivity.class);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}