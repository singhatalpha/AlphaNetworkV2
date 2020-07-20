package com.example.alphanetwork.Feed;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.alphanetwork.Model.Comments;
import com.example.alphanetwork.R;

import java.util.List;

import Utils.LikesToggle;
import Utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by User on 8/22/2017.
 */

public class CommentListAdapter extends ArrayAdapter<Comments> {

    private static final String TAG = "CommentListAdapter";

    private LayoutInflater mInflater;
    private int layoutResource;
    private List<Comments> commenties;
    private Context mContext;
    private String type;

    public CommentListAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List<Comments> objects, String type) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        layoutResource = resource;
        this.commenties = objects;
        this.type = type;
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

            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.username = (TextView) convertView.findViewById(R.id.comment_username);
            holder.timestamp = (TextView) convertView.findViewById(R.id.comment_time_posted);
//            holder.reply = (TextView) convertView.findViewById(R.id.comment_reply);
            holder.likes = convertView.findViewById(R.id.likes_count);

            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.comment_profile_image);


            holder.like = (ImageView) convertView.findViewById(R.id.comment_like);
            holder.dislike = convertView.findViewById(R.id.comment_dislike);
            holder.disliked = convertView.findViewById(R.id.comment_disliked);
            holder.liked = convertView.findViewById(R.id.comment_liked);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set the comment
        holder.comment.setText(getItem(position).getComment());


        holder.username.setText(getItem(position).getProfile().getUser());
        holder.timestamp.setText(Utils.DateToTimeFormat(getItem(position).getCommented_date()));
        Glide.with(mContext).load(getItem(position).getProfile().getPhoto()).dontAnimate()
                .placeholder(R.drawable.alphanotext)
                .into(holder.profileImage);

        System.out.println(getItem(position).getLikes_count());
        holder.likes.setText(String.valueOf(getItem(position).getLikes_count())+" likes");


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

        public lGestureListener(ViewHolder holder, final int position) {
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
            holder.liking.toggleLike(id,type);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            String id = commenties.get(position).getId();
            System.out.println(id);
            System.out.println(position);
            System.out.println(type);
            holder.liking.toggleLike(id,type);
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
            holder.liking.toggleDisLike(id,type);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            String id = commenties.get(position).getId();
            holder.liking.toggleDisLike(id,type);
            return true;
        }


    }
}















    /**
     * Returns a string representing the number of days ago the post was made
     * @return
     */
//    private String getTimestampDifference(Comments comment){
//        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");
//
//        String difference = "";
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
//        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));//google 'android list of timezones'
//        Date today = c.getTime();
//        sdf.format(today);
//        Date timestamp;
//        final String photoTimestamp = comment.getDate_created();
//        try{
//            timestamp = sdf.parse(photoTimestamp);
//            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
//        }catch (ParseException e){
//            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
//            difference = "0";
//        }
//        return difference;
//    }

