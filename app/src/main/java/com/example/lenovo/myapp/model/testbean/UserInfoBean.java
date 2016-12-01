package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;

/**
 * 美食易时间段
 */

public class UserInfoBean implements Serializable {

    private String access_token;//用户令牌
    private String isfirst;//是否第一次登录
    private String bossid;//用户id

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getIsfirst() {
        return isfirst;
    }

    public void setIsfirst(String isfirst) {
        this.isfirst = isfirst;
    }

    public String getBossid() {
        return bossid;
    }

    public void setBossid(String bossid) {
        this.bossid = bossid;
    }
}
