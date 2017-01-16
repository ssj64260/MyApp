package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;

/**
 * 相片
 */

public class PhotoBean implements Serializable {

    private long id;            //ID               （INTEGER long）（_id）

    private String data;        //图片路径          （DATA STREAM）（_data）
    private long size;          //图片大小              （INTEGER）（_size）
    private String displayName; //显示名称                 （TEXT）（_display_name）
    private String title;       //标题                     （TEXT）（title）
    private long dateAdded;     //创建日期         （INTEGER long）（date_added）
    private long dateModified;  //最后修改日期      （INTEGER long）（date_modified）
    private String type;        //MIME类型                 （TEXT）（mime_type）
    private int width;          //图片宽度                         （width）
    private int height;         //图片高度                         （height）

    private String description;         //描述                        （TEXT）（description）
    private String picasaId;            //                            （TEXT）（picasa_id）
    private int isprivate;              //是否私有                  （INTEGER）（isprivate）
    private double latitude;            //捕获图像的纬度             （DOUBLE）（latitude）
    private double longitude;           //捕获图像的经度             （DOUBLE）（longitude）
    private long dateTaken;             //拍摄时间（毫秒）           （INTEGER）（datetaken）
    private int orientation;            //图片角度（0，90，180，270）（INTEGER）（orientation）
    private long miniThumbMagic;        //                         （INTEGER）（mini_thumb_magic）
    private String bucketId;            //上级目录ID                   （TEXT）（bucket_id）
    private String bucketDisplayName;   //上级目录显示名称              （TEXT）（bucket_display_name）

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicasaId() {
        return picasaId;
    }

    public void setPicasaId(String picasaId) {
        this.picasaId = picasaId;
    }

    public int getIsprivate() {
        return isprivate;
    }

    public void setIsprivate(int isprivate) {
        this.isprivate = isprivate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public long getMiniThumbMagic() {
        return miniThumbMagic;
    }

    public void setMiniThumbMagic(long miniThumbMagic) {
        this.miniThumbMagic = miniThumbMagic;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }
}
