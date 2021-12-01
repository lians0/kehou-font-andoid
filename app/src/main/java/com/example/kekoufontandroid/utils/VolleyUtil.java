package com.example.kekoufontandroid.utils;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kekoufontandroid.utils.App;
import com.google.gson.Gson;

import java.util.Map;

public class VolleyUtil {
    /**
     * post请求
     *
     * @param path   请求路径
     * @param map    请求参数
     * @param entity 需要解析的类
     * @return
     */
    public static void StringRequest_POST(final Handler handler, String path, final Map<String, String> map, final Object entity) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Object o = gson.fromJson(response, entity.getClass());
                Message msg = Message.obtain();
                msg.obj = o;
                handler.sendMessage(msg);
//                Map<String, Member> map = gson.fromJson(response,
//                        new TypeToken<Map<String, Member>>() {
//                        }.getType());
            }
        }, new MyErrorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setTag(path);
        App.queue.add(request);
    }

    /**
     * get请求
     *
     * @param path   请求路径
     * @param entity 需要解析的类
     * @return
     */
    public static void StringRequest_GET(final Handler handler, String path, final Object entity) {
        StringRequest request = new StringRequest(path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Object o = gson.fromJson(response, entity.getClass());

                Message msg = Message.obtain();
                msg.obj = o;
                handler.sendMessage(msg);
            }
        }, new MyErrorListener());
        request.setTag(path);
        App.queue.add(request);
    }

    private static final class VolleyUtilHold {
        public static VolleyUtil getVolleyUtilInstance() {
            return new VolleyUtil();
        }
    }

    /**
     * 执行请求错误
     */
    public static class MyErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            //  LogUtils.showLog("VolleyError" + error.getMessage());
            Log.e("volleyerror",error.getMessage());
        }
    }

    /**
     * 需要手动解析字符串的post请求
     *
     * @param path
     * @param succes
     * @param error
     * @param map
     */
    public static void simple_post(String path,
                            Response.Listener<String> succes, Response.ErrorListener error,
                            final Map<String, String> map) {

        StringRequest request_post = new StringRequest(Request.Method.POST,
                path, succes, error) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request_post.setTag(path);
        System.out.println(request_post);
        App.queue.add(request_post);

    }

    /**
     * 需要手动解析字符串的get请求
     *
     * @param path
     * @param succes
     * @param error
     */
    public static void simple_get(String path, Response.Listener<String> succes, Response.ErrorListener error) {
        Log.e("VolleyUtil", "path-->"+path);
        StringRequest request = new StringRequest(path,succes,error);
        App.queue.add(request);
    }

    /**
     * 取消请求
     *
     * @param path 请求路径
     */
    public static void removeRequest(String path) {
        App.queue.cancelAll(path);
    }


}

