package com.example.kekoufontandroid.adapter.subject;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.domain.Course;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SubjectDetailRecordAdapter extends BaseAdapter {
    private List<Course> data = new ArrayList<>();

    @SuppressLint("ViewHolder")
    private View recordItemView;

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        recordItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);

        return recordItemView;
    }


}
