package com.dennis.basicnews.presenters;

import com.android.volley.VolleyError;
import com.dennis.basicnews.fragments.NewsListFragment;
import com.dennis.basicnews.presenters.listeners.CustomStringListener;

import java.lang.ref.WeakReference;

public class NewsListPresenter implements CustomStringListener {

    private WeakReference<NewsListFragment> contextWeak;

    public NewsListPresenter(NewsListFragment context) {
        contextWeak = new WeakReference<>(context);
    }

    @Override
    public void onSuccess(String response) {
        NewsListFragment context = contextWeak.get();
        if (context != null) {
            context.onSuccessFetchData(response);
        }
    }

    @Override
    public void onFailure(VolleyError error) {
        NewsListFragment context = contextWeak.get();
        if (context != null) {
            context.onFailureFetchData(error);
        }
    }
}
