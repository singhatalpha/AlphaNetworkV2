package com.alpha.alphanetwork.Dark;



import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alpha.alphanetwork.Feed.MediaAdapter;
import com.alpha.alphanetwork.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import Utils.BottomNavigationViewHelper;


import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;


public class Dark extends AppCompatActivity implements MediaAdapter.OnFragmentInteractionListener{
    private static final String TAG = "Dark";
    private static final int ACTIVITY_NUM = 4;

    private Context mContext = Dark.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark);
        Log.d(TAG, "onCreate: started.");

        setupBottomNavigationView();
        setupViewPager();
    }


    private void setupViewPager(){
        DarkPagerAdapter adapter = new DarkPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DarkGlobalWallFragment()); //index 0
//        adapter.addFragment(new HomeEventsFragment()); //index 1
        adapter.addFragment(new DarkLocalWallFragment()); //index 1
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_wall);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_wall);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_live);
    }


    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx =  findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}








//public class Dark extends AppCompatActivity{
//    private static final String TAG = "Dark";
//    private static final int ACTIVITY_NUM = 4;
//
//    private Context mContext = Dark.this;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dark);
//        Log.d(TAG, "onCreate: started.");
//
//        setupBottomNavigationView();
//    }
//
//    /**
//     * BottomNavigationView setup
//     */
//    private void setupBottomNavigationView(){
//        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//    }
//}