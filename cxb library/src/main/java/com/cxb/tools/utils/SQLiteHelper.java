package com.cxb.tools.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite 帮助工具
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "MyApp.db";
    private static final int VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
