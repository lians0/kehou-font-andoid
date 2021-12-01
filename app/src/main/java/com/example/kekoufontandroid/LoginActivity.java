package com.example.kekoufontandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.kekoufontandroid.domain.Result;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.SPDataUtils;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {


    private TextView username;
    private TextView password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        String token = SPDataUtils.get(this);
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        initListener();
    }

    public void initListener() {
        login.setOnClickListener(v -> new Thread(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("username", username.getText().toString().trim());
            map.put("password", password.getText().toString().trim());
            Object s = OkHttpUtil.synPost("/login", map);

            if (s != null) {
                SPDataUtils.save(this, s.toString());
                Log.i("okhttp", s.toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

        }).start());

    }


}