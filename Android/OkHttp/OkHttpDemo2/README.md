- [PersistentCookieJar Github ��ַ](https://github.com/franmontiel/PersistentCookieJar)
- [���okhttp��https](https://www.jianshu.com/p/98db08fcf70d)
- [����׿OKHTTP�����HTTPS֤����֤](https://blog.csdn.net/u014653815/article/details/83754057)
- [Android Https�����ȫ���� ��OkHttp����Https](https://blog.csdn.net/lmj623565791/article/details/48129405)


��һ����Ŀ�����ȥ��װ Okhttp ��ʹ����������أ�����������������һ�����ķֽ⡣��Ȼ��һ����������Ҫ�����Ҫ������

```
implementation 'com.squareup.okhttp3:okhttp:3.11.0'
implementation 'com.google.code.gson:gson:2.8.5'
```

> [android gradle������implementation ��compile������](https://www.jianshu.com/p/f34c179bc9d0)

#### Okhttp ��Ŀ��װ

����һ�� `UrlLib.java` ʵ��һ������ģʽ

```
final public class UrlLib {
    private static UrlLib mInstance;

    private UrlLib() {}

    // ˫�ؼ��ĵ���ģʽ
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

�������Ҵ���һ�� `ILoader.java` �ӿ�,�ӿ������Ƕ�����һ����־�������͵ı��� `EXECUTE_POST_BODY`,���ڱ�־��������ķ�ʽ�����������չ������һЩ��ʽ�����ﻹ������һЩ������һ���ڲ��ӿڣ�����ڲ��ӿ������������Ļص���

```
public interface ILoader<T> {
    int EXECUTE_POST_BODY = 10002;  //POST �����ύ��ֵ��Bean;

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

Ϊ�˷�����������������Ҫƴ�Ӻ����ǵ���������������� `UrlLib.java` ���������·�����

```
public <T> void buildRequest(String url, Class<T> cls, ILoader.Callback<T> call, Map<String, String> args) {
    FormBody body = buildRequestBody(args);
    request(url, body, cls, call);
}

private <T> void request(String url, RequestBody body, Class<T> cls, ILoader.Callback<T> callback) {
    // ��������
}

private FormBody buildRequestBody(Map<String, String> args) {
    // ����һ�� FormBody ����
    FormBody.Builder builder = new FormBody.Builder();
    for (Map.Entry<String, String> body : args.entrySet()) {
        // ���� map������ֵ�Լ��뵽 FormBody ������
        builder.add(body.getKey(), body.getValue());
    }
    return builder.build();
}
```

����Ĵ��빦�ܼܺ򵥣� `buildRequest` ��������� Map ��ֵ�Բ���ת��Ϊ�� FormBody ����Ҳ����˵�����������Ѿ��������ˣ��������� `buildRequest` �����ļ�������

- `url`: ����ĵ�ַ
- `Class<T> cls`: ʵ����������ͣ����ں��� Gson ��ת�� json �ַ���Ϊʵ��
- `ILoader.Callback<T> call`�� ���Ǹղ������涨��Ľӿڣ������������Ļص�
- `Map<String, String> args`�� ��������ļ�ֵ�Բ���


�����Ѿ���װ���ˣ�������Ҫʹ�ò���ȥ���������ˣ��ڴ�֮ǰ��������Ҫ����һ���࣬�����ڶ����ǵ��������ͽ��зַ������� `DataLoader.java` �ļ�,�ڴ�������ļ�֮ǰ������������ɺ���Ҫ�� json ����ת��Ϊʵ���࣬Ϊ�˱���Ӱ�� UI �̣߳����Ǿ�������Щ�����������߳���ִ�С����Ǵ���һ�� `Loader.java` ���̳��� Thread ��

```
public abstract class Loader extends Thread {
    @Override
    public void run() {
        execute();
    }
    protected abstract void execute();
}
```

����������ȥʵ�� `DataLoader.java` ����

```
public class DataLoader<T> extends Loader implements ILoader<T> {

    public String mUrl;     // mUrl
    public ConcurrentHashMap<String, String> mLinkedHashMap;    // post ����� key value;
    public RequestBody mBody;   // post ����� body
    public List<String> mList;  // post ����� list
    public String mJson;    // post json ����
    public Class<T> mCls;   // ���ص�bean
    public int mExecuteFction;  // ����״ִ̬�з���
    private Callback<T> mCall;  //�ص�
    private long mDelayed = -1; //�ӳ�ʱ��ص�
    private String mPath;   //�ļ�,ͼƬ·������ͼ��

    public DataLoader() {
    }

    /**
     * �������� ����״̬���ò�ͬ�ķ���
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        T t = null;
        // �������״��
        if (NetUtils.hasNetwork(Program.getAppContext())) {
            switch (mExecuteFction) {
                case EXECUTE_POST_BODY:
                    t = httpsPostResponseBean(mUrl, mBody, mCls);
                    break;
                // ������ݲ�ͬ����������ȥ����
                }

                default:
                    throw new IllegalArgumentException("ExecuteAction not be empty");
            }
            // �ص�����Ϊ��
            if (mCall == null)
                throw new IllegalArgumentException("Callback can not be empty");
        }

        final T finalT = t;
        if (mDelayed != -1) {
            SystemClock.sleep(mDelayed);
        }

        // UI �߳�ȥ����
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
                    // δ��¼
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

    //POST ���� Bean
    public <T> T httpsPostResponseBean(String url, RequestBody request, Class<T> cls) {
        String result;
        // ����ȥ��������
        result = HttpLoader.getInstace().executePostBody(url, request);
        if (!TextUtils.isEmpty(result)) {
            // �� json ת��Ϊʵ����
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
        // ����������� url ���й��˻��ߴ���
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

�����жϺ� GsonTool �Ĵ������ﲻ������������ Demo ��ַ�����������ʵ���Ǵ�����һ���̣߳�����������������ͺͲ���ȥ���� `HttpLoader.java` �����ȥ�������󣬻ص�ǰ��� `request` �������������ڿ��԰�������������

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

����������������Ĵ����� `HttpLoader.getInstace().executePostBody(url, request);` ����������������ôʵ����� `HttpLoader.java` ��

```
public class HttpLoader {
    private static OkHttpClient mOkHttpClient;
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8"); //json ����
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png"); //ͼƬ����
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

    // ��ȡ OkHttpClient ����
    public OkHttpClient getmOkHttpClient(InputStream... certificates) {
        if (mOkHttpClient == null) {
            synchronized (HttpLoader.class) {
                // ʹ��ö����ʵ�ֵ���
                mOkHttpClient = OkHttpFactory.INSTANCE.getOkHttpClient(certificates);
            }
        }
        return mOkHttpClient;
    }

    // POST ͬ�������ύ��ֵ��;
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

������Ҫ��ע���д��� `OkHttpFactory.INSTANCE.getOkHttpClient(certificates);` �������Ǵ��������ǵ� `OkhttpClient` ���󲢶�������������

```
enum OkHttpFactory {

    INSTANCE;

    public static final int TIME_OUT = 5;
    private final OkHttpClient okHttpClient;
    private InputStream[] certificates;

    OkHttpFactory() {
        //����Ŀ¼
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
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)     // ���ӳ�ʱ
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)       // д�볬ʱ
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)        // ��ȡ��ʱ
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

�������������� Cookie ���桢��ʱ��https ֧�֡�������������÷����Բο���������£�

- [PersistentCookieJar Github ��ַ](https://github.com/franmontiel/PersistentCookieJar)
- [Android Https�����ȫ���� ��OkHttp����Https](https://blog.csdn.net/lmj623565791/article/details/48129405)


������Ϊֹ����������Ѿ��������ˣ��������ǻ�û���� `OkHttpClient` �����ʼ���������� `Application.java` �ļ��ж������г�ʼ��

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
        handler = new Handler();    // Ĭ�ϻ�ȡ�����̵߳� looper
        HttpLoader.getInstace().getmOkHttpClient(); // ��ʼ�� OkHttpClient
    }

    public static Program getAppContext() {
        return appContext;
    }
}
```


�������ǾͿ���ʹ�����Ƿ�װ��������ˣ�����ʹ�á�wanandroid���� api����ȡ��������

Url.java

```
public interface Url {
    String BASE_URL = "https://www.wanandroid.com";
    String FRIENDS = BASE_URL + "/friend/json";
}
```

����һ�� get ����

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

���н����

```
Friend name is: ���ڴ�ţ���ͼ���
Friend name is: �����ţ���ͼ���
Friend name is: jcenter�ֿ�
Friend name is: google�ֿ�
Friend name is: maven�ֿ�
Friend name is: ����Ĳ���
Friend name is: ���صĲ���
Friend name is: �������Ŀ�Դ��Ŀ
Friend name is: ������Ŀ
Friend name is: stackoverflow
Friend name is: �����ں����¾ۺ�
Friend name is: �ɻ�����Ӫ
Friend name is: ���
Friend name is: ������ͷ��
...
```


