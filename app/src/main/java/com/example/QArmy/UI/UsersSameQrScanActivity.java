package com.example.QArmy.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import java.util.List;

/**
 * An activity that displays a list of users who have scanned the same QR codes.
 */
public class UsersSameQrScanActivity extends AppCompatActivity {

    private static final String TAG = "UsersSameQrScanActivity";

    private QRCode qrCode;

    private Database db = new Database();


    private UserViewAdapter adapter; // this recycle view shows the list of all the users that scan the code

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


        String qrCodeHash = (String) getIntent().getStringExtra("Object");

        db.getQRUsersByHash(qrCodeHash, new QueryListener<User>() {
            @Override
            public void onSuccess(List<User> data) {
                // users which scanned the same tags

                RecyclerView recyclerView = findViewById(R.id.recyclerView2);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                adapter = new UserViewAdapter(getApplicationContext(), data);
                recyclerView.setAdapter(adapter); // set the adapter to view
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();

            }
        });
    }
}