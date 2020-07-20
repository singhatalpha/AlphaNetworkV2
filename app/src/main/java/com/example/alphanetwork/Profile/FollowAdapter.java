package com.example.alphanetwork.Profile;




import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.FragmentManager;

//import android.support.v4.view.PagerAdapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


import com.example.alphanetwork.Model.ModelFeed;

import com.example.alphanetwork.R;
import Utils.Utils;




public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.MyViewHolder>{

    private List<ModelFeed> posts;
    private Context context;
    private FragmentManager fragmentManager;
    private static final String TAG = "Adapter";






    public FollowAdapter(List<ModelFeed> posts, Context context,FragmentManager fragmentManager) {
        this.posts = posts;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_follow_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        ModelFeed modelFeed = posts.get(position);



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();


//        if(modelFeed.getMedia().size()!=0) {
//            holder.imgView_postPic.setVisibility(View.VISIBLE);
//            System.out.println(modelFeed.getMedia().get(0).getFile_data() );
//
//            Glide.with(context)
//                    .load(modelFeed.getMedia().get(0).getFile_data())
//                    .apply(requestOptions)
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//
//                            System.out.println(e);
//                            holder.progressBar.setVisibility(View.GONE);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//
////                            holder.progressBar.setVisibility(View.GONE);
//                            return false;
//                        }
//                    })
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .dontAnimate()
//                    .into(holder.imgView_postPic);
//
////            Glide.with(context).load(modelFeed.getMedia().get(0).getFile_data()).dontAnimate().into(holder.imgView_postPic);
//        }
//        else{
////            holder.imgView_postPic.setVisibility(View.GONE);
//        }



        holder.tv_name.setText(modelFeed.getProfile().getUser());
        Glide.with(context).load(modelFeed.getProfile().getPhoto()).dontAnimate().into(holder.imgView_proPic);


    }

    @Override
    public int getItemCount() {

        return posts.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView imgView_proPic, imgView_profilemenu, back;
        Button follow;







        public MyViewHolder(View itemView) {

            super(itemView);


            imgView_proPic = (ImageView) itemView.findViewById(R.id.imgView_proPic);
//            imgView_postPic = (ImageView) itemView.findViewById(R.id.imgView_postPic);


            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            imgView_profilemenu = itemView.findViewById(R.id.profileMenu);
            back = itemView.findViewById(R.id.profileback);
            follow = itemView.findViewById(R.id.follow);

            //comments

//            tv_comments.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    ((Home)context).onCommentThreadSelected();
//
//                    //going to need to do something else?
//                    ((Home)context).hideLayout();
//
//                }
//            });



        }


    }}

