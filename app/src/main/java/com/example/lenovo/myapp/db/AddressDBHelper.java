package com.example.lenovo.myapp.db;

import android.content.Context;

import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.FileUtil;
import com.cxb.tools.utils.LiteOrmHelper;
import com.cxb.tools.utils.SDCardUtil;
import com.example.lenovo.myapp.model.testbean.Area;
import com.example.lenovo.myapp.model.testbean.City;
import com.example.lenovo.myapp.model.testbean.Province;
import com.example.lenovo.myapp.model.testbean.Street;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 地址数据库管理
 */

public class AddressDBHelper {

    private static final String DB_NAME = "region.db";

    private static final boolean DEBUGGABLE = true; // 是否输出log

    private static volatile LiteOrm liteOrm;

    private static LiteOrm getInstance(Context context) {
        if (liteOrm == null) {
            synchronized (LiteOrmHelper.class) {
                if (liteOrm == null) {
                    liteOrm = LiteOrm.newSingleInstance(context, DB_NAME);
                    liteOrm.setDebugged(DEBUGGABLE);
                }
            }
        }
        return liteOrm;
    }

    public static boolean updateDB(Context context) {
        File dbFile = new File(SDCardUtil.getDataBaseDir(context, DB_NAME));
        InputStream inputStream = AssetsUtil.getInputStream(context, DB_NAME);

        if (inputStream != null) {
            try {
                long streamSize = inputStream.available();
                long dbFileSize = FileUtil.getFileSize(dbFile);
                if (streamSize != dbFileSize) {
                    FileUtil.copyFile(inputStream, dbFile.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dbFile.exists();
    }

    public static List<Province> getProvince(Context context) {
        return getInstance(context).query(Province.class);
    }

    public static List<City> getCity(Context context, String provinceId) {
        return getInstance(context)
                .query(new QueryBuilder<>(City.class)
                        .where("pid = ?", provinceId));
    }

    public static List<Area> getArea(Context context, String cityId) {
        return getInstance(context).query(new QueryBuilder<>(Area.class)
                .where("pid = ?", cityId));
    }

    public static List<Street> getStreet(Context context, String areaId) {
        return getInstance(context)
                .query(new QueryBuilder<>(Street.class)
                        .where("pid = ?", areaId));
    }

    public static void closeDB() {
        if (liteOrm != null) {
            liteOrm.close();
        }
    }
}
