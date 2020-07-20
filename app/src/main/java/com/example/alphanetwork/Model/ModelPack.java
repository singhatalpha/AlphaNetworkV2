package com.example.alphanetwork.Model;

import java.util.List;

public class ModelPack {
    private String name;
    private String dp;
    private String user_id;
    private String pack_id;
    private String alpha;
    private int influence;
    private int membercount;
    private List<ModelPackProfile> members;

    public int getMembercount() {
        return membercount;
    }

    public void setMembercount(int membercount) {
        this.membercount = membercount;
    }


    public int getInfluence() {
        return influence;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPack_id() {
        return pack_id;
    }

    public void setPack_id(String pack_id) {
        this.pack_id = pack_id;
    }

    public void setInfluence(int influence) {
        this.influence = influence;}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public List<ModelPackProfile> getMembers() {
        return members;
    }

    public void setMembers(List<ModelPackProfile> members) {
        this.members = members;
    }
}
