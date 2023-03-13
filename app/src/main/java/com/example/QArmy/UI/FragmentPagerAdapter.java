package com.example.QArmy.UI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.QArmy.UI.qrcodes.QRListFragment;
import com.example.QArmy.UI.rank.RankFragment;


/**
 * Adapter to manage the three fragments on the main activity
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
     * Get the number of fragments managed by this adapter
     * @return The number of fragments managed by the adapter
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}
