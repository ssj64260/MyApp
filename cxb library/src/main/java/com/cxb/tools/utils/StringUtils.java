package com.cxb.tools.utils;

import java.io.UnsupportedEncodingException;

/**
 * 字符串处理工具类
 */

public class StringUtils {

    public static final int PRINT_MAX_SIZE = 48;//打印机一行最长字节
    public static final int NAME_MAX_SIZE = 28;//名称一行最大长度
    public static final int COUNT_MAX_SIZE = 4;//数量一行最大长度
    public static final int HALF_MAX_SIZE = 22;//打印机一行一半字节数（中间有空格）

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

    // 功能：字符串半角转换为全角
    // 说明：半角空格为32,全角空格为12288.
    //       其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
    // 输入参数：input -- 需要转换的字符串
    // 输出参数：无：
    // 返回值: 转换后的字符串
    public static String halfToFull(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }

            //根据实际情况，过滤不需要转换的符号
            //if (c[i] == 46) //半角点号，不转换
            // continue;

            if (c[i] > 32 && c[i] < 127)    //其他符号都转换为全角
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }


    // 功能：字符串全角转换为半角
    // 说明：全角空格为12288，半角空格为32
    //       其他字符全角(65281-65374)与半角(33-126)的对应关系是：均相差65248
    // 输入参数：input -- 需要转换的字符串
    // 输出参数：无：
    // 返回值: 转换后的字符串
    public static String fullToHalf(String input) {
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


    ///////////////////////////////////////////////////////////////////////////
    // 打印机字符串处理
    ///////////////////////////////////////////////////////////////////////////

    //返回后面补足空格后的字符串（有换行处理）
    public static String[] getHalfStringWithEndSpace(String str, int maxSize) {
        String[] halfStr = new String[2];
        int num = getBytesLength(str);
        int spaceNum = maxSize - num;
        halfStr[0] = str;

        if (spaceNum < 0) {
            halfStr[0] = getLimitStringWithEndSpace(str, maxSize);
            halfStr[1] = str.replace(halfStr[0], "");
            num = getBytesLength(halfStr[0]);
            spaceNum = maxSize - num;
        }

        halfStr[0] += addSpace(spaceNum);

        return halfStr;
    }

    //取极限字符串
    private static String getLimitStringWithEndSpace(String str, int maxSize) {
        String temp = "";
        int num = 0;
        final int count = str.length();
        for (int i = 1; i < count; i++) {
            temp = str.substring(0, str.length() - i);
            num = getBytesLength(temp);
            if (num <= maxSize) {
                return temp;
            }
        }
        return str;
    }

    //返回后面补足空格后的字符串（无换行处理）
    public static String getStringWithEndSpace(String str, int maxSize) {
        int num = getBytesLength(str);
        int spaceNum = maxSize - num;

        if (spaceNum < 0) {
            return str;
        }

        str += addSpace(spaceNum);

        return str;
    }

    //返回前面补足空格后的字符串
    public static String getStringWithStartSpace(String str, int maxSize) {
        int num = getBytesLength(str);
        int spaceNum = maxSize - num;

        if (spaceNum >= 0) {
            return addSpace(spaceNum) + str;
        } else {
            //TODO 超过最大值要换行处理
            return "";
        }
    }

    //获取 GBK编码下 字符串所占字节数
    public static int getBytesLength(String str) {
        int num = 0;
        try {
            num = str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return num;
    }

    //添加空格
    public static String addSpace(int spaceNum) {
        String str = "";
        int index = 0;
        while (index < spaceNum) {
            str += " ";
            index++;
        }
        return str;
    }
}
