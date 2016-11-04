package com.cxb.tools.network.okhttp;

/**
 * 请求回调接口
 */

public class OnRequestCallBack {

    public enum FailureReason {
        CANCELED("请求已取消"),
        TIMEOUT("请求超时"),
        DATAANALYSIS("数据解析出错"),
        OTHER("请求失败");

        private String reason;

        FailureReason(String reason){
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }

    public void onBefore(int requestId) {
    }

    public void onFailure(int requestId, FailureReason reason) {
    }

    public void onResponse(int requestId, Object dataObject, int networkCode) {
    }

}