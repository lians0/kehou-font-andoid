package com.example.kekoufontandroid.fragment.subject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.subject.SubjectDetailAdapter;
import com.example.kekoufontandroid.adapter.subject.SubjectDetailRecordAdapter;
import com.example.kekoufontandroid.diycommon.DiyGridview;
import com.example.kekoufontandroid.domain.Course;
import com.example.kekoufontandroid.domain.vo.SubjectAndSubjectInfoVO;
import com.example.kekoufontandroid.utils.MyCallback;
import com.example.kekoufontandroid.utils.OkHttpUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;


public class SubjectDetailRecordFragment extends Fragment {
    private String subjectId;
    private Course course;
    private View recordFragment;
    private SubjectDetailRecordAdapter subjectDetailRecordAdapter;

    public SubjectDetailRecordFragment(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化 fragment_subject_detail_record
        recordFragment = inflater.inflate(R.layout.fragment_subject_detail_record, container, false);
        initRecord();
        return recordFragment;
    }
    /**
     * 获取记录 todo:分页处理
     * 获取数据，绑定适配器
     */
    private void initRecord() {
        DiyGridview diyGridview =  recordFragment.findViewById(R.id.subject_detail_diyGridview);
        subjectDetailRecordAdapter = new SubjectDetailRecordAdapter();

        diyGridview.setAdapter(subjectDetailRecordAdapter);
        OkHttpUtil.asyGet("/course/getCourseList/" + subjectId, new MyCallback() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call call, Response response) {
                String data = OkHttpUtil.dealData(response);
                System.out.println(data);
                Log.d("okhttp", data);
                Type listType = (Type) new TypeReference<List<Course>>() {
                }.getType();
                List<Course> courseList = JSON.parseObject(data, listType);
                Log.d("okhttp", courseList.toString());
                subjectDetailRecordAdapter.setData(courseList);

                //返回ui线程
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    subjectDetailRecordAdapter.notifyDataSetChanged();
//                    diyGridview.setAdapter(subjectDetailRecordAdapter);
//                    subjectDetailRecordAdapter.notifyDataSetChanged();
                });
            }
        });
    }
}