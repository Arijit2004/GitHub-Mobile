package com.example.arijit.github_mobile.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.arijit.github_mobile.GitApplication;
import com.example.arijit.github_mobile.model.UserDetails;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by arijit on 03/12/17.
 */


public class AppPreference {

    private static final AppPreference instance = new AppPreference();
    private static final String file = "location_name_prefs";
    private SharedPreferences prefs;

    public static final String KEY_AUTH_CODE = "auth_code";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_USER_DETAILS = "user_details";


    public static AppPreference getInstance() {
        return instance;
    }

    private AppPreference() {
        prefs = GitApplication.getContext().getSharedPreferences(file,
                Context.MODE_PRIVATE);
    }

    public void setAccessToken(String token) {
            prefs.edit().putString(KEY_ACCESS_TOKEN, token).apply();
    }

    public String getAccessToken() {
        return prefs.getString(KEY_ACCESS_TOKEN, null);
    }

    public void setAuthCode(String code) {
            prefs.edit().putString(KEY_AUTH_CODE, code).apply();
    }

    public String getAuthCode() {
        return prefs.getString(KEY_AUTH_CODE, "");
    }


    public void registerChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public void setUser(UserDetails user) {
        if (user != null) {
            prefs.edit().putString(KEY_USER_DETAILS, new Gson().toJson(user)).apply();
        }
    }

    public UserDetails getUser() {
        String data = prefs.getString(KEY_USER_DETAILS, null);
        if (data == null) {
            return new UserDetails();
        } else {
            Gson gson = new Gson();
            return gson.fromJson(data, UserDetails.class);
        }
    }

    public void clearCart() {
        prefs.edit().remove(KEY_USER_DETAILS);
    }
}