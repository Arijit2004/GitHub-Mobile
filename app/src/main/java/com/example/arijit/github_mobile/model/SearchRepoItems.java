package com.example.arijit.github_mobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arijit on 05/12/17.
 */

public class SearchRepoItems {


    @SerializedName("items")
    private List<SearchRepoDetails> searchlist;

    public List<SearchRepoDetails> getSearchlist() {
        return searchlist;
    }
}
