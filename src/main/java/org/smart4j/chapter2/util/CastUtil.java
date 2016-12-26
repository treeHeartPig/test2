package org.smart4j.chapter2.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by 269871 on 2016/12/14.
 */
public final class CastUtil {
    /**
     * 转为string类型
     */
    public static  String castString(Object obj){
        return CastUtil.castString(obj,"");
    }
    /**
     * 转为string类型(提供默认值)
     */
    public static String castString(Object obj,String defaultVlaue){
        return  obj != null ? String.valueOf(obj):defaultVlaue;
    }

    /**
     * 转为double类型
     */
    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj,0.0);
    }
    /**
     * 转为double类型（指定默认值）
     */
    public static double castDouble(Object obj,Double defaultValue){
        double value=defaultValue;
        if(obj != null){
            String strValue=String.valueOf(obj);
            if(StringUtils.isNotEmpty(strValue)){
                value=Double.parseDouble(strValue);
            }
        }
        return value;
    }

    /**
     * 转为long型
     */
    public static Long castLong(Object obj){
        return CastUtil.castLong(obj,0);
    }
    /**
     * 转为long型(指定默认值）
     */
    public static Long castLong(Object obj,long defaultValue){
        long value=defaultValue;
        if(obj != null){
            String strValue=String.valueOf(obj);
            if(StringUtils.isNotEmpty(strValue)){
                value=Long.parseLong(strValue);
            }
        }
        return value;
    }
    /**
     * 转为int类型
     */
    public static int castInt(Object obj){
        return CastUtil.castInt(obj,0);
    }
    /**
     * 转为int类型(指定默认值）
     */
    public static int castInt(Object obj,int defaultValue){
      int value=defaultValue;
        if(obj !=null){
            String strValue=String.valueOf(obj);
            if(StringUtils.isNotEmpty(strValue)){
                value=Integer.parseInt(strValue);
            }
        }
        return value;
    }

    /**
     * 转为布尔类型
     */
    public static boolean castBoolean(Object obj){
        return  CastUtil.castBoolean(obj,false);
    }

    /**
     * 转为布尔类型(指定默认值）
     */
    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean value=defaultValue;
        if(obj!=null){
            String strValue=castString(obj);
            value=Boolean.parseBoolean(strValue);
        }

        return  value;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str){
        if(str !=null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

}
