package com.example.tharapk.models;

public class FeedPost {
    private String image_uri, desc, timestamp, uid, team_id;

    public FeedPost() {
    }

    public FeedPost(String image_uri, String desc, String timestamp, String uid, String team_id) {
        this.image_uri = image_uri;
        this.desc = desc;
        this.timestamp = timestamp;
        this.uid = uid;
        this.team_id = team_id;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTimestamp() {
        long timestampLong = Long.parseLong(timestamp);
        return timestampLong;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }
}
