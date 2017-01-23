package com.cxb.tools.network.okhttp;

/**
 * 下载文件回调
 */

public interface OnDownloadCallBack {

    public void onPregrass(long curSize, long maxSize);

    public void onSuccess(String fileUri);

    public void onFailed(String fileUri);

}
