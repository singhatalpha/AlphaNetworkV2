package com.example.alphanetwork.Dark;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that stores fragments for tabs
 */
public class DarkPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "DarkPagerAdapter";

    private final List<Fragment> mFragmentList = new ArrayList<>();


    public DarkPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }

}