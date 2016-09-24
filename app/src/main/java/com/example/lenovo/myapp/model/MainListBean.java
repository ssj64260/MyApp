package com.example.lenovo.myapp.model;

import java.io.Serializable;

/**
 * 首页列表模型
 */

public class MainListBean implements Serializable {

    private String name;
    private String head;

    public MainListBean(String head, String name) {
        this.head = head;
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
