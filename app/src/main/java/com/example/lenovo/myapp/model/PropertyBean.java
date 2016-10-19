package com.example.lenovo.myapp.model;

import java.io.Serializable;

/**
 * 属性
 */

public class PropertyBean implements Serializable {

    private String id;
    private String name;
    private String en_name;

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

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }
}
