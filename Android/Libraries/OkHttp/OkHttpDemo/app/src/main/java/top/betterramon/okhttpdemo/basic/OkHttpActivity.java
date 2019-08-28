package top.betterramon.okhttpdemo.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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
import top.betterramon.okhttpdemo.R;
import top.betterramon.okhttpdemo.simple.net.SimpleNetTestActivity;

/***
 * OKHttp 的基本使用方法
 */
public class OkHttpActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpActivity";

    private static final String BASE_URL = "http://write.blog.csdn.net/postlist/0/0/enabled/1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        // 同步请求 execute
        findViewById(R.id.execute_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 需要在子线程中进行
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            runSynGet(BASE_URL);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // 异步请求 enqueue
        findViewById(R.id.enqueue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runAsyncGet(BASE_URL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // post 提交 json 数据
        findViewById(R.id.post_json_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            postJson("http://write.blog.csdn.net/postlist/0/0/enabled/1", "Ramon");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // post 提交键值对
        findViewById(R.id.post_pair_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postPair("http://write.blog.csdn.net/postlist/0/0/enabled/1");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // post 异步上传文件
        findViewById(R.id.post_file_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // 提取响应头
        findViewById(R.id.response_header_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getRespoonseHeader();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

       // post 方式提交 String
        findViewById(R.id.post_string_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // post 方式提交流
        findViewById(R.id.post_stream_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // post 方式提交键值对
        findViewById(R.id.post_form_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postForm();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // post 分块请求
        findViewById(R.id.post_multi_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postMultipartBody();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // 响应缓存
        findViewById(R.id.cache_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cache();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // 配置超时
        findViewById(R.id.timeout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    configTimeouts();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // 简易封装库
        findViewById(R.id.simple_lib_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OkHttpActivity.this, SimpleNetTestActivity.class);
                startActivity(intent);
            }
        });
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

    // POST 提交键值对
    private void postPair(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("name","Ramon")
                .add("occupation","Android")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i(TAG, "postPair" + str);
            }
        });
    }

    // 异步上传文件
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");    // 定义上传文件的类型
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

    // post 方式提交 String, 这种 body 存放在内存中，不适合上传较大的文件
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

    // 通过 Post 方式提交表单，也就是提交键值对
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

    // 配置超时,连接、读取和写入超时
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
