package com.example.lenovo.myapp.model;

import java.io.Serializable;

/**
 * 首页列表模型
 */

public class MainListBean implements Serializable {

    private String name;
    private String head;
    private float number;
    private boolean isShowAnimation = true;

    public boolean isShowAnimation() {
        return isShowAnimation;
    }

    public void setShowAnimation(boolean showAnimation) {
        isShowAnimation = showAnimation;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
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
