package com.example.lenovo.myapp.model;

/**
 * 口袋妖怪 中日英名称
 */

public class PokemonNameBean {

    private String id;
    private String cn_name;
    private String jp_name;
    private String en_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
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
}
