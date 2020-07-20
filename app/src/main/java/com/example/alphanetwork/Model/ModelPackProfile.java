package com.example.alphanetwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPackProfile {
    @SerializedName("image")
    @Expose
    private String photo;
    private String name;
    @SerializedName("packDesignation")
    @Expose
    private String packdesig;
    @SerializedName("_id")
    @Expose
    private String user;
    @SerializedName("partyDesignation")
    @Expose
    private String partydesig;

    public String getPackdesig() {
        return packdesig;
    }

    public void setPackdesig(String packdesig) {
        this.packdesig = packdesig;
    }

    public String getPartydesig() {
        return partydesig;
    }

    public void setPartydesig(String partydesig) {
        this.partydesig = partydesig;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}

