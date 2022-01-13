package com.example.kekoufontandroid.adapter.subject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.domain.CommentCourse;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Setter
public class SubjectDetailCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CommentCourse> data;
    private final Context context;

    public SubjectDetailCommentAdapter(List<CommentCourse> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View firstItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_course, parent, false);
        return new ItemViewHolder(firstItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
//        if (data!=null) {

            CommentCourse commentCourse = data.get(position);

            itemHolder.semester.setText(commentCourse.getSemesterId()!=null?commentCourse.getSemesterId().toString():"");
            itemHolder.testType.setText(commentCourse.getTestType()!=null?commentCourse.getTestType():"");
            itemHolder.commentCourse.setText(commentCourse.getCommentText()!=null?commentCourse.getCommentText():"");
//        }

    }

    @Override
    public int getItemCount() {
//        return 12;
        return data != null ? data.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView semester;
        private final TextView testType;
        private final TextView commentCourse;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            testType = (TextView) itemView.findViewById(R.id.test_type);
            semester = (TextView) itemView.findViewById(R.id.semester);
            commentCourse = (TextView) itemView.findViewById(R.id.comment_course);

        }
    }
}
