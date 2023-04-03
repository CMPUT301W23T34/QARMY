package com.example.QArmy.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QArmy.R;
import com.example.QArmy.db.Database;
import com.example.QArmy.db.QueryListener;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity that displays a list of users who have scanned the same QR codes.
 */
public class UsersSameQrScanActivity extends AppCompatActivity {

    private QRCode qrCode;

    private Database db = new Database();

    private ArrayList<User> users = new ArrayList<>();

    private ListView listView;


    private ArrayAdapter adapter; // this recycle view shows the list of all the users that scan the code


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

        setSupportActionBar(findViewById(R.id.scanned_by_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String qrCodeHash = (String) getIntent().getStringExtra("Object");
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, R.layout.item_user, users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent otherUserProfileIntent = new Intent(this, OtherUserProfileActivity.class);
            otherUserProfileIntent.putExtra("user", users.get(i));
            startActivity(otherUserProfileIntent);
        });

        db.getQRUsersByHash(qrCodeHash, new QueryListener<User>() {
            @Override
            public void onSuccess(List<User> data) {
                // users which scanned the same tags
                users.clear();
                users.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
