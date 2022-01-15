package com.example.kekoufontandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.kekoufontandroid.R;
import com.example.kekoufontandroid.utils.App;
import com.example.kekoufontandroid.utils.OkHttpUtil;
import com.example.kekoufontandroid.utils.RespCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    private String subjectId;
    private EditText editText;
    private Spinner spinner;
    private Button btn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // 接受Fragment传过来的值
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        subjectId = bundle.getString("subjectId");
        Log.d("test", subjectId);
        initView();
        initListener();

    }

    private void initView() {
        spinner = findViewById(R.id.spinner_activity_comment);
        editText = findViewById(R.id.editText_activity_comment);
        btn = findViewById(R.id.btn_activity_comment);
        toolbar = (Toolbar) findViewById(R.id.comment_toolbar);
    }

    private void initListener(){

        btn.setOnClickListener(v -> {
            String selectedItem = (String) spinner.getSelectedItem();
            Editable text = editText.getText();
            HashMap<Object, Object> map = new HashMap<>();
            map.put("subjectId",subjectId);
            map.put("testType",selectedItem);
            map.put("commentText",text.toString());
            Log.d("test",selectedItem);
            Log.d("test",text.toString());
            OkHttpUtil.asyPost("/commentCourse/addCommentCourse", JSON.toJSONString(map), new RespCallback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    CommentActivity.this.setResult(0);
                    CommentActivity.this.finish();
                }
            });
        });



        // 提交数据数据

    }

}