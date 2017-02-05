package com.example.lenovo.myapp.model.testbean;

import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;

/**
 * 省份
 */
@Table("province")
public class Province implements Serializable {

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
