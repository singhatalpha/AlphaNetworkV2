package com.alpha.alphanetwork.Circle;



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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alpha.alphanetwork.Feed.MediaAdapter;
import com.alpha.alphanetwork.Model.ModelFeed;
import com.alpha.alphanetwork.Model.ModelHomeWall;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;

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
    private ImageView verified_filter, profession_filter, both_filter;
    private RelativeLayout relativeLayout1;



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
//        verified_filter = view.findViewById(R.id.verified_filter);
//        profession_filter = view.findViewById(R.id.profession_filter);
//        both_filter = view.findViewById(R.id.both_filter);
        relativeLayout1 = view.findViewById(R.id.relLayout1);
        relativeLayout1.setVisibility(View.GONE);

        LoadJson();




        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerView.setAdapter(null);
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