package com.alpha.alphanetwork.Home;




import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.alpha.alphanetwork.Feed.MediaAdapter;
import com.alpha.alphanetwork.R;
//import com.example.alphanetwork.addpost.post;


import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;







public class RankingActivity extends AppCompatActivity implements MediaAdapter.OnFragmentInteractionListener{
    private static final String TAG = "Ranking";


    private Context mContext = RankingActivity.this;
    private ViewPager mViewPager;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;
    public ImageView backarrow;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Log.d(TAG, "onCreate: started.");

        mViewPager = (ViewPager) findViewById(R.id.container);
        mFrameLayout = (FrameLayout) findViewById(R.id.fragmentcontainer);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayoutParent);
        backarrow = findViewById(R.id.back);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setupViewPager();








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
        super.onBackPressed();
        finish();
    }


    private void setupViewPager(){
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RankingAlphaFragment()); //index 0
//        adapter.addFragment(new HomeEventsFragment()); //index 1
        adapter.addFragment(new RankingPacksFragment()); //index 1

        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("Alphas");
        tabLayout.getTabAt(1).setText("Packs");
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_live);
    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}
