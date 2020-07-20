package com.example.alphanetwork.Home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphanetwork.Feed.Adapter;
import com.example.alphanetwork.Feed.MediaAdapter;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.Model.ModelHomeWall;
import com.example.alphanetwork.Model.SearchFeed;
import com.example.alphanetwork.Model.ViewProfile;
import com.example.alphanetwork.Profile.ProfileActivity;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;

import com.example.alphanetwork.Home.RankingAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RankingAlphaFragment extends Fragment implements  MediaAdapter.OnFragmentInteractionListener{



    private Context mContext;
    private static final String TAG = "RankingAlphaFragment";
    private ListView mListView;

    //vars
    private List<ViewProfile> mUserList;
    private RankingAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        mContext = getActivity();
        mListView = (ListView) view.findViewById(R.id.listView);
        Log.d(TAG, "onCreate: started.");


        LoadJson();


        return view;
    }

    private void LoadJson(){


            Api api = RetrofitClient.getInstance().getApi();
            Call<SearchFeed> call;
            call = api.getalpharanking();
            call.enqueue(new Callback<SearchFeed>() {
                @Override
                public void onResponse(Call<SearchFeed> call, Response<SearchFeed> response) {
                    if(response.isSuccessful() ){

                        mUserList = response.body().getProfiles();
                        System.out.println(mUserList);
                        updateUsersList();


                    } else {

                        Toast.makeText(mContext, "No Response", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<SearchFeed> call, Throwable t) {
                    Toast.makeText(mContext, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
                }

            });

    }

    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");


        mAdapter = new RankingAdapter(mContext, R.layout.layout_user_listitem, mUserList);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected user: " + mUserList.get(position).toString());

                //navigate to profile activity
                Intent intent =  new Intent(mContext, ProfileActivity.class);
                intent.putExtra("user_id", mUserList.get(position).getUser_id()) ;
//                intent.putExtra(getString(R.string.calling_activity), "Search");
//                intent.putExtra(getString(), mUserList.get(position));
                startActivity(intent);
            }
        });
    }






    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}