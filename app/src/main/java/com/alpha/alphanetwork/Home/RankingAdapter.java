package com.alpha.alphanetwork.Home;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.alpha.alphanetwork.Model.ViewProfile;
import com.alpha.alphanetwork.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by User on 9/17/2017.
 */

public class RankingAdapter extends ArrayAdapter<ViewProfile>{

    private static final String TAG = "UserListAdapter";


    private LayoutInflater mInflater;
    private List<ViewProfile> mUsers;
    private int layoutResource;
    private Context mContext;


    public RankingAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ViewProfile> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        mUsers = objects;

    }

    private static class ViewHolder{
        TextView username, influence, designation;
        CircleImageView profileImage;
        Button add;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.influence = convertView.findViewById(R.id.influence);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.profile_image);
            holder.designation = convertView.findViewById(R.id.tv_designation);
            holder.add = convertView.findViewById(R.id.add_members);


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }



        holder.username.setText(mUsers.get(position).getUsername());


        holder.influence.setText(""+mUsers.get(position).getInfluence());
        Glide.with(mContext).load(mUsers.get(position).getPhoto()).dontAnimate().into(holder.profileImage);



        return convertView;
    }
}