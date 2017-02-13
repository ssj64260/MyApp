package com.cxb.tools.utils;

import android.content.Context;

import com.litesuits.orm.LiteOrm;

/**
 * Description :
 * LiteORM 数据库框架，适用于无需加密的SQLite数据库需求
 * 介绍页：https://github.com/litesuits/android-lite-orm
 * 帮助类
 */
public class LiteOrmHelper {

    private static final String DB_NAME = String.format("%s_database.db", "myapp");

    private static final boolean DEBUGGABLE = true; // 是否输出log

    private static volatile LiteOrm sLiteOrm;

    public static LiteOrm getInstance(Context context) {
        if (sLiteOrm == null) {
            synchronized (LiteOrmHelper.class) {
                if (sLiteOrm == null) {
                    sLiteOrm = LiteOrm.newCascadeInstance(context, DB_NAME);
                    sLiteOrm.setDebugged(DEBUGGABLE);
                }
            }
        }
        return sLiteOrm;
    }

    public static boolean deleteDB() {
        if (sLiteOrm != null && sLiteOrm.deleteDatabase()) {
            closeDB();
            sLiteOrm = null;
            return true;
        } else {
            return false;
        }
    }

    public static void closeDB() {
        if (sLiteOrm != null) {
            sLiteOrm.close();
        }
    }


}
