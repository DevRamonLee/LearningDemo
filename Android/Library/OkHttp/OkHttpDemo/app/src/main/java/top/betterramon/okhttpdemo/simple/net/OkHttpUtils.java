package top.betterramon.okhttpdemo.simple.net;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by limeng on 2019/3/31.
 * simple.net 包下的属于简化的封装版本，只能用于学习，不能用于生产
 */

public class OkHttpUtils {
    // OkHttpClient 需要是单例的
    private static OkHttpUtils mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;

    private Handler mHandler;

    static {
        mInstance = new OkHttpUtils();
    }

    private OkHttpUtils() {
        mHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = mHttpClient.newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10,TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mGson = new Gson();

        mHandler = new Handler();
    }

    public static OkHttpUtils getInstance() {
        return mInstance;
    }

    public void doRequest(final Request request, final BaseCallBack baseCallBack) {
        // 在请求之前做一些处理，比如显示进度条
        baseCallBack.onBeforeRequest(request);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                baseCallBack.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    // 根据类型判断
                    if (baseCallBack.mType == String.class) {
                        /*baseCallBack.onSuccess(response, resultStr);*/
                        callBackSuccess(baseCallBack, response, resultStr);
                    } else {
                        try {
                            Object obj = mGson.fromJson(resultStr, baseCallBack.mType);
                            /*baseCallBack.onSuccess(response, obj);*/
                            callBackSuccess(baseCallBack, response, obj);
                        } catch (JsonParseException e) {
                            // json 解析错误
                            /*baseCallBack.onError(response, response.code(), e);*/
                            callBackError(baseCallBack, response, e);
                        }
                    }
                } else {
                    // 请求出现错误
                    /*baseCallBack.onError(response, response.code(), null);*/
                    callBackError(baseCallBack, response, null);
                }
            }
        });
    }

    public void  get(String url, BaseCallBack callback) {
        Request request = buildRequest(url, HttpMethodType.GET, null);
        doRequest(request, callback);
    }

    public void post(String url, Map<String, Object> params, BaseCallBack callBack) {
        Request request = buildRequest(url, HttpMethodType.POST, params);
        doRequest(request, callBack);
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

    // 使用 handler 进行 UI 的操作
    private void callBackSuccess(final BaseCallBack baseCallBack, final Response response, final Object obj) {
        mHandler.post(new Runnable(){
            @Override
            public void run() {
                baseCallBack.onSuccess(response, obj);
            }
        });
    }

    private void callBackError(final BaseCallBack baseCallBack, final Response response, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onError(response, response.code(), e);
            }
        });
    }
}
