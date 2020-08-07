package com.alpha.alphanetwork.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alpha.alphanetwork.R;

import Utils.MyApp;

public class VerifyProfileFragment extends Fragment {

    private static final String TAG = "SignOutFragment";

    private SharedPreferences sharedpref;
    private Context context = MyApp.getContext();

    private ProgressBar mProgressBar;
    private TextView tvSignout, tvSigningOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verifyprofile, container, false);
        return view;
    }




}
