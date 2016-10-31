package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;

/**
 * 广告
 */

public class AdBean implements Serializable {

    private String id;
    private String jump_type;
    private String pic_url;
    private String jump_newid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJump_type() {
        return jump_type;
    }

    public void setJump_type(String jump_type) {
        this.jump_type = jump_type;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getJump_newid() {
        return jump_newid;
    }

    public void setJump_newid(String jump_newid) {
        this.jump_newid = jump_newid;
    }
}
