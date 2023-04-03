package com.example.QArmy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.profile.EditProfileActivity;
import com.example.QArmy.UI.qrcodes.CommentsActivity;
import com.example.QArmy.UI.qrcodes.QRCodeScanActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Test the QRCodeScanActivity class.
 * @version 1.0
 * @author Brett Merkosky
 */
public class ScanActivityTest {
    private Solo solo;
    private Database db;
    private User user;
    private AppContainer model;
    private final int timeout = 1000;

    @Rule
    public ActivityTestRule<QRCodeScanActivity> rule = new ActivityTestRule<>(QRCodeScanActivity.class, true, false);

    /**
     * Launch the activity with a user and qrCode text
     */
    @Before
    public void setUp() {
        String qrData = "ScanActivityTest";
        User user = new User("test");
        AppContainer model = ((QArmy) ApplicationProvider.getApplicationContext()).model;
        db = model.db;
        user = model.user;
        Intent intent = new Intent();
        intent.putExtra("qrCodeText", qrData);
        intent.putExtra("user", user);


        rule.launchActivity(intent);
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Test the geolocation toggle
     */
    @Test
    public void testGeolocationToggle() {
        assertTrue(solo.waitForText("Geolocation On", 1, timeout));
        solo.clickOnView(solo.getView(R.id.geolocation_toggle));
        assertTrue(solo.waitForText("Geolocation Off", 1, timeout));
    }

    /**
     * Test the "Finish Training" button, making sure that the activity successfully finishes
     */
    @Test
    public void testFinishTrainingButton() {
        solo.clickOnView(solo.getView(R.id.finish_training_button));
        // Activity does not immediately close, so we must wait briefly
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(rule.getActivity().isFinishing());

    }

    /**
     * Finish activity after tests.
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

}
