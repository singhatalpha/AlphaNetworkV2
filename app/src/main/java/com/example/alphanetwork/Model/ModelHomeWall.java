package com.example.alphanetwork.Model;

import com.example.alphanetwork.Model.ModelFeed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelHomeWall {


    private String status;

    @SerializedName("feeds")
    @Expose
    private List<ModelFeed> posts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ModelFeed> getPosts() {
        return posts;
    }

    public void setPosts(List<ModelFeed> posts) {
        this.posts = posts;
    }


}
