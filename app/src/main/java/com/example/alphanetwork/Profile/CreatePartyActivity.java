package com.example.alphanetwork.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.core.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.alphanetwork.Model.ModelFeed;
import com.example.alphanetwork.Model.ModelHomeWall;
import com.example.alphanetwork.Model.ModelViewProfile;
import com.example.alphanetwork.Model.ViewProfile;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.Api;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.example.alphanetwork.addmedia.pictureselector.GlideCacheEngine;
import com.example.alphanetwork.addmedia.pictureselector.GlideEngine;
import com.example.alphanetwork.addmedia.pictureselector.adapter.GridImageAdapter;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.instagram.InsGallery;
import com.luck.picture.lib.listener.OnResultCallbackListener;
//import com.example.alphanetwork.addpost.gallery;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.alphanetwork.addmedia.pictureselector.MainActivity.isVideoFile;


/**
 * Created by User on 6/4/2017.
 */

public class CreatePartyActivity extends AppCompatActivity {


    private static final String TAG = "EditProfileFragment";

    private Context mContext = CreatePartyActivity.this;


    private EditText mUsername;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;
//    public static List<String> urls = new ArrayList<>();
    public static String url = null;



    //vars
//    private UserSettings mUserSettings;


    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        mProfilePhoto = findViewById(R.id.profile_photo);
        mUsername = findViewById(R.id.username);
        mChangeProfilePhoto = (TextView) findViewById(R.id.changeProfilePhoto);
        mChangeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                urls.clear();
//                Intent i = new Intent(mContext, gallery.class);
//                i.putExtra(getString(R.string.calling_activity), "party");
//                mContext.startActivity(i);
                url = null;
                InsGallery.openGallery(CreatePartyActivity.this, GlideEngine.createGlideEngine(), GlideCacheEngine.createCacheEngine(), new OnResultCallbackListenerImpl());


            }
        });





        //back arrow for navigating back to "ProfileActivity"
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to PartyActivity");
                url = null;
                finish();
            }
        });

        ImageView checkmark = (ImageView) findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save changes.");
                try {
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void onBackPressed() {
        finish();

    }


    @Override
    public void onResume() {
        super.onResume();

        if(url!=null) {
//            File file = new File(urls.get(0));
            System.out.println("The url in ON RESUME IS :");
            boolean isVideo = isVideoFile(url);
            if (isVideo) {
                Toast.makeText(mContext, "Video Not Allowed in Display Picture", Toast.LENGTH_LONG).show();
            } else {
                Glide.with(mContext)
                        .load(new File(url))// Uri of the picture
                        .into(mProfilePhoto);
            }
        }
    }

    private void save() throws IOException {

        final String user = mUsername.getText().toString();

        boolean isVideo = isVideoFile(url);

        if (isVideo) {
            Toast.makeText(mContext, "Video Not Allowed in Display Picture", Toast.LENGTH_LONG).show();
        }


//pass it like this

        RequestBody username =
                RequestBody.create(MediaType.parse("multipart/form-data"), user);
//        RequestBody phone =
//                RequestBody.create(MediaType.parse("multipart/form-data"), phoneNumber);
//        RequestBody email =
//                RequestBody.create(MediaType.parse("multipart/form-data"), mail);

        if (url != null && !isVideo) {
            List<MultipartBody.Part> parts = new ArrayList<>();

            System.out.println("The urls are :" + url);

            File file = new File(url);

//                RequestBody requestFile =
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), new Compressor(mContext).compressToFile(file));

// MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("media", file.getName(), requestFile);
            parts.add(body);


            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .createparty(parts, username);
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {


                    if(response.code()==200){
                        Toast.makeText(CreatePartyActivity.this, "Party Created", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(CreatePartyActivity.this, PartyActivity.class);
                        startActivity(intent);
                    }

                    Log.v("Upload", "success");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });
        }
        else{
            Toast.makeText(CreatePartyActivity.this, "Please provide both name and image", Toast.LENGTH_LONG).show();
        }
    }

    private static class OnResultCallbackListenerImpl implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapter;

        public OnResultCallbackListenerImpl() {

        }

        @Override
        public void onResult(List<LocalMedia> result) {

            url = result.get(0).getCutPath();
            if(url==null){
                url = result.get(0).getPath();
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "PictureSelector Cancel");
        }
    }
//
//
//
//    private void createpack(){
//        Api api = RetrofitClient.getInstance().getApi();
//        Call<ResponseBody> call;
//        call = api.createpack();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.code()==200){
//                    Toast.makeText(CreatePackActivity.this, "Pack Created", Toast.LENGTH_SHORT).show();
//
//
//                }
//                else if (response.code()==401){
//                    Toast.makeText(CreatePackActivity.this, "User not found/Token expired, Please re-login", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(CreatePackActivity.this, "No Response", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(CreatePackActivity.this, "onFailure for Pack is triggered", Toast.LENGTH_LONG).show();
//            }
//
//        });
//    }
}
