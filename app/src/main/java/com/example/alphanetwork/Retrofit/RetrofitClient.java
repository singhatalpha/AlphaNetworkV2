package com.example.alphanetwork.Retrofit;

import android.app.Activity;
import android.content.Context;

//import com.example.alphanetwork.MainActivity;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.0.107:3000/api/";
//    private static final String BASE_URL = "http://alphanetwork.herokuapp.com/api/";
//    private static final String BASE_URL = "https://knowingisowning.com/api/";




    private static RetrofitClient mInstance;
    private static Retrofit retrofit;
    public Context context;



    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new RequestInterceptor()) // This is used to add ApplicationInterceptor.
            .addNetworkInterceptor(new RequestInterceptor()) //This is used to add NetworkInterceptor.
            .build();


    private RetrofitClient()
    {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new RetrofitClient();
        }
        return  mInstance;
    }


    public Api getApi()
    {
        return retrofit.create(Api.class);
    }
}



