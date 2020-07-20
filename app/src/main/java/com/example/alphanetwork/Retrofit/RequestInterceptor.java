package com.example.alphanetwork.Retrofit;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;

import Utils.MyApp;
//import com.example.alphanetwork.MainActivity;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;



public class RequestInterceptor implements Interceptor {

//    .addHeader("Authenticator", prefs.getToken())
//    Authorization: Token jwt.token.here
    private SharedPreferences sharedpref;
    private Context context = MyApp.getContext();


//    public RequestInterceptor(Context context) {
//        this.context = context;
//    }


    @Override
    public Response intercept(Chain chain)
            throws IOException {

        sharedpref = context.getSharedPreferences("Login",Context.MODE_PRIVATE);
        String token = sharedpref.getString("token" , "NULL");
        System.out.println(token);


        Request original = chain.request();



            if(token == "NULL"){
                // don't add token header
                return chain.proceed(original);
            }
            else {
                token = "Token "+ token;
                System.out.println(token);
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", token);
                Request request = requestBuilder.build();
                return chain.proceed(request);

            }
        }




}

