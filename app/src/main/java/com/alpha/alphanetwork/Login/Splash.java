package com.alpha.alphanetwork.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.alpha.alphanetwork.Home.Home;
import com.alpha.alphanetwork.R;

import Utils.MyApp;

public class Splash extends Activity {


    private SharedPreferences sharedpref;
    private Context context = MyApp.getContext();


    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);


        sharedpref = context.getSharedPreferences("Login",Context.MODE_PRIVATE);
        final String token = sharedpref.getString("token" , "NULL");





        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(token == "NULL"){
                    Intent mainIntent = new Intent(Splash.this, user_login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
                else{
                    Intent mainIntent = new Intent(Splash.this, Home.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}