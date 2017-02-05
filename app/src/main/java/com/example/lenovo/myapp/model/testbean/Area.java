package com.example.lenovo.myapp.model.testbean;

import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;

/**
 *  地区
 */
@Table("area")
public class Area implements Serializable {

    private String id;
    private String pid;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
