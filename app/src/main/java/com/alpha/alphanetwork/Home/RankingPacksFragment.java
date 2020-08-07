package com.alpha.alphanetwork.Home;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alpha.alphanetwork.Feed.MediaAdapter;
import com.alpha.alphanetwork.Model.PackFeed;
import com.alpha.alphanetwork.Model.PackRanking;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RankingPacksFragment extends Fragment implements  MediaAdapter.OnFragmentInteractionListener{



    private Context mContext ;
    private static final String TAG = "RankingAlphaFragment";
    private ListView mListView;

    //vars
    private List<PackFeed> mUserList;
    private PackAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        mContext = getActivity();
        mListView = (ListView) view.findViewById(R.id.listView);
        Log.d(TAG, "onCreate: started.");


        LoadJson();


        return view;
    }

    private void LoadJson(){


        Api api = RetrofitClient.getInstance().getApi();
        Call<PackRanking> call;
        call = api.getpackranking();
        call.enqueue(new Callback<PackRanking>() {
            @Override
            public void onResponse(Call<PackRanking> call, Response<PackRanking> response) {
                if(response.isSuccessful() ){

                    mUserList = response.body().getPackfeed();
                    System.out.println(mUserList);
                    updateUsersList();


                } else {

                    Toast.makeText(mContext, "No Response", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<PackRanking> call, Throwable t) {
                Toast.makeText(mContext, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");


        mAdapter = new PackAdapter(mContext, R.layout.layout_user_listitem, mUserList);

        mListView.setAdapter(mAdapter);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: selected user: " + mUserList.get(position).toString());
//
//                //navigate to profile activity
//                Intent intent =  new Intent(mContext, ProfileActivity.class);
//                intent.putExtra("user_id", mUserList.get(position).getUser_id()) ;
////                intent.putExtra(getString(R.string.calling_activity), "Search");
////                intent.putExtra(getString(), mUserList.get(position));
//                startActivity(intent);
//            }
//        });
    }






    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}