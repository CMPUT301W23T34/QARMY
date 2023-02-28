package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager; 
    private FragmentPagerAdapter fragmentPagerAdapter;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        viewPager = findViewById(R.id.viewPager2);
        fragmentPagerAdapter = new FragmentPagerAdapter(this);
        viewPager.setAdapter(fragmentPagerAdapter);
        prevMenuItem = bottomNavigationView.getMenu().getItem(1);

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
}