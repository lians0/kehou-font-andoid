package com.example.kekoufontandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.domain.dto.CourseSearchDTO;
import com.example.kekoufontandroid.domain.dto.SubjectSearchDTO;
import com.example.kekoufontandroid.domain.vo.SearchResultVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // todo:使用枚举
    private static final int TITLE_SUBJECT = 0;  //列表元素类型为标题
    private static final int TITLE_COURSE = 1;   //列表元素类型为标题
    private static final int ITEM_SUBJECT = 2;
    private static final int ITEM_COURSE = 3;
    int subjectLen; //subject 列表长度包括title（即subjectList.size()+1）
    int sumCount;   //列表总长 包括所有title
    private SearchResultVO data;
    private Context context;

    enum ViewType {
        TITLE_SUBJECT, TITLE_COURSE, ITEM_SUBJECT, ITEM_COURSE;

    }

    public SearchResultAdapter(SearchResultVO data, Context context) {
        this.data = data;
        this.context = context;
    }

    /**
     * 判断view类型 返回对应的实例
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item;
        switch (viewType) {
            case TITLE_SUBJECT:
            case TITLE_COURSE:
                item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_group_title, parent, false);
                return new SearchGroupTitleItemViewHolder(item);
            case ITEM_SUBJECT:
            case ITEM_COURSE:
                item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_subject_course, parent, false);
                return new SubjectAndCourseItemViewHolder(item);
        }
        return null;
    }

    /**
     * 元素排列学科 课程 用户
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position < subjectLen) {
            if (data.getSubjectList() != null) {
                if (position == 0) {
                    return TITLE_SUBJECT;
                } else {
                    return ITEM_SUBJECT;
                }
            }
        }
        if (position < sumCount) {
            if (data.getCourseList() != null) {
                if (position == subjectLen) {
                    return TITLE_COURSE;
                } else {
                    return ITEM_COURSE;
                }
            }
        }
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        Log.i("test", "position:" + position);

        switch (itemViewType) {
            case TITLE_SUBJECT:
                SearchGroupTitleItemViewHolder titleSubjectViewHolder = (SearchGroupTitleItemViewHolder) holder;
                titleSubjectViewHolder.tvLeftTitle.setText("学科");
                titleSubjectViewHolder.tvRightTitle.setText("查看全部>>");
                break;
            case TITLE_COURSE:
                SearchGroupTitleItemViewHolder titleCourseViewHolder = (SearchGroupTitleItemViewHolder) holder;
                titleCourseViewHolder.tvLeftTitle.setText("课程");
                titleCourseViewHolder.tvRightTitle.setText("查看全部>>");
                break;
            case ITEM_SUBJECT:
                SubjectAndCourseItemViewHolder itemSubjectViewHolder = (SubjectAndCourseItemViewHolder) holder;
                SubjectSearchDTO subjectSearchDTO = data.getSubjectList().get(position - 1);
                Glide.with(context).load(subjectSearchDTO.getIcon()).into(itemSubjectViewHolder.imageSearchSubject);
                itemSubjectViewHolder.tvMainTitle.setText(subjectSearchDTO.getSubjectName() + "");
                itemSubjectViewHolder.tvSubTitle.setText("老师："+subjectSearchDTO.getCreatorName() + "");
                break;
            case ITEM_COURSE:
                SubjectAndCourseItemViewHolder itemCourseViewHolder = (SubjectAndCourseItemViewHolder) holder;
                CourseSearchDTO courseSearchDTO = data.getCourseList().get(position - subjectLen - 1);
//                Glide.with(context).load(subjectSearchDTO.getIcon()).into(itemSubjectViewHolder.imageSearchSubject);

                itemCourseViewHolder.tvMainTitle.setText(courseSearchDTO.getCourseName() + "");
                itemCourseViewHolder.tvSubTitle.setText("来自："+courseSearchDTO.getSubjectName() + "");
                break;
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (data.getSubjectList() != null && data.getSubjectList().size() > 0) {
            subjectLen = count += data.getSubjectList().size() + 1;
        }
        if (data.getCourseList() != null && data.getCourseList().size() > 0) {
            count += data.getCourseList().size() + 1;
        }
        sumCount = count;
        Log.d("test", "count:" + count);
        return count;
    }

    private class SubjectAndCourseItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageSearchSubject;
        private final TextView tvMainTitle;
        private final TextView tvSubTitle;

        public SubjectAndCourseItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSearchSubject = itemView.findViewById(R.id.image_search_subject_course);
            tvMainTitle = itemView.findViewById(R.id.tv_search_title_subject_course);
            tvSubTitle = itemView.findViewById(R.id.tv_search_from_subject_course);
        }
    }

    public class SearchGroupTitleItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvLeftTitle;
        private final TextView tvRightTitle;

        public SearchGroupTitleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLeftTitle = itemView.findViewById(R.id.tv_search_group_title_left);
            tvRightTitle = itemView.findViewById(R.id.tv_search_group_title_right);
        }
    }

}


