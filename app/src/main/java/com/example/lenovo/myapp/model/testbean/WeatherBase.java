package com.example.lenovo.myapp.model.testbean;

import java.io.Serializable;

/**
 * 天气对象基础部分
 */

public class WeatherBase implements Serializable {

    private String msg;//请求返回信息
    private String msgid;//请求返回ID
    private String success;//是否成功 0:失败，1:成功

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
