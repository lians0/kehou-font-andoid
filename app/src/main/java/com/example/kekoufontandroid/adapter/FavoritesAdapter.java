package com.example.kekoufontandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.domain.Favorites;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {
    public   List<Favorites> data;
    private final Context context;

    public FavoritesAdapter(List<Favorites> data, Context context) {
        this.data = data;
        this.context = context;
    }

    /**
     * 创建一个创建每个item的处理器
     * 规定必须用类中的内部类 {@link MyViewHolder}
     * 这里全是固定写法
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建一个item的实例
        View view = View.inflate(context, R.layout.favorites_item, null);
        // 把整个item传给ViewHolder
        return new MyViewHolder(view);
    }

    /**
     * 将item与数据绑定 每次渲染item都会调用此方法
     *
     * @param holder   视图处理器
     * @param position 当前item的索引值
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position).getSubjectName());
        Glide.with(context).load(data.get(position).getIconUrl()).into(holder.imageView);
    }

    /**
     * 只做设置item的个数，固定写法
     *
     * @return RecycleView中item的个数
     */
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    /**
     * 视图处理器 是为了listview滚动的时候快速设置值
     * 使listView不用每次都findById来寻找控件；只需要复用已创建的。
     *
     * 处理一个item中控件
     * 包括控件的绑定，设置监听器
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);

            // 设置点击事件
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.OnRecyclerItemClick(getAdapterPosition());

//                    Log.e("lian","OnRecyclerItemClick"+getAdapterPosition());
            });
        }
    }

    private OnRecyclerItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void OnRecyclerItemClick(int position);
    }

}
