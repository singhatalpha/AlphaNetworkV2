package com.example.alphanetwork.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAnonymousFeed{
    private String title;
//    private List<String> media;
    private int likes = 7;
    private int comments = 1;
    private String time = "2019-08-13T13:21:08Z";
    private String postid;


    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;}

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public List<String> getMedia() {
//        return media;
//    }
//
//
//    public void setMedia(List<String> media) {
//        if(media.size() == 0) {
//            this.media = null;
//        }
//        this.media = media;
//    }

}
