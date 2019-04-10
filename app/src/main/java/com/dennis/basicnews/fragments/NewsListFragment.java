package com.dennis.basicnews.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.dennis.basicnews.R;
import com.dennis.basicnews.adapters.lists.NewsListAdapter;
import com.dennis.basicnews.models.NewsItemModel;
import com.dennis.basicnews.models.NewsListModel;
import com.dennis.basicnews.presenters.NewsListPresenter;
import com.dennis.basicnews.presenters.requesters.CustomStringRequest;
import com.dennis.basicnews.utilities.Common;
import com.dennis.basicnews.utilities.NewsApplication;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.android.volley.Request.Method.GET;

public class NewsListFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;

    private boolean isLazyLoading;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;
    private int currentPage, totalPages;

    private ArrayList<NewsItemModel> listItems;
    private NewsListAdapter newsListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        swipeRefresh = view.findViewById(R.id.layout_swipe);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        listItems = new ArrayList<>();
        newsListAdapter = new NewsListAdapter(getActivity(), listItems);
        recyclerView.setAdapter(newsListAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLazyLoading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)
                        && totalPages > currentPage) {
                    fetchMoreData(currentPage + 1);
                    isLazyLoading = true;
                }
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(1, Common.SWIPE_TO_REFRESH);
            }
        });

        swipeRefresh.setRefreshing(true);
        fetchData(1, Common.SWIPE_TO_REFRESH);
    }

    private void fetchMoreData(int page) {
        listItems.add(null);
        newsListAdapter.notifyItemInserted(listItems.size() - 1);
        fetchData(page, Common.LAZY_LOAD);
    }

    private void fetchData(int requestedPage, String tag) {
        String jsonLink = Common.LINK_NEWS + requestedPage + Common.LINK_SUFFIX;

        CustomStringRequest jsonRequest = new CustomStringRequest(GET, jsonLink, new NewsListPresenter(this));
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(Common.RETRY_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonRequest.setTag(tag);

        NewsApplication.getInstance().cancelAllRequest(tag);
        NewsApplication.getInstance().addNoCacheRequestQueue(jsonRequest);
    }

    public void onSuccessFetchData(String response) {
        // to remove "Loading..."
        listItems.remove(null);

        swipeRefresh.setRefreshing(false);

        // fill listItems with content
        Gson gson = new Gson();
        NewsListModel model = gson.fromJson(response, NewsListModel.class);
        currentPage = model.getPageNumber();
        totalPages = model.getTotalPages();

        // if page is 1, clear list
        if (currentPage == 1) {
            listItems.clear();
        }
        listItems.addAll(model.getStories());

        // then refresh adapter
        newsListAdapter.notifyDataSetChanged();
        isLazyLoading = false;
    }

    public void onFailureFetchData(VolleyError error) {
        if (error instanceof NoConnectionError) {
            Toast.makeText(getActivity(), R.string.toast_error_no_connection, Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(getActivity(), R.string.toast_error_timeout, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), R.string.toast_error, Toast.LENGTH_LONG).show();
        }
        swipeRefresh.setRefreshing(false);
    }
}