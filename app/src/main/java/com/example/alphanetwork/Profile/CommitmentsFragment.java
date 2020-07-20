package com.example.alphanetwork.Profile;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.alphanetwork.Model.CommitmentFeed;
import com.example.alphanetwork.Model.Commitments;
import com.example.alphanetwork.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/12/2017.
 */

public class CommitmentsFragment extends Fragment {

    private static final String TAG = "CommitmentsFragment";



    //widgets
    private ImageView mBackArrow, mCheckMark;
    private EditText mComment;
    private ListView mListView;
    private String id;
    private String type;



    private List<Commitments> commitmentfeed = new ArrayList<>();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commitments, container, false);
        mBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        mCheckMark = (ImageView) view.findViewById(R.id.ivPostComment);
        mComment = (EditText) view.findViewById(R.id.commitment);
        mListView = (ListView) view.findViewById(R.id.listViewCommitments);
        mContext = getActivity();


//        String value = getArguments().getString("YourKey");
        setupWidgets();
        LoadJson();


        return view;
    }






    private void setupWidgets() {




        mCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mComment.getText().toString().equals("")) {
                    Log.d(TAG, "onClick: attempting to submit new Commitment.");
                    addNewComment(mComment.getText().toString());

                    mComment.setText("");
                    closeKeyboard();
                } else {
                    Toast.makeText(getActivity(), "you can't post a blank commitment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back");

                getActivity().getSupportFragmentManager().popBackStack();



            }
        });





    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void addNewComment(String newComment) {
        Log.d(TAG, "addNewComment: adding new comment: " + newComment);
//        Gson gson = new Gson();
//        String json = gson.toJson(id);
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .addcommitment(newComment);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
//                System.out.println(s);
                System.out.println(response.code());
                try {
                    if(response.code() == 200) {
                        Toast.makeText(getActivity(), "Commitment Added", Toast.LENGTH_LONG).show();
                        LoadJson();
                    }
                    else
                    {



                        s = response.errorBody().string();


                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();



                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(s!= null)
                {
                    try {

                        JSONObject jsonObject = new JSONObject(s);
//                        Toast.makeText(Registration.this, jsonObject.getString("detail"), Toast.LENGTH_LONG).show();


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }

    public void LoadJson() {


        Api api = RetrofitClient.getInstance().getApi();
        Call<CommitmentFeed> call;
        call = api.getcommitments();
        call.enqueue(new Callback<CommitmentFeed>() {
            @Override
            public void onResponse(Call<CommitmentFeed> call, Response<CommitmentFeed> response) {
                if(response.isSuccessful() ){

                    commitmentfeed = response.body().getCommitments();
                    System.out.println(commitmentfeed);
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


