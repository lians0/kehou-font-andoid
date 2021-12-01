package com.example.kekoufontandroid.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SPDataUtils {

    private static final  String mFileName="myData";

    public static void save(Context context , String data){
        SharedPreferences sp = context.getSharedPreferences(mFileName, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("token",data);
        edit.apply();

    }

    public static String get(Context context){
        SharedPreferences sp = context.getSharedPreferences(mFileName, MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public static void del(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(mFileName, MODE_PRIVATE);
        sp.edit().remove(key).apply();

    }

}
