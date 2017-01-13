package com.cxb.tools.utils;

import java.io.UnsupportedEncodingException;

/**
 * 字符串处理
 */
public class PrinterStringUtil {

    public static final int PRINT_MAX_SIZE = 48;//打印机一行最长字节
    public static final int NAME_MAX_SIZE = 28;//名称一行最大长度
    public static final int COUNT_MAX_SIZE = 4;//数量一行最大长度

    public static final int HALF_MAX_SIZE = 22;//打印机一行一半字节数（中间有空格）

    //返回后面补足空格后的字符串（有换行处理）
    public static String[] getHalfStringWithEndSpace(String str, int maxSize) {
        String[] halfStr = new String[2];
        int num = getBytesLength(str);
        int spaceNum = maxSize - num;
        halfStr[0] = str;

        if (spaceNum < 0) {
            halfStr[0] = getLimitStringWithEndSpace(str, maxSize);
            halfStr[1] = str.replace(halfStr[0],"");
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
        for (int i = 1; i < str.length(); i++) {
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
    public static int getBytesLength(String mobiles) {
        int num = 0;
        try {
            num = mobiles.getBytes("GBK").length;
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
