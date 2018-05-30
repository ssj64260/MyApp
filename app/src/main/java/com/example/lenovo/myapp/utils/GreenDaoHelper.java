package com.example.lenovo.myapp.utils;

import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.myapp.MyApplication;
import com.example.lenovo.myapp.model.DaoMaster;
import com.example.lenovo.myapp.model.DaoSession;

public class GreenDaoHelper {

    private static final String DATABASE_NAME = "Pokemon-db";

    private DaoSession daoSession;
    private SQLiteDatabase basedb;
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster daoMaster;

    public GreenDaoHelper() {
        helper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), DATABASE_NAME, null);
        basedb = helper.getWritableDatabase();
        daoMaster = new DaoMaster(basedb);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getBasedb() {
        return basedb;
    }

    public DaoMaster.DevOpenHelper getHelper() {
        return helper;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }
}
