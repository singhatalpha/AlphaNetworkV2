package com.alpha.alphanetwork.Circle;


import android.content.Context;
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


import com.alpha.alphanetwork.Feed.CommentListAdapter;
import com.alpha.alphanetwork.Model.CommentFeed;
import com.alpha.alphanetwork.Model.Comments;
import com.alpha.alphanetwork.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alpha.alphanetwork.Retrofit.Api;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/12/2017.
 */

public class CircleViewCommentsFragment extends Fragment {

    private static final String TAG = "ViewCommentsFragment";



    //widgets
    private ImageView mBackArrow, mCheckMark;
    private EditText mComment;
    private ListView mListView;
    private String id;
    private String type;

    public CircleViewCommentsFragment(String id,String type) {
        this.type = type;
        this.id = id;
    }
//vars

    private List<Comments> commentfeed = new ArrayList<>();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_comments, container, false);
        mBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        mCheckMark = (ImageView) view.findViewById(R.id.ivPostComment);
        mComment = (EditText) view.findViewById(R.id.comment);
        mListView = (ListView) view.findViewById(R.id.listView);
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
                    Log.d(TAG, "onClick: attempting to submit new comment.");
                    addNewComment(mComment.getText().toString());

                    mComment.setText("");
                    closeKeyboard();
                } else {
                    Toast.makeText(mContext, "you can't post a blank comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back");
// TO DO - if coming from home wall, do this, else do something else , in other cases, we wouldnt want to show layout of home!
                getActivity().getSupportFragmentManager().popBackStack();
                ((Circle)getActivity()).showLayout();


            }
        });



//        if(mPhoto.getComments().size() == 0){
//            mComments.clear();
//            Comment firstComment = new Comment();
//            firstComment.setComment(mPhoto.getCaption());
//            firstComment.setUser_id(mPhoto.getUser_id());
//            firstComment.setDate_created(mPhoto.getDate_created());
//            mComments.add(firstComment);
//            mPhoto.setComments(mComments);
//            setupWidgets();
//        }


    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                .addcomment(id,newComment,type);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
//                System.out.println(s);
                System.out.println(response.code());
                try {
                    if(response.code() == 200) {
                        Toast.makeText(mContext, "Comment Added", Toast.LENGTH_LONG).show();
                        LoadJson();
                    }
                    else
                    {
//                        System.out.println("else");


                        s = response.errorBody().string();
//                        JSONObject jsonObject = new JSONObject(s);

//                        Toast.makeText(Registration.this, s, Toast.LENGTH_LONG).show();



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

                Toast.makeText(mContext,"Couldn't reach the server",Toast.LENGTH_LONG).show();

            }
        });



    }

//    private String getTimestamp() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
//        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
//        return sdf.format(new Date());
//    }



    public void LoadJson() {


        Api api = RetrofitClient.getInstance().getApi();
        Call<CommentFeed> call;
        call = api.getcomments(id,type);
        call.enqueue(new Callback<CommentFeed>() {
            @Override
            public void onResponse(Call<CommentFeed> call, Response<CommentFeed> response) {
                if(response.isSuccessful() ){

                    commentfeed = response.body().getComments();
                    System.out.println(commentfeed);
                    CommentListAdapter adapter = new CommentListAdapter(mContext,
                            R.layout.layout_comment, commentfeed,type);

                    mListView.setAdapter(adapter);

                } else {

                    Toast.makeText(mContext, "No Response", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<CommentFeed> call, Throwable t) {
                Toast.makeText(mContext, "Couldn't reach the server", Toast.LENGTH_LONG).show();
            }

        });

    }




}


