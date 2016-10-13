package com.ch.date1008;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/9  16:57
 */
public class Utils {

    private static final String spFileName = "app";
    public static Boolean getBoolean(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, false);
        return result;
    }

//    public static Boolean getBoolean(Context context, String strKey,
//                                     Boolean strDefault) {
//        SharedPreferences setPreferences = context.getSharedPreferences(
//                spFileName, Context.MODE_PRIVATE);
//        Boolean result = setPreferences.getBoolean(strKey, strDefault);
//        return result;
//    }


    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }


}
