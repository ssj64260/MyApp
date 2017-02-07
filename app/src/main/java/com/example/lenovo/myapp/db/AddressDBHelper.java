package com.example.lenovo.myapp.db;

import android.content.Context;

import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.FileUtil;
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

    private final String DB_NAME = "region.db";

    private final boolean DEBUGGABLE = true; // 是否输出log

    private LiteOrm liteOrm;

    public AddressDBHelper(Context context) {
        liteOrm = LiteOrm.newSingleInstance(context, DB_NAME);
        liteOrm.setDebugged(DEBUGGABLE);
    }

    public boolean updateDB(Context context) {
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

    public List<Province> getProvince() {
        return liteOrm.query(Province.class);
    }

    public List<City> getCity(String provinceId) {
        return liteOrm.query(new QueryBuilder<>(City.class)
                .where("pid = ?", provinceId));
    }

    public List<Area> getArea(String cityId) {
        return liteOrm.query(new QueryBuilder<>(Area.class)
                .where("pid = ?", cityId));
    }

    public List<Street> getStreet(String areaId) {
        return liteOrm.query(new QueryBuilder<>(Street.class)
                .where("pid = ?", areaId));
    }

    public void closeDB() {
        if (liteOrm != null) {
            liteOrm.close();
        }
    }
}
