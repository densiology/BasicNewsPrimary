package com.dennis.basicnews.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dennis.basicnews.R;
import com.dennis.basicnews.adapters.lists.NewsFavoritesAdapter;
import com.dennis.basicnews.models.NewsItemModel;
import com.dennis.basicnews.utilities.DBFunctions;

import java.util.ArrayList;

public class FavoritesListFragment extends Fragment {

    public NewsFavoritesAdapter newsFavoritesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_list, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<NewsItemModel> favorites = DBFunctions.getFavorites();
        newsFavoritesAdapter = new NewsFavoritesAdapter(getActivity(), favorites);
        recyclerView.setAdapter(newsFavoritesAdapter);
    }
}
