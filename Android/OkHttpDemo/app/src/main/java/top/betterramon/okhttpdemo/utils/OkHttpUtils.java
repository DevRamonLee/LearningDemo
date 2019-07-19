package top.betterramon.okhttpdemo.utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by limeng on 2019/3/31.
 */

public class OkHttpUtils {
    // OkHttpClient 需要是单例的
    private static OkHttpUtils mInstance;
    private OkHttpClient mHttpClient;

    private OkHttpUtils() {}

    public static OkHttpUtils getInstance() {
        return mInstance;
    }

    public void doRequest(final Request request) {
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void get(String url) {
        Request request = buildRequest(url, HttpMethodType.GET, null);
        //doRequest(request, callback);
    }

    public void post(String url, Map<String, Object> params) {
        Request request = buildRequest(url, HttpMethodType.POST, params);
        //doRequest(request, callback);
    }

    private RequestBody builderFormDate(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for(Map.Entry<String, Object> entry : params.entrySet()) {
                builder.add(entry.getKey(), (String)entry.getValue());
            }
        }
        return builder.build();
    }

    enum HttpMethodType {
        GET,
        POST,
    }

    private Request buildRequest(String url,HttpMethodType methodType, Map<String, Object> params) {
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (methodType == HttpMethodType.POST) {
            RequestBody body = builderFormDate(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }
        return builder.build();
    }



}
