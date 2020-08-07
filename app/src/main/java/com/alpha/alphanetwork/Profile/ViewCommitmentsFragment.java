package com.alpha.alphanetwork.Profile;





import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.alpha.alphanetwork.Model.CommitmentFeed;
import com.alpha.alphanetwork.Model.Commitments;
import com.alpha.alphanetwork.R;

import java.util.ArrayList;
import java.util.List;

import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/12/2017.
 */

public class ViewCommitmentsFragment extends Fragment {

    private static final String TAG = "CommitmentsFragment";



    //widgets
    private ImageView mBackArrow, mCheckMark;
    private EditText mComment;
    private ListView mListView;
    private String id;
    private String type;
    private String user_id;



    private List<Commitments> commitmentfeed = new ArrayList<>();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_commitments, container, false);
        mBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        mCheckMark = (ImageView) view.findViewById(R.id.ivPostComment);
        mComment = (EditText) view.findViewById(R.id.comment);
        mListView = (ListView) view.findViewById(R.id.listViewCommitments);
        mContext = getActivity();
        user_id = getArguments().getString("user_id");


//        String value = getArguments().getString("YourKey");
        setupWidgets();
        LoadJson();


        return view;
    }

    private void setupWidgets() {






        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back");

                getActivity().getSupportFragmentManager().popBackStack();



            }
        });





    }


    public void LoadJson() {


        Api api = RetrofitClient.getInstance().getApi();
        Call<CommitmentFeed> call;
        call = api.viewcommitments(user_id);
        call.enqueue(new Callback<CommitmentFeed>() {
            @Override
            public void onResponse(Call<CommitmentFeed> call, Response<CommitmentFeed> response) {
                if(response.isSuccessful() ){

                    commitmentfeed = response.body().getCommitments();

                    CommitmentsAdapter adapter = new CommitmentsAdapter(mContext,
                            R.layout.layout_commitments, commitmentfeed);

                    mListView.setAdapter(adapter);

                } else {

                    Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<CommitmentFeed> call, Throwable t) {
                Toast.makeText(getActivity(), "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
            }

        });

    }




}



