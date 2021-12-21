package com.example.kekoufontandroid.adapter.subject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.FavoritesAdapter;
import com.example.kekoufontandroid.domain.dto.SubjectDetailDTO;
import com.example.kekoufontandroid.domain.vo.SubjectAndSubjectInfoVO;

import java.util.List;

public class SubjectDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public SubjectAndSubjectInfoVO data;
    private final Context context;

    private final int FIRST_ITEM = 0;


    public SubjectDetailAdapter(SubjectAndSubjectInfoVO data, Context context) {
        this.data = data;
        this.context = context;
    }

    /**
     * 根据不同的position，设置不同的ViewType
     * position表示当前是第几个Item，通过position拿到当前的Item对象，然后判断这个item对象需要那种视图
     */
//    @Override
//    public int getItemViewType(int position) {
//        if (position == FIRST_ITEM) {
//            return 0;
//        } else {
//            return position;
////            return super.getItemViewType(position);
//        }
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == FIRST_ITEM) {
//            View firstItem = View.inflate(context, R.layout.item_subject_detail_first, parent);
//            return new FirstItemViewHolder(firstItem);
//        } else {
//            View otherItem = View.inflate(context, R.layout.item_record, parent);
//            return new OtherItemViewHolder(otherItem);
//        }
            View otherItem = View.inflate(context, R.layout.item_record, null);
            return new OtherItemViewHolder(otherItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if (position==FIRST_ITEM) {
//            FirstItemViewHolder firstItemViewHolder = (FirstItemViewHolder) holder;
//            Glide.with(context).load(data.getSubjectIcon()).into(firstItemViewHolder.bookImage);
//            firstItemViewHolder.joinTotal.setText(data.getJoinTotal());
//            firstItemViewHolder.teacherName.setText(data.getTeacher());
//            firstItemViewHolder.subjectDesc.setText(data.getSubjectDesc());
//        }else{
//            OtherItemViewHolder otherItemViewHolder = (OtherItemViewHolder) holder;
//            List<SubjectAndSubjectInfoVO.CourseVO> courseList = data.getCourseList();
//            SubjectAndSubjectInfoVO.CourseVO courseVO = courseList.get(position - 1);
//            otherItemViewHolder.recordTitle.setText(courseVO.getCourseName());
//            otherItemViewHolder.classAddr.setText("测试"+position);
//            otherItemViewHolder.dataIndex.setText(position);
//        }
        OtherItemViewHolder otherItemViewHolder = (OtherItemViewHolder) holder;
        List<SubjectAndSubjectInfoVO.CourseVO> courseList = data.getCourseList();
        SubjectAndSubjectInfoVO.CourseVO courseVO = courseList.get(position );
        otherItemViewHolder.recordTitle.setText(courseVO.getCourseName());
        otherItemViewHolder.classAddr.setText("测试"+position);
        otherItemViewHolder.dataIndex.setText(position);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class FirstItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView bookImage;
        public TextView joinTotal;
        public TextView teacherName;
        public TextView subjectDesc;
        public Button isJoin;

        public FirstItemViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = (ImageView) itemView.findViewById(R.id.imView_book_view);
            joinTotal = (TextView) itemView.findViewById(R.id.tv_joinTotal);
            teacherName = (TextView) itemView.findViewById(R.id.tv_teacherName);
            subjectDesc = (TextView) itemView.findViewById(R.id.tv_item_subject_detail_first_subjectDesc);
            isJoin = (Button) itemView.findViewById(R.id.btn_isJoin);
        }
    }

    public class OtherItemViewHolder extends RecyclerView.ViewHolder {

        public TextView recordTitle;
        public TextView classAddr;
        public TextView dataIndex;

        public OtherItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dataIndex = (TextView) itemView.findViewById(R.id.tv_data_index);
            classAddr = (TextView) itemView.findViewById(R.id.tv_class_addr);
            recordTitle = (TextView) itemView.findViewById(R.id.tv_record_title);

        }
    }


}
