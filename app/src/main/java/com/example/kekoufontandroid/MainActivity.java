package com.example.kekoufontandroid;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.kekoufontandroid.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    BottomNavigationView navView;
    ViewPager2 pagerView;
    List<Fragment> fragments;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        pagerView = findViewById(R.id.pager_view);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new LauncherFragment());
        fragments.add(new NotificationFragment());

        pagerView.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

    }

    private void initListener() {
        // BottomNavigationView Listener
        navView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                // 第二个参数禁滑动动画
                pagerView.setCurrentItem(0, false);
                toolbar.setTitle("Home");
            } else if (itemId == R.id.msg) {
                pagerView.setCurrentItem(1, false);
                toolbar.setTitle("记录");
            } else if (itemId == R.id.mine) {
                pagerView.setCurrentItem(2, false);
                toolbar.setTitle("我的");
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