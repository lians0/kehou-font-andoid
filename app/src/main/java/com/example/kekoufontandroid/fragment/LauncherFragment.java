package com.example.kekoufontandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.utils.OkHttpUtil;

public class LauncherFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        new Thread(()->{
//            String s = OkHttpUtil.synGet("/favorites/getFavoritesByUsername");
//            Log.d("okhttp", s);
//        }).start();



        return inflater.inflate(R.layout.activity_launcherfragment,container,false);
    }
}
