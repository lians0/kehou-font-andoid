package com.example.kekoufontandroid.adapter.subject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.domain.vo.SubjectAndSubjectInfoVO;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.RespCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Setter;
import okhttp3.Call;
import okhttp3.Response;

@Setter
public class SubjectDetailMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SubjectAndSubjectInfoVO data;
    private final Context context;

    private final int FIRST_ITEM = 0;

    public SubjectDetailMainAdapter(SubjectAndSubjectInfoVO data, Context context) {
        this.data = data;
        this.context = context;
    }

    /**
     * 根据不同的position，设置不同的ViewType
     * position表示当前是第几个Item，通过position拿到当前的Item对象，然后判断这个item对象需要那种视图
     */
    @Override
    public int getItemViewType(int position) {
        if (position == FIRST_ITEM) {
            return 0;
        } else {
            return position;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FIRST_ITEM) {
            //解决宽度不能铺满
            View firstItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_detail_first, parent, false);
            return new FirstItemViewHolder(firstItem);
        } else {
            //解决宽度不能铺满
            View otherItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
            return new OtherItemViewHolder(otherItem);
        }
    }

    /**
     * 配置第一个与其他的不同
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == FIRST_ITEM) {
            FirstItemViewHolder firstItemViewHolder = (FirstItemViewHolder) holder;
            Glide.with(context).load(data.getSubjectIcon()).into(firstItemViewHolder.bookImage);
            firstItemViewHolder.joinTotal.setText(data.getJoinTotal() + "");
            firstItemViewHolder.teacherName.setText(data.getTeacher());
            firstItemViewHolder.subjectDesc.setText(data.getSubjectDesc());
            //先根据data.isJoin() 设置按钮样式
            Button isJoinBtn = firstItemViewHolder.isJoin;
            if (data.isJoin()) {
                // 已参加样式
                isJoinBtn.setTextColor(Color.parseColor("#000000"));
                isJoinBtn.setText("已参加");
                isJoinBtn.setBackgroundResource(R.drawable.btn_join);
            } else {
                // 未参加样式
                isJoinBtn.setTextColor(Color.parseColor("#ffffff"));
                isJoinBtn.setText("参加");
                isJoinBtn.setBackgroundResource(R.drawable.btn_not_join);
            }

            isJoinBtn.setOnClickListener(v -> {
                // 防止连击发送多条请求
                isJoinBtn.setEnabled(false);
                if (data.isJoin()) {
                    //已参加 请求退出 改变为未参加样式
                    OkHttpUtil.asyGet("/favorites/del/" + data.getSubjectId(), new RespCallback() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call call, Response response) {
                            isJoinBtn.setTextColor(Color.parseColor("#ffffff"));
                            isJoinBtn.setText("参加");
                            isJoinBtn.setBackgroundResource(R.drawable.btn_not_join);
                            data.setJoin(false);
                            ((FragmentActivity) context).runOnUiThread(() -> {
                                isJoinBtn.setEnabled(true);
                            });
                        }
                    });
                } else {
                    //未参加 请求参加 改变为参加样式
                    OkHttpUtil.asyGet("/favorites/add/" + data.getSubjectId(), new RespCallback() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call call, Response response) {
                            // 已参加样式
                            isJoinBtn.setTextColor(Color.parseColor("#000000"));
                            isJoinBtn.setText("已参加");
                            isJoinBtn.setBackgroundResource(R.drawable.btn_join);
                            data.setJoin(true);
                            ((FragmentActivity) context).runOnUiThread(() -> {
                                isJoinBtn.setEnabled(true);
                            });
                        }
                    });
                }
            });

        } else {
            OtherItemViewHolder otherItemViewHolder = (OtherItemViewHolder) holder;
            List<SubjectAndSubjectInfoVO.CourseVO> courseList = data.getCourseList();
            SubjectAndSubjectInfoVO.CourseVO courseVO = courseList.get(position - 1);

            otherItemViewHolder.recordTitle.setText(courseVO.getCourseName());
            otherItemViewHolder.classAddr.setText("测试" + position);
            otherItemViewHolder.dataIndex.setText(position + "");

            // 配置标签显示
            otherItemViewHolder.labelsView.setIndicator(true);
            ArrayList<String> strings = new ArrayList<>();
            if (courseList.get(position - 1).isJoin()) {
                strings.add("已参加");
                strings.add("已参加");
                otherItemViewHolder.labelsView.setLabels(strings);
                otherItemViewHolder.labelsView.setTextBold(true);
                otherItemViewHolder.labelsView.setSelects(0);
                otherItemViewHolder.labelsView.setLabelBackgroundResource(R.drawable.label_bg);
                List<Integer> selectLabels = otherItemViewHolder.labelsView.getSelectLabels();
                Log.d("test", selectLabels.toString());
                System.out.println(selectLabels);
            } else {
                strings.add("未参加");
                otherItemViewHolder.labelsView.setLabels(strings);
//                otherItemViewHolder.labelsView.clearAllSelect();
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.getCourseList() != null ? data.getCourseList().size() + 1 : 0;
    }

    /**
     * 第一个元素的ViewHolder
     */
    public class FirstItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView bookImage;
        public TextView joinTotal;
        public TextView teacherName;
        public TextView subjectDesc;
        public Button isJoin;

        public FirstItemViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.imView_book_view);
            joinTotal = itemView.findViewById(R.id.tv_joinTotal);
            teacherName = itemView.findViewById(R.id.tv_teacherName);
            subjectDesc = itemView.findViewById(R.id.tv_item_subject_detail_first_subjectDesc);
            isJoin = itemView.findViewById(R.id.btn_isJoin);
        }
    }

    /**
     * 其他元素的ViewHolder
     */
    public class OtherItemViewHolder extends RecyclerView.ViewHolder {

        public TextView recordTitle;
        public TextView classAddr;
        public TextView dataIndex;
        private final LabelsView labelsView;

        public OtherItemViewHolder(@NonNull View itemView) {
            super(itemView);
            labelsView = itemView.findViewById(R.id.labels_item_record);
            dataIndex = itemView.findViewById(R.id.tv_data_index);
            classAddr = itemView.findViewById(R.id.tv_class_addr);
            recordTitle = itemView.findViewById(R.id.tv_record_title);
            // 设置课程记录的点击事件 利用回调机制 将触发函数写在Fragment中
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.OnRecyclerItemClick(getAdapterPosition());
                Log.d("lian", "OnRecyclerItemClick" + getAdapterPosition());
            });

        }
    }

    private SubjectDetailMainAdapter.OnRecyclerItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(SubjectDetailMainAdapter.OnRecyclerItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void OnRecyclerItemClick(int position);
    }

}
