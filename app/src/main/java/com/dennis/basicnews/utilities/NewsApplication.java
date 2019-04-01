package com.dennis.basicnews.utilities;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NewsApplication extends Application {

    private static NewsApplication newsApplication;

    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        newsApplication = this;
    }

    public static synchronized NewsApplication getInstance() {
        return newsApplication;
    }

    public <T> void addNoCacheRequestQueue(Request<T> request) {
        request.setShouldCache(false);
        getRequestQueue().add(request);
    }

    public void cancelAllRequest(String tag){
        getRequestQueue().cancelAll(tag);
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
}
