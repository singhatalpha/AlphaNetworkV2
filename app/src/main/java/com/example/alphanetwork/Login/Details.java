package com.example.alphanetwork.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.alphanetwork.Home.Home;
import com.example.alphanetwork.Model.ViewProfile;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.example.alphanetwork.addmedia.pictureselector.GlideCacheEngine;
import com.example.alphanetwork.addmedia.pictureselector.GlideEngine;
import com.example.alphanetwork.addmedia.pictureselector.adapter.GridImageAdapter;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.instagram.InsGallery;
import com.luck.picture.lib.listener.OnResultCallbackListener;

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

public class Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private EditText mDisplayName, mUsername, mPhone, mEmail;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;
    private Context mContext;
    public static String url = null;
    private ViewProfile vp;
    public String profession = null;
    private Button proceed;
    private static final String[] paths = {"NONE", "Accountant", "Actor", "Athlete", "Businessman", "Doctor", "Engineer", "Lawyer", "Farmer", "Gamer", "Police", "Politician",
            "Student", "Teacher"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Details.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mContext = Details.this;


        mProfilePhoto = (CircleImageView) findViewById(R.id.profile_photo);
//        mPhone = view.findViewById(R.id.phoneNumber);
//        mEmail = view.findViewById(R.id.email);
        mUsername = (EditText) findViewById(R.id.username);
        mChangeProfilePhoto = (TextView) findViewById(R.id.changeProfilePhoto);
        mChangeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = null;
                InsGallery.openGallery(Details.this, GlideEngine.createGlideEngine(), GlideCacheEngine.createCacheEngine(), new OnResultCallbackListenerImpl());

            }
        });
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveProfileSettings();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onResume() {
//        if (urls.size() != 0) {
        super.onResume();

        if(url!=null) {
//            File file = new File(urls.get(0));
            System.out.println("The url in ON RESUME IS :");
            boolean isVideo = isVideoFile(url);
            if (isVideo) {
                Toast.makeText(getApplication(), "Video Not Allowed in Display Picture", Toast.LENGTH_LONG).show();
            } else {
                Glide.with(getApplication())
                        .load(new File(url))// Uri of the picture
                        .into(mProfilePhoto);
            }
        }
    }

    private void saveProfileSettings() throws IOException {

        final String user = mUsername.getText().toString();
        final String prof = profession;
        if(profession==null || profession.equals("NONE")){
            Toast.makeText(getApplication(), "Please select a profession", Toast.LENGTH_LONG).show();
        }
        if(user.equals("")){
            Toast.makeText(getApplication(), "Name cannot be empty", Toast.LENGTH_LONG).show();
        }
        boolean isVideo = isVideoFile(url);

        List<MultipartBody.Part> parts = null;


//pass it like this

        RequestBody username =
                RequestBody.create(MediaType.parse("multipart/form-data"), user);
        RequestBody professionrb =
                RequestBody.create(MediaType.parse("multipart/form-data"), prof);
//        RequestBody phone =
//                RequestBody.create(MediaType.parse("multipart/form-data"), phoneNumber);
//        RequestBody email =
//                RequestBody.create(MediaType.parse("multipart/form-data"), mail);
        if (isVideo) {
            Toast.makeText(getApplication(), "Video Not Allowed in Display Picture", Toast.LENGTH_LONG).show();
        }

//        if (urls.size() != 0) {
        if (url != null && !isVideo) {

            parts = new ArrayList<>();

//            System.out.println("The urls are :" + urls);

//            File file = new File(urls.get(0));




            File file = new File(url);
//                RequestBody requestFile =
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), new Compressor(getApplication()).compressToFile(file));

// MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("media", file.getName(), requestFile);
            parts.add(body);

        }

        if(profession!=null && !user.equals("") && !profession.equals("NONE")){

            Toast.makeText(getApplication(), "Saving.......Please Wait.", Toast.LENGTH_SHORT).show();

            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .updateProfile(parts, username, professionrb);
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Done", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Details.this, Home.class);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                    String m = response.message();
                    System.out.println(m);

                    Log.v("Upload", "success");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        profession = parent.getItemAtPosition(position).toString();
        System.out.println(profession);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
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

        }
    }

}