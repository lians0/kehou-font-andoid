package com.example.kekoufontandroid.fragment.subject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.subject.SubjectDetailAdapter;
import com.example.kekoufontandroid.domain.vo.SubjectAndSubjectInfoVO;
import com.example.kekoufontandroid.utils.RespCallback;
import com.example.kekoufontandroid.utils.OkHttpUtil;


import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 课程详细Fragment
 * <p/>
 * 请求获取数据
 * 配置RecycleView布局
 */
public class SubjectDetailFragment extends Fragment {
    private String subjectId;
    private View view;
    private RecyclerView mRecyclerView;
    private SubjectDetailAdapter subjectDetailAdapter;
    private SubjectAndSubjectInfoVO subjectAndSubjectInfoVO = new SubjectAndSubjectInfoVO();

    public SubjectDetailFragment(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subject_detail_main, container, false);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mRecyclerView = view.findViewById(R.id.re_fragment_subject_detail);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        subjectDetailAdapter = new SubjectDetailAdapter(subjectAndSubjectInfoVO, getActivity());
        mRecyclerView.setAdapter(subjectDetailAdapter);
        initData();
    }

    private void initData() {
        // 获取学科详情数据
        OkHttpUtil.asyGet("/course/getCourseListAndSubjectInfo/" + subjectId, new RespCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call call, Response response) {
                String data = OkHttpUtil.dealData(response);
                System.out.println(data);
                Log.d("okhttp", data);
                SubjectAndSubjectInfoVO newVO = JSON.parseObject(data, SubjectAndSubjectInfoVO.class);
                subjectDetailAdapter.setData(newVO);

//                subjectAndSubjectInfoVO = JSON.parseObject(data, SubjectAndSubjectInfoVO.class);

                Log.d("okhttp", SubjectDetailFragment.this.subjectAndSubjectInfoVO.toString());

                //返回ui线程
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    subjectDetailAdapter.notifyDataSetChanged();
                });
            }
        });
    }

}