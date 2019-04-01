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

public class NewsFavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<NewsItemModel> newsItems;

    public NewsFavoritesAdapter(Context context, ArrayList<NewsItemModel> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsFavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof NewsFavoritesViewHolder) {
            NewsFavoritesViewHolder newsFavoritesViewHolder = (NewsFavoritesViewHolder) viewHolder;

            String imgFileName = newsItems.get(position).getBaseFilename();
            if (imgFileName != null) {
                imgFileName = imgFileName.replaceAll(" ", Common.LINK_SPACE);
            }

            Glide.with(context)
                    .load(newsItems.get(position).getBaseUrl()
                            + Common.LINK_PHOTO_PREFIX + imgFileName)
                    .into(newsFavoritesViewHolder.imgThumbnail);

            newsFavoritesViewHolder.txtTitle.setText(Html.fromHtml(newsItems.get(position).getTitle()));
            newsFavoritesViewHolder.txtDate.setText(newsItems.get(position).getDate());
            newsFavoritesViewHolder.txtTeaser.setText(Html.fromHtml(newsItems.get(position).getTeaser()));

            newsFavoritesViewHolder.layoutWhole.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showPopupMenu(view, newsItems.get(position));
                    return true;
                }
            });

            newsFavoritesViewHolder.layoutWhole.setOnClickListener(new View.OnClickListener() {
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
        popupMenu.getMenuInflater().inflate(R.menu.popup_news_favorites, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.delete_article) {
                    deleteArticle(item);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void deleteArticle(NewsItemModel item) {
        if (DBFunctions.deleteFavorite(item.getId())) {
            refreshFavorites();
            Toast.makeText(context, context.getString(R.string.toast_delete_article), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getString(R.string.toast_delete_article_fail), Toast.LENGTH_LONG).show();
        }
    }

    void refreshFavorites() {
        newsItems = DBFunctions.getFavorites();
        notifyDataSetChanged();
    }

    static class NewsFavoritesViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutWhole;
        private ImageView imgThumbnail;
        private TextView txtTitle;
        private TextView txtDate;
        private TextView txtTeaser;
        NewsFavoritesViewHolder(View itemView) {
            super(itemView);
            layoutWhole = itemView.findViewById(R.id.layout_whole);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtTeaser = itemView.findViewById(R.id.txt_teaser);
        }
    }
}