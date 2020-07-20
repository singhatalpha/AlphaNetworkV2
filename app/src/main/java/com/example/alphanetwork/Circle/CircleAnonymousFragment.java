package com.example.alphanetwork.Circle;




import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphanetwork.Dark.DarkAdapter;

//import com.example.alphanetwork.Dark.DarkMediaAdapter;
import com.example.alphanetwork.Feed.MediaAdapter;
import com.example.alphanetwork.Model.ModelAnonymousFeed;
import com.example.alphanetwork.Model.ModelAnonymousWall;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.Model.ModelHomeWall;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CircleAnonymousFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{




    private static final String TAG = "DarkLocalFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelAnonymousFeed> feed = new ArrayList<>();
    private CircleDarkAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    private SharedPreferences sharedPref;
    public String LONG,LAT;
    private Bundle mBundleRecyclerViewState;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_darkfeed, container, false);





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



    @Override
    public void onResume()
    {
        super.onResume();
        //INSERT CUSTOM CODE HERE
    }


    public void LoadJson() {


            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelAnonymousWall> call;
            call = api.topanonymousfeed();
            call.enqueue(new Callback<ModelAnonymousWall>() {
                @Override
                public void onResponse(Call<ModelAnonymousWall> call, Response<ModelAnonymousWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {

                        feed = response.body().getPosts();
                        System.out.println(feed);
                        adapter = new CircleDarkAdapter(feed, getActivity(), getActivity().getSupportFragmentManager());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onFailure(Call<ModelAnonymousWall> call, Throwable t) {
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



}

//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_dark_local_wall, container, false);
//
//        return view;
//    }
//}