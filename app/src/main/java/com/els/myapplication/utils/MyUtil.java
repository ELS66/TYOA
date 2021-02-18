package com.els.myapplication.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {
    public static String md5(String str) {
        /*if (TextUtils.isEmpty(str)){
            return "";
        }*/
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int c = b & 0xff;
                String result = Integer.toHexString(c);
                if(result.length()<2){
                    sb.append(0);
                }
                sb.append(result);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isChinese(String str) throws UnsupportedEncodingException {
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static boolean passisok(String str){
        boolean havenum = false;
        boolean haveMinZi = false;
        boolean haveMaxZi = false;
        char[] chars = str.toCharArray();
        for (int i=0;i<chars.length;i++){
            if (chars[i]<58 && chars[i]>47 ){
                havenum = true;
            } else if (chars[i]>64 && chars[i] < 91){
                haveMaxZi = true;
            } else if (chars[i] >96 && chars[i] <123) {
                haveMinZi = true;
            }
        }
        return (haveMaxZi || haveMinZi) && havenum;
    }

    public static boolean bissextile(int year){  //创建boolean类型的方法
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){  //平闰年判断算法
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        String s1 = "0243";
        System.out.println(md5(s1));
    }


}
