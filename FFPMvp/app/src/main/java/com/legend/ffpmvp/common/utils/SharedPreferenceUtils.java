package com.legend.ffpmvp.common.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author Legend
 * @data by on 2018/1/3.
 * @description sharedPreference工具类
 */

public class SharedPreferenceUtils {

    public static final String COOKIE = "cookie";
    public static final String OPENID = "open_id";
    public static final String ACCESSTOKEN = "access_token";
    public static final String ACCOUNTJWT = "account_jwt";
    public static final String EXPIRES = "expires";
    // jwt有效期限为7天
    public static final String JWT_EXPIRES = "jwt_expires";
    private SharedPreferences sharedPreferences;

    /**
     *  初始化
     * @param application
     * @param fileName
     */
    public SharedPreferenceUtils(Application application, String fileName) {
        sharedPreferences =
                application.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     *  初始化
     * @param context
     * @param fileName
     */
    public SharedPreferenceUtils(Context context, String fileName) {
        sharedPreferences =
                context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     *  保存键值对
     * @param key
     * @param value
     */
    public void save(String key,String value) {
        sharedPreferences.edit().putString(key,value).commit();
    }

    /**
     *  读取键值对
     * @param key
     * @return
     */
    public String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    /**
     *  保存对象
     * @param key
     * @param object
     */
    public void save(String key,Object object) {
        String value = new Gson().toJson(object);
        save(key,value);
    }

    /**
     *  读取对象
     * @param key
     * @param cls
     * @return
     */
    public Object get(String key,Class cls) {
        String value = get(key);
        try {
            if (value != null) {
                Object object = new Gson().fromJson(value,cls);
                return object;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
