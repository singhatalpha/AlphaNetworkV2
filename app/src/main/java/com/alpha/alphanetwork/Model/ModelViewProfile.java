package com.alpha.alphanetwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ModelViewProfile {


    @SerializedName("profile")
    @Expose
    private ViewProfile profile;

    public ViewProfile getProfile() {
        return profile;
    }

    public void setProfile(ViewProfile profile) {
        this.profile = profile;
    }



}
