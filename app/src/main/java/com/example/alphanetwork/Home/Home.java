package com.example.alphanetwork.Home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;

//import com.example.alphanetwork.Dark.DarkMediaAdapter;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.addmedia.pictureselector.MainActivity;
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


public class Home extends LocationBaseActivity implements MediaAdapter.OnFragmentInteractionListener {
    private static final String TAG = "Home";
    private static final int ACTIVITY_NUM = 0;

    private Context mContext = Home.this;
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
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started.");

        mViewPager = (ViewPager) findViewById(R.id.container);
        mFrameLayout = (FrameLayout) findViewById(R.id.fragmentcontainer);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);
        profile = findViewById(R.id.profile);
        ranking = findViewById(R.id.ranking);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, RankingActivity.class);
                startActivity(i);
            }
        });



        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);

            }
        });


        sharedPref = getSharedPreferences("Location" , Context.MODE_PRIVATE);

        getLocation();


//        if(checkPermissionArray(Permissions.permissions))
//        {
//
//
//        }
//        else {
//            verifyPermission(Permissions.permissions);
//        }



        message = findViewById(R.id.message);
        //CALL API TO SEE IF THERE'S A MESSAGE LIKE UPDATING THE App.


        setupBottomNavigationView();
        setupViewPager();








    }





    @Override
    protected void onResume() {
        super.onResume();

        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            Log.d(TAG, "Resumed,getting location");
        }
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Please Provide Permissions", "Please Turn on your GPS");
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, location.toString());
        SharedPreferences.Editor editor = sharedPref.edit();
        Log.d(TAG, "Entering location into sharedpref");
        LAT = String.valueOf(location.getLatitude());
        LONG = String.valueOf(location.getLongitude());
        editor.putString("LAT" , LAT);
        editor.putString("LONG" , LONG);

        editor.apply();
    }

    @Override
    public void onLocationFailed(@FailType int failType) {

        LONG  = sharedPref.getString("LONG" , "NULL");
        LAT   = sharedPref.getString("LAT","NULL");

        Log.e("Location ", "FAILED: ");
        Log.d(TAG, "Saved location:"+LONG+LAT);

        switch (failType) {
            case FailType.TIMEOUT: {
                Log.e("Location","Couldn't get location, and timeout!");
                break;
            }
            case FailType.PERMISSION_DENIED: {
                Log.e("Location","Couldn't get location, because user didn't give permission!");
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                Log.e("Location","Couldn't get location, because network is not accessible!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_NOT_AVAILABLE: {
                Log.e("Location","Couldn't get location, because Google Play Services not available!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_CONNECTION_FAIL: {
                Log.e("Location","Couldn't get location, because Google Play Services connection failed!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_SETTINGS_DIALOG: {
                Log.e("Location","Couldn't display settingsApi dialog!");
                break;
            }
            case FailType.GOOGLE_PLAY_SERVICES_SETTINGS_DENIED: {
                Log.e("Location","Couldn't get location, because user didn't activate providers via settingsApi!");
                break;
            }
            case FailType.VIEW_DETACHED: {
                Log.e("Location","Couldn't get location, because in the process view was detached!");
                break;
            }
            case FailType.VIEW_NOT_REQUIRED_TYPE: {
                Log.e("Location","Couldn't get location, "
                        + "because view wasn't sufficient enough to fulfill given configuration!");
                break;
            }
            case FailType.UNKNOWN: {
                Log.e("Location","Ops! Something went wrong!");
                break;
            }
        }
    }

    @Override
    public void onProcessTypeChanged(@ProcessType int processType) {
        Log.e("Process Change ", "ProcessType");
        switch (processType) {
            case ProcessType.GETTING_LOCATION_FROM_GOOGLE_PLAY_SERVICES: {
                Log.e("Process Change ","Getting Location from Google Play Services...");
                break;
            }
            case ProcessType.GETTING_LOCATION_FROM_GPS_PROVIDER: {
                Log.e("Process Change ","Getting Location from GPS...");
                break;
            }
            case ProcessType.GETTING_LOCATION_FROM_NETWORK_PROVIDER: {
                Log.e("Process Change ","Getting Location from Network...");
                break;
            }
            case ProcessType.ASKING_PERMISSIONS:
            case ProcessType.GETTING_LOCATION_FROM_CUSTOM_PROVIDER:
                // Ignored
                break;
        }
    }






























    public void onCommentThreadSelected(String id,String type) {

        ViewCommentsFragment fragment = new ViewCommentsFragment(id,type);
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
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeWallFragment()); //index 0
//        adapter.addFragment(new HomeEventsFragment()); //index 1
        adapter.addFragment(new HomeLocalWallFragment()); //index 1

        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("Local");
        tabLayout.getTabAt(1).setText("Anonymous");
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




//    private boolean checkPermissionArray(String[] permission) {
//
//        for (int i=0;i<permission.length;i++){
//            String singlep = permission[i];
//            if(!checksinglep(singlep)){
//                return false;
//            }
//
//        }
//
//        return true;
//    }
//
//    private boolean checksinglep(String singlep) {
//
//        int PermissionGranted = ActivityCompat.checkSelfPermission(Home.this,singlep);
//        if(PermissionGranted!= PackageManager.PERMISSION_GRANTED){
//            return false;
//        }
//        else {
//            return true;
//        }
//
//    }
//
//    private void verifyPermission(String[] permissions) {
//        Log.e("verify ", "verifyPermission: ");
//
//        ActivityCompat.requestPermissions(Home.this,permissions,1);
//    }


}