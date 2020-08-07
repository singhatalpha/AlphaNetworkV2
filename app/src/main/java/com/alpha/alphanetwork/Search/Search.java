package com.alpha.alphanetwork.Search;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.alpha.alphanetwork.Model.SearchFeed;
import com.alpha.alphanetwork.Model.ViewProfile;
import com.alpha.alphanetwork.Profile.ProfileActivity;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Utils.BottomNavigationViewHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Search extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;

    private Context mContext = Search.this;

    //widgets
    private EditText mSearchParam;
    private ListView mListView;

    //vars
    private List<ViewProfile> mUserList;
    private UserListAdapter mAdapter;
    public String typeofp = null;
    public String p_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchParam =  findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.listView);
        Log.d(TAG, "onCreate: started.");

        hideSoftKeyboard();
        setupBottomNavigationView();
        initTextListener();
        Intent intent = getIntent();
        if (intent.hasExtra("activity") ){

            Log.d(TAG, "Getting pack or party");
            Bundle bundle = intent.getExtras();
            String act = bundle.getString("activity");
            System.out.println(act);
            typeofp = act;
            p_id = bundle.getString("id");




        }
    }

    private void initTextListener(){
        Log.d(TAG, "initTextListener: initializing");

        mUserList = new ArrayList<>();

        mSearchParam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = mSearchParam.getText().toString().toLowerCase(Locale.getDefault());
                searchForMatch(text);
            }
        });
    }

    private void searchForMatch(String keyword){
        Log.d(TAG, "searchForMatch: searching for a match: " + keyword);
        mUserList.clear();
        //update the users list view
        if(keyword.length() ==0){

        }else{

            Api api = RetrofitClient.getInstance().getApi();
            Call<SearchFeed> call;
            call = api.search(keyword);
            call.enqueue(new Callback<SearchFeed>() {
                @Override
                public void onResponse(Call<SearchFeed> call, Response<SearchFeed> response) {
                    if(response.isSuccessful() ){

                        mUserList = response.body().getProfiles();
                        System.out.println(mUserList);
                        updateUsersList();


                    } else {

                        Toast.makeText(Search.this, "No Response", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<SearchFeed> call, Throwable t) {
                    Toast.makeText(Search.this, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
                }

            });

//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//            Query query = reference.child(getString(R.string.dbname_users))
//                    .orderByChild(getString(R.string.field_username)).equalTo(keyword);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
//                        Log.d(TAG, "onDataChange: found user:" + singleSnapshot.getValue(User.class).toString());
//
//                        mUserList.add(singleSnapshot.getValue(User.class));
//                        //update the users list view
//                        updateUsersList();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        }
    }

    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");


        mAdapter = new UserListAdapter(Search.this, R.layout.layout_user_listitem, mUserList,typeofp,p_id);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected user: " + mUserList.get(position).toString());

                //navigate to profile activity
                Intent intent =  new Intent(Search.this, ProfileActivity.class);
                intent.putExtra("user_id", mUserList.get(position).getUser_id()) ;
//                intent.putExtra(getString(R.string.calling_activity), "Search");
//                intent.putExtra(getString(), mUserList.get(position));
                startActivity(intent);
            }
        });
    }


    private void hideSoftKeyboard(){
        if(getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
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