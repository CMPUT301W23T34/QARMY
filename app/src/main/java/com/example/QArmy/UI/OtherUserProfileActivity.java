/*
 * OtherUserProfileActivity
 *
 * Version: 1.1
 *
 * Date: 2023-04-03
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */
package com.example.QArmy.UI;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.R;
import com.example.QArmy.UI.qrcodes.NoDeleteQRAdapter;
import com.example.QArmy.UI.qrcodes.SummaryFragment;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.QRList;
import com.example.QArmy.model.User;

import java.util.List;

/**
 * Activity representing the other user's profile screen
 * @author Nicholas Mellon
 * @author Brett Merkosky
 */
public class OtherUserProfileActivity extends AppCompatActivity {
    private Database db;
    private QRListener listener;
    private User user;
    private QRList qrList;

    /**
     * Initialize the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_others);

        setSupportActionBar(findViewById(R.id.other_user_profile_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle inputData = getIntent().getExtras();

        user = (User) inputData.getSerializable("user");
        db = new Database();
        listener = new QRListener();
        qrList = new QRList();
        setTitle(user.getName());

        ListView qrCodeList = findViewById(R.id.qr_code_list);
        NoDeleteQRAdapter qrCodeAdapter = new NoDeleteQRAdapter(this, qrList, db);

        qrCodeList.setAdapter(qrCodeAdapter);
        qrList.addView(qrCodeAdapter);

        SummaryFragment fragment = new SummaryFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
        qrList.addView(fragment);

        db.getUserCodes(user, listener);
    }

    /**
     * Listen for the completion of a query to the database and update the
     * model with the new data.
     */
    class QRListener implements QueryListener<QRCode> {

        @Override
        public void onSuccess(List<QRCode> data) {
            qrList.modify(data);
        }

        @Override
        public void onFailure(Exception e) {

        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
