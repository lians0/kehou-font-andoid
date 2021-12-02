package com.example.kekoufontandroid.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 用来获取应用的上下文
 * App.getContext()
 */
public class App extends Application {

    public static RequestQueue queue;
    public static Context mContext;

    public static RequestQueue getQueue() {
        return queue;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // Fresco.initialize(this);//图片控件初始化
        mContext = getApplicationContext();
        queue = Volley.newRequestQueue(getApplicationContext());//volley网络请求初始化
    }


}
