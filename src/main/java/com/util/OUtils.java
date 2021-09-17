package com.util;

/**
 * 工具类
 * @author tsp
 *
 */
public class OUtils {
    public static int ZERO = 0;				//零

    /**	字符串是否为null或者为""
     */
    public static boolean isEmpty(String text){
        return text == null || text.length() == 0;
    }

    /**	字符串是否为null或者""
     */
    public static boolean isEmpty(Object obj){
        return obj == null || String.valueOf(obj).length() == 0;
    }

    /**	如果字符串为null 返回""
     */
    public static String getN(String text){
        return text == null ? "":text;
    }

    /**	获取随机数	取4位
     */
    public static String getRandomAndFour(){
        return	String.valueOf((int)(Math.random()*9999)+1000);
    }

    /** 转换，失败返回0
     */
    public static int parseInt(String val){
        int result = 0;
        try{
            result = Integer.parseInt(val);
        }catch(Exception e){

        }
        return result;
    }

    /** 转换，失败返回0
     */
    public static long parseLong(String val){
        long result = 0;
        try{
            result = Long.parseLong(val);
        }catch(Exception e){

        }
        return result;
    }

    /** 转换，失败返回0
     */
    public static float parseFloat(String val){
        float result = 0;
        try{
            result = Float.parseFloat(val);
        }catch(Exception e){

        }
        return result;
    }

    /** 转换，失败返回0
     */
    public static float parseFloat(String val,float def){
        try{
            return Float.parseFloat(val);
        }catch(Exception e){

        }
        return def;
    }

    /** 转换，失败返回0
     */
    public static double parseDouble(String val){
        return parseDouble(val,0);
    }

    /** 转换，失败返回0
     */
    public static double parseDouble(String val,double def){
        try{
            return Double.parseDouble(val);
        }catch(Exception e){

        }
        return def;
    }

    /** 转换，失败返回0
     */
    public static String parseFloatStr(String val){
        String result = "0";
        try{
            Float.parseFloat(val);
            result = val;
        }catch(Exception e){

        }
        return result;
    }
}
