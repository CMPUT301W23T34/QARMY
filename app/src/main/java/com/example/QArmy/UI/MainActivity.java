/*
 * MainActivity
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */

package com.example.QArmy.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.UI.qrcodes.QRCodeScanActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;

import com.example.QArmy.GPSLocation;
import com.example.QArmy.QArmy;
import com.example.QArmy.R;
import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.UI.profile.RegistrationActivity;
import com.example.QArmy.UI.profile.UserProfileActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.QArmy.R;
import com.example.QArmy.UI.profile.MySharedPreferences;
import com.example.QArmy.UI.profile.UserProfileActivity;
import com.example.QArmy.UI.profile.RegistrationActivity;
import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activity created when the app is launched.
 * Allows users to access the map, QR list, and rank fragment.
 * Provides the toolbar to scan QR codes.
 *
 * @author Nicholas Mellon
 * @author Kai Luedemann
 * @author yasminghaznavian
 * @author Brett Merkosky
 * @author Japkirat Kaur
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private MenuItem prevMenuItem;
    private User user;
    private Database db;
    private LocationManager locationManager;

    /**
     * Initialize the activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shared Preferences
        user = MySharedPreferences.loadUserProfile(this);
        Log.d("Main", user.getName());
        if (user.getName().equals("")) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            user = null;
        }

        db = new Database();

        setSupportActionBar(findViewById(R.id.my_toolbar));

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        viewPager = findViewById(R.id.viewPager2);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(this);
        viewPager.setAdapter(fragmentPagerAdapter);
        prevMenuItem = bottomNavigationView.getMenu().getItem(1);
        viewPager.setCurrentItem(1);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_home:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_rank:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(1).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Create toolbar menu
     *
     * @param menu The toolbar menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Perform action when toolbar button is pressed
     *
     * @param item The toolbar item selected
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            scanCode();
        } else if (item.getItemId() == R.id.action_profile) {
            openProfile();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openProfile() {
        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
        intent.putExtra("name", user.getName());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("phone", user.getPhone());
        intent.putExtra("id", user.getUniqueID());
        startActivity(intent);
    }

    /**
     * Open the camera to scan the QR code.
     */
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(false);
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureAct.class); // may have to create seperate class
        QRLauncher.launch(options);

    }

    /**
     * This class is responsible for launching the camera activity and adding the QR code
     * upon completion.
     */
    ActivityResultLauncher<ScanOptions> QRLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            Intent intent = new Intent(this, FetchLocationAndPictureActivity.class);
            intent.putExtra("QR_CODE", result.getContents());
            startActivity(intent);
        }
    });



    @Override
    protected void onResume() {
        super.onResume();
        if (user == null || user.getName().length() == 0) {
            user = ((QArmy) getApplication()).getUser();
        }
    }

    public User getUser() {
        return user;
    }
    public LocationManager getLocationManager() {return locationManager;}
}