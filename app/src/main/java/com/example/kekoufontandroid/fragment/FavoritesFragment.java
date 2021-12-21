package com.example.kekoufontandroid.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.activity.SubjectDetailActivity;
import com.example.kekoufontandroid.adapter.FavoritesAdapter;
import com.example.kekoufontandroid.domain.Favorites;
import com.example.kekoufontandroid.utils.MyCallback;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class FavoritesFragment extends Fragment {
    private View view;
    public RecyclerView mRecyclerView;
    private List<Favorites> favorites;
    private FavoritesAdapter favoritesAdapter;
    private SmartRefreshLayout favorites_sr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        initRecyclerView();
        initSmartRecyclerView();
        initData();
        return view;
    }

    private void initSmartRecyclerView() {
        favorites_sr = view.findViewById(R.id.favorites_sr);
        favorites_sr.setOnRefreshListener(refreshLayout -> {
            Toast.makeText(getActivity(), "刷新", Toast.LENGTH_SHORT).show();
            refresh();
            refreshLayout.finishRefresh();
        });
        //上拉加载更多数据
        favorites_sr.setOnLoadMoreListener(refreshLayout -> {
            Toast.makeText(getActivity(), "loading", Toast.LENGTH_SHORT).show();
            refreshLayout.finishLoadMore();
        });

    }

    /**
     * 进入Favorites去请求数据
     */
    private void initData() {
        refresh();
    }

    private void initRecyclerView() {
        mRecyclerView = view.findViewById(R.id.review_fragment_favorites);

        //设置recycleView的布局方式
        //垂直布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(linearLayoutManager);

        //网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        favoritesAdapter = new FavoritesAdapter(favorites, getActivity());

        /* 为了在这个里写事件要做的事情
         点击元素的事件 讲subjectId 从当前fragment->SubjectDetailActivity
         */
        favoritesAdapter.setOnItemClickListener(position -> {
            Log.e("lian", "OnRecyclerItemClick" + position);
            Intent intent = new Intent(getActivity(), SubjectDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("subjectId",favorites.get(position).getSubjectId());
            intent.putExtras(bundle);
            Log.e("lian",favorites.get(position).getSubjectId());
            startActivity(intent);
        });
        mRecyclerView.setAdapter(favoritesAdapter);
    }

    /**
     * 刷新
     */
    public void refresh() {
        OkHttpUtil.asyGet("/favorites/getFavoritesByUsername", new MyCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = OkHttpUtil.dealData(response);
                Log.d("okhttp", data);
                favorites = JSON.parseArray(data, Favorites.class);
                Log.d("okhttp", favorites.toString());
                favoritesAdapter.data = favorites;

                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    favoritesAdapter.notifyDataSetChanged();
                });
            }
        });
    }
}
