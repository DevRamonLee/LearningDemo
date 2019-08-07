- http://www.ymapk.com/article-250-1.html(转载自此文)
- [官网介绍](https://square.github.io/okhttp/)
- [OkHttp Wiki 文档](https://github.com/square/okhttp/wiki/Calls)

## 简介

OkHttp 是一个精巧的网络请求库，它有如下特性：

- 支持 http2, 对一台机器的所有请求共享同一个 socket
- 内置连接池，支持连接复用，减少延迟（如果 HTTP2 不可用）
- 支持透明的 gzip 压缩响应体
- 通过缓存避免重复的请求
- 请求失败时自动重试主机的其他 ip，自动重定向



## Android Studio 引入 OkHttp3

首先在 Android Studio 的 gradle 中进行如下的配置：
```
implementation("com.squareup.okhttp3:okhttp:3.10.0")
```
添加网络权限
```
<uses-permission android:name="android.permission.INTERNET"/>
```
## 基本使用

- **OkHttp 的 get 请求**

OkHttp 默认是 Get 请求：
```
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
```

`client.newCall(request).execute()` 是个同步的方法, 在调用它的线程中执行，所以不能直接在 UI 线程中使用它，需要新建子线程。

OkHttpClient 同样支持异步的请求方法 `client.newCall(request).enqueue(new  Callback() ...)`  调用 enqueue 方法并且传递一个回调接口的实例，代码如下：

```
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
```

这个方法是异步请求的（会自动创建子线程），我们不需要额外再给它创建一个子线程。


- **Post 提交 Json 数据**

```
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
```

> [关于MediaType的详细介绍](https://www.jianshu.com/p/4721d7b5e780)


- **Post 提交键值对**，很多时候我们需要通过 post 把键值对传给服务器

```
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
```


- **异步上传文件**
```
// 定义上传类型为文件
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
```
添加读写权限
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

- **提取响应头**
```
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
```

提取结果
```
D/OKHttpActivityTag: Server: GitHub.com
D/OKHttpActivityTag: Date: Sun, 31 Mar 2019 08:41:08 GMT
D/OKHttpActivityTag: Vary: [Accept, Accept-Encoding]
```

- **Post 方式提交 String**

使用 HTTP post 方式提交一个 String 类型的 markdown 文档到 web 服务，以 Html 方式渲染 Markdown.
```
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
```
运行结果：
```
D/OKHttpActivityTag: postString<hr>
                     <ul>
                     <li>number one</li
                     <li>number two</li
                     </ul>
```

- **Post 方式提交流**

以流的方式 POST 提交请求体，请求体内容由流写入产生，这里使用的是 Okio 的 BufferedSink。
```
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
```

- **POST 方式提交表单**

```
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
```

- **Post 方式提交分块请求**

MultipartBody 可以构建复杂的请求体，多块请求体中每块都是一个请求体，可以定义自己的请求头，这些请求头可以用来描述这块请求。

```
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
```

- **响应缓存**

为了实现响应缓存，你需要做到以下几点：

- 1.首先需要一个可以读写的缓存目录并且设置缓存大小(注意这个缓存目录应该是私有的，不信任的程序不能访问)
- 2.一个缓存目录不能拥有多个缓存访问。大多数程序只需要调用一次 `new OkHttpClient()`, 在第一次调用时配置好缓存，然后其他地方调用这个缓存实例即可。不然多次创建 `HttpClient` 可能会导致混乱，应用崩溃。
- 3.在请求头中配置 HTTP 响应缓存 `Cache-Control: max-stale=3600` OkHttp缓存会支持。你的服务通过响应头确定响应缓存多长时间，例如使用`Cache-Control: max-age=9600`

```
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
```

- **超时**

没有响应时使用超时结束 call，OkHttp 支持**连接、读取和写入超时**。

```
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
```

#### 遇到的问题

- **问题1**`java.net.ProtocolException: Expected ':status' header not present`

解决方法：更新 Okhttp 到版本 3.10.0 解决了。