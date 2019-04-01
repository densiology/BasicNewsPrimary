package com.dennis.basicnews.adapters.pagers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dennis.basicnews.R;
import com.dennis.basicnews.fragments.FavoritesListFragment;
import com.dennis.basicnews.fragments.NewsListFragment;

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    public FavoritesListFragment favoritesListFragment;

    public MainActivityPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new NewsListFragment();
        } else {
            favoritesListFragment = new FavoritesListFragment();
            return favoritesListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.title_news);
        } else {
            return context.getString(R.string.title_favorites);
        }
    }
}
