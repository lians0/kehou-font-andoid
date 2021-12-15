package com.example.kekoufontandroid.fragment.subject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.utils.App;

public class SubjectDetailFragment extends Fragment {
    private  String subjectId;
    private View view;
    TextView viewById;

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
         viewById = (TextView) view.findViewById(R.id.tv_001);

    }


    private void initData() {
        viewById.setText(subjectId);
        Toast.makeText(App.getContext().getApplicationContext(),subjectId,Toast.LENGTH_SHORT).show();
    }
}