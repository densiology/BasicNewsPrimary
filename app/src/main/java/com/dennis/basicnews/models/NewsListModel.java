package com.dennis.basicnews.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsListModel {
    @SerializedName("stories") private ArrayList<NewsItemModel> stories = new ArrayList<>();
    @SerializedName("page_number") private int pageNumber;
    @SerializedName("total_pages") private int totalPages;

    public ArrayList<NewsItemModel> getStories() {
        return stories;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }
}