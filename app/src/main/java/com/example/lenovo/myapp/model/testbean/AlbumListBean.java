package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;
import java.util.List;

/**
 * 相册列表
 */

public class AlbumListBean implements Serializable {

    private String icon;
    private String name;
    private String path;
    private List<PhotoBean> photos;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<PhotoBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoBean> photos) {
        this.photos = photos;
    }
}
