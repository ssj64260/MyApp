package com.cxb.tools.utils;

import android.content.Context;
import android.text.format.Formatter;

import java.io.File;
import java.io.FileInputStream;

public class GetFileSizeUtil {

    //获取文件大小
    public static long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
        }
        return s;
    }

    //获取文件夹大小
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size += getFileSize(flist[i]);
            } else {
                size += flist[i].length();
            }
        }
        return size;
    }

    //转换文件大小单位(B/KB/MB/GB)
    public static String FormetFileSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }

    //获取文件个数
    public static long getlist(File f) {// 递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }

}