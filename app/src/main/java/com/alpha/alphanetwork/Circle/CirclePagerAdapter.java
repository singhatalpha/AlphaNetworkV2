package com.alpha.alphanetwork.Circle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that stores fragments for tabs
 */
public class CirclePagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "CirclePagerAdapter";

    private final List<Fragment> nFragmentList = new ArrayList<>();


    public CirclePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return nFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return nFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        nFragmentList.add(fragment);
    }

}