package com.example.alphanetwork.Dark;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphanetwork.Feed.MediaAdapter;
import com.example.alphanetwork.Model.ModelAnonymousFeed;
import com.example.alphanetwork.Model.ModelAnonymousWall;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.Model.ModelHomeWall;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DarkLocalWallFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MediaAdapter.OnFragmentInteractionListener{




    private static final String TAG = "DarkLocalFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelAnonymousFeed> feed = new ArrayList<>();
    private DarkAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    private SharedPreferences sharedPref;
    public String LONG,LAT;



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
        sharedPref = getActivity().getSharedPreferences("Location" , Context.MODE_PRIVATE);

        onLoadingSwipeRefresh();

        return view;

    }

    public void LoadJson() {

        swipeRefreshLayout.setRefreshing(true);

        LONG  = sharedPref.getString("LONG" , "NULL");
        LAT   = sharedPref.getString("LAT","NULL");
        if(LONG=="NULL"){
            Toast.makeText(getActivity(), "Please Enable Location, We need location for the feed", Toast.LENGTH_LONG).show();
        }
        else {
            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelAnonymousWall> call;
            call = api.anonymousfeed(LONG,LAT);
            call.enqueue(new Callback<ModelAnonymousWall>() {
                @Override
                public void onResponse(Call<ModelAnonymousWall> call, Response<ModelAnonymousWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {

                        feed = response.body().getPosts();
                        System.out.println(feed);
                        adapter = new DarkAdapter(feed, getActivity(), getActivity().getSupportFragmentManager());
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

//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_dark_local_wall, container, false);
//
//        return view;
//    }
//}