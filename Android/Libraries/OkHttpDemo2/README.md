- [PersistentCookieJar Github 地址](https://github.com/franmontiel/PersistentCookieJar)
- [添加okhttp的https](https://www.jianshu.com/p/98db08fcf70d)
- [给安卓OKHTTP库添加HTTPS证书验证](https://blog.csdn.net/u014653815/article/details/83754057)
- [Android Https相关完全解析 当OkHttp遇到Https](https://blog.csdn.net/lmj623565791/article/details/48129405)


在一个项目中如何去封装 Okhttp ，使得其简单易用呢，接下来我们来进行一步步的分解。当然第一步，我们需要引入必要的依赖

```
implementation 'com.squareup.okhttp3:okhttp:3.11.0'
implementation 'com.google.code.gson:gson:2.8.5'
```

> [android gradle依赖：implementation 和compile的区别](https://www.jianshu.com/p/f34c179bc9d0)

#### Okhttp 项目封装

创建一个 `UrlLib.java` 实现一个单例模式

```
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
}
```

接下来我创建一个 `ILoader.java` 接口,接口中我们定义了一个标志请求类型的变量 `EXECUTE_POST_BODY`,用于标志我们请求的方式，这里可以扩展其他的一些方式，这里还定义了一些方法和一个内部接口（这个内部接口用于请求结果的回调）

```
public interface ILoader<T> {
    int EXECUTE_POST_BODY = 10002;  //POST 请求提交键值对Bean;

    ILoader path(String path);
    ILoader url(String url);
    ILoader list(List<String> list);
    ILoader map(ConcurrentHashMap<String, String> map);
    ILoader cls(Class<T> cls);
    ILoader requestBody(RequestBody body);
    ILoader json(String json);
    ILoader executeFction(int fction);
    ILoader delayed(long time);
    Thread callback(Callback<T> call);

    interface Callback<T> {
        @SuppressWarnings("unchecked")
        void onResult(T data);
        void onError(Error error);
    }
}
```

为了发送请求，首先我们需要拼接好我们的请求参数，我们在 `UrlLib.java` 中增加如下方法：

```
public <T> void buildRequest(String url, Class<T> cls, ILoader.Callback<T> call, Map<String, String> args) {
    FormBody body = buildRequestBody(args);
    request(url, body, cls, call);
}

private <T> void request(String url, RequestBody body, Class<T> cls, ILoader.Callback<T> callback) {
    // 发送请求
}

private FormBody buildRequestBody(Map<String, String> args) {
    // 创建一个 FormBody 对象
    FormBody.Builder builder = new FormBody.Builder();
    for (Map.Entry<String, String> body : args.entrySet()) {
        // 遍历 map，将键值对加入到 FormBody 对象中
        builder.add(body.getKey(), body.getValue());
    }
    return builder.build();
}
```

上面的代码功能很简单， `buildRequest` 方法里面把 Map 键值对参数转换为了 FormBody 对象，也就是说请求体我们已经创建好了，再来看看 `buildRequest` 其他的几个参数

- `url`: 请求的地址
- `Class<T> cls`: 实体类的类类型，用于后面 Gson 库转换 json 字符串为实体
- `ILoader.Callback<T> call`： 我们刚才在上面定义的接口，用于请求结果的回调
- `Map<String, String> args`： 我们请求的键值对参数


参数已经封装好了，我们需要使用参数去发送请求了，在此之前，我们需要创建一个类，它用于对我们的请求类型进行分发，创建 `DataLoader.java` 文件,在创建这个文件之前，由于请求完成后需要对 json 数据转换为实体类，为了避免影响 UI 线程，我们决定把这些操作放在子线程中执行。我们创建一个 `Loader.java` 它继承自 Thread 类

```
public abstract class Loader extends Thread {
    @Override
    public void run() {
        execute();
    }
    protected abstract void execute();
}
```

接下来可以去实现 `DataLoader.java` 类了

```
public class DataLoader<T> extends Loader implements ILoader<T> {

    public String mUrl;     // mUrl
    public ConcurrentHashMap<String, String> mLinkedHashMap;    // post 请求的 key value;
    public RequestBody mBody;   // post 请求的 body
    public List<String> mList;  // post 请求的 list
    public String mJson;    // post json 数据
    public Class<T> mCls;   // 返回的bean
    public int mExecuteFction;  // 根据状态执行方法
    private Callback<T> mCall;  //回调
    private long mDelayed = -1; //延迟时间回调
    private String mPath;   //文件,图片路径（单图）

    public DataLoader() {
    }

    /**
     * 请求网络 根据状态调用不同的方法
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        T t = null;
        // 检查网络状况
        if (NetUtils.hasNetwork(Program.getAppContext())) {
            switch (mExecuteFction) {
                case EXECUTE_POST_BODY:
                    t = httpsPostResponseBean(mUrl, mBody, mCls);
                    break;
                // 这里根据不同的请求类型去请求
                }

                default:
                    throw new IllegalArgumentException("ExecuteAction not be empty");
            }
            // 回调不能为空
            if (mCall == null)
                throw new IllegalArgumentException("Callback can not be empty");
        }

        final T finalT = t;
        if (mDelayed != -1) {
            SystemClock.sleep(mDelayed);
        }

        // UI 线程去更新
        Utils.post(new Runnable() {
            @Override
            public void run() {
                if (finalT != null) {
                    result(finalT);
                } else {
                    returnNetError();
                }
            }
        });
    }

    private void result(T finalT) {
        if (finalT instanceof BaseDataBean) {
            BaseDataBean data = (BaseDataBean) finalT;
            switch (data.code) {
                case 0:
                    mCall.onResult(finalT);
                    break;
                case -2:
                    // 未登录
                    break;
                default:
                    returnError(data);
                    break;
            }
        } else if (finalT instanceof Map) {
            mCall.onResult(finalT);
        } else if (finalT instanceof List) {
            mCall.onResult(finalT);
        }
    }

    private void returnNetError() {
        Error error = new Error();
        error.error = Utils.getStringXml(R.string.net_error);
        mCall.onError(error);
    }

    private void returnError(BaseDataBean finalT) {
        Error error = new Error(finalT.message);
        error.code = finalT.code;
        if (error.code == -1 && error.error.contains("Exception")) {
            error.error = Utils.getStringXml(R.string.system_exception);
        }
        mCall.onError(error);
    }

    //POST 请求 Bean
    public <T> T httpsPostResponseBean(String url, RequestBody request, Class<T> cls) {
        String result;
        // 真正去发送请求
        result = HttpLoader.getInstace().executePostBody(url, request);
        if (!TextUtils.isEmpty(result)) {
            // 将 json 转化为实体类
            T t = GsonTools.changeGsonToBean(result, cls);
            return t;
        } else {
            return null;
        }
    }

    @Override
    public ILoader path(String path) {
        mPath = path;
        return this;
    }

    @Override
    public ILoader url(String url) {
        // 可以在这里对 url 进行过滤或者处理
        mUrl = url;
        return this;
    }

    @Override
    public ILoader list(List<String> list) {
        mList = list;
        return this;
    }


    @Override
    public ILoader map(ConcurrentHashMap<String, String> map) {
        mLinkedHashMap = map;
        return this;
    }

    @Override
    public ILoader cls(Class<T> cls) {
        mCls = cls;
        return this;
    }

    @Override
    public ILoader requestBody(RequestBody body) {
        mBody = body;
        return this;
    }

    @Override
    public ILoader json(String json) {
        mJson = json;
        return this;
    }

    @Override
    public ILoader executeFction(int fction) {
        mExecuteFction = fction;
        return this;
    }

    @Override
    public ILoader delayed(long time) {
        mDelayed = time;
        return this;
    }

    @Override
    public Thread callback(Callback<T> call) {
        mCall = call;
        return this;
    }
}
```

网络判断和 GsonTool 的代码这里不给出（后面有 Demo 地址），这个类其实就是创建了一个线程，根据我们请求的类型和参数去调用 `HttpLoader.java` 这个类去发送请求，回到前面的 `request` 方法，我们现在可以把它补充完整了

```
private <T> void request(String url, RequestBody body, Class<T> cls, ILoader.Callback<T> callback) {
    DataLoader<T> dataLoader = new DataLoader<>();
    dataLoader.url(url);
    dataLoader.requestBody(body);
    dataLoader.cls(cls);
    dataLoader.executeFction(ILoader.EXECUTE_POST_BODY);
    dataLoader.callback(callback);
    dataLoader.start();
}
```

我们真正发送请求的代码是 `HttpLoader.getInstace().executePostBody(url, request);` 下面我们来看看怎么实现这个 `HttpLoader.java` 类

```
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
}
```

我们主要关注这行代码 `OkHttpFactory.INSTANCE.getOkHttpClient(certificates);` 这里我们创建了我们的 `OkhttpClient` 对象并对它进行了配置

```
enum OkHttpFactory {

    INSTANCE;

    public static final int TIME_OUT = 5;
    private final OkHttpClient okHttpClient;
    private InputStream[] certificates;

    OkHttpFactory() {
        //缓存目录
        Cache cache = new Cache(Program.getAppContext().getCacheDir(), 10 * 1024 * 1024);

        ClearableCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(),
                new SharedPrefsCookiePersistor(Program.getAppContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, null, null);
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CacheInterceptor())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)     // 连接超时
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)       // 写入超时
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)        // 读取超时
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(cookieJar)
                .build();
    }

    public OkHttpClient getOkHttpClient(InputStream... certificates) {
        this.certificates = certificates;
        return okHttpClient;
    }

    public OkHttpClient getOkHttpClient() {
        return getOkHttpClient(new InputStream[]{});
    }
}
```

这里我们配置了 Cookie 缓存、超时、https 支持、拦截器，相关用法可以参考下面的文章：

- [PersistentCookieJar Github 地址](https://github.com/franmontiel/PersistentCookieJar)
- [Android Https相关完全解析 当OkHttp遇到Https](https://blog.csdn.net/lmj623565791/article/details/48129405)


到现在为止整体的流程已经基本有了，但是我们还没有让 `OkHttpClient` 对象初始化，我们在 `Application.java` 文件中对它进行初始化

```
public class Program extends Application {
    private static Program appContext;
    public static Application application = null;
    public static Handler handler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        application = this;
        handler = new Handler();    // 默认获取所在线程的 looper
        HttpLoader.getInstace().getmOkHttpClient(); // 初始化 OkHttpClient
    }

    public static Program getAppContext() {
        return appContext;
    }
}
```


现在我们就可以使用我们封装的这个库了，我们使用『wanandroid』的 api，获取友链内容

Url.java

```
public interface Url {
    String BASE_URL = "https://www.wanandroid.com";
    String FRIENDS = BASE_URL + "/friend/json";
}
```

发送一个 get 请求

```
UrlLib.getInstance().buildRequest(Url.FRIENDS, FriendsList.class, new ILoader.Callback<FriendsList>() {
    @Override
    public void onResult(FriendsList data) {
        for(Friend friend : data.getData()) {
            Log.i("MainActivity", "Friend name is: " + friend.getName());
        }
    }

    @Override
    public void onError(Error error) {

    }
});
```

运行结果：

```
Friend name is: 国内大牛博客集合
Friend name is: 国外大牛博客集合
Friend name is: jcenter仓库
Friend name is: google仓库
Friend name is: maven仓库
Friend name is: 鸿洋的博客
Friend name is: 郭霖的博客
Friend name is: 今天最火的开源项目
Friend name is: 最新项目
Friend name is: stackoverflow
Friend name is: 鸿洋公众号文章聚合
Friend name is: 干货集中营
Friend name is: 掘金
Friend name is: 开发者头条
...
```


