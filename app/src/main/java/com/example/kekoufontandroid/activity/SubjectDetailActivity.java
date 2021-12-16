package com.example.kekoufontandroid.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import com.alibaba.fastjson.JSON;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.MainViewPagerAdapter;
import com.example.kekoufontandroid.adapter.SubjectDetailAdapter;
import com.example.kekoufontandroid.domain.Favorites;
import com.example.kekoufontandroid.fragment.FavoritesFragment;
import com.example.kekoufontandroid.fragment.HomeFragment;
import com.example.kekoufontandroid.fragment.NotificationFragment;
import com.example.kekoufontandroid.fragment.subject.SubjectDetailCommentFragment;
import com.example.kekoufontandroid.fragment.subject.SubjectDetailFragment;
import com.example.kekoufontandroid.fragment.subject.SubjectDetailRecordFragment;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 课程详情主activity
 */
public class SubjectDetailActivity extends AppCompatActivity {

    private TabLayout subject_detail_tab;
    private ViewPager2 subject_detail_pager2;
    private List<Fragment> fragments;
    private List<String> titles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        subject_detail_tab = findViewById(R.id.subject_detail_tab);
        subject_detail_pager2 = findViewById(R.id.subject_detail_pager2);

    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String subjectId = bundle.getString("subjectId");

//        Log.i("okhttp",subjectId);

        titles.add("课程详情");
        titles.add("记录");
        titles.add("学科评价");
        fragments = new ArrayList<>();
        fragments.add(new SubjectDetailFragment(subjectId));
        fragments.add(new SubjectDetailRecordFragment());
        fragments.add(new SubjectDetailCommentFragment(subjectId));
        subject_detail_pager2.setAdapter(new SubjectDetailAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
    }

    private void initListener() {
        // 使TabLayout与ViewPager2联动
        new TabLayoutMediator(subject_detail_tab, subject_detail_pager2,
                (tab, position) ->
                        tab.setText(titles.get(position))
        ).attach();
    }
    //查询subject 下的课程记录和课程详情 todo: 课程评价


}