package com.gamora.gamoraapp.model.data;

import android.media.Image;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class User implements Serializable {

    private String Uid;
    private String email;
    private String password;
    private String nickname;
    private String realName;
    private int numOfFollowers;
    private Date joinDate;
    private List<Integer> platforms;
    private List<User> following;
    private List<User> followers;
    private List<String> posts;

    public User() {
    }

    public User(String Uid, String email, String password, String nickname, String realName, List<Integer> userPlatforms)
    {
        this.Uid = Uid;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.realName = realName;
        this.platforms = userPlatforms;
    }

    public String getUID() {
        return Uid;
    }

    public void setUID(String Uid) {
        this.Uid = Uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public List<Integer> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Integer> platforms) {
        this.platforms = platforms;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getNumOfFollowers() {
        return numOfFollowers;
    }

    public void setNumOfFollowers(int numOfFollowers) {
        this.numOfFollowers = numOfFollowers;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }


}
