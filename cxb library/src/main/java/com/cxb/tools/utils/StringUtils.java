package com.cxb.tools.utils;

/**
 * 字符串处理工具类
 */

public class StringUtils {

    //获取url里最后目录名称
    public static String getUrlTag(String url) {
        if (url.contains("?")) {
            if (url.lastIndexOf("/") == url.indexOf("?") - 1) {
                int last = url.lastIndexOf("/");
                return url.substring(url.lastIndexOf("/", last - 1) + 1, last);
            } else {
                return url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
            }
        } else {
            if (url.lastIndexOf("/") == url.length() - 1) {
                int last = url.lastIndexOf("/");
                return url.substring(url.lastIndexOf("/", last - 1) + 1, last);
            } else {
                return url.substring(url.lastIndexOf("/") + 1, url.length());
            }
        }
    }

    //半角转全角
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
