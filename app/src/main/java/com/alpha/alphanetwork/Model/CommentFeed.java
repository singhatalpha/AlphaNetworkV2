package com.alpha.alphanetwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentFeed {




    @SerializedName("comments")
    @Expose
    private List<Comments> comments;




    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }








}

