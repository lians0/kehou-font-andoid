package com.example.kekoufontandroid.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.SearchResultAdapter;
import com.example.kekoufontandroid.domain.vo.SearchResultVO;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.RespCallback;

import okhttp3.Call;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recycleView;
    private Spinner spinner;
    SearchResultVO searchResultVO = new SearchResultVO();
    private SearchResultAdapter searchResultAdapter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        // 设置searchView 自动获取焦点以及图标的显示方式
        searchView.onActionViewExpanded();
        //设置输入框提示语
        searchView.setQueryHint("搜索");

        initRecyclerView();
        initListener();

    }

    private void initView() {
        searchView = findViewById(R.id.searchView);
        spinner = findViewById(R.id.search_spinner);
    }

    private void initRecyclerView() {
        recycleView = findViewById(R.id.re_search);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        searchResultAdapter = new SearchResultAdapter(searchResultVO, getApplicationContext());
        recycleView.setAdapter(searchResultAdapter);
        //设置分割符
        recycleView.addItemDecoration(getItemDecoration());
        //设置课程和学科item的点击事件
        searchResultAdapter.setMOnSubjectAndCourseItemClickListener((itemViewType, position) -> {
            // 判断点击的
            switch (itemViewType) {
                case SearchResultAdapter.ITEM_SUBJECT:
                    Integer subjectId = searchResultAdapter.getData()
                            .getSubjectList().get(position - 1).getSubjectId();

                    Intent intent = new Intent(getApplicationContext(), SubjectDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("subjectId", subjectId.toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case SearchResultAdapter.ITEM_COURSE:

            }

        });

    }

    private void initListener() {
        //搜索框文字变化监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e("CSDN_LQR", "TextSubmit : " + s);
                refresh("1", s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 请求后端并刷新数据
                Log.e("CSDN_LQR", "TextChange --> " + s);
                return false;
            }
        });

    }

    private void refresh(String searchType, String searchValue) {
        OkHttpUtil.asyGet("/search/" + searchType + "/" + searchValue, new RespCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call call, Response response) {
                String data = OkHttpUtil.dealData(response);
                System.out.println(data);
                Log.d("okhttp", data);
                SearchResultVO newVO = JSON.parseObject(data, SearchResultVO.class);
                searchResultVO = newVO;
                searchResultAdapter.setData(newVO);
                Log.d("okhttp", newVO.toString());

                //返回ui线程
                runOnUiThread(() -> {
                    searchResultAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    /**
     * 配置item 分割线
     */
    private RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == searchResultAdapter.getSubjectLen() - 1) { //给最后一位的item设置50下边距
                    position = parent.getChildAdapterPosition(view);
                    Log.d("test", "position=>" + position);
                    outRect.bottom = 20;
                }
            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                Log.d("test","onDraw()");
                Paint dividerPaint = new Paint();
                dividerPaint.setColor(getApplicationContext().getResources().getColor(R.color.line_color, null));
                View view1 = parent.getChildAt(searchResultAdapter.getSubjectLen() - 1);
//                Log.d("test","view："+view1);
                // 这里的childAdapterPosition会随着滑动变化
                int childAdapterPosition = parent.getChildAdapterPosition(view1);
//                Log.d("test",childAdapterPosition+"");
                int count = parent.getChildCount();
//                Log.d("test","count=>"+count);
//                parent.getPaddingLeft();
                for (int i = 0; i < count; i++) {
                    View view = parent.getChildAt(i);
                    int top = view.getTop();
                    int bottom = view.getBottom();
                    int left = view.getLeft();
                    int right = view.getRight();
                    c.drawRect(left, top - 20, right, top, dividerPaint);
                }
            }
        };
    }


}
