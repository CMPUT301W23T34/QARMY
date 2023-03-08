package com.example.QArmy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.QArmy.db.Database;
import com.example.QArmy.model.QRCode;
import com.example.QArmy.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private MenuItem prevMenuItem;

    private User user;
    private Database db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database();
        user = new User("kai");

        setSupportActionBar(findViewById(R.id.my_toolbar));

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        viewPager = findViewById(R.id.viewPager2);
        fragmentPagerAdapter = new FragmentPagerAdapter(this);
        viewPager.setAdapter(fragmentPagerAdapter);
        prevMenuItem = bottomNavigationView.getMenu().getItem(1);
        viewPager.setCurrentItem(1);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId())
            {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            scanCode();
        }
        return super.onOptionsItemSelected(item);
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(false);
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureAct.class); // may have to create seperate class
        QRLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> QRLauncher = registerForActivityResult(new ScanContract(), result->{
        if (result.getContents() !=null) {
            QRCode code = new QRCode(result.getContents(), user);
            db.addQRCode(code, task -> {
                if (!task.isSuccessful()) {
                    //Log.d("Main", "Error adding QR code");
                }
            });
        }
    });
}