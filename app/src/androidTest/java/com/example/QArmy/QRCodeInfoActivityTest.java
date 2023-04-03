package com.example.QArmy;


import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.ScannedByActivity;
import com.example.QArmy.UI.qrcodes.CommentsActivity;
import com.example.QArmy.UI.qrcodes.QRCodeInfoActivity;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

public class QRCodeInfoActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<QRCodeInfoActivity> rule = new ActivityTestRule<>(QRCodeInfoActivity.class, true, false);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        Intent intent = new Intent();
        intent.putExtra("QRCode", new QRCode("CommentTest", new User("test"), null, new Date()));
        rule.launchActivity(intent);
    }

    @Test
    public void testNavigation() {
        assertTrue(solo.waitForText("Lat"));
    }

    @Test
    public void testComments() {
        solo.clickOnView(solo.getView(R.id.comments_image_view));
        solo.assertCurrentActivity("Wrong activity", CommentsActivity.class);
    }

    @Test
    public void testUsers() {
        solo.clickOnView(solo.getView(R.id.users_image_view));
        solo.assertCurrentActivity("Wrong activity", ScannedByActivity.class);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}