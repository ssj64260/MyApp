package com.example.lenovo.myapp.utils;

/**
 * 网络请求常量
 */

public class Constants {

    public static final String DEBUG_URL = "itest.meishiyi.cn/index.php/";//测试链接
    //    public static final String DEBUG_URL = "121.43.145.235/msy/api/index.php";//测试链接
    public static final String OFFICIAL_URL = "i.meishiyi.cn/index.php/";//正式链接
    //    public static final String CUSTOM_URL = "192.168.199.173/meishiyi/api/index.php";//自定义链接
    public static final String CUSTOM_URL = "192.168.199.178/aiweiplatform/api/index.php";//自定义链接


    //获取天气预报
    public static final int ID_GET_WEATHER = 1001;
    public static final int ID_POST_WEATHER = 1002;
    public static final String URL_WEATHER = "api.k780.com:88";

    //获取GitHub上Okhttp的信息
    public static final int ID_GET_GITHUB_INFO = 1003;
    public static final String HOST_GITHUB = "api.github.com";
    public static final String URL_GET_GITHUB_INFO = "gists/c2a7c39532239ff261be";

    //获取需要验证 Okhttp的信息
    public static final int ID_GET_OKHTTP_INFO = 1004;
    public static final String HOST_PUBLIC_OBJECT = "publicobject.com";
    public static final String URL_GET_OKHTTP_INFO = "secrets/hellosecret.txt";
}
