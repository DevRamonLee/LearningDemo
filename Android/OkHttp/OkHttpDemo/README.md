- http://www.ymapk.com/article-250-1.html(ת���Դ���)
- [��������](https://square.github.io/okhttp/)
- [OkHttp Wiki �ĵ�](https://github.com/square/okhttp/wiki/Calls)

## ���

OkHttp ��һ�����ɵ���������⣬�����������ԣ�

- ֧�� http2, ��һ̨����������������ͬһ�� socket
- �������ӳأ�֧�����Ӹ��ã������ӳ٣���� HTTP2 �����ã�
- ֧��͸���� gzip ѹ����Ӧ��
- ͨ����������ظ�������
- ����ʧ��ʱ�Զ��������������� ip���Զ��ض���



## Android Studio ���� OkHttp3

������ Android Studio �� gradle �н������µ����ã�
```
implementation("com.squareup.okhttp3:okhttp:3.10.0")
```
�������Ȩ��
```
<uses-permission android:name="android.permission.INTERNET"/>
```
## ����ʹ��

- **OkHttp �� get ����**

OkHttp Ĭ���� Get ����
```
private void runSynGet(String url) throws IOException {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url(url)
            .build();
    // Java 7 try-with-resources �����Զ��ر���Դ
    try(Response response = client.newCall(request).execute()) {
        Log.i(TAG, "runSynGet " + response.body().string());
    }
}
```

`client.newCall(request).execute()` �Ǹ�ͬ���ķ���, �ڵ��������߳���ִ�У����Բ���ֱ���� UI �߳���ʹ��������Ҫ�½����̡߳�

OkHttpClient ͬ��֧���첽�����󷽷� `client.newCall(request).enqueue(new  Callback() ...)`  ���� enqueue �������Ҵ���һ���ص��ӿڵ�ʵ�����������£�

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

����������첽����ģ����Զ��������̣߳������ǲ���Ҫ�����ٸ�������һ�����̡߳�


- **Post �ύ Json ����**

```
// POST �ύ JSON ����
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

> [����MediaType����ϸ����](https://www.jianshu.com/p/4721d7b5e780)


- **Post �ύ��ֵ��**���ܶ�ʱ��������Ҫͨ�� post �Ѽ�ֵ�Դ���������

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


- **�첽�ϴ��ļ�**
```
// �����ϴ�����Ϊ�ļ�
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
��Ӷ�дȨ��
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

- **��ȡ��Ӧͷ**
```
// ��ȡ��Ӧͷ
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

��ȡ���
```
D/OKHttpActivityTag: Server: GitHub.com
D/OKHttpActivityTag: Date: Sun, 31 Mar 2019 08:41:08 GMT
D/OKHttpActivityTag: Vary: [Accept, Accept-Encoding]
```

- **Post ��ʽ�ύ String**

ʹ�� HTTP post ��ʽ�ύһ�� String ���͵� markdown �ĵ��� web ������ Html ��ʽ��Ⱦ Markdown.
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
���н����
```
D/OKHttpActivityTag: postString<hr>
                     <ul>
                     <li>number one</li
                     <li>number two</li
                     </ul>
```

- **Post ��ʽ�ύ��**

�����ķ�ʽ POST �ύ�����壬��������������д�����������ʹ�õ��� Okio �� BufferedSink��
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

        // �����ֽ�
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

- **POST ��ʽ�ύ��**

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

- **Post ��ʽ�ύ�ֿ�����**

MultipartBody ���Թ������ӵ������壬�����������ÿ�鶼��һ�������壬���Զ����Լ�������ͷ����Щ����ͷ�������������������

```
// post �ύ�ֿ�����MultipartBody ʵ��ͬʱ�ϴ��ļ��Ͳ���
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

- **��Ӧ����**

Ϊ��ʵ����Ӧ���棬����Ҫ�������¼��㣺

- 1.������Ҫһ�����Զ�д�Ļ���Ŀ¼�������û����С(ע���������Ŀ¼Ӧ����˽�еģ������εĳ����ܷ���)
- 2.һ������Ŀ¼����ӵ�ж��������ʡ����������ֻ��Ҫ����һ�� `new OkHttpClient()`, �ڵ�һ�ε���ʱ���úû��棬Ȼ�������ط������������ʵ�����ɡ���Ȼ��δ��� `HttpClient` ���ܻᵼ�»��ң�Ӧ�ñ�����
- 3.������ͷ������ HTTP ��Ӧ���� `Cache-Control: max-stale=3600` OkHttp�����֧�֡���ķ���ͨ����Ӧͷȷ����Ӧ����೤ʱ�䣬����ʹ��`Cache-Control: max-age=9600`

```
// ��Ӧ����
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

- **��ʱ**

û����Ӧʱʹ�ó�ʱ���� call��OkHttp ֧��**���ӡ���ȡ��д�볬ʱ**��

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


## �򵥷�װ

�����������½�һ�� `OkHttpUtils.java` �ļ���һ��Ӧ���У�`OkHttpUtils` ��Ҫ�ǵ����ģ���������������Ҫ��ʵ��һ������

```
public class OkHttpUtils {
    // OkHttpClient ��Ҫ�ǵ�����
    private static OkHttpUtils mInstance;
    private OkHttpClient mHttpClient;

    private OkHttpUtils() {}

    public static OkHttpUtils getInstance() {
        return mInstance;
    }
    
    ...
}
```

һ�����������Ϊ get �� post �������֣�������������������Ҫ�õ� request �ģ������������ȷ�װһ�� request,����һ�� doRequest �������������ȱ�д `mHttpClient.newCall(request).enqueue(new Callback())` ����߼�

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

������Ҫ�Զ���һ��callback��BaseCallback,�����䴫��request������

```
public abstract class BaseCallback  {

}
```

�� `OkHttpUtils.java` �б�д get �� post ����

```
public void get(String url){


}

public void post(String url,Map<String,Object> param){


}
```

post �����й��� request ��������������Ҫ����һ�� buildRequest �������������� request ����

```
private  Request buildRequest(String url,HttpMethodType methodType,Map<String,Object> params){
    return null;
}
```

������Ҫ��һ��ö�ٶ��� HttpMethodType������������ get ���� post

```
enum  HttpMethodType {
    GET,
    POST
}
```

buildRequest �������� HttpMethodType ��ͬ����Ӧ���߼�����

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

`builder.post()`��������Ҫһ�� body ,����������Ҫ����һ������ `builderFormData()` �������ڷ��� RequestBody ,�����ڲ��߼������ٽ�������

```
private RequestBody builderFormData(Map<String,Object> params){
    return null;
}
```

���� `buildRequest()` �������������

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

get ���������޸�

```
public void get(String url,BaseCallback callback){
    Request request = buildRequest(url,HttpMethodType.GET,null);
    doRequest(request,callback);
}
```

post ���������޸�

```
public void post(String url,Map<String,Object> params,BaseCallback callback){
    Request request = buildRequest(url,HttpMethodType.POST,params);
    doRequest(request,callback);
}
```

���� `buildFormData()` ����

```
private RequestBody builderFormData(Map<String,String> params){
    FormBody.Builder builder =  new FormBody.Builder();
    // ��Ӽ�ֵ��
    if(params!=null){
        for(Map.Entry<String,String> entry:params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
    }
    return builder.build();
}
```

`BaseCallback` �ж���һ�����󷽷� `onBeforeRequest`���������������������ڼ����������ݳɹ�ǰ��һ�㶼�н���������ʾ���������������������Щ�����

```
public abstract class BaseCallback  {
    public  abstract void onBeforeRequest(Request request);
}
```

`OkHttpUtils` �� `doRequest` ��������������䣺

```
baseCallback.onBeforeRequest(request);
```

BaseCallback �жඨ��2�����󷽷�

```
public abstract  void onFailure(Request request, Exception e) ;

/**
 *����ɹ�ʱ���ô˷���
 * @param response
 */
public abstract  void onResponse(Response response);
```

���� Response ��״̬�ж��֣�����ɹ���ʧ�ܣ�������Ҫ�� onResponse �ֽ�Ϊ3�����󷽷�

```
/**
 *
 * ״̬�����200��С��300 ʱ���ô˷���
 * @param response
 * @param t
 * @throws
 */
public abstract void onSuccess(Response response,T t) ;

/**
 * ״̬��400��404��403��500��ʱ���ô˷���
 * @param response
 * @param code
 * @param e
 */
public abstract void onError(Response response, int code,Exception e) ;

/**
 * Token ��֤ʧ�ܡ�״̬��401,402,403 ��ʱ���ô˷���
 * @param response
 * @param code
 */
public abstract void onTokenError(Response response, int code);
```

`response.body.string()` �������صĶ��� String ���ͣ���������Ҫ��ʾ��������ʵ�Ƕ����������Ǿ����ȡ��������ֱ�ӷ��ض����������ǲ�֪�������������ʲô������������`BaseCallback` ��ʹ�÷���

```
public abstract class BaseCallback<T>  
```

BaseCallback ����Ҫ������ת��Ϊ Type������Ҫ���� Type ����

```
public   Type mType;
```

BaseCallback ����Ҫ����һ�δ��룬������ T ת��Ϊ Type ����

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
���� `$Gson$Types` ����ʾ Can not resolved,������Ҫ��� GSON ֧�֣������� Gradle �е�������

```
implementation 'com.google.code.gson:gson:2.8.5'
```

Ȼ�����ļ��е��������

```
import com.google.gson.internal.$Gson$Types;
```

�� BaseCallback �Ĺ��캯���н��� mType ���и�ֵ

```
public BaseCallBack() {
    mType = getSuperClassTypeParameter(getClass());
}
```

`OkHttpUtils` �� `doRequest`������ `onFailure` �� `onResponse` ��������Ӧ��ȥ���� `baseCallback`�ķ���

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

onResponse �����гɹ�������������֣����� mType �����Ͳ�ͬ����Ӧ�Ĵ����߼���ͬʱ��Ҫ���� Gson ������������

```
 @Override
public void onResponse(Call call, Response response) throws IOException {
    // ����ɹ�
    if (response.isSuccessful()) {
        String resultStr = response.body().string();
        // ���������ж�
        if (baseCallBack.mType == String.class) {
            baseCallBack.onSuccess(response, resultStr);
        } else {
            try {
                Object obj = mGson.fromJson(resultStr, baseCallBack.mType);
                baseCallBack.onSuccess(response, obj);
            } catch (JsonParseException e) {
                // json ��������
                baseCallBack.onError(response, response.code(), e);
            }
        }
    } else {
        // ������ִ���
        baseCallBack.onError(response, response.code(), null);
    }
}
```

���캯���н���һЩȫ�ֱ����ĳ�ʼ���Ĳ���������һЩ��ʱ�����

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

��̬������ʼ�� OkHttpUtils ����
```
static {
    mInstance = new OkHttpUtils();
}
```

�� okHttpUtils �ڣ���Ҫ���� handler ���� UI ����ĸ��²��������� `callbackSuccess` ����

```
// ʹ�� handler ���� UI �Ĳ���
private void callBackSuccess(final BaseCallBack baseCallBack, final Response response, final Object obj) {
    mHandler.post(new Runnable(){
        @Override
        public void run() {
            baseCallBack.onSuccess(response, obj);
        }
    });
}
```

����������Ҫ�ڹ��캯���д��� Handler

```
private Handler mHandler;
private OkHttpUtils() {
    ...
    mHandler = new Handler();
}
```

doRequest �� onResponse ����Ҳ��Ҫ���и�д

```
if (baseCallBack.mType == String.class) {
    /*baseCallBack.onSuccess(response, resultStr);*/
    callBackSuccess(baseCallBack, response, resultStr);
}
```

���� callbackError ����

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

�� `doRequest` ������ `onResponse` �����е� `baseCallback.onError(response,response.code(),e);` �滻Ϊ `callbackError(baseCallback,response,e);` ����

```
@Override
public void onResponse(Call call, Response response) throws IOException {
    // ����ɹ�
    if (response.isSuccessful()) {
        String resultStr = response.body().string();
        // ���������ж�
        if (baseCallBack.mType == String.class) {
            /*baseCallBack.onSuccess(response, resultStr);*/
            callBackSuccess(baseCallBack, response, resultStr);
        } else {
            try {
                Object obj = mGson.fromJson(resultStr, baseCallBack.mType);
                /*baseCallBack.onSuccess(response, obj);*/
                callBackSuccess(baseCallBack, response, obj);
            } catch (JsonParseException e) {
                // json ��������
                /*baseCallBack.onError(response, response.code(), e);*/
                callBackError(baseCallBack, response, e);
            }
        }
    } else {
        // ������ִ���
        /*baseCallBack.onError(response, response.code(), null);*/
        callBackError(baseCallBack, response, null);
    }
}
```

ʹ��������׷�װ��

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


#### ����������

- **����1**`java.net.ProtocolException: Expected ':status' header not present`

������������� Okhttp ���汾 3.10.0 ����ˡ�