package com.example.kekoufontandroid.fragment.subject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kekoufontandroid.R;

public class SubjectDetailFragment extends Fragment {
    private  String subjectId;
    private View view;

    public SubjectDetailFragment(String subjectId) {
        this.subjectId = subjectId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subject_detail, container, false);
//        Toast.makeText(getActivity(),subjectId,Toast.LENGTH_SHORT).show();
        initView();
        initData();
        return view;
    }

    private void initView() {

    }


    private void initData() {

    }
}