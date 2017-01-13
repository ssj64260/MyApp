package com.cxb.tools.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by meimarco on 15-8-1.
 */
public class StringUtil {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    //获取当前日期
    public static String nowDateAddDay(int i) {
        Date now = new Date();

        long t = now.getTime() / 86400000 + i;

        Date before = new Date(t * 86400000);

        String[] date = dateFormat.format(before).split("-");

        return date[0] + "年" + date[1] + "月" + date[2] + "日";

    }

    /**
     * 将字符串转位日期类型
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.format(cal.getTime());
        String paramDate = dateFormater2.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.format(time);
        }
        return ftime;
    }


    //2015-11-02转成2015年11月02日
    public static String friendDatetime(String date) {
        String[] dates = date.split("-");
        if (dates.length != 3) {
            return date;
        }

        return dates[0] + "年" + dates[1] + "月" + dates[2] + "日";

    }

    //今天 10:00
    public static String friendDateAndTime(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return sdate;
        }
        String[] dates = sdate.split(" ");
        if (dates.length != 2) {
            return sdate;
        }
        String[] times = dates[1].split(":");
        if (times.length < 2) {
            return sdate;
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;

        int days = (int) (ct - lt);

        if (days == 0) {
            ftime = "今天 " + times[0] + ":" + times[1];
        } else if (days == 1) {
            ftime = "昨天 " + times[0] + ":" + times[1];
        } else {
//            ftime = "更早";
            ftime = sdate;
        }
        return ftime;
    }

    //友好显示时间
    public static String friendDate(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;

        int days = (int) (ct - lt);

        if (days == 0) {
            ftime = "今天";
        } else if (days == 1) {
            ftime = "昨天";
        } else {
            ftime = "更早";
        }
        return ftime;
    }

    public static boolean isSameMinute(String preTime, String time) {
        Date preT = toDate(preTime);
        Date t = toDate(time);
        if (preT == null || t == null) {
            return false;
        }
        long preM = preT.getTime() / 60000;
        long m = t.getTime() / 60000;

        if (m == preM) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定字符串时间是否为今日
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.format(today);
            String timeDate = dateFormater2.format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtil.isEmpty(str);
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 半角转全角
     */
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
