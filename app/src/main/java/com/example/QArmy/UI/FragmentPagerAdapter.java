/*
 * FragmentPagerAdapter
 *
 * Version: 1.0
 *
 * Date: 2023-03-23
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 */
package com.example.QArmy.UI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.QArmy.UI.map.MapFragment;
import com.example.QArmy.UI.qrcodes.QRListFragment;
import com.example.QArmy.UI.rank.RankFragment;


/**
 * Controls the ViewPager's behaviour, allowing us to switch between fragments on the main activity
 * @author Japkirat Kaur
 */
public class FragmentPagerAdapter extends FragmentStateAdapter {
    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Create the fragment which we will move to
     * @param position The number of the fragment we will create
     * @return The fragment which we have created
     * @return Null if the position was invalid
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new MapFragment();
            case 1:
                return new QRListFragment();
            case 2:
                return new RankFragment();
        }
        return null;
    }

    /**
     * Get the number of fragments controlled by the ViewPager
     * @return The number of fragments controlled by the ViewPager
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}