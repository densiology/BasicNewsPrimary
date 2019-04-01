package com.dennis.basicnews.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dennis.basicnews.models.NewsItemModel;

import java.util.ArrayList;

public class DBFunctions {

    private static SQLiteDatabase db;
    private static DBSetup dbHelper;

    public static void openDB(Context context) {
        dbHelper = new DBSetup(context);
        if (db != null) {
            if (!db.isOpen()) {
                db = dbHelper.getWritableDatabase();
            }
        } else {
            db = dbHelper.getWritableDatabase();
        }
    }

    public static void closeDB() {
        if (db.isOpen()) {
            db.close();
            dbHelper.close();
        }
    }

    public static ArrayList<NewsItemModel> getFavorites() {
        ArrayList<NewsItemModel> favorites = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT webId, title, main, teaser, date, baseUrl, baseFilename FROM "
                                    + DBSetup.TABLE_FAVORITES + " ORDER BY date DESC", null);

        if (cursor != null) {
            NewsItemModel item;
            while (cursor.moveToNext()) {
                item = new NewsItemModel(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                favorites.add(item);
            }
            closeCursor(cursor);
        }
        return favorites;
    }

    public static boolean isArticleFavorite(String webId) {
        Cursor cursor = db.rawQuery("SELECT id FROM " + DBSetup.TABLE_FAVORITES
                        + " WHERE webId = '" + webId + "'", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                closeCursor(cursor);
                return true;
            }
            closeCursor(cursor);
        }

        return false;
    }

    public static boolean saveFavorite(NewsItemModel item) {
        ContentValues values = new ContentValues();
        values.put("webId", item.getId());
        values.put("title", item.getTitle());
        values.put("main", item.getMain());
        values.put("teaser", item.getTeaser());
        values.put("date", item.getDate());
        values.put("baseUrl", item.getBaseUrl());
        values.put("baseFilename", item.getBaseFilename());

        long result = db.insert(DBSetup.TABLE_FAVORITES, null, values);
        return result != -1;
    }

    public static boolean deleteFavorite(String webId) {
        try {
            db.execSQL("DELETE FROM " + DBSetup.TABLE_FAVORITES
                    + " WHERE webId= '" + webId + "'");
            return true;
        } catch(SQLException ex) {
            return false;
        }
    }

    private static void closeCursor(Cursor cursor) {
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }
}