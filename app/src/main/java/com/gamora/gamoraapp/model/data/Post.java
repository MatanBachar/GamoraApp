package com.gamora.gamoraapp.model.data;

public class Post {

    private int postID;
    private PlatformManager platform;
    private String Game;
    private String content;
    // Add media


    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public PlatformManager getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformManager platform) {
        this.platform = platform;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
