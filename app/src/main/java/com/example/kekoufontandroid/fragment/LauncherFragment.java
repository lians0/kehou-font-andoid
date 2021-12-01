package com.example.kekoufontandroid.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.MyAdapter;
import com.example.kekoufontandroid.domain.Favorites;
import com.example.kekoufontandroid.utils.MyCallback;
import com.example.kekoufontandroid.utils.OkHttpUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class LauncherFragment extends Fragment {
    private View view;
    public RecyclerView mRecyclerView;
    private List<Favorites> favorites;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_launcherfragment, container, false);
        initRecyclerView();
        initData();
        return view;
    }

    private void initData() {
        OkHttpUtil.asyGet("/favorites/getFavoritesByUsername", new MyCallback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = OkHttpUtil.dealData(response);
                Log.d("okhttp", data);
                favorites = JSON.parseArray(data, Favorites.class);
                Log.d("okhttp", favorites.toString());
                myAdapter.data = favorites;
                Objects.requireNonNull(getActivity()).runOnUiThread(() ->
                        myAdapter.notifyDataSetChanged());
            }
        });

    }

    private void initRecyclerView() {
        mRecyclerView = view.findViewById(R.id.re_view);

        //设置recycleView的布局方式
        //垂直布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //网格布局
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
//        recycleVeiw.setLayoutManager(gridLayoutManager);

        myAdapter = new MyAdapter(favorites, getActivity());
        mRecyclerView.setAdapter(myAdapter);

        // 为了在这个里写事件要做的事情
        myAdapter.setmOnItemClickListener(new MyAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnRecyclerItemClick(int position) {
                Log.e("lian", "OnRecyclerItemClick" + position);
            }
        });
    }
}
