package com.example.alphanetwork.Notification;


import android.content.Context;

import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.alphanetwork.Model.ModelNotification;
import com.example.alphanetwork.Model.ModelNotificationFeed;
import com.example.alphanetwork.Model.ViewProfile;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 9/17/2017.
 */

public class NotificationAdapter extends ArrayAdapter<ModelNotificationFeed>{

    private static final String TAG = "UserListAdapter";


    private LayoutInflater mInflater;
    private List<ModelNotificationFeed> mUsers;
    private int layoutResource;
    private Context mContext;
    public String typeof;
    public String req_id;
    public String p_id;

    public NotificationAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ModelNotificationFeed> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        mUsers = objects;

    }

    private static class ViewHolder{
        TextView username, influence, designation;
        CircleImageView profileImage;
        Button accept;

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
            holder.accept = convertView.findViewById(R.id.accept);


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        typeof = mUsers.get(position).getType();
//        req_id = mUsers.get(position).getId();
//        p_id = mUsers.get(position).getP_id();

        if(mUsers.get(position).getType().equals("pack")){
            holder.accept.setText("ACCEPT PACK");
        }

        holder.username.setText(mUsers.get(position).getUsername());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Accepting", Toast.LENGTH_LONG).show();
                Api api = RetrofitClient.getInstance().getApi();
                Call<ResponseBody> call;
                call = api.accept(mUsers.get(position).getId(),mUsers.get(position).getType(),mUsers.get(position).getP_id());
                System.out.println(req_id);
                System.out.println(typeof);
                System.out.println(p_id);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.code()==200){
                            Toast.makeText(mContext, "Joining", Toast.LENGTH_LONG).show();
                            holder.accept.setVisibility(View.GONE);
                        }

                        else if(response.code()==400){

                            Toast.makeText(mContext, "You can't join multiple Parties or Packs", Toast.LENGTH_LONG).show();

                        }
                        else if(response.code()==300){

                            Toast.makeText(mContext, "Pack Member Limit Reached,Cant join.", Toast.LENGTH_LONG).show();

                        }

                        else {

                            Toast.makeText(mContext, "No Response", Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "onFailure for Comments is triggered", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

        holder.influence.setText(""+mUsers.get(position).getInfluence());
        Glide.with(mContext).load(mUsers.get(position).getPhoto()).dontAnimate().into(holder.profileImage);



        return convertView;
    }
}
