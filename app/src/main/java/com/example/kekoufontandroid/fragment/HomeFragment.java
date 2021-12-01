package com.example.kekoufontandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.SPDataUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        initListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_homeframe, container, false);
    }

    private void initView() {
    }

    private void initListener() {
        //get 同步
        btn1.setOnClickListener(v -> new Thread(() -> {
            String s = OkHttpUtil.synGet("/favorites/getFavoritesByUsername");
            Log.d("okhttp", s);

        }).start());

        //get 异步
        btn2.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url("https://www.httpbin.org/get?a=1&b=2")
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.i("lian", "response:" + response.body().string());
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }
            });
        });


        // post 异步请求
        btn3.setOnClickListener(v -> {
            FormBody formBody = new FormBody.Builder().add("a", "1")
                    .add("b", "2")
                    .build();
            Request request = new Request.Builder()
                    .url("https://www.httpbin.org/post")
                    .post(formBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.i("lian", "response:" + response.body().string());
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }
            });

        });
        btn4.setOnClickListener(v->{
            SPDataUtils.del(getActivity(),"token");
            Toast.makeText(getActivity(),"del",Toast.LENGTH_SHORT).show();

        });


    }

}
