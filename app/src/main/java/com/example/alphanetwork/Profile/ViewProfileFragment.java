package com.example.alphanetwork.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.Model.ModelHomeWall;
import com.example.alphanetwork.Model.ModelViewProfile;
import com.example.alphanetwork.Model.ViewProfile;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import Utils.BaseBackPressedListener;
import Utils.ExpandableHeightGridView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewProfileFragment extends Fragment {

    private static final String TAG = "ViewProfileFragment";


    public interface OnGridImageSelectedListener{
        void onGridImageSelected(List<ModelFeed> feed, int position);
    }

    OnGridImageSelectedListener mOnGridImageSelectedListener;

    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;




    //widgets
    private TextView mDisplayName, mInfluence, mPopularity, mCommitment, mPackname, mPartyname, mCommitment1, position ;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto, mPackimage,mPartyimage;
    private ExpandableHeightGridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu, back;


    private Context mContext;

    private List<ModelFeed> feed = new ArrayList<>();
    private ViewProfile vp;
    private String user_id;
    //vars
//    private int mFollowersCount = 0;
//    private int mFollowingCount = 0;
//    private int mPostsCount = 0;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



//        ((ProfileActivity)getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()));



        mDisplayName = (TextView) view.findViewById(R.id.display_name);
//        mUsername = (TextView) view.findViewById(R.id.username);
        position = (TextView) view.findViewById(R.id.position);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);

        mProgressBar = (ProgressBar) view.findViewById(R.id.profileProgressBar);
        back = view.findViewById(R.id.profileback);

        gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
        gridView.setExpanded(true);


        toolbar = (Toolbar) view.findViewById(R.id.profileToolBar);
        profileMenu = (ImageView) view.findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings.");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



        mInfluence = (TextView) view.findViewById(R.id.tv_influence);
        mPopularity = view.findViewById(R.id.tv_popularity);

        mCommitment = (TextView) view.findViewById(R.id.tv_commitments);
        mCommitment1 = view.findViewById(R.id.tv_commitment1);


        mPackname = (TextView) view.findViewById(R.id.tv_pack);
        mPartyname = (TextView) view.findViewById(R.id.tv_party);
        mPackimage =  view.findViewById(R.id.imgView_pack);
        mPartyimage = view.findViewById(R.id.imgView_party);



        user_id = getArguments().getString("user_id");



        mPackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), PackActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });
        mPackname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), PackActivity.class);
                intent.putExtra("user_id",user_id);

                startActivity(intent);
            }
        });
        mPartyimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), PartyActivity.class);
                intent.putExtra("user_id",user_id);

                startActivity(intent);
            }
        });
        mPartyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), PartyActivity.class);
                intent.putExtra("user_id",user_id);

                startActivity(intent);
            }
        });






        mContext = getActivity();

        Log.d(TAG, "onCreateView: stared.");



//        setupToolbar();
        setProfileWidgets(user_id);
        setupGridView(user_id);

//        getFollowersCount();
//        getFollowingCount();
//        getPostsCount();

//        TextView editProfile = (TextView) view.findViewById(R.id.textEditProfile);
//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to " + mContext.getString(R.string.edit_profile_fragment));
//                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
//                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
//                startActivity(intent);
////                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });

//  FOLLOWERS

//         mFollowers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to " + mContext.getString(R.string.edit_profile_fragment));
//                Intent intent = new Intent(getActivity(), FollowersActivity.class);
//                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
//                startActivity(intent);
////                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });
//        mFollowersCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to " + mContext.getString(R.string.edit_profile_fragment));
//                Intent intent = new Intent(getActivity(), FollowersActivity.class);
//                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
//                startActivity(intent);
////                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });



//   FOLLOWING
//        mFollowing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to " + mContext.getString(R.string.edit_profile_fragment));
//                Intent intent = new Intent(getActivity(), FollowingActivity.class);
//                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
//                startActivity(intent);
////                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });
//
//        mFollowingCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to " + mContext.getString(R.string.edit_profile_fragment));
//                Intent intent = new Intent(getActivity(), FollowingActivity.class);
//                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
//                startActivity(intent);
////                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });
//
//
//
//
//        mHighlights.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ViewProfileFragment fragment = new ViewProfileFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                Bundle args = new Bundle();
//                args.putString("onClick", "Highlights");
//                fragment.setArguments(args);
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack(String.valueOf(R.string.profile_fragment));
//                fragmentTransaction.commit();
//            }
//        });

        mCommitment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewCommitmentsFragment fragment = new ViewCommitmentsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle args = new Bundle();
                args.putString("user_id",user_id);
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(String.valueOf(R.string.profile_fragment));
                fragmentTransaction.commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'Home activity'");
//                getFragmentManager().popBackStack();
                getActivity().finish();

            }
        });







        return view;
    }

    @Override
    public void onAttach(Context context) {
        try{
            mOnGridImageSelectedListener = (OnGridImageSelectedListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
        super.onAttach(context);
    }
    @Override
    public void onResume() {
        super.onResume();
        setProfileWidgets(user_id);
    }

    private void setupGridView(String user_id){
        Log.d(TAG, "setupGridView: Setting up image grid.");

        //setup our image grid
        Api api = RetrofitClient.getInstance().getApi();
        Call<ModelHomeWall> call;
        call = api.feedgrid(user_id);
        call.enqueue(new Callback<ModelHomeWall>() {
            @Override
            public void onResponse(Call<ModelHomeWall> call, Response<ModelHomeWall> response) {
                if(response.isSuccessful() && response.body().getStatus()!=null){

                    feed = response.body().getPosts();
                    System.out.println(feed);
//                    adapter = new Adapter(feed, getActivity(), getActivity().getSupportFragmentManager());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();



                    int gridWidth = getResources().getDisplayMetrics().widthPixels;
                    int imageWidth = gridWidth/NUM_GRID_COLUMNS;
                    gridView.setColumnWidth(imageWidth);

                    ArrayList<String> imgUrls = new ArrayList<String>();

                    for(int i = 0; i < feed.size(); i++){
                        ModelFeed modelFeed = feed.get(i);
                        List<String> urls = modelFeed.getMedia();

                        if(urls.size() != 0)
                        {
                            imgUrls.add(modelFeed.getMedia().get(0));
                        }

                    }


                    System.out.println(imgUrls);


                    GridImageAdapter adapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_imageview,
                            "", imgUrls);
                    gridView.setAdapter(adapter);




                } else {

                    Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<ModelHomeWall> call, Throwable t) {
                Toast.makeText(getActivity(), "onFailure is triggered", Toast.LENGTH_LONG).show();
            }

        });







        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnGridImageSelectedListener.onGridImageSelected(feed, position);
            }
        });
    }



    private void setProfileWidgets(String user_id) {

        Log.d(TAG, "setupProfileWidgets: Setting up profile widgets.");

        //setup our image grid
        Api api = RetrofitClient.getInstance().getApi();
        Call<ModelViewProfile> call;
        call = api.getViewProfile(user_id);
        call.enqueue(new Callback<ModelViewProfile>() {
            @Override
            public void onResponse(Call<ModelViewProfile> call, Response<ModelViewProfile> response) {
                if(response.isSuccessful() ){
                    Log.d(TAG, "setupProfileWidgets: got response");
                    if (response.body() != null) {
                        System.out.println(response.body());
                        System.out.println(vp);
                        vp = response.body().getProfile();
                        mDisplayName.setText(vp.getUsername());
                        mInfluence.setText(""+vp.getInfluence());
                        mPopularity.setText(""+vp.getPopularity());
                        if(!vp.getCommitment().isEmpty()) {
                            mCommitment1.setText(vp.getCommitment());
                        }
                        mPackname.setText(vp.getPack().getPackname());
                        mPartyname.setText(vp.getParty().getPartyname());
                        System.out.println(vp.getPosition());
                        if(!vp.getPosition().isEmpty()){
                            position.setVisibility(View.VISIBLE);
                            position.setText(vp.getPosition());
                        }

                        Glide.with(getActivity())
                                .load(vp.getPack().getPackimage())
                                .placeholder(R.drawable.alpha)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mPackimage);

//        mFollowingCount.setText(viewProfile.getFollowing());
                        mProgressBar.setVisibility(View.GONE);


                        Glide.with(getActivity())
                                .load(vp.getParty().getPartyimage())
                                .placeholder(R.drawable.alpha)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mPartyimage);
//        mFollowingCount.setText(viewProfile.getFollowing());
                        mProgressBar.setVisibility(View.GONE);



                        Glide.with(getActivity())
                                .load(vp.getPhoto())
                                .placeholder(R.drawable.alpha)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mProfilePhoto);
//        mFollowingCount.setText(viewProfile.getFollowing());
                        mProgressBar.setVisibility(View.GONE);
                    }



                } else {
                    Log.d(TAG, "setupProfileWidgets: No damn response");
                    Toast.makeText(getActivity(), "No Response", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<ModelViewProfile> call, Throwable t) {
                Log.d(TAG, "setupProfileWidgets: On failure");
                Toast.makeText(getActivity(), "onFailure is triggered", Toast.LENGTH_LONG).show();
            }

        });





    }




}