package com.alpha.alphanetwork.Dark;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alpha.alphanetwork.R;


public class DarkGlobalWallFragment extends Fragment {
    private static final String TAG = "DarkGlobalFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dark_global_wall ,container, false);

        return view;
    }
}