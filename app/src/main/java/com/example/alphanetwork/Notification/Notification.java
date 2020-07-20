package com.example.alphanetwork.Notification;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.health.SystemHealthManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alphanetwork.Model.ModelNotification;
import com.example.alphanetwork.Model.ModelNotificationFeed;
import com.example.alphanetwork.Model.SearchFeed;
import com.example.alphanetwork.Model.ViewProfile;
import com.example.alphanetwork.Profile.ProfileActivity;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Utils.BottomNavigationViewHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Notification extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = Notification.this;

    //widgets
    private EditText mSearchParam;
    private ListView mListView;

    //vars
    private List<ModelNotificationFeed> mUserList;
    private NotificationAdapter mAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mSearchParam =  findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.listView);
        Log.d(TAG, "onCreate: started.");


        setupBottomNavigationView();
        getNotification();


    }



    private void getNotification(){
        Log.d(TAG, "Getting Notifications" );

        //update the users list view




            Api api = RetrofitClient.getInstance().getApi();
            Call<ModelNotification> call;
            call = api.getnotification();
            call.enqueue(new Callback<ModelNotification>() {
                @Override
                public void onResponse(Call<ModelNotification> call, Response<ModelNotification> response) {
                    if(response.isSuccessful() ){
                        System.out.println(response.body());
                        mUserList = response.body().getNotifications();
                        System.out.println(mUserList);
                        updateUsersList();


                    } else {

                        Toast.makeText(Notification.this, "No Response", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<ModelNotification> call, Throwable t) {
                    Toast.makeText(Notification.this, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
                }

            });


        }


    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");
        if(mUserList!=null) {
            mAdapter = new NotificationAdapter(Notification.this, R.layout.layout_notification_listitem, mUserList);

            mListView.setAdapter(mAdapter);
        }

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: selected user: " + mUserList.get(position).toString());
//
//                //navigate to profile activity
//                Intent intent =  new Intent(Notification.this, ProfileActivity.class);
//                intent.putExtra("user_id", mUserList.get(position).getUser_id()) ;
////                intent.putExtra(getString(R.string.calling_activity), "Search");
////                intent.putExtra(getString(), mUserList.get(position));
//                startActivity(intent);
//            }
//        });
    }





    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}