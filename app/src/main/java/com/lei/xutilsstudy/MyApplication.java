package com.lei.xutilsstudy;

import android.app.Application;

import org.xutils.x;

/**
 * Created by yanle on 2018/2/24.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        x.Ext.init(this);
    }
}
