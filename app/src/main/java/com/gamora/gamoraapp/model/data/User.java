package com.gamora.gamoraapp.model.data;

import android.media.Image;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class User {

    private String Uid;
    private String email;
    private String password;
    private String nickname;
    private String realName;
    private int numOfFollowers;
    private Date joinDate;
    private Image profilePic;
    private Set<Integer> platforms;
    private Set<User> following;
    private Set<User> followers;
    private Set<Post> posts;

    public User(String Uid,String email, String password, String nickname, String realName, Set<Integer> userPlatforms)
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

    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }

    public Set<Integer> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Integer> platforms) {
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

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }


}
