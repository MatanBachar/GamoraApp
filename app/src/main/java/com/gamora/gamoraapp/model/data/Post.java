package com.gamora.gamoraapp.model.data;

import android.media.Image;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable{

    private String postID;
    private int platformID;
    private String game;
    private String content;
    private Date postDate;
    private String imageUri;

    public Post(String postID, int platformID, String game, String content, Date postDate, String imageUri) {
        this.postID = postID;
        this.platformID = platformID;
        this.game = game;
        this.content = content;
        this.postDate = postDate;
        this.imageUri = imageUri;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public int getPlatformID() {
        return platformID;
    }

    public void setPlatformID(int platformID) {
        this.platformID = platformID;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        game = game;
    }

    public String getPostImage() {
        return imageUri;
    }

    public void setPostImage(String imageUri) {
        this.imageUri = imageUri;
    }

}
