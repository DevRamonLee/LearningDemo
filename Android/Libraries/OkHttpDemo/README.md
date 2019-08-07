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


- http://www.ymapk.com/article-250-1.html(转载自此文)
- [官网介绍](https://square.github.io/okhttp/)
- [OkHttp Wiki 文档](https://github.com/square/okhttp/wiki/Calls)

## 简单封装

首先我们来新建一个 `OkHttpUtils.java` 文件，一个应用中，`OkHttpUtils` 需要是单例的，所以我们首先需要来实现一个单例

```
public class OkHttpUtils {
    // OkHttpClient 需要是单例的
    private static OkHttpUtils mInstance;
    private OkHttpClient mHttpClient;

    private OkHttpUtils() {}

    public static OkHttpUtils getInstance() {
        return mInstance;
    }
    
    ...
}
```

一般网络请求分为 get 和 post 请求两种，但无论哪种请求都是需要用到 request 的，所以我们首先封装一个 request,创建一个 doRequest 方法，在其内先编写 `mHttpClient.newCall(request).enqueue(new Callback())` 相关逻辑

```
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
```

我们需要自定义一个callback，BaseCallback,并将其传入request方法中

```
public abstract class BaseCallback  {

}
```

在 `OkHttpUtils.java` 中编写 get 和 post 方法

```
public void get(String url){


}

public void post(String url,Map<String,Object> param){


}
```

post 方法中构建 request 对象，这里我们需要创建一个 buildRequest 方法，用于生成 request 对象

```
private  Request buildRequest(String url,HttpMethodType methodType,Map<String,Object> params){
    return null;
}
```

这里需要定一个枚举对象 HttpMethodType，用于区分是 get 还是 post

```
enum  HttpMethodType {
    GET,
    POST
}
```

buildRequest 方法根据 HttpMethodType 不同有相应的逻辑处理

```
private  Request buildRequest(String url,HttpMethodType methodType,Map<String,Object> params){

    Request.Builder builder = new Request.Builder()
            .url(url);
    if (methodType == HttpMethodType.POST){

        builder.post(body);
    }
    else if(methodType == HttpMethodType.GET){
        builder.get();
    }
    return builder.build();
}
```

`builder.post()`方法中需要一个 body ,所以我们需要创建一个方法 `builderFormData()` 方法用于返回 RequestBody ,这里内部逻辑后面再进行完善

```
private RequestBody builderFormData(Map<String,Object> params){
    return null;
}
```

于是 `buildRequest()` 方法变成了这样

```
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
```

get 方法进行修改

```
public void get(String url,BaseCallback callback){
    Request request = buildRequest(url,HttpMethodType.GET,null);
    doRequest(request,callback);
}
```

post 方法进行修改

```
public void post(String url,Map<String,Object> params,BaseCallback callback){
    Request request = buildRequest(url,HttpMethodType.POST,params);
    doRequest(request,callback);
}
```

完善 `buildFormData()` 方法

```
private RequestBody builderFormData(Map<String,String> params){
    FormBody.Builder builder =  new FormBody.Builder();
    // 添加键值对
    if(params!=null){
        for(Map.Entry<String,String> entry:params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
    }
    return builder.build();
}
```

`BaseCallback` 中定义一个抽象方法 `onBeforeRequest`，这样做的理由是我们在加载网络数据成功前，一般都有进度条等显示，这个方法就是用来做这些处理的

```
public abstract class BaseCallback  {
    public  abstract void onBeforeRequest(Request request);
}
```

`OkHttpUtils` 的 `doRequest` 方法增加如下语句：

```
baseCallback.onBeforeRequest(request);
```

BaseCallback 中多定义2个抽象方法

```
public abstract  void onFailure(Request request, Exception e) ;

/**
 *请求成功时调用此方法
 * @param response
 */
public abstract  void onResponse(Response response);
```

由于 Response 的状态有多种，比如成功和失败，所以需要将 onResponse 分解为3个抽象方法

```
/**
 *
 * 状态码大于200，小于300 时调用此方法
 * @param response
 * @param t
 * @throws
 */
public abstract void onSuccess(Response response,T t) ;

/**
 * 状态码400，404，403，500等时调用此方法
 * @param response
 * @param code
 * @param e
 */
public abstract void onError(Response response, int code,Exception e) ;

/**
 * Token 验证失败。状态码401,402,403 等时调用此方法
 * @param response
 * @param code
 */
public abstract void onTokenError(Response response, int code);
```

`response.body.string()` 方法返回的都是 String 类型，而我们需要显示的数据其实是对象，所以我们就想抽取出方法，直接返回对象，由于我们不知道对象的类型是什么，所以我们在`BaseCallback` 中使用范型

```
public abstract class BaseCallback<T>  
```

BaseCallback 中需要将泛型转换为 Type，所以要声明 Type 类型

```
public   Type mType;
```

BaseCallback 中需要如下一段代码，将泛型 T 转换为 Type 类型

```
static Type getSuperClassTypeParameter(Class<?> subClass) {
    Type superClass = subClass.getGenericSuperclass();
    if (subClass instanceof  Class) {
        throw new RuntimeException("Missing type parameter");
    }
    ParameterizedType parameterized = (ParameterizedType) superClass;
    return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
}
```
这里 `$Gson$Types` 会提示 Can not resolved,我们需要添加 GSON 支持，首先在 Gradle 中导入依赖

```
implementation 'com.google.code.gson:gson:2.8.5'
```

然后在文件中导入这个类

```
import com.google.gson.internal.$Gson$Types;
```

在 BaseCallback 的构造函数中进行 mType 进行赋值

```
public BaseCallBack() {
    mType = getSuperClassTypeParameter(getClass());
}
```

`OkHttpUtils` 中 `doRequest`方法的 `onFailure` 与 `onResponse` 方法会相应的去调用 `baseCallback`的方法

```
mHttpClient.newCall(request).enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
        baseCallBack.onFailure(request, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            baseCallBack.onSuccess(response, null);
        } else {
            baseCallBack.onError(response, response.code(), null);
        }
    }
});
```

onResponse 方法中成功的情况又有区分，根据 mType 的类型不同有相应的处理逻辑，同时还要考虑 Gson 解析错误的情况

```
 @Override
public void onResponse(Call call, Response response) throws IOException {
    // 请求成功
    if (response.isSuccessful()) {
        String resultStr = response.body().string();
        // 根据类型判断
        if (baseCallBack.mType == String.class) {
            baseCallBack.onSuccess(response, resultStr);
        } else {
            try {
                Object obj = mGson.fromJson(resultStr, baseCallBack.mType);
                baseCallBack.onSuccess(response, obj);
            } catch (JsonParseException e) {
                // json 解析错误
                baseCallBack.onError(response, response.code(), e);
            }
        }
    } else {
        // 请求出现错误
        baseCallBack.onError(response, response.code(), null);
    }
}
```

构造函数中进行一些全局变量的初始化的操作，还有一些超时的设计

```
private OkHttpUtils() {
    mHttpClient = new OkHttpClient();
    OkHttpClient.Builder builder = mHttpClient.newBuilder();
    builder.connectTimeout(10, TimeUnit.SECONDS);
    builder.readTimeout(10,TimeUnit.SECONDS);
    builder.writeTimeout(30, TimeUnit.SECONDS);

    mGson = new Gson();
}
```

静态代码块初始化 OkHttpUtils 对象
```
static {
    mInstance = new OkHttpUtils();
}
```

在 okHttpUtils 内，需要创建 handler 进行 UI 界面的更新操作，创建 `callbackSuccess` 方法

```
// 使用 handler 进行 UI 的操作
private void callBackSuccess(final BaseCallBack baseCallBack, final Response response, final Object obj) {
    mHandler.post(new Runnable(){
        @Override
        public void run() {
            baseCallBack.onSuccess(response, obj);
        }
    });
}
```

这里我们需要在构造函数中创建 Handler

```
private Handler mHandler;
private OkHttpUtils() {
    ...
    mHandler = new Handler();
}
```

doRequest 的 onResponse 方法也需要进行改写

```
if (baseCallBack.mType == String.class) {
    /*baseCallBack.onSuccess(response, resultStr);*/
    callBackSuccess(baseCallBack, response, resultStr);
}
```

创建 callbackError 方法

```
private void callBackError(final BaseCallBack baseCallBack, final Response response, final Exception e) {
    mHandler.post(new Runnable() {
        @Override
        public void run() {
            baseCallBack.onError(response, response.code(), e);
        }
    });
}
```

将 `doRequest` 方法的 `onResponse` 方法中的 `baseCallback.onError(response,response.code(),e);` 替换为 `callbackError(baseCallback,response,e);` 方法

```
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
```

使用这个简易封装库

```
findViewById(R.id.get_test).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String url = "http://write.blog.csdn.net/postlist/0/0/enabled/1";
        OkHttpUtils.getInstance().get(url, new BaseCallBack<String>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onSuccess(Response response, String s) {
                getResultTv.setText(s);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onTokenError(Response response, int code) {

            }
        });
    }
});
```


#### 遇到的问题

- **问题1**`java.net.ProtocolException: Expected ':status' header not present`

解决方法：更新 Okhttp 到版本 3.10.0 解决了。