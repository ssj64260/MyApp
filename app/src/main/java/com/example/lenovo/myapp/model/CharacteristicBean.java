package com.example.lenovo.myapp.model;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * 特性
 */
@Table("Characteristic")
public class CharacteristicBean implements Serializable {

    @PrimaryKey(AssignType.BY_MYSELF)
    private String id;
    private String name;
    private String jp_name;
    private String en_name;
    private String description;

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

    public String getJp_name() {
        return jp_name;
    }

    public void setJp_name(String jp_name) {
        this.jp_name = jp_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
