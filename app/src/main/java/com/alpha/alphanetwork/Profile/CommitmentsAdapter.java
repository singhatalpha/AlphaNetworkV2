package com.alpha.alphanetwork.Profile;



import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.alpha.alphanetwork.Model.Commitments;
import com.alpha.alphanetwork.R;

import java.util.List;

import Utils.LikesToggle;
import Utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by User on 8/22/2017.
 */

public class CommitmentsAdapter extends ArrayAdapter<Commitments> {

    private static final String TAG = "CommentListAdapter";

    private LayoutInflater mInflater;
    private int layoutResource;
    private List<Commitments> commenties;
    private Context mContext;

    public CommitmentsAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List<Commitments> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        layoutResource = resource;
        this.commenties = objects;
    }

    public static class ViewHolder {
        TextView comment, username, timestamp, reply, likes;
        CircleImageView profileImage;
        ImageView like, dislike, liked, disliked;
        GestureDetector lGestureDetector;
        GestureDetector dGestureDetector;
        LikesToggle liking;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.comment = (TextView) convertView.findViewById(R.id.commitments);
            holder.timestamp = convertView.findViewById(R.id.comment_time_posted);


            holder.likes = convertView.findViewById(R.id.likes_count);

            holder.like = (ImageView) convertView.findViewById(R.id.comment_like);
            holder.dislike = convertView.findViewById(R.id.comment_dislike);
            holder.disliked = convertView.findViewById(R.id.comment_disliked);
            holder.liked = convertView.findViewById(R.id.comment_liked);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set the comment
        holder.comment.setText(getItem(position).getCommitment());
        System.out.println(getItem(position).getCommitment());


        holder.timestamp.setText(Utils.DateToTimeFormat(getItem(position).getDate()));


        holder.likes.setText(getItem(position).getLikes()+" likes");


        holder.lGestureDetector = new GestureDetector(mContext, new lGestureListener(holder, position));
        holder.dGestureDetector = new GestureDetector(mContext, new dGestureListener(holder, position));


        holder.liked.setVisibility(View.GONE);
        holder.like.setVisibility(View.VISIBLE);
        holder.disliked.setVisibility(View.GONE);
        holder.dislike.setVisibility(View.VISIBLE);

        holder.liking = new LikesToggle(holder.like, holder.liked, holder.dislike, holder.disliked);


        holder.liked.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.lGestureDetector.onTouchEvent(event);
            }
        });


        holder.like.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "Entered like touch listerner");
                return holder.lGestureDetector.onTouchEvent(event);
            }
        });


        holder.disliked.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.dGestureDetector.onTouchEvent(event);
            }
        });


        holder.dislike.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "Entered dislike touch listerner");
                return holder.dGestureDetector.onTouchEvent(event);
            }
        });


        //set the timestamp difference
//        String timestampDifference = getTimestampDifference(getItem(position));
//        if(!timestampDifference.equals("0")){
//            holder.timestamp.setText(timestampDifference + " d");
//        }else{
//            holder.timestamp.setText("today");
//        }


//        try{
//            if(position == 0){
//                holder.like.setVisibility(View.GONE);
//                holder.likes.setVisibility(View.GONE);
//                holder.reply.setVisibility(View.GONE);
//            }
//        }catch (NullPointerException e){
//            Log.e(TAG, "getView: NullPointerException: " + e.getMessage() );
//        }


        return convertView;
    }


    public class lGestureListener extends GestureDetector.SimpleOnGestureListener {
        ViewHolder holder;
        final int position;

        public lGestureListener(ViewHolder holder,int position) {
            this.holder = holder;
            this.position = position;

        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            String id = commenties.get(position).getId();
            System.out.println(id);
            System.out.println(position);
            holder.liking.toggleLike(id,"commitment");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            String id = commenties.get(position).getId();
            System.out.println(id);
            System.out.println(position);
            holder.liking.toggleLike(id,"commitment");
            return true;
        }


    }

    public class dGestureListener extends GestureDetector.SimpleOnGestureListener {


        ViewHolder holder;
        final int position;

        public dGestureListener(ViewHolder holder, final int position) {
            this.holder = holder;
            this.position = position;

        }


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            String id = commenties.get(position).getId();
            holder.liking.toggleDisLike(id,"commitment");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            String id = commenties.get(position).getId();
            holder.liking.toggleDisLike(id,"commitment");
            return true;
        }


    }
}



