package com.dennis.basicnews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dennis.basicnews.R;
import com.dennis.basicnews.adapters.pagers.MainActivityPagerAdapter;
import com.dennis.basicnews.models.NewsItemModel;
import com.dennis.basicnews.utilities.DBFunctions;

public class MainActivity extends AppCompatActivity {

    public MainActivityPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB setup
        DBFunctions.openDB(getApplicationContext());

        //Adapter setup
        pagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager(), this);

        //ViewPager setup
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);

        //TabLayout setup
        TabLayout tabLayout = findViewById(R.id.layout_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void openNews(NewsItemModel newsItem) {
        Intent newsIntent = new Intent(this, NewsActivity.class);
        newsIntent.putExtra("news_item", newsItem);
        startActivity(newsIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBFunctions.closeDB();
    }
}