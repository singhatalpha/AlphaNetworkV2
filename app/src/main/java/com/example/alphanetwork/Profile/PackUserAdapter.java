package com.example.alphanetwork.Profile;

import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.alphanetwork.Model.ModelPack;
import com.example.alphanetwork.Model.ModelPackProfile;
import com.example.alphanetwork.Model.ViewProfile;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 9/17/2017.
 */

public class PackUserAdapter extends ArrayAdapter<ModelPackProfile>{

    private static final String TAG = "UserListAdapter";


    private LayoutInflater mInflater;
    private List<ModelPackProfile> mUsers;
    private int layoutResource;
    private Context mContext;
    private boolean isAlpha = false;
    private String type;



    public PackUserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ModelPackProfile> objects, boolean alpha,String t) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        mUsers = objects;
        isAlpha = alpha;
        type = t;

    }

    private static class ViewHolder{
        TextView username, influence, designation;
        CircleImageView profileImage;
        ImageView rankup,rankdown,remove;
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
            if(isAlpha) {
                holder.remove = convertView.findViewById(R.id.remove);
                holder.rankup = convertView.findViewById(R.id.up);
                holder.rankdown = convertView.findViewById(R.id.down);

            }

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if (type.equals("pack")) {
            holder.designation.setText(mUsers.get(position).getPackdesig());
        }
        else{
            holder.designation.setText(mUsers.get(position).getPartydesig());

        }
        if(isAlpha) {
            if (type.equals("pack")) {

                if (mUsers.get(position).getPackdesig().equals("Alpha")) {
                    holder.remove.setVisibility(View.GONE);
                    holder.rankup.setVisibility(View.GONE);
                    holder.rankdown.setVisibility(View.GONE);
                }
            } else {

                if (mUsers.get(position).getPartydesig().equals("President")) {
                    holder.remove.setVisibility(View.GONE);
                    holder.rankup.setVisibility(View.GONE);
                    holder.rankdown.setVisibility(View.GONE);
                }

            }
        }




        if(isAlpha) {

                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Api api = RetrofitClient.getInstance().getApi();
                        Call<ResponseBody> call;
                        call = api.remove(type,mUsers.get(position).getUser(),position);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200 ){
                                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_LONG).show();

                                }
                                else if(response.code()==401 ) {

                                    Toast.makeText(getContext(), "Not Authorised", Toast.LENGTH_LONG).show();

                                }
                                else {

                                    Toast.makeText(getContext(), "No Response or Member not found", Toast.LENGTH_LONG).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), "onFailure is triggered", Toast.LENGTH_LONG).show();
                            }

                        });

                    }
                });
                holder.rankup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Api api = RetrofitClient.getInstance().getApi();
                        Call<ResponseBody> call;
                        call = api.promote(type,mUsers.get(position).getUser(),position);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200 ){
                                    Toast.makeText(getContext(), "Promoted", Toast.LENGTH_LONG).show();

                                }
                                else if(response.code()==401 ) {

                                    Toast.makeText(getContext(), "Not Authorised or Rank limit reached", Toast.LENGTH_LONG).show();

                                }
                                else {

                                    Toast.makeText(getContext(), "No Response or Member not found", Toast.LENGTH_LONG).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), "onFailure is triggered", Toast.LENGTH_LONG).show();
                            }

                        });
//                        Toast.makeText(getContext(), "Rank Upgraded", Toast.LENGTH_LONG).show();
                    }
                });
                holder.rankdown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Api api = RetrofitClient.getInstance().getApi();
                        Call<ResponseBody> call;
                        call = api.demote(type,mUsers.get(position).getUser(),position);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200 ){
                                    Toast.makeText(getContext(), "Demoted", Toast.LENGTH_LONG).show();

                                }
                                else if(response.code()==401 ) {

                                    Toast.makeText(getContext(), "Not Authorised or Rank limit reached", Toast.LENGTH_LONG).show();

                                }
                                else {

                                    Toast.makeText(getContext(), "No Response or Member not found", Toast.LENGTH_LONG).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), "onFailure is triggered", Toast.LENGTH_LONG).show();
                            }

                        });
//                        Toast.makeText(getContext(), "Rank Downgraded", Toast.LENGTH_LONG).show();
                    }
                });

        }

        holder.username.setText(mUsers.get(position).getName());

//        if(enable){
//            holder.designation.setText("Influence : ");
//        }
//        else{
//
//        }
//        holder.influence.setText(""+mUsers.get(position).getInfluence());
        Glide.with(mContext).load(mUsers.get(position).getPhoto()).dontAnimate().into(holder.profileImage);


//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference.child(mContext.getString(R.string.dbname_user_account_settings))
//                .orderByChild(mContext.getString(R.string.field_user_id))
//                .equalTo(getItem(position).getUser_id());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: found user: " +
//                            singleSnapshot.getValue(UserAccountSettings.class).toString());
//
//                    ImageLoader imageLoader = ImageLoader.getInstance();
//
//                    imageLoader.displayImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
//                            holder.profileImage);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        return convertView;
    }
}
