package com.example.kekoufontandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kekoufontandroid.R;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recycleView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        searchView.onActionViewExpanded();
        //设置输入框提示语
        searchView.setQueryHint("搜索");
        initListener();

    }

    private void initView() {
        searchView = findViewById(R.id.searchView);
        recycleView = findViewById(R.id.re_search);
        spinner = findViewById(R.id.search_spinner);
    }

    private void initListener() {
        //搜索框文字变化监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e("CSDN_LQR", "TextSubmit : " + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("CSDN_LQR", "TextChange --> " + s);
                return false;
            }
        });

    }


}