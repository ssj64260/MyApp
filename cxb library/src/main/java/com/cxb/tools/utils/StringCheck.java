package com.cxb.tools.utils;

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

    public static boolean isOnlyNumber(String number){
        String regex = "\\d+";
        return !isEmpty(number) && number.matches(regex);
    }

    //判断输入的验证码是否6位纯数字
    public static boolean isCode(String code) {
        String regex = "\\d{6}";
        return !isEmpty(code) && code.matches(regex);
    }

    public static boolean isAddress(String address) {
        String regex = "[^((\\ud83c\\udc00-\\ud83c\\udfff)|(\\ud83d\\udc00-\\ud83d\\udfff)|(\\u2600-\\u27ff))]+";
        return sizeIn(address, 2, 200) && address.matches(regex);
    }

    public static boolean isStreet(String street) {
        String regex = ".{2,20}";
        return !isEmpty(street) && street.matches(regex);
    }

    public static boolean isName(String name) {
        String regex = "[\\u4E00-\\u9FA5·]+";
        return sizeIn(name, 4, 40) && name.matches(regex);
    }

    public static boolean isCompanyName(String companyName) {
        String regex = "[\\u4E00-\\u9FA5A-Za-z0-9\\(\\)\\uff08\\uff09]+";
        return sizeIn(companyName, 2, 200) && companyName.matches(regex);
    }

    public static boolean isNickName(String nickName) {
        String regex = "[\\u4E00-\\u9FA50-9A-Za-z_.]+";
        return sizeIn(nickName, 2, 12) && nickName.matches(regex);
    }

    public static boolean isPassword(String password) {
//        String telRegex = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,12})$";
        String regex = "[0-9A-Za-z]{6,12}";
        return !isEmpty(password) && password.matches(regex);
    }

    public static boolean isIDCard(String idCard) {
        String regex = "\\d{15}|\\d{17}[0-9Xx]";
        return !isEmpty(idCard) && idCard.matches(regex);
    }

    public static boolean isTaxIDCard(String taxIdCard) {
        String regex = "[0-9a-zA-Z]{15,18}";
        return !isEmpty(taxIdCard) && taxIdCard.matches(regex);
    }

    public static boolean isEmpty(String str) {
        return !(str != null && str.length() != 0 && !"".equals(str));
    }

    public static boolean isCarNumber(String carNumber) {
        String regex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}";
        return carNumber.matches(regex);
    }

    public static boolean isCarType(String carType) {
        String regex = "[\\u4e00-\\u9fa5A-Za-z0-9-]+";
        return sizeIn(carType, 2, 40) && carType.matches(regex);
    }

    public static boolean sizeIn(String str, int min, int max) {
        int num;
        try {
            num = str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        return (num >= min && num <= max);
    }

    //判断密码复杂度
    public static int getPasswordLevel(String password) {
        String regex1 = "[0-9]";
        String regex2 = "[a-z]";
        String regex3 = "[A-Z]";

        int pwdLen = password.length();

        int lenLevel = 0;

        if (isEmpty(password)) {
            return 0;
        } else {
            if (pwdLen <= 4) {
                lenLevel = 1;
            } else if (pwdLen > 4 && pwdLen <= 6) {
                lenLevel = 2;
            } else if (pwdLen > 6 && pwdLen <= 8) {
                lenLevel = 3;
            } else if (pwdLen > 8) {
                lenLevel = 4;
            }
        }

        int formatLevel = 0;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;

        for (int i = 0; i < pwdLen; i++) {
            String str = String.valueOf(password.charAt(i));
            if (str.matches(regex1)) {
                if (flag1) {
                    continue;
                }
                flag1 = true;
                formatLevel++;
            } else if (str.matches(regex2)) {
                if (flag2) {
                    continue;
                }
                flag2 = true;
                formatLevel++;
            } else if (str.matches(regex3)) {
                if (flag3) {
                    continue;
                }
                flag3 = true;
                formatLevel++;
            } else {
                if (flag4) {
                    continue;
                }
                flag4 = true;
                formatLevel++;
            }
        }

        return Math.min(lenLevel, formatLevel);
    }
}
