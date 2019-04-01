package com.dennis.basicnews.presenters.requesters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dennis.basicnews.presenters.listeners.CustomStringListener;

public class CustomStringRequest extends StringRequest {

    public CustomStringRequest(int method, String url, final CustomStringListener mListener) {
        super(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onFailure(error);
            }
        });
    }
}
