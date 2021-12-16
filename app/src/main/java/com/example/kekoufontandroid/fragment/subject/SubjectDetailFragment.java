package com.example.kekoufontandroid.fragment.subject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.domain.vo.SubjectDetailVO;
import com.example.kekoufontandroid.utils.App;
import com.example.kekoufontandroid.utils.MyCallback;
import com.example.kekoufontandroid.utils.OkHttpUtil;

import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class SubjectDetailFragment extends Fragment {
    private String subjectId;
    private View view;
    TextView teacherName;
    TextView joinTotal;
    TextView subjectDesc;
    ImageView imageViewBook;


    public SubjectDetailFragment(String subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subject_detail, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        teacherName = view.findViewById(R.id.tv_teacherName);
        joinTotal = view.findViewById(R.id.tv_joinTotal);
        subjectDesc = view.findViewById(R.id.tv_subjectDesc);
        imageViewBook = view.findViewById(R.id.imageView_book);

    }

    private void initData() {
        // 获取学科详情数据
        OkHttpUtil.asyGet("/subject/getSubjectDetail/" + subjectId, new MyCallback() {

            @Override
            public void onResponse(Call call, Response response) {
                String data = OkHttpUtil.dealData(response);
                System.out.println(data);
                Log.d("okhttp", data);
                SubjectDetailVO subjectDetailVO = JSON.parseObject(data, SubjectDetailVO.class);
                Log.d("okhttp", subjectDetailVO.toString());

                //返回ui线程
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    teacherName.setText(subjectDetailVO.getTeacher());
                    joinTotal.setText(Integer.toString(subjectDetailVO.getJoinTotal()));
                    subjectDesc.setText(subjectDetailVO.getSubjectDesc());
                    Glide.with(getActivity()).load(subjectDetailVO.getSubjectIcon()).into(imageViewBook);
                });
            }
        });
    }

}