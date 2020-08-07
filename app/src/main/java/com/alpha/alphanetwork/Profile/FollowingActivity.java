package com.alpha.alphanetwork.Profile;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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


public class FollowingActivity extends AppCompatActivity {




    private static final String TAG = "FollowersActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelFeed> feed = new ArrayList<>();
    private FollowAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView back;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Log.d(TAG, "onCreate: started.");

        back = findViewById(R.id.profileback);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'Home activity'");
                finish();

            }
        });


    }

    public void LoadJson() {


        Api api = RetrofitClient.getInstance().getApi();
        Call<ModelHomeWall> call;
        call = api.feed("temp","temp");
        call.enqueue(new Callback<ModelHomeWall>() {
            @Override
            public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
                if(response.isSuccessful() && response.body().getStatus()!=null){

                    feed = response.body().getPosts();
                    adapter = new FollowAdapter(feed, getApplicationContext(), FollowingActivity.this.getSupportFragmentManager());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();



                } else {

                    Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<ModelHomeWall> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure is triggered", Toast.LENGTH_LONG).show();
            }

        });

    }


}