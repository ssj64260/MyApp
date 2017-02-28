package com.example.lenovo.myapp.utils;

import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.myapp.MyApplication;
import com.litesuits.orm.BuildConfig;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.SQLiteHelper;

import java.util.List;

/**
 * liteOrm 封装
 */

public enum UOrm implements SQLiteHelper.OnUpdateListener {
    INSTANCE;
    private LiteOrm mLiteOrm;
    private final String DBName = "MyApp.db";

    UOrm() {
        DataBaseConfig config = new DataBaseConfig(MyApplication.getInstance());
        config.dbName = DBName;
        config.dbVersion = 1;
        config.onUpdateListener = this;
        config.debugged = BuildConfig.DEBUG;
        mLiteOrm = LiteOrm.newSingleInstance(config);
    }

    @Override
    public void onUpdate(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void save(Object o) {
        if (o == null) {
            return;
        }

        mLiteOrm.save(o);
    }

    public <T> void save(List<T> collection) {
        if (collection != null && collection.size() > 0) {
            return;
        }

        mLiteOrm.save(collection);
    }

    public <T> void delete(Class<T> tClass) {
        if (tClass == null) {
            return;
        }

        mLiteOrm.delete(tClass);
    }

    public <T> List<T> queryAll(Class<T> tClass) {
        if (tClass == null) {
            return null;
        }

        return mLiteOrm.query(tClass);
    }

}
