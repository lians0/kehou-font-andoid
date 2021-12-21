package com.example.kekoufontandroid.utils;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.SneakyThrows;
import okhttp3.Callback;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

/**
 * 对okHttp进一步封装
 */
public class OkHttpUtil {
    private static final OkHttpClient client = new OkHttpClient();
    static final String BASE_URL ="http://192.168.237.169:8081";


    /**
     * 创建一个使用http协议的OkHttpClient
     */
    private static OkHttpClient getUnsafeOkHttpClient() {
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(DO_NOT_VERIFY)
                .build();
    }

    private static OkHttpClient getHttpClient() {
        // Https 异常：javax.net.ssl.SSLHandshakeException: Handshake failed
        // Caused by: javax.net.ssl.SSLProtocolException: SSL handshake aborted: ssl=0x7a59e45208: Failure in SSL library, usually a protocol error
        // 解决在Android5.0版本以下https无法访问（亲测5.0以上版本也报同样的错误，猜测应该通过服务器配置协议兼容可以解决，目前是Android端自己做了兼容）
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build();//解决在Android5.0版本以下https无法访问
        ConnectionSpec spec1 = new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build();//兼容http接口
        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        return client.connectionSpecs(Arrays.asList(spec,spec1)).build();
        return client.connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS)).build();
    }

    /**
     * 同步GET请求
     * 需要在调用的activity或者fragment中添加响应的回调处理方法runOnUiThread(new Runable())，同步的方法统一返回的是响应内容的字符串形式
     * 如果有参数，参数加载在url中，就是先拼接好URL
     */
    public static String synGet(String url) {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + url)
                    .addHeader("Auth", SPDataUtils.get(App.mContext) == null ? "null" : SPDataUtils.get(App.mContext))
                    .addHeader("Connection", "close")
                    .build();
            Response response = client.newCall(request).execute();

            return dealData(response);

        } catch (Exception e) {
            Log.d("okhttp", e.getMessage());
            Looper.prepare();
            Toast.makeText(App.mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            Looper.loop();

            e.printStackTrace();
            return null;
        }
    }

    /**
     * 同步POST请求
     * @param params 参数map
     */
    public static String synPost(String url, Map<String, String> params) {
        try {

            String jsonStr = JSON.toJSONString(params);

            RequestBody body = FormBody.create(MediaType.parse("application/json"), jsonStr);
            Request request = new Request.Builder()
                    .url(BASE_URL + url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Connection", "close")
                    .addHeader("Auth", SPDataUtils.get(App.mContext) == null ? "null" : SPDataUtils.get(App.mContext))
                    .post(body)
                    .build();
            Log.d("okhttp", request.toString());
            Response response = client.newCall(request).execute();

            return dealData(response);
        } catch (Exception e) {
            Log.d("okhttp", e.getMessage());

            Looper.prepare();
            Toast.makeText(App.mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            Looper.loop();

            e.printStackTrace();
            return null;
        }
    }

    /**
     * 异步GET请求
     */
    public static void asyGet(String url, MyCallback callback) {
        try {
            Request request = new Request.Builder()
                    .addHeader("Auth", SPDataUtils.get(App.mContext) == null ? "null" : SPDataUtils.get(App.mContext))
                    .addHeader("Connection", "close")
                    .url(BASE_URL + url)
                    .build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            Log.d("okhttp", "ok http is error");
            e.printStackTrace();
        }
    }

    /**
     * 异步POST请求
     */
    public static void asyPost(String url, Map<Object, Object> params, MyCallback callback) {
        try {

            String jsonStr = JSON.toJSONString(params);
            RequestBody body = FormBody.create(MediaType.parse("application/json"), jsonStr);
            Request request = new Request.Builder()
                    .url(BASE_URL + url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Connection", "close")
                    .addHeader("Auth", SPDataUtils.get(App.mContext) == null ? "null" : SPDataUtils.get(App.mContext))
                    .post(body)
                    .build();

            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            Log.d("okhttp", "ok http is error");
            e.printStackTrace();
        }
    }

    /**
     * 处理响应数据
     * 返回data体中的json字符串
     */
    @SneakyThrows
    public static String dealData(Response response) {
//        Log.d("okhttp", "1111"+response);

        assert response.body() != null;
//        if (response.body()==null) {
//            return "{}";
//        }
        String responseString = response.body().string();
        Log.d("okhttp", responseString);
        Map<String, String> responseMap = JSON.parseObject(responseString, new TypeReference<HashMap<String, String>>() {
        });
        String code = responseMap.get("code");
        if (!"200".equals(code)) {
            Toast.makeText(App.mContext, responseMap.get("msg"), Toast.LENGTH_SHORT).show();
//            throw new RuntimeException(responseMap.get("msg"));
        }
        return responseMap.get("data");
    }


//    //文件上传,同步类型
//    public static String fileUpload(String url, File file,Map params){
//        try{
//            String jsonStr=JSON.toJSONString(params);
//            RequestBody fileBody=RequestBody.create(file,MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"));
//            MultipartBody multipartBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("jsonStr",jsonStr)
//                    .addFormDataPart("file",file.getName(),fileBody)
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(multipartBody)
//                    .build();
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        }catch (Exception e){
//            Log.d("ok http","ok http is error");
//            e.printStackTrace();
//            return "error";
//        }
//    }
//    返回主线程并执行更改UI操作
//    private void showResponse(final String string) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                //这里进行UI操作
//                mTv_responseText.setText(string);
//            }
//        });
//    }
}
