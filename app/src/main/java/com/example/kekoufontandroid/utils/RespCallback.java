package com.example.kekoufontandroid.utils;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 自定义异步请求的回调函数
 * 在此处做公共操作
 * 主要是处理请求失败的处理
 */
public abstract class RespCallback implements Callback {
    @Override
    public void onFailure(@NonNull Call call, IOException e) {
        Log.d("okhttp", e.getMessage());
        Looper.prepare();
        Toast.makeText(App.mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        Looper.loop();
        e.printStackTrace();
    }

    @Override
    public abstract void onResponse(Call call, Response response) throws IOException;


}
