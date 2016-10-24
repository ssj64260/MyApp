package com.cxb.tools.network.okhttp;

import java.io.Serializable;

/**
 * model 基类
 */

public class ServiceResult<T> implements Serializable {

    private int code;//返回码
    private String msg;//返回信息

    T data;//返回内容，根据相应对象返回

    private String primeResults;//原始结果

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPrimeResults() {
        return primeResults;
    }

    public void setPrimeResults(String primeResults) {
        this.primeResults = primeResults;
    }
}
