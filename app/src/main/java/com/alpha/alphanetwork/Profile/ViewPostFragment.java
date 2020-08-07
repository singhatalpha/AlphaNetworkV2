package com.alpha.alphanetwork.Profile;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alpha.alphanetwork.Model.ModelFeed;
import com.alpha.alphanetwork.R;

import java.util.ArrayList;


public class ViewPostFragment extends Fragment {




    private static final String TAG = "ViewPostFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ViewPostAdapter adapter;
    private ImageView verified_filter, profession_filter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feed, container, false);

        Bundle bundle = getArguments();
        ArrayList<ModelFeed> feed = bundle.getParcelableArrayList("feed");
        int position = bundle.getInt("position");


        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.scrollToPosition(position);
//        recyclerView.smoothScrollToPosition(position);

        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.smoothScrollToPosition(position);
//        layoutManager.scrollToPosition(position);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

//        smoothScroller.setTargetPosition(position);  // pos on which item you want to scroll recycler view
//        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);

        verified_filter = view.findViewById(R.id.verified_filter);
        profession_filter = view.findViewById(R.id.profession_filter);
        verified_filter.setVisibility(View.GONE);
        profession_filter.setVisibility(View.GONE);


        init(feed,position);










        return view;
    }

//    LinearSmoothScroller smoothScroller=new LinearSmoothScroller(getActivity()){
//        @Override
//        protected int getVerticalSnapPreference() {
//            return LinearSmoothScroller.SNAP_TO_START;
//        }
//    };





    public void init(ArrayList<ModelFeed> feed,int position) {


                        adapter = new ViewPostAdapter(feed,position, getActivity(), getActivity().getSupportFragmentManager());
//                        recyclerView.scrollToPosition(position);
//                        layoutManager.scrollToPosition(position);
//            ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(position,0);

//                        recyclerView.smoothScrollToPosition(position);
//                        layoutManager.smoothScrollToPosition(recyclerView,null,position);
                        recyclerView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();




        }



}