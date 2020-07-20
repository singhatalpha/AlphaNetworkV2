package com.example.alphanetwork.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alphanetwork.Feed.MediaAdapter;
import com.example.alphanetwork.Feed.ViewCommentsFragment;
import com.example.alphanetwork.Home.Home;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.R;

import java.util.ArrayList;
import java.util.List;

import Utils.OnBackPressedListener;
import Utils.OnBackPressedPopListener;


/**
 * Created by User on 5/28/2017.
 */


//implements
//        ProfileFragment.OnGridImageSelectedListener ,
//        ViewPostFragment.OnCommentThreadSelectedListener,
//        ViewProfileFragment.OnGridImageSelectedListener

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnGridImageSelectedListener, MediaAdapter.OnFragmentInteractionListener,ViewProfileFragment.OnGridImageSelectedListener {

    private static final String TAG = "ProfileActivity";

//    @Override
//    public void onCommentThreadSelectedListener(Photo photo) {
//        Log.d(TAG, "onCommentThreadSelectedListener:  selected a comment thread");
//
//        ViewCommentsFragment fragment = new ViewCommentsFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(getString(R.string.photo), photo);
//        fragment.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, fragment);
//        transaction.addToBackStack(getString(R.string.view_comments_fragment));
//        transaction.commit();
//    }

    @Override
    public void onGridImageSelected(List<ModelFeed> feed, int position) {
        Log.d(TAG, "onGridImageSelected: selected an image gridview: " );
        System.out.println(position);
        ViewPostFragment fragment = new ViewPostFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("feed", (ArrayList<? extends Parcelable>) feed);
        args.putInt("position",position);
        fragment.setArguments(args);
        FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack("viewpostfragment");
        transaction.commit();

    }


    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;

    private Context mContext = ProfileActivity.this;
    protected OnBackPressedListener onBackPressedListener;
    protected OnBackPressedPopListener onBackPressedPopListener;

    private ProgressBar mProgressBar;
    private ImageView profileMenu, back;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");
        toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        profileMenu = (ImageView) findViewById(R.id.profileMenu);
        back = findViewById(R.id.profileback);


        init();

    }
    public void onCommentThreadSelected(String id,String type) {

        ViewPostCommentFragment fragment = new ViewPostCommentFragment(id,type);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("YourKey", "YourValue");
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("ViewPostFragment");
        fragmentTransaction.commit();
    }

//    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
//        this.onBackPressedListener = onBackPressedListener;
//    }
//
//    public void setOnBackPressedPopListener(OnBackPressedPopListener onBackPressedPopListener) {
//        this.onBackPressedPopListener = onBackPressedPopListener;
//    }

    @Override
    public void onBackPressed() {

        if (onBackPressedPopListener != null) {
            System.out.println("doPop");
            onBackPressedPopListener.doPop();
            onBackPressedPopListener = null;
        } else if (onBackPressedListener != null) {
            System.out.println("doBack");
            onBackPressedListener.doBack();
            onBackPressedListener = null;
        } else {
            System.out.println("else back");
//            super.onBackPressed();
            Intent intent =  new Intent(this, Home.class);
            startActivity(intent);
//            finish();
        }


//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
////            super.onBackPressed();
//            Intent intent =  new Intent(this, Home.class);
//            startActivity(intent);
//            finish();
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }

//        super.onBackPressed();
//        Intent intent =  new Intent(this, Home.class);
//        startActivity(intent);
//        finish();

//        }

    }

    private void init() {
        Log.d(TAG, "init: inflating profile fragment");
        Intent intent = getIntent();
        if (intent.hasExtra("user_id")) {
            Log.d(TAG, "init: searching for user object attached as intent extra");
                    Bundle bundle = intent.getExtras();
                    String id = bundle.getString("user_id");

                    Log.d(TAG, "init: inflating view profile");
                    ViewProfileFragment fragment = new ViewProfileFragment();
                    Bundle args = new Bundle();
                    args.putString("user_id",id);
                    fragment.setArguments(args);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
//                    transaction.addToBackStack(getString(R.string.view_profile_fragment));
                    transaction.commit();


        }
        else {
            Log.d(TAG, "init: inflating Profile");
            ProfileFragment fragment = new ProfileFragment();
            FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(getString(R.string.profile_fragment));
            transaction.commit();
//        }
//


        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}