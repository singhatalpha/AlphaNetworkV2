package com.example.alphanetwork.Login;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.alphanetwork.Home.Home;
import com.example.alphanetwork.R;
import com.example.alphanetwork.Retrofit.RetrofitClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//560785564795-s7g0co0ofauusgmd3r35d3p97i2icf12.apps.googleusercontent.com

public class user_login extends AppCompatActivity {
    private static final String TAG = "user_login activity";
    private EditText id,pass;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
//                .requestServerAuthCode("560785564795-mdk5qqqe3cctv7iinvlle76o283l3rjn.apps.googleusercontent.com")
//                .requestIdToken("560785564795-mdk5qqqe3cctv7iinvlle76o283l3rjn.apps.googleusercontent.com")
                .requestEmail()
                .requestId()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        id = findViewById(R.id.txtEmail);
        pass = findViewById(R.id.txtPwd);

        Button login = (Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = id.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if(validateLogin(user,password)){
                    login(user,password);
                }
            }
        });
        SignInButton googleLogin = findViewById(R.id.sign_in_button);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }

            }
        });


        TextView register = (TextView)findViewById(R.id.lnkRegister);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_login.this, Registration.class);
                startActivity(intent);
            }
        });


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String idToken = account.getEmail();
            String id = account.getId();
            Log.w(TAG,"HERE COMES THE TOKEN ::::::::::::" + idToken);
            Toast.makeText(this, "Logging in, please wait.", Toast.LENGTH_LONG).show();
            // Signed in successfully, show authenticated UI.
            googlelogin(idToken,id);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
////        updateUI(account);
//    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void login(final String user, final String password){
//        String user = id.getText().toString().trim();
//        String password = pass.getText().toString().trim();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(user,password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String t = null;
                try {
                    if(response.code() == 200 || response.code() == 201) {

                        JSONObject json = new JSONObject(response.body().string());
                        JSONObject toki = json.getJSONObject("user");

                        t = toki.get("token").toString();
                        Log.d(TAG,t);


                        SharedPreferences sharedPref = getSharedPreferences("Login" , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Log.d(TAG, "Entered On response, token is: " + t);
                        // login token
                        editor.putString("token" , t);
                        // login boolean to check for login every time app is launched
                        editor.putBoolean("logged",true);
                        editor.apply();
                        String temp = sharedPref.getString("token" , "NULL");



                        //redirect to home activity if successful
                        Intent intent = new Intent(user_login.this, Home.class);
                        startActivity(intent);

                }
                else
                {
                    t = response.errorBody().string();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(t!= null)
            {
                try {

                    JSONObject jsonObject = new JSONObject(t);
                    Toast.makeText(user_login.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

            Toast.makeText(user_login.this,t.getMessage(),Toast.LENGTH_LONG).show();

        }
    });

    }

    public void googlelogin(final String token, String id){
//        String user = id.getText().toString().trim();
//        String password = pass.getText().toString().trim();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .googleLogin(token,id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String t = null;
                try {
                    if(response.code() == 200 || response.code() == 201) {

                        JSONObject json = new JSONObject(response.body().string());
                        JSONObject toki = json.getJSONObject("user");

                        t = toki.get("token").toString();
                        Log.d(TAG,t);


                        SharedPreferences sharedPref = getSharedPreferences("Login" , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Log.d(TAG, "Entered On response, token is: " + t);
                        // login token
                        editor.putString("token" , t);
                        // login boolean to check for login every time app is launched
                        editor.putBoolean("logged",true);
                        editor.apply();
                        String temp = sharedPref.getString("token" , "NULL");

//                        Toast.makeText(user_login.this, temp + " " + "sharedpref", Toast.LENGTH_LONG).show();

                        //redirect to home activity if successful
                        Intent intent = new Intent(user_login.this, Home.class);
                        startActivity(intent);

                    }
                    else
                    {
                        t = response.errorBody().string();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(t!= null)
                {
                    try {

                        JSONObject jsonObject = new JSONObject(t);
                        Toast.makeText(user_login.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(user_login.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
//btn_login.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(MainActivity.this, Login.class);
//            startActivity(intent);
//        }
//    });
//logout.setOnClickListener(new View.OnClickListener() {
//
//        @Override
//        public void onClick(View view) {
//            // Launching News Feed Screen
//
//            SharedPreferences preferences =getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
//            editor.commit();
//            finish();
//        });
