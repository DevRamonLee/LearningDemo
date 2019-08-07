package top.betterramon.okhttpdemo2.net;

import android.os.SystemClock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.betterramon.okhttpdemo2.Program;
import top.betterramon.okhttpdemo2.bean.UploadImageKey;
import top.betterramon.okhttpdemo2.conf.Consts;
import top.betterramon.okhttpdemo2.conf.Url;
import top.betterramon.okhttpdemo2.utils.AppUtil;
import top.betterramon.okhttpdemo2.utils.GsonTools;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class HttpLoader {
    private static OkHttpClient mOkHttpClient;
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8"); //json 类型
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png"); //图片类型
    private String mAppVersion = AppUtil.getVersionName(Program.application);

    private HttpLoader() {
    }

    private static HttpLoader INSTANCE;

    public static HttpLoader getInstace() {
        if (INSTANCE == null) {
            synchronized (HttpLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpLoader();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 返回当前环境Cookies
     *
     * @return
     */
    public List<Cookie> getCookies() {
        return mOkHttpClient.cookieJar().loadForRequest(HttpUrl.parse(Url.BASE_URL));
    }

    // 获取 OkHttpClient 对象
    public OkHttpClient getmOkHttpClient(InputStream... certificates) {
        if (mOkHttpClient == null) {
            synchronized (HttpLoader.class) {
                // 使用枚举来实现单例
                mOkHttpClient = OkHttpFactory.INSTANCE.getOkHttpClient(certificates);
            }
        }
        return mOkHttpClient;
    }

    //GET 同步请求
    public String executeGet(String url) {
        Request request = new Request
                .Builder()
                .addHeader(Consts.APP_VERSION, mAppVersion)
                .addHeader("Cache-Control", "no-cache, no-store, must-revalidate")
                .addHeader("Pragma", "no-cache")
                .addHeader("Expires", "0")
                .url(url)
                .tag(url)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
            }
        } catch (IOException e) {
        }
        return null;
    }

    // POST 同步请求提交Json数据
    public String executePostJson(String url, String json) {
        //增加locale
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .addHeader(Consts.APP_VERSION, mAppVersion)
                .url(url)
                .tag(url)
                .post(body)
                .build();

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // POST 同步请求提交键值对;
    public <T> String executePostBody(String url, RequestBody body) {
        Request request = new Request.Builder()
                .addHeader(Consts.APP_VERSION, mAppVersion)
                .url(url)
                .tag(url)
                .post(body)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //POST 同步请求提交键值对;
    public String executePostMap(String url, ConcurrentHashMap<String, String> map) {
        RequestBody body = BuilderBody(map);
        Request request = new Request.Builder()
                .addHeader(Consts.APP_VERSION, mAppVersion)
                .url(url)
                .tag(url)
                .post(body)
                .build();

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }

        } catch (IOException e) {
        }

        return null;
    }


    private static Map<String, String> images;
    private List<String> imagesList;

    //多图片上传
    public List<String> executePostImages(String url, final List<String> paths) {
        imagesList = new ArrayList<>(paths.size());

        final AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < paths.size(); i++) {
            count.incrementAndGet();
            final String oldPath = paths.get(i);
            RequestBody body = MultipartBuilderBody(oldPath);
            final Request request = new Request.Builder()
                    .addHeader(Consts.APP_VERSION, mAppVersion)
                    .url(url)
                    .tag(url)
                    .post(body)
                    .build();

            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    count.decrementAndGet();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        imagesList.add(response.body().string());
                    } else {
                    }

                    count.decrementAndGet();
                }
            });
        }

        while (count.get() > 0) {
            SystemClock.sleep(1000);
        }

        return imagesList;
    }

    /**
     * 多图上传
     *
     * @return
     */
    public List<String> executePostImagesNewApi(String url, ConcurrentHashMap<String, String> map, final List<String> paths) {
        final List<String> fileBeanList = new ArrayList<>(paths.size());

        final AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < paths.size(); i++) {
            count.incrementAndGet();
            final String oldPath = paths.get(i);
            RequestBody body = MultipartBuilderBody(oldPath, map);

            final Request request = new Request.Builder()
                    .addHeader(Consts.APP_VERSION, mAppVersion)
                    .url(url)
                    .tag(url)
                    .post(body)
                    .build();

            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    count.decrementAndGet();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        fileBeanList.add(response.body().string());
                    } else {
                    }
                    count.decrementAndGet();
                }
            });
        }

        while (count.get() > 0) {
            SystemClock.sleep(1000);
        }

        return fileBeanList;
    }

    public Map<String, String> executePostImagesKey(String url, final HashMap<String, String> paths) {
        images = new ConcurrentHashMap<>();

        final AtomicInteger count = new AtomicInteger(0);

        for (final Map.Entry<String, String> entry : paths.entrySet()) {
            count.incrementAndGet();
            RequestBody body = MultipartBuilderBodyKey(entry.getValue(), entry.getKey());
            final Request request = new Request.Builder()
                    .addHeader(Consts.APP_VERSION, mAppVersion)
                    .url(url + "?key=" + entry.getKey())
                    .tag(url)
                    .post(body)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    count.decrementAndGet();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String path = response.body().string();
                        UploadImageKey uploadImageKey = GsonTools.changeGsonToBean(path, UploadImageKey.class);
                        UploadImageKey.Data data = uploadImageKey.data;
                        images.put(data.key, data.url);
                    } else {
                    }
                    count.decrementAndGet();
                }
            });
        }
        while (count.get() > 0) {
            SystemClock.sleep(1000);
        }
        return images;
    }

    //单图上传（头像）
    public String executePostImage(String url, String path) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("file", path, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .addHeader(Consts.APP_VERSION, mAppVersion)
                .url(url)
                .tag(url)
                .post(body)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
        }

        return "";
    }

    private RequestBody BuilderBody(ConcurrentHashMap<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = map.get(key);
            if (value == null)
                break;
            builder.add(key, value);
        }

        return builder.build();
    }

    private RequestBody MultipartBuilderBody(String path, ConcurrentHashMap<String, String> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", path, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        for (String key : map.keySet()) {
            builder.addFormDataPart(key, map.get(key));
        }
        return builder.build();
    }

    private RequestBody MultipartBuilderBody(String path) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", path, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        return builder.build();
    }

    private RequestBody MultipartBuilderBodyKey(String path, String key) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", path, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        return builder.build();
    }

    public void cancelCall(String... tags) {
        if (tags == null || tags.length == 0) return;
        Dispatcher dispatcher = mOkHttpClient.dispatcher();

        List<String> tagList = Arrays.asList(tags);

        for (String tag : tagList) {
            for (Call call : dispatcher.queuedCalls()) {
                boolean equals = call.request().tag().equals(tag);
                if (equals) {
                    call.cancel();
                }
            }

            for (Call call : dispatcher.runningCalls()) {
                boolean equals = call.request().tag().equals(tag);
                if (equals) {
                    call.cancel();
                }
            }
        }
    }
}
