package com.messieyawo.wordbridefellowship.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.messieyawo.wordbridefellowship.youtubeFragments.ChannelFragmentLive;

import com.messieyawo.wordbridefellowship.Fragments.VersesFragment;


/**
 * Created by George Benjamin on 8/2/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new VersesFragment();
            case 1:
                return new ChannelFragmentLive();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
