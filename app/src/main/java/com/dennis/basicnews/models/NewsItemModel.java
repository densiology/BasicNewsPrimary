package com.dennis.basicnews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//TODO experiment if variablename is used and annotation is removed
public class NewsItemModel implements Parcelable {
    @SerializedName("id") private String id = "";
    @SerializedName("title") private String title = "";
    @SerializedName("main") private String main = "";
    @SerializedName("teaser") private String teaser = "";
    @SerializedName("date") private String date = "";
    @SerializedName("base_url") private String baseUrl = "";
    @SerializedName("base_filename") private String baseFilename = "";

    public NewsItemModel(String id, String title, String main, String teaser, String date, String baseUrl, String baseFilename) {
        this.id = id;
        this.title = title;
        this.main = main;
        this.teaser = teaser;
        this.date = date;
        this.baseUrl = baseUrl;
        this.baseFilename = baseFilename;
    }

    private NewsItemModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        main = in.readString();
        teaser = in.readString();
        date = in.readString();
        baseUrl = in.readString();
        baseFilename = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain() {
        return main;
    }

    public String getTeaser() {
        return teaser;
    }

    public String getDate() {
        return date;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBaseFilename() {
        return baseFilename;
    }

    public static final Creator<NewsItemModel> CREATOR = new Creator<NewsItemModel>() {
        @Override
        public NewsItemModel createFromParcel(Parcel in) {
            return new NewsItemModel(in);
        }

        @Override
        public NewsItemModel[] newArray(int size) {
            return new NewsItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(main);
        dest.writeString(teaser);
        dest.writeString(date);
        dest.writeString(baseUrl);
        dest.writeString(baseFilename);
    }
}
