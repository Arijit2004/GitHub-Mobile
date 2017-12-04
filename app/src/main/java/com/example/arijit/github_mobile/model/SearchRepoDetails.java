package com.example.arijit.github_mobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arijit on 05/12/17.
 */

public class SearchRepoDetails {

    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("private")
    private boolean type;
    @SerializedName("description")
    private String description;
    @SerializedName("language")
    private String language;
    @SerializedName("watchers")
    private int watchers;
    @SerializedName("forks")
    private int forks;
    @SerializedName("stargazers_count")
    private int stars;

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public int getWatchers() {
        return watchers;
    }

    public int getForks() {
        return forks;
    }

    public int getStars() {
        return stars;
    }
}
