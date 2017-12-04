package com.example.arijit.github_mobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arijit on 04/12/17.
 */

public class UserDetails {

    @SerializedName("login")
    private String login;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("followers")
    private String followers;
    @SerializedName("following")
    private String following;
    @SerializedName("updated_at")
    private String updated_at;

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFollowers() {
        return followers;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getFollowing() {
        return following;
    }
}
