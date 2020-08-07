package com.alpha.alphanetwork.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alpha.alphanetwork.Login.user_login;
import com.alpha.alphanetwork.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import Utils.MyApp;


/**
 * Created by User on 6/4/2017.
 */

public class SignOutFragment extends Fragment {

    private static final String TAG = "SignOutFragment";

    private SharedPreferences sharedpref;
    private Context context = MyApp.getContext();

    private ProgressBar mProgressBar;
    private TextView tvSignout, tvSigningOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signout, container, false);
        tvSignout = (TextView) view.findViewById(R.id.tvConfirmSignout);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvSigningOut = (TextView) view.findViewById(R.id.tvSigningOut);
        Button btnConfirmSignout = (Button) view.findViewById(R.id.btnConfirmSignout);

        mProgressBar.setVisibility(View.GONE);
        tvSigningOut.setVisibility(View.GONE);


        btnConfirmSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to sign out.");
                mProgressBar.setVisibility(View.VISIBLE);
                tvSigningOut.setVisibility(View.VISIBLE);
                sharedpref = context.getSharedPreferences("Login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.clear();
                editor.apply();

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context,gso);
                googleSignInClient.signOut();

                Intent mainIntent = new Intent(getActivity(), user_login.class);
                startActivity(mainIntent);
//                mAuth.signOut();
//                getActivity().finish();
            }
        });

        return view;
    }




}