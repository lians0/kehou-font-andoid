package com.example.kekoufontandroid.fragment.subject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.example.kekoufontandroid.activity.CommentActivity;
import com.example.kekoufontandroid.adapter.subject.SubjectDetailCommentAdapter;
import com.example.kekoufontandroid.domain.CommentCourse;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.RespCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
        initFloatingButton();
        initRecyclerView();
        return view;
    }

    private void initFloatingButton() {

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingButton_fragment_subject_detail_comment);
        floatingActionButton.setOnClickListener(v -> {

            //跳转页面 跳转 Fragment->Activity 并传值
            Intent intent = new Intent(getActivity(), CommentActivity.class);
            //传递参数
            Bundle bundle = new Bundle();
            bundle.putString("subjectId", subjectId);
            intent.putExtras(bundle);
//            startActivity(intent);
            startActivityForResult(intent, 0);
        });
    }


    private void initRecyclerView() {
        SmartRefreshLayout subjectDetailCommentSr = view.findViewById(R.id.subject_detail_comment_sr);
        subjectDetailCommentSr.setOnRefreshListener(refreshLayout -> {
            Toast.makeText(getActivity(), "刷新", Toast.LENGTH_SHORT).show();
            initData();
            refreshLayout.finishRefresh();
        });

        mRecyclerView = view.findViewById(R.id.re_fragment_subject_detail_comment);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        subjectDetailCommentAdapter = new SubjectDetailCommentAdapter(commentList, getActivity());
        mRecyclerView.setAdapter(subjectDetailCommentAdapter);
        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "显示了");
        initData();
    }

    private void initData() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("subjectId", this.subjectId);

        OkHttpUtil.asyPost("/commentCourse/getCommentCourseBySubjectId", map, new RespCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = OkHttpUtil.dealData(response);
                Log.d("okhttp", data);
                Type listType = new TypeReference<List<CommentCourse>>() {
                }.getType();
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