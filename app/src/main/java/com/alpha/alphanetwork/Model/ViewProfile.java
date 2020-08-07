package com.alpha.alphanetwork.Model;

public class ViewProfile
{

//    @SerializedName("image")
//    @Expose
    private String photo;
    private String username;
    private String profession;
    private int influence;
    private int popularity;
    private String position;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String commitment;
    private ProfilePack pack;
    private ProfileParty party;
    private String user_id;


//    private String post_count;
//    private String follower_count;
//    private String following_count;


    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCommitment() {
        return commitment;
    }

    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }

    public int getInfluence() {
        return influence;
    }

    public void setInfluence(int influence) {
        this.influence = influence;
    }

    public int getPopularity() {
        return popularity;
    }



    public ProfilePack getPack() {
        return pack;
    }

    public void setPack(ProfilePack pack) {
        this.pack = pack;
    }

    public ProfileParty getParty() {
        return party;
    }

    public void setParty(ProfileParty party) {
        this.party = party;
    }



    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String photo)
    {
        this.photo = photo;
    }


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }


//
//    @Override
//    public String toString()
//    {
//        return "ClassPojo [birthdate = "+birthdate+", latitude = "+latitude+", photo = "+photo+", crew = "+crew+", highlights = "+highlights+", phone = "+phone+", following_count = "+following_count+", commits = "+commits+", location = "+location+", followed_by_count = "+followed_by_count+", id = "+id+", post_count = "+post_count+", user = "+user+", passion = "+passion+", username = "+username+", longitude = "+longitude+"]";
//    }
}
