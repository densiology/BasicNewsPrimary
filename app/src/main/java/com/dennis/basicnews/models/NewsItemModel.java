package com.dennis.basicnews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NewsItemModel implements Parcelable {
    private String id = "";
    private String title = "";
    private String main = "";
    private String teaser = "";
    private String date = "";
    private String base_url = "";
    private String base_filename = "";

    public NewsItemModel(String id, String title, String main, String teaser, String date, String baseUrl, String baseFilename) {
        this.id = id;
        this.title = title;
        this.main = main;
        this.teaser = teaser;
        this.date = date;
        this.base_url = baseUrl;
        this.base_filename = baseFilename;
    }

    private NewsItemModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        main = in.readString();
        teaser = in.readString();
        date = in.readString();
        base_url = in.readString();
        base_filename = in.readString();
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
        return base_url;
    }

    public String getBaseFilename() {
        return base_filename;
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
        dest.writeString(base_url);
        dest.writeString(base_filename);
    }
}
