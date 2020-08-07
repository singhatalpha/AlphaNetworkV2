package com.alpha.alphanetwork.Home;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.alpha.alphanetwork.Dark.DarkAdapter;

//import com.example.alphanetwork.Dark.DarkMediaAdapter;
import com.alpha.alphanetwork.Model.ModelAnonymousFeed;
import com.alpha.alphanetwork.Model.ModelAnonymousWall;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeLocalWallFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{



    private Context mContext;
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
    private Bundle mBundleRecyclerViewState;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_darkfeed, container, false);


        mContext = getActivity();
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        sharedPref = getActivity().getSharedPreferences("Location" , Context.MODE_PRIVATE);
        String f = sharedPref.getString("darkfeed","NULL");

        if (!f.equals("NULL")) {
            Gson gson = new Gson();
            feed = gson.fromJson(f, ModelAnonymousWall.class).getPosts();
            adapter = new DarkAdapter(feed, getActivity(), getActivity().getSupportFragmentManager());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            LoadJson();
        }
        else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Anonymous feed contains content some people may not like. User discretion advised. Are you sure you want to continue?");
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
    public void onResume()
    {
        super.onResume();


        //INSERT CUSTOM CODE HERE
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (mBundleRecyclerViewState != null) {
//            Parcelable listState = mBundleRecyclerViewState.getParcelable("state");
//            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
//        }
//    }
//
//
//    public void onSaveInstanceState(Bundle state) {
//        super.onSaveInstanceState(state);
//
//
//        mBundleRecyclerViewState = new Bundle();
//        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
//        mBundleRecyclerViewState.putParcelable("state", listState);
//    }
//
//    public void onRestoreInstanceState(Bundle state) {
//        super.onRestoreInstanceState(state);
//
//        /if (mBundleRecyclerViewState != null) {
//            Parcelable listState = mBundleRecyclerViewState.getParcelable("state");
//            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
//        }
//    }

    public void LoadJson() {

        swipeRefreshLayout.setRefreshing(true);

        LONG  = sharedPref.getString("LONG" , "NULL");
        LAT   = sharedPref.getString("LAT","NULL");
        if(LONG=="NULL"){
            Toast.makeText(getActivity(), "Please Enable Location, location is require for the feed", Toast.LENGTH_LONG).show();
        }
        else {
            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelAnonymousWall> call;
            call = api.anonymousfeed(LONG,LAT);
            call.enqueue(new Callback<ModelAnonymousWall>() {
                @Override
                public void onResponse(Call<ModelAnonymousWall> call, Response<ModelAnonymousWall> response) {
                    if (response.isSuccessful() && response.body().getStatus() != null) {



                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("darkfeed" , json);
                        editor.apply();



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
//    @Override
//    public void onDarkFragmentInteraction(Uri uri) {
//
//    }


}
