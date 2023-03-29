package com.example.QArmy.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import java.util.List;

/**
 * An activity that displays a list of users who have scanned the same QR codes.
 @author yasminghaznavian
 */
public class UsersSameQrScanActivity extends AppCompatActivity {

    private QRCode qrCode;

    private Database db = new Database();

    /**
     * Called when the activity is first created. Initializes the activity UI and
     * populates the list view with scanned user data.
     *
     * @param savedInstanceState the saved instance state, or null if none
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_same_qr_scan);


        qrCode = (QRCode) getIntent().getSerializableExtra("Object");

        db.getQRUsers(qrCode, new QueryListener<User>() {
            @Override
            public void onSuccess(List<User> data) {
                // users which scanned the same tags
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}