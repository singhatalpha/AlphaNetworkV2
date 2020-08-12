package com.alpha.alphanetwork.Home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alpha.alphanetwork.Feed.Adapter;
import com.alpha.alphanetwork.Feed.MediaAdapter;
import com.alpha.alphanetwork.Model.ModelFeed;
import com.alpha.alphanetwork.Model.ModelHomeWall;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeWallFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MediaAdapter.OnFragmentInteractionListener{



    private static final String TAG = "HomeWallFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelFeed> feed = new ArrayList<>();
//    private List<ModelFeed> limitedfeed = new ArrayList<>();
    private Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public String LONG,LAT;
    private SharedPreferences sharedPref;
    private ImageView verified_filter, profession_filter,both_filter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feed, container, false);




        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        sharedPref = getActivity().getSharedPreferences("Location" , Context.MODE_PRIVATE);
        verified_filter = view.findViewById(R.id.verified_filter);
        profession_filter = view.findViewById(R.id.profession_filter);
        both_filter = view.findViewById(R.id.both_filter);

        verified_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Filtering Posts By Verified Users Only.", Toast.LENGTH_LONG).show();
                VerifiedLoadJson();
            }
        });
        profession_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Filtering Posts By Users with same Profession as yours.", Toast.LENGTH_LONG).show();
                ProfessionLoadJson();
            }
        });
        both_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Filtering Posts By Verified Users with Same Profession.", Toast.LENGTH_LONG).show();
                BothLoadJson();
            }
        });






        String f = sharedPref.getString("feed","NULL");

        if (!f.equals("NULL")) {


            Gson gson = new Gson();
            feed = gson.fromJson(f, ModelHomeWall.class).getPosts();
//            int i = 0;
//            while(i<5){
//                limitedfeed.add(feed.get(i));
//                i++;
//            }

            mAdapter = new Adapter(feed, getActivity(), getActivity().getSupportFragmentManager(),recyclerView);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            LoadJson();

        }
        else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Local contains feed based on location, the posts are sorted by distance as per user location. Please tap Yes to Continue.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            LoadJson();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            swipeRefreshLayout.setRefreshing(false);
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        String f = sharedPref.getString("feed","NULL");
        if (!f.equals("NULL")) {
        Gson gson = new Gson();
        feed = gson.fromJson(f, ModelHomeWall.class).getPosts();
//        int i = 0;
//        while(i<5){
//            limitedfeed.add(feed.get(i));
//            i++;
//        }

        mAdapter = new Adapter(feed, getActivity(), getActivity().getSupportFragmentManager(),recyclerView);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        recyclerView.setAdapter(null);
    }

    public void LoadJson() {
        swipeRefreshLayout.setRefreshing(true);

        LONG  = sharedPref.getString("LONG" , "NULL");
        LAT   = sharedPref.getString("LAT","NULL");
        System.out.println(LONG);
        if(LONG=="NULL"){
            Toast.makeText(getActivity(), "Please Enable Location, We need location for the feed", Toast.LENGTH_LONG).show();
        }
        else {


            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelHomeWall> call;
            call = api.feed(LONG,LAT);
            call.enqueue(new Callback<ModelHomeWall>() {
                @Override
                public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {







                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("feed" , json);
                        editor.apply();








                        feed = response.body().getPosts();
//                        int i = 0;
//                        while(i<5){
//                            limitedfeed.add(feed.get(i));
//                            i++;
//                        }

                        mAdapter = new Adapter(feed, getActivity(), getActivity().getSupportFragmentManager(),recyclerView);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onFailure(Call<ModelHomeWall> call, Throwable t) {
                    Toast.makeText(getActivity(), "onFailure is triggered", Toast.LENGTH_LONG).show();
                }

            });
        }

    }

    public void ProfessionLoadJson() {
        swipeRefreshLayout.setRefreshing(true);

        LONG  = sharedPref.getString("LONG" , "NULL");
        LAT   = sharedPref.getString("LAT","NULL");
        System.out.println(LONG);
        if(LONG=="NULL"){
            Toast.makeText(getActivity(), "Please Enable Location, We need location for the feed", Toast.LENGTH_LONG).show();
        }
        else {


            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelHomeWall> call;
            call = api.professionfeed(LONG,LAT);
            call.enqueue(new Callback<ModelHomeWall>() {
                @Override
                public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {

                        feed = response.body().getPosts();
//                        int i = 0;
//                        while(i<5){
//                            limitedfeed.add(feed.get(i));
//                            i++;
//                        }

                        mAdapter = new Adapter(feed, getActivity(), getActivity().getSupportFragmentManager(),recyclerView);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onFailure(Call<ModelHomeWall> call, Throwable t) {
                    Toast.makeText(getActivity(), "onFailure is triggered", Toast.LENGTH_LONG).show();
                }

            });
        }

    }
    public void VerifiedLoadJson() {
        swipeRefreshLayout.setRefreshing(true);

        LONG  = sharedPref.getString("LONG" , "NULL");
        LAT   = sharedPref.getString("LAT","NULL");
        System.out.println(LONG);
        if(LONG=="NULL"){
            Toast.makeText(getActivity(), "Please Enable Location, We need location for the feed", Toast.LENGTH_LONG).show();
        }
        else {


            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelHomeWall> call;
            call = api.verifiedfeed(LONG,LAT);
            call.enqueue(new Callback<ModelHomeWall>() {
                @Override
                public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {



                        feed = response.body().getPosts();
//                        int i = 0;
//                        while(i<5){
//                            limitedfeed.add(feed.get(i));
//                            i++;
//                        }
                        mAdapter = new Adapter(feed, getActivity(), getActivity().getSupportFragmentManager(),recyclerView);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onFailure(Call<ModelHomeWall> call, Throwable t) {
                    Toast.makeText(getActivity(), "onFailure is triggered", Toast.LENGTH_LONG).show();
                }

            });
        }

    }
    public void BothLoadJson() {
        swipeRefreshLayout.setRefreshing(true);

        LONG  = sharedPref.getString("LONG" , "NULL");
        LAT   = sharedPref.getString("LAT","NULL");
        System.out.println(LONG);
        if(LONG=="NULL"){
            Toast.makeText(getActivity(), "Please Enable Location, We need location for the feed", Toast.LENGTH_LONG).show();
        }
        else {


            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelHomeWall> call;
            call = api.bothfeed(LONG,LAT);
            call.enqueue(new Callback<ModelHomeWall>() {
                @Override
                public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {


                        feed = response.body().getPosts();
//                        int i = 0;
//                        while(i<5){
//                            limitedfeed.add(feed.get(i));
//                            i++;
//                        }

                        mAdapter = new Adapter(feed, getActivity(), getActivity().getSupportFragmentManager(),recyclerView);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onFailure(Call<ModelHomeWall> call, Throwable t) {
                    Toast.makeText(getActivity(), "onFailure is triggered", Toast.LENGTH_LONG).show();
                }

            });
        }

    }






//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try{
//            mOnCommentThreadSelectedListener = (OnCommentThreadSelectedListener) getActivity();
//        }catch (ClassCastException e){
//            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
//        }
//    }


    @Override
    public void onRefresh() {
        LoadJson();
    }

    private void onLoadingSwipeRefresh(){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson();
                    }
                }
        );

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }




}