package com.dennis.basicnews.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSetup extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "BasicNewsPrimary";
    static final String TABLE_FAVORITES = "BNPTableFavorites";

    DBSetup(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;

        createTable = "CREATE TABLE IF NOT EXISTS "
                + TABLE_FAVORITES
                + "(id integer primary key autoincrement, "
                + "webId VARCHAR, title VARCHAR, main VARCHAR, teaser VARCHAR, date VARCHAR, baseUrl VARCHAR, baseFilename VARCHAR);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // add upgrade codes here if available
        this.onCreate(db);
    }
}
