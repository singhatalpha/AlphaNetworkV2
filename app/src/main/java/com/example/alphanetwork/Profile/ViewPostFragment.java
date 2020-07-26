package com.example.alphanetwork.Profile;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearSmoothScroller;
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
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewPostFragment extends Fragment {




    private static final String TAG = "ViewPostFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ViewPostAdapter adapter;
    private ImageView verified_filter, profession_filter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feed, container, false);

        Bundle bundle = getArguments();
        ArrayList<ModelFeed> feed = bundle.getParcelableArrayList("feed");
        int position = bundle.getInt("position");


        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.scrollToPosition(position);
//        recyclerView.smoothScrollToPosition(position);

        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.smoothScrollToPosition(position);
//        layoutManager.scrollToPosition(position);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

//        smoothScroller.setTargetPosition(position);  // pos on which item you want to scroll recycler view
//        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);

        verified_filter = view.findViewById(R.id.verified_filter);
        profession_filter = view.findViewById(R.id.profession_filter);
        verified_filter.setVisibility(View.GONE);
        profession_filter.setVisibility(View.GONE);


        init(feed,position);










        return view;
    }

//    LinearSmoothScroller smoothScroller=new LinearSmoothScroller(getActivity()){
//        @Override
//        protected int getVerticalSnapPreference() {
//            return LinearSmoothScroller.SNAP_TO_START;
//        }
//    };





    public void init(ArrayList<ModelFeed> feed,int position) {


                        adapter = new ViewPostAdapter(feed,position, getActivity(), getActivity().getSupportFragmentManager());
//                        recyclerView.scrollToPosition(position);
//                        layoutManager.scrollToPosition(position);
//            ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(position,0);

//                        recyclerView.smoothScrollToPosition(position);
//                        layoutManager.smoothScrollToPosition(recyclerView,null,position);
                        recyclerView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();




        }



}