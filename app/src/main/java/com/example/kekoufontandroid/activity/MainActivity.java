package com.example.kekoufontandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.adapter.MainViewPagerAdapter;
import com.example.kekoufontandroid.fragment.HomeFragment;
import com.example.kekoufontandroid.fragment.FavoritesFragment;
import com.example.kekoufontandroid.fragment.NotificationFragment;
import com.example.kekoufontandroid.utils.SPDataUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    BottomNavigationView navView;
    ViewPager2 pagerView;
    List<Fragment> fragments;
    TextView toolbar_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SPDataUtils.get(this)==null){
           startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }else{
            Toast.makeText(this,SPDataUtils.get(this),Toast.LENGTH_SHORT).show();
        }
        initView();
        initData();
        initListener();
    }

    private void initView() {
        pagerView = findViewById(R.id.pager_view);
        navView = findViewById(R.id.nav_view);
        toolbar_tv = findViewById(R.id.toolbar_tv);
    }

    private void initData() {
        fragments = new ArrayList<>();
        toolbar_tv.setText("记录");
        fragments.add(new HomeFragment());
        fragments.add(new FavoritesFragment());
        fragments.add(new NotificationFragment());
        pagerView.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
    }

    private void initListener() {
        // BottomNavigationView Listener
        navView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                // 第二个参数禁滑动动画
                pagerView.setCurrentItem(0, false);
                toolbar_tv.setText("记录");
            } else if (itemId == R.id.msg) {
                pagerView.setCurrentItem(1, false);
                toolbar_tv.setText("课程");
            } else if (itemId == R.id.mine) {
                pagerView.setCurrentItem(2, false);
                toolbar_tv.setText("通知");
            }
            return true;
        });

        //
        pagerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                navView.getMenu().getItem(position).setChecked(true);
            }
        });
        //禁止滑动
        pagerView.setUserInputEnabled(false);

//        toolbar.setNavigationOnClickListener(()->{
//
//        });
    }


}