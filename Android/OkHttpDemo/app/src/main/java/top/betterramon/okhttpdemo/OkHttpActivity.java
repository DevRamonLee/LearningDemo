package top.betterramon.okhttpdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OkHttpActivity extends AppCompatActivity {

    private static final String TAG = "OKHttpActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        // 同步方法 execute()
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //runSynGet("https://wanandroid.com/wxarticle/chapters/json"); // 同步 get 请求
                    //postJson("http://write.blog.csdn.net/postlist/0/0/enabled/1", "Ramon"); // post 提交 json 数据
                    getRespoonseHeader(); // 提取请求头
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

        // 异步方法 enqueue()
        try {
            //runAsyncGet("https://wanandroid.com/wxarticle/chapters/json");
            //postFile();
            //postString();
            //postStream();
            //postForm();
            //postMultipartBody();
            //cache();
            configTimeouts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // execute 是个同步请求的方法
    private void runSynGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        // Java 7 try-with-resources 可以自动关闭资源
        try(Response response = client.newCall(request).execute()) {
            Log.i(TAG, "runSynGet " + response.body().string());
        }
    }

    // 异步请求方法 enqueue
    private void runAsyncGet(String url) throws  IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG, "runAsyncGet" + json);
            }
        });
    }

    // POST 提交 JSON 数据
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private void postJson(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Log.d(TAG, "postJson : " + response.body().string());
        }
    }



    // 异步上传文件
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private void postFile() throws IOException {
        OkHttpClient client = new OkHttpClient();
        File file = new File("/sdcard/demo.txt");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, " postFile : " + response.body().string());
            }
        });
    }

    // 提取响应头
    private void getRespoonseHeader() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Response response = client.newCall(request).execute();
        if(!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        Log.d(TAG, "Server: " + response.header("Server"));
        Log.d(TAG, "Date: " + response.header("Date"));
        Log.d(TAG, "Vary: " + response.headers("Vary"));
    }

    // post 方式提交 String
    private void postString() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String postBody = ""
                + "----\n"
                + "* number one\n"
                + "* number two\n";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "postString" + response.body().string());
            }
        });
    }

    // post 方式提交流
    private void postStream() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("------\n");
                for (int i = 2; i < 100; i++) {
                    sink.writeUtf8(String.format("* %s = %s\n",i, factor(i)));
                }
            }

            // 素数分解
            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n /i;
                    if (x * i == n) return factor(x) + " X " + i;
                }
                return Integer.toString(n);
            }
        };

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "postStream" + response.body().string());
            }
        });
    }

    // 通过 Post 方式提交表单
    private void postForm() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();

        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, " postForm : " + response.body().string());
            }
        });
    }

    // post 提交分块请求，MultipartBody 实现同时上传文件和参数
    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private void postMultipartBody() throws IOException {
        OkHttpClient client = new OkHttpClient();
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        MultipartBody body = new MultipartBody.Builder("AaBO3x")
                .setType(MultipartBody.FORM)
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"title\""),
                        RequestBody.create(null,"Square Logo"))
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"image\""),
                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, " postMultipartBody : " + response.body().string());
            }
        });
    }

    // 响应缓存
    public void cache() throws IOException {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "response cache response " + response.cacheResponse());
                Log.i(TAG, "response network response " + response.networkResponse());
            }
        });
    }

    // 配置超时
    private void configTimeouts() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Response : " + response);
            }
        });
    }
}
