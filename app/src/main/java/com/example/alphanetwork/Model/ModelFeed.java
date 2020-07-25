package com.example.alphanetwork.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelFeed implements Parcelable {
    private String title;
    private List<String> media;
    private String video;
    private int likes ;
    private int comments ;
    private String time ;
    private String id;
    private String postid;




    private ModelProfile profile;

    protected ModelFeed(Parcel in) {
        title = in.readString();
        media = in.createStringArrayList();
        likes = in.readInt();
        comments = in.readInt();
        time = in.readString();
        id = in.readString();
        postid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(media);
        dest.writeInt(likes);
        dest.writeInt(comments);
        dest.writeString(time);
        dest.writeString(id);
        dest.writeString(postid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelFeed> CREATOR = new Creator<ModelFeed>() {
        @Override
        public ModelFeed createFromParcel(Parcel in) {
            return new ModelFeed(in);
        }

        @Override
        public ModelFeed[] newArray(int size) {
            return new ModelFeed[size];
        }
    };

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMedia() {
        return media;
    }
//    public String oneMedia()
//    {
//
//            return this.getMedia().get(0).getFile_data();
//
//    }

    public void setMedia(List<String> media) {
        if(media.size() == 0) {
            this.media = null;
        }
        this.media = media;
    }

    public ModelProfile getProfile() {
        return profile;
    }

    public void setProfile(ModelProfile profile) {
        this.profile = profile;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
//    public ModelFeed(String title, List<String> media, int likes, int comments, String time, String id, String postid, ModelProfile profile) {
//        this.title = title;
//        this.media = media;
//        this.likes = likes;
//        this.comments = comments;
//        this.time = time;
//        this.id = id;
//        this.postid = postid;
//        this.profile = profile;
//    }
}
