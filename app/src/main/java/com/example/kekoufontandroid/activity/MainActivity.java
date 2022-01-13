package com.example.kekoufontandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    BottomNavigationView navView;
    ViewPager2 pagerView;
    List<Fragment> fragments;
    TextView toolbar_tv;
    private NavigationView navLeftView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (SPDataUtils.get(this) == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            Toast.makeText(this, SPDataUtils.get(this), Toast.LENGTH_SHORT).show();
        }
        initView();
        initData();
        initListener();
    }

    private void initView() {
        pagerView = findViewById(R.id.pager_view);
        navView = findViewById(R.id.nav_view);
        toolbar_tv = findViewById(R.id.toolbar_tv);
        navLeftView = findViewById(R.id.nav_left_view);
        drawerLayout = findViewById(R.id.drawer_layout);

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

        //为左侧划出菜单添加事件
        navLeftView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_left_menu_logout:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("确定退出登录?")
                            .setMessage("确定吗")
                            .setPositiveButton("是", (dialog, which) -> {
                                SPDataUtils.del(getApplicationContext(),"token");
                                Toast.makeText(getApplicationContext(),"退出登录",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            })
                            .setNegativeButton("否", null)
                            .show();
                    break;


            }
            // 设置侧边菜单内是否有多选结构 false没有多选结构
            item.setCheckable(false);
            //设置选择选项后是否关闭侧边栏 默认是不关闭的
//            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
//        toolbar.setNavigationOnClickListener(()->{
//
//        });
    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(view.getContext(),"del"+position,Toast.LENGTH_SHORT).show();
//    }
}