package com.example.alphanetwork.Circle;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;

//import com.example.alphanetwork.Dark.DarkMediaAdapter;
import com.example.alphanetwork.Home.RankingActivity;
import com.example.alphanetwork.Model.ModelFeed;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alphanetwork.Feed.MediaAdapter;
import com.example.alphanetwork.Feed.ViewCommentsFragment;
import com.example.alphanetwork.Profile.ProfileActivity;
import com.example.alphanetwork.R;
import com.example.alphanetwork.addpost.Permissions.Permissions;
//import com.example.alphanetwork.addpost.post;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import Utils.BottomNavigationViewHelper;
import com.google.android.material.tabs.TabLayout;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.listener.LocationListener;

import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;






import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.ProcessType;
import android.location.Location;
import android.widget.TextView;
import android.widget.Toast;


public class Circle extends AppCompatActivity implements MediaAdapter.OnFragmentInteractionListener {
    private static final String TAG = "Circle";
    private static final int ACTIVITY_NUM = 2;

    private Context mContext = Circle.this;
    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;
    private ImageView profile,ranking;
    private FloatingActionButton fab;
    private SharedPreferences sharedPref;
    private TextView message;
    public String LONG,LAT;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        Log.d(TAG, "onCreate: started.");

        mViewPager = (ViewPager) findViewById(R.id.container);
        mFrameLayout = (FrameLayout) findViewById(R.id.fragmentcontainer);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);
        profile = findViewById(R.id.profile);
        ranking = findViewById(R.id.ranking);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Circle.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Circle.this, RankingActivity.class);
                startActivity(i);
            }
        });

        Toast.makeText(mContext, "Daily Top Posts", Toast.LENGTH_LONG).show();







        setupBottomNavigationView();
        setupViewPager();








    }





    @Override
    protected void onResume() {
        super.onResume();

    }




    public void onCommentThreadSelected(String id,String type) {

        CircleViewCommentsFragment fragment = new CircleViewCommentsFragment(id,type);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("YourKey", "YourValue");
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        fragmentTransaction.addToBackStack(String.valueOf(R.string.home_wall));
        fragmentTransaction.commit();
    }



    public void hideLayout(){
        Log.d(TAG, "hideLayout: hiding layout");
        mRelativeLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }

    public void showLayout(){
        Log.d(TAG, "hideLayout: showing layout");
        mRelativeLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if(mFrameLayout.getVisibility() == View.VISIBLE){
            showLayout();
        }
        else{
            finishAffinity();
        }

    }


    private void setupViewPager(){
        CirclePagerAdapter adapter = new CirclePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CircleLocalFragment()); //index 0
//        adapter.addFragment(new HomeEventsFragment()); //index 1
        adapter.addFragment(new CircleAnonymousFragment()); //index 1

        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("TopLocal");
        tabLayout.getTabAt(1).setText("TopAnony");
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