package com.alpha.alphanetwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAnonymousWall {


    private String status;

    @SerializedName("feeds")
    @Expose
    private List<ModelAnonymousFeed> posts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ModelAnonymousFeed> getPosts() {
        return posts;
    }

    public void setPosts(List<ModelAnonymousFeed> posts) {
        this.posts = posts;
    }


}
