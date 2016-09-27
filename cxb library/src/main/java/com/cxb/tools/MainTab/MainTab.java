package com.cxb.tools.MainTab;

import java.io.Serializable;

/**
 * 自定义头部功能菜单基类
 */
public class MainTab implements Serializable {

    private String id;
    private String logo;
    private String name;
    private String url;
    private int logoResource;

    public MainTab(String id, int logoResource, String name) {
        this.id = id;
        this.logoResource = logoResource;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLogoResource() {
        return logoResource;
    }

    public void setLogoResource(int logoResource) {
        this.logoResource = logoResource;
    }
}
