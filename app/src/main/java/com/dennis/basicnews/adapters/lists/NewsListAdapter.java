package com.dennis.basicnews.adapters.lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dennis.basicnews.R;
import com.dennis.basicnews.activities.MainActivity;
import com.dennis.basicnews.models.NewsItemModel;
import com.dennis.basicnews.utilities.Common;
import com.dennis.basicnews.utilities.DBFunctions;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;

    private Context context;
    private ArrayList<NewsItemModel> newsItems;

    public NewsListAdapter(Context context, ArrayList<NewsItemModel> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @Override
    public int getItemViewType(int position) {
        return newsItems.get(position) == null ? TYPE_LOADING : TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
            return new NewsListViewHolder(view);
        } else if (viewType == TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof NewsListViewHolder) {
            NewsListViewHolder newsListViewHolder = (NewsListViewHolder) viewHolder;

            String imgFileName = newsItems.get(position).getBaseFilename();
            if (imgFileName != null) {
                imgFileName = imgFileName.replaceAll(" ", Common.LINK_SPACE);
            }

            Glide.with(context)
                    .load(newsItems.get(position).getBaseUrl()
                            + Common.LINK_PHOTO_PREFIX + imgFileName)
                    .into(newsListViewHolder.imgThumbnail);

            newsListViewHolder.txtTitle.setText(Html.fromHtml(newsItems.get(position).getTitle()));
            newsListViewHolder.txtDate.setText(newsItems.get(position).getDate());
            newsListViewHolder.txtTeaser.setText(Html.fromHtml(newsItems.get(position).getTeaser()));

            newsListViewHolder.layoutWhole.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showPopupMenu(view, newsItems.get(position));
                    return true;
                }
            });

            newsListViewHolder.layoutWhole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)context).openNews(newsItems.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsItems == null ? 0 : newsItems.size();
    }

    private void showPopupMenu(View view, final NewsItemModel item) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_news_list, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.save_article) {
                    saveArticle(item);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void saveArticle(NewsItemModel item) {
        if (DBFunctions.isArticleFavorite(item.getId())) {
            Toast.makeText(context, context.getString(R.string.toast_favorite_exists), Toast.LENGTH_LONG).show();
        } else {
            if (DBFunctions.saveFavorite(item)) {
                // TODO use refresh on visible instead
                ((MainActivity)context).pagerAdapter.favoritesListFragment.newsFavoritesAdapter.refreshFavorites();
                Toast.makeText(context, context.getString(R.string.toast_save_article), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, context.getString(R.string.toast_save_article_fail), Toast.LENGTH_LONG).show();
            }
        }
    }

    static class NewsListViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutWhole;
        private ImageView imgThumbnail;
        private TextView txtTitle;
        private TextView txtDate;
        private TextView txtTeaser;
        NewsListViewHolder(View itemView) {
            super(itemView);
            layoutWhole = itemView.findViewById(R.id.layout_whole);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtTeaser = itemView.findViewById(R.id.txt_teaser);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
