package com.example.kekoufontandroid.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class App extends Application {

    public static RequestQueue queue;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
       // Fresco.initialize(this);//图片控件初始化
        mContext = getApplicationContext();
        queue = Volley.newRequestQueue(getApplicationContext());//volley网络请求初始化
    }


}
