package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;

/**
 * 相片
 */

public class PhotoBean implements Serializable {

    private String title;       //图片名           （title）
    private String fileName;    //带后缀的图片名    （_display_name）
    private String albumName;   //上级目录名称      （bucket_display_name）
    private String path;        //图片路径          （_data）
    private long size;          //图片大小          （_size）
    private long dateTaken;     //时间戳            （datetaken）
    private String type;        //图片类型          （mime_type）
    private int width;          //图片宽度          （width）
    private int height;         //图片高度          （height）


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
