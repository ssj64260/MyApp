package com.cxb.tools.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputMethodUtil {

    /**
     * 隐藏输入法
     *
     * @param context
     * @param view
     */
    public final static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 开关输入法
     *
     * @param context
     */
    public final static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 是否为数字
     */
    public final static boolean isNum(String s) {
        Pattern pattern = Pattern.compile("[1-9]+\\d*\\.*\\d*");
        Matcher isNum = pattern.matcher(s);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 匹配正整数
     */
    public final static boolean isIntNum(String s) {
        Pattern pattern = Pattern.compile("^[1-9]\\d*$");
        Matcher isNum = pattern.matcher(s);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 输入手机号码验证
     *
     * @param mobiles
     * @return
     */
    public final static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9])|(17[0,5-9])|(14[4-8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        //System.out.println(m.matches() + "---");
        return m.matches();
    }

    //安全度验证
    public final static int testSafe(String pwdString) {
        int F = 0;//0:低;1:中;2:安全;3:非常安全;
        int minLength = 6;
        int ucharCount = 0; //计算大写
        int lcharCount = 0; //计算小写
        int dcharCount = 0; //计算数字
        int ocharCount = 0; //计算其他字符

        int[] dcharAnsi = {48, 57};
        int[] lcharAnsi = {97, 122};
        int[] ucharAnsi = {65, 90};
        String[] simplePwd = {"password", "sex", "god", "123456", "123", "liverpool", "letmein", "qwerty", "monkey"};
        final int count = pwdString.length();
        for (int i = 0; i < count; i++) {
            char str1 = pwdString.charAt(i);
            int ansiStr = (int) str1;
            if (ansiStr >= lcharAnsi[0] && ansiStr <= lcharAnsi[1]) {
                lcharCount++;
                continue;
            }
            if (ansiStr >= ucharAnsi[0] && ansiStr <= ucharAnsi[1]) {
                ucharCount++;
                continue;
            }
            if (ansiStr >= dcharAnsi[0] && ansiStr <= dcharAnsi[1]) {
                dcharCount++;
                continue;
            }
            ocharCount++;
        }
        if (pwdString.length() > minLength) {
            if ((float) lcharCount / pwdString.length() == 1 || (float) ucharCount / pwdString.length() == 1 || (float) dcharCount / pwdString.length() == 1 || (float) ocharCount / pwdString.length() == 1) {
                F = 0;
            }
            if (((float) lcharCount / pwdString.length() > 0 || (float) ucharCount / pwdString.length() > 0) && (float) dcharCount / pwdString.length() > 0) {
                F = 1;
            }
            if (((float) lcharCount / pwdString.length() > 0 || (float) ucharCount / pwdString.length() > 0) && (float) dcharCount / pwdString.length() > 0 && (float) ocharCount / pwdString.length() > 0) {
                F = 2;
            }

            if (pwdString.length() > minLength + 3) {
                if ((float) lcharCount / pwdString.length() > 0 && (float) ucharCount / pwdString.length() > 0 && (float) dcharCount / pwdString.length() > 0 && (float) ocharCount / pwdString.length() > 0) {
                    F = 3;
                }
            }
        } else {
            F = 0;
        }
        /* for(int D=0;D<simplePwd.length;D++){
             if(pwdString.toLowerCase() == simplePwd[D]){
                 F=-200;
             }
         }*/

        return F;

    }
    /*public final static String CheckPwdRepetition(int pLen,String str)
    {
     String res="";
     for (int i=0;i<str.length();i++){
      Boolean repeated=true;
      int j=0;
      for(j=0;j<pLen&&(j + i + pLen)<str.length();j++){
       repeated=repeated&&(str.charAt(j+i)==str.charAt(j+i+pLen));
      }
      if(j < pLen){
    	  repeated = false; 
      }      
      if(repeated){
       i+=pLen-1;
       repeated=false;
      }else {
       res+=str.charAt(i);
      }
     }
     return res;
    }
    
	public final static int   CheckPwdStrength(String Password){
	     int flag=0;
	     int score=0;
	     if(Password.length()<6)
	     {
	      flag=3;
	      return flag;
	     }
//     if(Password.length()<9&&Password.matches("/^[0-9]*[1-9][0-9]*$/"))
//     {
//      flag=4;
//      return flag;
//     }
	    // score+=Password.length()*4;
	     score+=(CheckPwdRepetition(1,Password).length()-Password.length())*1;
	     score+=(CheckPwdRepetition(2,Password).length()-Password.length())*1;
	     score+=(CheckPwdRepetition(3,Password).length()-Password.length())*1;
	     score+=(CheckPwdRepetition(4,Password).length()-Password.length())*1;
         //step1:密码有三个数字
         //step2:密码有两个特殊字符
         if (Password.matches("(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])")) { score += 5; }
         //step3:密码有大写和小写字母
         if (Password.matches("([a-z].*[A-Z])|([A-Z].*[a-z])")) { score += 0; }
         //step4:密码有数字和字母
         if (Password.matches("([a-zA-Z])") && Password.matches("([0-9])")) { score += 15; }
        
         //step5:密码有数字和特殊字符
         if (Password.matches("([!,@,#,$,%,^,&,*,?,_,~])") && Password.matches("([0-9])")) { score += 15; }
         //step6:密码有字母和特殊字符
         if (Password.matches("([!,@,#,$,%,^,&,*,?,_,~])") && Password.matches("([a-zA-Z])")) { score += 15; }
         return score;
    }*/
}
