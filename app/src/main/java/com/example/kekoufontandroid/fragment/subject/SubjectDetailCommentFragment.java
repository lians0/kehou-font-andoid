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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.subject.SubjectDetailCommentAdapter;
import com.example.kekoufontandroid.adapter.subject.SubjectDetailMainAdapter;
import com.example.kekoufontandroid.domain.CommentCourse;
import com.example.kekoufontandroid.domain.vo.SubjectAndSubjectInfoVO;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.RespCallback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class SubjectDetailCommentFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private String subjectId;
    private View view;
    private List<CommentCourse> commentList;
    private SubjectDetailCommentAdapter subjectDetailCommentAdapter;

    public SubjectDetailCommentFragment(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subject_detail_comment, container, false);
//        Toast.makeText(getActivity(),subjectId,Toast.LENGTH_SHORT).show();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mRecyclerView = view.findViewById(R.id.re_fragment_subject_detail_comment);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        subjectDetailCommentAdapter = new SubjectDetailCommentAdapter(commentList, getActivity());
        mRecyclerView.setAdapter(subjectDetailCommentAdapter);
        initData();
    }


    private void initData() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("subjectId", this.subjectId);

        OkHttpUtil.asyPost("/commentCourse/getCommentCourseBySubjectId",map, new RespCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = OkHttpUtil.dealData(response);
                Log.d("okhttp", data);
                Type listType = new TypeReference<List<CommentCourse>>() {}.getType();
                commentList = JSON.parseObject(data, listType);
                subjectDetailCommentAdapter.setData(commentList);
                Log.d("okhttp", commentList.toString());

                //返回ui线程
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    subjectDetailCommentAdapter.notifyDataSetChanged();
                });
            }
        });

    }
}