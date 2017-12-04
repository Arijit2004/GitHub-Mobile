package com.example.arijit.github_mobile.rest;

import com.example.arijit.github_mobile.model.AccessToken;
import com.example.arijit.github_mobile.model.UserDetails;
import com.example.arijit.github_mobile.model.UserRepoDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by arijit on 03/12/17.
 */

public interface ApiInterface {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );


    @Headers("Accept: application/json")
    @GET("user")
    Call<UserDetails> getUserDetails(@Query("access_token") String accessToken);

    @Headers("Accept: application/json")
    @GET("user/repos")
    Call<List<UserRepoDetails>> getUserRepoDetails(@Query("access_token") String accessToken);

}
