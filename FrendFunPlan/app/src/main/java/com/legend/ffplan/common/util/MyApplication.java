package com.legend.ffplan.common.util;

import android.app.Application;

/**
 * @author Legend
 * @data by on 2018/1/3.
 * @description 全局Application
 */

public class MyApplication extends Application {
    public static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static MyApplication getInstance() {
        return instance;
    }
}
