package com.dennis.basicnews.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.dennis.basicnews.R;
import com.dennis.basicnews.models.NewsItemModel;
import com.dennis.basicnews.utilities.Common;

import java.net.URLDecoder;

public class NewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initialize();
    }

    private void initialize() {
        WebView webView = findViewById(R.id.webview_news);
        NewsItemModel item = getIntent().getParcelableExtra("news_item");

        int fontSizeMain = 4;
        int fontSizeHeader = 5;
        int fontSizeDate = 2;

        if (item.getTitle().contains("%")) {
            item.setTitle(item.getTitle().replaceAll("[%]", "%25"));
        }
        item.setTitle(URLDecoder.decode(item.getTitle()));

        String customHtml = "<html><body><b>"
                + "<font size=\"" + fontSizeHeader + "\"><font_reg>" + item.getTitle() + "</font_reg></font></b>"
                + "<font_size=\"" + fontSizeDate + "\"><br><br><i><font_reg>" + item.getDate() + "</font_reg></i></font><br><br>"
                + "<center><img src=\""+ item.getBaseUrl() + Common.LINK_PHOTO_PREFIX + item.getBaseFilename() + "\"></center>"
                + "<font_size=\"" + fontSizeMain + "\"><font_light>" + item.getMain() + "</font_light></font><br><br>"
                + "</body></html>";

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData(customHtml, "text/html", "utf-8");
    }
}
