package com.cxb.tools.MainTab;

import java.io.Serializable;

/**
 * 自定义头部功能菜单基类
 */
public class MainTab implements Serializable {

    private String id;
    private String name;
    private String url;//图标url
    private int logoResource;//图标本地资源

    public MainTab(String id, int logoResource, String name) {
        this.id = id;
        this.logoResource = logoResource;
        this.name = name;
    }

    public MainTab(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
