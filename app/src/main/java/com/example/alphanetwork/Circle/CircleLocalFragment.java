package com.example.alphanetwork.Circle;



import android.content.Context;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphanetwork.Feed.Adapter;
import com.example.alphanetwork.Feed.MediaAdapter;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.Model.ModelHomeWall;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CircleLocalFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MediaAdapter.OnFragmentInteractionListener{




    private static final String TAG = "HomeWallFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelFeed> feed = new ArrayList<>();
    private CircleAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public String LONG,LAT;
    private SharedPreferences sharedPref;

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

        LoadJson();










        return view;
    }




    public void LoadJson() {
        swipeRefreshLayout.setRefreshing(true);


            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelHomeWall> call;
            call = api.topfeed();
            call.enqueue(new Callback<ModelHomeWall>() {
                @Override
                public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {

                        feed = response.body().getPosts();
                        System.out.println(feed);
                        adapter = new CircleAdapter(feed, getActivity(), getActivity().getSupportFragmentManager());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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