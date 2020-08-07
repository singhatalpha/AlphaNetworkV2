//package com.example.alphanetwork;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.net.Uri;
//
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.alphanetwork.Feed.Adapter;
//import com.example.alphanetwork.Feed.MediaAdapter;
////import com.example.alphanetwork.Feed.ViewCommentsFragment;
//import com.example.alphanetwork.Model.ModelFeed;
//import com.example.alphanetwork.Retrofit.Api;
//import com.example.alphanetwork.Model.ModelHomeWall;
//import com.example.alphanetwork.Retrofit.RetrofitClient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MediaAdapter.OnFragmentInteractionListener {
//
//
//    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;
//    private List<ModelFeed> feed = new ArrayList<>();
//    private Adapter adapter;
//    private String TAG = MainActivity.class.getSimpleName();
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private RelativeLayout errorLayout;
//    private ImageView errorImage;
//    private TextView errorTitle, errorMessage;
//    private Button btnRetry;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed);
//
//
//        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(MainActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setNestedScrollingEnabled(false);
//        onLoadingSwipeRefresh();
//
//        //FOr testing interceptor token
//
//
//        SharedPreferences sharedPref = getSharedPreferences("Login" , Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        String s = "52f29b7492ca7c80b1e7b63057d41b0ac419ad51";
//        editor.putString("token" , s);
//        editor.apply();
//
//
//        //For testing interceptor token
//    }
//
//    public void LoadJson() {
//
//        swipeRefreshLayout.setRefreshing(true);
//
//        Api api = RetrofitClient.getInstance().getApi();
//        Call <ModelHomeWall> call;
////        call = api.feed();
//        call.enqueue(new Callback<ModelHomeWall>() {
//            @Override
//            public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
//                if(response.isSuccessful() && response.body().getStatus()!=null){
//
//                    feed = response.body().getPosts();
//                    System.out.println(feed);
//                    adapter = new Adapter(feed, MainActivity.this, getSupportFragmentManager());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//
//                    swipeRefreshLayout.setRefreshing(false);
//
//                } else {
//                    swipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_LONG).show();
//
//                    }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ModelHomeWall> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "onFailure is triggered", Toast.LENGTH_LONG).show();
//            }
//
//        });
//
//    }
//
//
////    public void initListener(){
////        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(View view, int position) {
////                Toast.makeText(MainActivity.this, "Clicks not available yet", Toast.LENGTH_LONG).show();
////            }
////
////            @Override
////            public void iconTextViewOnClick(View view, int position) {
//////                Fragment fragment = new ViewCommentsFragment();
//////                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
//////                Bundle args = new Bundle();
//////                args.putString("YourKey", "YourValue");
//////                fragment.setArguments(args);
//////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//////                fragmentTransaction.replace(R.id.ViewCommentsFragment, fragment);
//////                fragmentTransaction.addToBackStack(null);
//////                fragmentTransaction.commit();
////            }
////
////            @Override
////            public void iconImageViewOnClick(View view, int position) {
//////                Fragment fragment = new ViewCommentsFragment();
//////                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
//////                Bundle args = new Bundle();
//////                args.putString("YourKey", "YourValue");
//////                fragment.setArguments(args);
//////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//////                fragmentTransaction.replace(R.id.fragment_view_comments, fragment);
//////                fragmentTransaction.addToBackStack(null);
//////                fragmentTransaction.commit();
////            }
////        });
////
////
////    }
//
//
//    @Override
//    public void onRefresh() {
//        LoadJson();
//    }
//
//    private void onLoadingSwipeRefresh(){
//
//        swipeRefreshLayout.post(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        LoadJson();
//                    }
//                }
//        );
//
//    }
//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//}
//
//
