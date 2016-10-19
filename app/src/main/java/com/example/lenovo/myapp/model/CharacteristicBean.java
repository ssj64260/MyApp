package com.example.lenovo.myapp.model;

import java.io.Serializable;

/**
 * 特性
 */

public class CharacteristicBean implements Serializable {

    private String id;
    private String name;

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
}
