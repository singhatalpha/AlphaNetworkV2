package com.alpha.alphanetwork.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.alpha.alphanetwork.Model.ModelPack;
import com.alpha.alphanetwork.Model.ModelPackProfile;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;
import com.alpha.alphanetwork.Search.Search;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackActivity extends AppCompatActivity {
    private static final String TAG = "PackActivity";
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = PackActivity.this;

    //widgets



    private ListView mListView;
    private Button add,create;
    private ImageView back,dp;
    private TextView backtv,message,name,influence;
    public String pack_id,alpha,user_id,view_id;

    //vars
    private List<ModelPackProfile> mUserList;
    private PackUserAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack);
//        CreatePackActivity.urls.clear();

        mListView = (ListView) findViewById(R.id.listView);
        add = findViewById(R.id.add_members);
        create = findViewById(R.id.create_pack);
        back = findViewById(R.id.backArrow);
        backtv = findViewById(R.id.tvBack);
        message = findViewById(R.id.message);
        name = findViewById(R.id.username);
        dp = findViewById(R.id.profile_image);
        influence = findViewById(R.id.influence);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent =  new Intent(PackActivity.this, ProfileActivity.class);
////                intent.putExtra("user_id", mUserList.get(position).getUser_id()) ;
//                startActivity(intent);
                finish();
            }
        });
        backtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent =  new Intent(PackActivity.this, ProfileActivity.class);
////                intent.putExtra("user_id", mUserList.get(position).getUser_id()) ;
//                startActivity(intent);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(PackActivity.this, Search.class);
                intent.putExtra("activity", "pack") ;
                intent.putExtra("id", pack_id);
                startActivity(intent);
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(PackActivity.this, CreatePackActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: started.");



        if (getIntent().hasExtra("user_id")) {
            Bundle extras = getIntent().getExtras();
            view_id = extras.getString("user_id",null);
            view_pack();
        }

        else{
            init();
        }

//        init();

//        hideSoftKeyboard();
//        setupBottomNavigationView();
//        initTextListener();
    }
    @Override
    public void onBackPressed() {
//        Intent intent =  new Intent(PackActivity.this, ProfileActivity.class);
//        startActivity(intent);
        finish();

    }

    private void init(){
        Api api = RetrofitClient.getInstance().getApi();
        Call<ResponseBody> call;
        call = api.checkpack();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){

                message.setVisibility(View.GONE);

                loadjson();
                }
                else if (response.code()==400){
                    Toast.makeText(PackActivity.this, "No Pack Joined Yet", Toast.LENGTH_SHORT).show();
//                    add.setText("Create");
                    create.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(PackActivity.this, "No Response", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PackActivity.this, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void loadjson(){
        Log.d(TAG, "Loading Pack ");

        //update the users list view


            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelPack> call;
            call = api.getpack();
            call.enqueue(new Callback<ModelPack>() {
                @Override
                public void onResponse(Call<ModelPack> call, Response<ModelPack> response) {
                    if(response.isSuccessful() ){
                    name.setText(response.body().getName());
                    Glide.with(mContext).load(response.body().getDp()).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(dp);
                    influence.setText(""+response.body().getInfluence());
                    pack_id = response.body().getPack_id();
                    mUserList = response.body().getMembers();
                    user_id = response.body().getUser_id();
                    alpha = response.body().getAlpha();
                    if(user_id.equals(alpha)){
                        add.setVisibility(View.VISIBLE);
                    }
                    if(response.body().getMembercount()>=12){
                        add.setVisibility(View.GONE);
                    }
                    updateUsersList();

                    } else {

                        Toast.makeText(PackActivity.this, "No Response", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<ModelPack> call, Throwable t) {
                    Toast.makeText(PackActivity.this, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
                }

            });



    }

    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");
        if(user_id.equals(alpha)) {
            mAdapter = new PackUserAdapter(PackActivity.this, R.layout.layout_pack_list, mUserList,true,"pack");
        }
        else{
            mAdapter = new PackUserAdapter(PackActivity.this, R.layout.layout_pack_list_members, mUserList,false,"pack");
        }
        mListView.setAdapter(mAdapter);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: selected user: " + mUserList.get(position).toString());
//
//                //navigate to profile activity
//                Intent intent =  new Intent(Search.this, ProfileActivity.class);
//                intent.putExtra("user_id", mUserList.get(position).getUser_id()) ;
////                intent.putExtra(getString(R.string.calling_activity), "Search");
////                intent.putExtra(getString(), mUserList.get(position));
//                startActivity(intent);
//            }
//        });




    }


    private void view_pack(){

        create.setVisibility(View.GONE);
        add.setVisibility(View.GONE);

        Api api = RetrofitClient.getInstance().getApi();
        Call<ResponseBody> call;
        call = api.view_checkpack(view_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    message.setVisibility(View.GONE);
                    view_loadjson();
                }
                else if (response.code()==400){
                    message.setText("No Pack Joined Yet");
                }
                else {
                    Toast.makeText(PackActivity.this, "No Response", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PackActivity.this, "onFailure is triggered", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void view_loadjson(){
        Log.d(TAG, "Loading Pack ");

        //update the users list view


        Api api = RetrofitClient.getInstance().getApi();
        Call<ModelPack> call;
        call = api.view_getpack(view_id);
        call.enqueue(new Callback<ModelPack>() {
            @Override
            public void onResponse(Call<ModelPack> call, Response<ModelPack> response) {
                if(response.isSuccessful() ){
                    name.setText(response.body().getName());
                    Glide.with(mContext).load(response.body().getDp()).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(dp);
                    influence.setText(""+response.body().getInfluence());
                    pack_id = response.body().getPack_id();
                    mUserList = response.body().getMembers();
                    user_id = response.body().getUser_id();
                    alpha = response.body().getAlpha();
                    Log.d(TAG, "updateUsersList: updating users list");


                    mAdapter = new PackUserAdapter(PackActivity.this, R.layout.layout_pack_list_members, mUserList,false,"pack");

                    mListView.setAdapter(mAdapter);


                } else {

                    Toast.makeText(PackActivity.this, "No Response", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ModelPack> call, Throwable t) {
                Toast.makeText(PackActivity.this, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
            }

        });



    }
//    private void createpack(){
//        Api api = RetrofitClient.getInstance().getApi();
//        Call<ResponseBody> call;
//        call = api.createpack();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.code()==200){
//                    Toast.makeText(PackActivity.this, "Pack Created", Toast.LENGTH_SHORT).show();
//                    message.setVisibility(View.GONE);
//                    add.setVisibility(View.VISIBLE);
////                loadjson();
//                }
//                else if (response.code()==401){
//                    Toast.makeText(PackActivity.this, "User not found/Token expired, Please re-login", Toast.LENGTH_SHORT).show();
//
//                    create.setVisibility(View.VISIBLE);
//                }
//                else {
//                    Toast.makeText(PackActivity.this, "No Response", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(PackActivity.this, "onFailure for Pack is triggered", Toast.LENGTH_LONG).show();
//            }
//
//        });
//    }
}