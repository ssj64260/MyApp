package com.cxb.tools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class GetFileSizeUtil {

    private static GetFileSizeUtil instance;

    public GetFileSizeUtil() {
    }

    public static GetFileSizeUtil getInstance() {
        if (instance == null) {
            instance = new GetFileSizeUtil();
        }
        return instance;
    }

    /*** 获取文件大小 ***/
    public long getFileSizes(File f) throws Exception {
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

    /*** 获取文件夹大小 ***/
    public long getFileSize(File f) throws Exception {
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

    /*** 转换文件大小单位(B/KB/MB/GB) ***/
    public String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString;
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /*** 获取文件个数 ***/
    public long getlist(File f) {// 递归求取目录文件个数
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