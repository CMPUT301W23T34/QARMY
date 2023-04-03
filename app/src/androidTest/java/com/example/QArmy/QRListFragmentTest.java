package com.example.QArmy;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.QArmy.UI.MainActivity;
import com.example.QArmy.UI.qrcodes.QRCodeInfoActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.AppContainer;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.QRList;
import com.example.QArmy.model.User;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

public class QRListFragmentTest {
    private Solo solo;
    private Database db;
    private User user;
    private QRList qrList;
    private final int timeout = 1000;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        AppContainer model = ((QArmy) ApplicationProvider.getApplicationContext()).model;
        db = model.db;
        user = model.user;
        qrList = model.qrList;

        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void testQRCodeAppears() {
        assertTrue(solo.waitForText("Corporal", 1, timeout));
    }

    @Test
    public void testDelete() {
        solo.clickOnView(solo.getView(R.id.deleteButton, 0));
        assertFalse(solo.waitForText("Lieutenant", 1, timeout));
        db.addQRCode(new QRCode("hashbrown", user, null, new Date()), task -> {
            db.getUserCodes(user, new QueryListener<QRCode>() {
                @Override
                public void onSuccess(List<QRCode> data) {
                    qrList.modify(data);
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        });
    }

    @Test
    public void testClick() {
        solo.clickInList(1);
        solo.assertCurrentActivity("Wrong activity", QRCodeInfoActivity.class);
        solo.waitForText("Corporal", 1, timeout);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }


}