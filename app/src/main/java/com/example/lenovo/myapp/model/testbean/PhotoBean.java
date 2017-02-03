package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;

/**
 * 相片
 * {@link android.provider.BaseColumns}
 * {@link android.provider.MediaStore.Images.ImageColumns}
 * {@link android.provider.MediaStore.MediaColumns}
 */

public class PhotoBean implements Serializable {

    //BaseColumns
    private long id;    //ID    （_id）

    //ImageColumns
    private String bucketDisplayName;   //上级目录显示名称              （bucket_display_name）
    private String bucketId;            //上级目录ID                   （bucket_id）
    private long dateTaken;             //拍摄时间（毫秒）              （datetaken）
    private String description;         //描述                         （description）
    private int isprivate;              //是否私有                     （isprivate）
    private double latitude;            //捕获图像的纬度                （latitude）
    private double longitude;           //捕获图像的经度                （longitude）
    private long miniThumbMagic;        //                             （mini_thumb_magic）
    private int orientation;            //图片角度（0，90，180，270）    （orientation）
    private String picasaId;            //                             （picasa_id）

    //MediaColumns
    private String data;        //图片路径        （_data）
    private long dateAdded;     //创建日期        （date_added）
    private long dateModified;  //最后修改日期    （date_modified）
    private String displayName; //显示名称        （_display_name）
    private int height;         //图片高度        （height）
    private String type;        //MIME类型        （mime_type）
    private long size;          //图片大小        （_size）
    private String title;       //标题            （title）
    private int width;          //图片宽度        （width）

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public long getMiniThumbMagic() {
        return miniThumbMagic;
    }

    public void setMiniThumbMagic(long miniThumbMagic) {
        this.miniThumbMagic = miniThumbMagic;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getPicasaId() {
        return picasaId;
    }

    public void setPicasaId(String picasaId) {
        this.picasaId = picasaId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
