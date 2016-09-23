package com.example.lenovo.myapp.utils;

import java.io.UnsupportedEncodingException;

/**
 *  字符串检测工具
 */
public class StringCheck {
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$";
        return !isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    //判断输入的验证码是否6位纯数字
    public static boolean isCode(String mobiles) {
        String regex = "\\d{6}";
        return !isEmpty(mobiles) && mobiles.matches(regex);
    }

    public static boolean isAddress(String mobiles) {
        String regex = "[^((\\ud83c\\udc00-\\ud83c\\udfff)|(\\ud83d\\udc00-\\ud83d\\udfff)|(\\u2600-\\u27ff))]+";
        return sizeIn(mobiles, 2, 200) && mobiles.matches(regex);
    }

    public static boolean isStreet(String mobiles) {
        String regex = ".{2,20}";
        return !isEmpty(mobiles) && mobiles.matches(regex);
    }

    public static boolean isName(String mobiles) {
        String regex = "[\\u4E00-\\u9FA5·]+";
        return sizeIn(mobiles, 4, 40) && mobiles.matches(regex);
    }

    public static boolean isCompanyName(String mobiles) {
        String regex = "[\\u4E00-\\u9FA5A-Za-z0-9\\(\\)\\uff08\\uff09]+";
        return sizeIn(mobiles, 2, 200) && mobiles.matches(regex);
    }

    public static boolean isNickName(String mobiles) {
        String regex = "[\\u4E00-\\u9FA50-9A-Za-z_.]+";
        return sizeIn(mobiles, 2, 12) && mobiles.matches(regex);
    }

    public static boolean isPassword(String mobiles) {
//        String telRegex = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,12})$";
        String regex = "[0-9A-Za-z]{6,12}";
        return !isEmpty(mobiles) && mobiles.matches(regex);
    }

    public static boolean isIDCard(String mobiles) {
        String regex = "\\d{15}|\\d{17}[0-9Xx]";
        return !isEmpty(mobiles) && mobiles.matches(regex);
    }

    public static boolean isTaxIDCard(String mobiles) {
        String regex = "[0-9a-zA-Z]{15,18}";
        return !isEmpty(mobiles) && mobiles.matches(regex);
    }

    public static boolean isEmpty(String str) {
        return !(str != null && str.length() != 0 && !"".equals(str));
    }

    public static boolean isCarNumber(String mobiles) {
        String regex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}";
        return mobiles.matches(regex);
    }

    public static boolean isCarType(String mobiles) {
        String regex = "[\\u4e00-\\u9fa5A-Za-z0-9-]+";
        return sizeIn(mobiles, 2, 40) && mobiles.matches(regex);
    }

    public static boolean sizeIn(String mobiles, int min, int max) {
        int num;
        try {
            num = mobiles.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        return (num >= min && num <= max);
    }
}
