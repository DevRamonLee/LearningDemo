package top.betterramon.okhttpdemo2.net;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
final public class UrlLib {
    private static UrlLib mInstance;

    private UrlLib() {}

    // 双重检测的单例模式
    public static UrlLib getInstance() {
        if(mInstance == null) {
            synchronized (UrlLib.class) {
                if (mInstance == null) {
                    mInstance = new UrlLib();
                }
            }
        }
        return mInstance;
    }

    public <T> void buildRequest(String url, Class<T> cls, ILoader.Callback<T> call, Map<String, String> args) {
        FormBody body = buildRequestBody(args);
        request(url, body, cls, call);
    }

    private <T> void request(String url, RequestBody body, Class<T> cls, ILoader.Callback<T> callback) {
        DataLoader<T> dataLoader = new DataLoader<>();
        dataLoader.url(url);
        dataLoader.requestBody(body);
        dataLoader.cls(cls);
        dataLoader.executeFction(ILoader.EXECUTE_POST_BODY);
        dataLoader.callback(callback);
        dataLoader.start();
    }

    public <T> void buildRequest(String url, Class<T> cls, ILoader.Callback<T> call, String json) {
        request(url, cls, call, json);
    }

    private <T> void request(String url, Class<T> cls, ILoader.Callback<T> callback, String json) {
        DataLoader<T> dataLoader = new DataLoader<>();
        dataLoader.url(url);
        dataLoader.json(json);
        dataLoader.cls(cls);
        dataLoader.executeFction(ILoader.EXECUTE_POST_JSON);
        dataLoader.callback(callback);
        dataLoader.start();
    }

    public <T> void buildRequest(String url, Class<T> cls, ILoader.Callback<T> call) {
        request(url, cls, call);
    }

    private <T> void request(String url, Class<T> cls, ILoader.Callback<T> callback) {
        DataLoader<T> dataLoader = new DataLoader<>();
        dataLoader.url(url);
        dataLoader.cls(cls);
        dataLoader.executeFction(ILoader.EXECUTE_GET_BODY);
        dataLoader.callback(callback);
        dataLoader.start();
    }

    public <T> void buildUpload(String url, Class<T> cls, ILoader.Callback<T> call, String path) {
        DataLoader<T> dataLoader = new DataLoader<>();
        dataLoader.url(url);
        dataLoader.path(path);
        dataLoader.cls(cls);
        dataLoader.executeFction(ILoader.EXECUTE_POST_IMAGE_UPLOADING);
        dataLoader.callback(call);
        dataLoader.start();
    }

    @NonNull
    private FormBody buildRequestBody(Map<String, String> args) {
        // 创建一个 FormBody 对象
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> body : args.entrySet()) {
            // 遍历 map，将键值对加入到 FormBody 对象中
            builder.add(body.getKey(), body.getValue());
        }
        return builder.build();
    }

    // 取消请求
    public void cancelCall(String... tags) {
        HttpLoader.getInstace().cancelCall(tags);
    }
}
