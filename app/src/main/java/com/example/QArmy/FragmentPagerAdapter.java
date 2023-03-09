package com.example.QArmy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentPagerAdapter extends FragmentStateAdapter {
    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

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

    @Override
    public int getItemCount() {
        return 3;
    }
}
