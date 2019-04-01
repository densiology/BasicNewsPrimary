package com.dennis.basicnews.presenters.listeners;

import com.android.volley.VolleyError;

public interface CustomStringListener {

    void onSuccess(String response);
    void onFailure(VolleyError error);

}
