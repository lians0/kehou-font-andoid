package com.example.kekoufontandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.SearchResultAdapter;
import com.example.kekoufontandroid.adapter.subject.SubjectDetailMainAdapter;
import com.example.kekoufontandroid.diycommon.SimplePaddingDecoration;
import com.example.kekoufontandroid.domain.vo.SearchResultVO;
import com.example.kekoufontandroid.domain.vo.SubjectAndSubjectInfoVO;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.RespCallback;

import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recycleView;
    private Spinner spinner;
    SearchResultVO searchResultVO = new SearchResultVO();
    private SearchResultAdapter searchResultAdapter;

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
    private void initRecyclerView(){
        recycleView = findViewById(R.id.re_search);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        searchResultAdapter = new SearchResultAdapter(searchResultVO, getApplicationContext());
        recycleView.setAdapter(searchResultAdapter);
        recycleView.addItemDecoration(new SimplePaddingDecoration(this));
    }

    private void initListener() {
        //搜索框文字变化监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e("CSDN_LQR", "TextSubmit : " + s);
                refresh("1",s);
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
    private void refresh(String searchType,String searchValue){
        OkHttpUtil.asyGet("/search/" + searchType+"/"+searchValue, new RespCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call call, Response response) {
                String data = OkHttpUtil.dealData(response);
                System.out.println(data);
                Log.d("okhttp", data);
                SearchResultVO newVO = JSON.parseObject(data, SearchResultVO.class);
                searchResultVO = newVO;
                searchResultAdapter.setData(newVO);
//                subjectAndSubjectInfoVO = JSON.parseObject(data, SubjectAndSubjectInfoVO.class);
                Log.d("okhttp", newVO.toString());

                //返回ui线程
                runOnUiThread(()->{
                    searchResultAdapter.notifyDataSetChanged();
                });
            }
        });
    }


}
