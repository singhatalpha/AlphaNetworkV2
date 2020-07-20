package com.example.alphanetwork.Search;

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

public class UserListAdapter extends ArrayAdapter<ViewProfile>{

    private static final String TAG = "UserListAdapter";


    private LayoutInflater mInflater;
    private List<ViewProfile> mUsers;
    private int layoutResource;
    private Context mContext;
    public String typeofp;
    public String p_id;

    public UserListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ViewProfile> objects, String type, String p_id) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        mUsers = objects;
        typeofp = type;
        this.p_id = p_id;
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
            if(typeofp!=null){
                holder.add.setVisibility(View.VISIBLE);
            }

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        System.out.println(typeofp);

        holder.username.setText(mUsers.get(position).getUsername());
        if(typeofp!=null){
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user_id = mUsers.get(position).getUser_id();


                    System.out.println(typeofp);
                    Api api = RetrofitClient.getInstance().getApi();
                    Call<ResponseBody> call;
                    call = api.send(typeofp,user_id,p_id);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful() && response.code()==400){

                                Toast.makeText(mContext, "Your Pack has reached member limit", Toast.LENGTH_LONG).show();

                            }
                            else if(response.isSuccessful() && response.code()==200){
                                Toast.makeText(mContext, "Request Sent", Toast.LENGTH_LONG).show();
                                holder.add.setVisibility(View.GONE);
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
        }
//        if(enable){
//            holder.designation.setText("Influence : ");
//        }
//        else{
//
//        }
        holder.influence.setText(""+mUsers.get(position).getInfluence());
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