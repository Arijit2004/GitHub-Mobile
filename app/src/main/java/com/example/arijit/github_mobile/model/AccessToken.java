package com.example.arijit.github_mobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arijit on 03/12/17.
 */

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
