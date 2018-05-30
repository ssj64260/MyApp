package com.example.lenovo.myapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 口袋妖怪 中日英名称
 */
@Entity(nameInDb = "GD_Pokemon_Name")
public class GDPokemonName {

    @Id
    private String id;//名称ID
    private String cn_name;//中文名
    private String jp_name;//日文名
    private String en_name;//英文名
    @Generated(hash = 629130851)
    public GDPokemonName(String id, String cn_name, String jp_name,
            String en_name) {
        this.id = id;
        this.cn_name = cn_name;
        this.jp_name = jp_name;
        this.en_name = en_name;
    }
    @Generated(hash = 1418110614)
    public GDPokemonName() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCn_name() {
        return this.cn_name;
    }
    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }
    public String getJp_name() {
        return this.jp_name;
    }
    public void setJp_name(String jp_name) {
        this.jp_name = jp_name;
    }
    public String getEn_name() {
        return this.en_name;
    }
    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }


}
