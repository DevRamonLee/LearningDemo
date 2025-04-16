- [Retrofit + RxJava �� OkHttp �����������ļ�-����ƪ](https://www.jianshu.com/p/5bc866b9cbb9)
- [Retrofit + RxJava �� OkHttp �����������ļ�-��װƪ](https://www.jianshu.com/p/811ba49d0748)
- [RxJava2����С�ǣ���CompositeDisposable�������š�Retrofit��������](https://blog.csdn.net/ysy950803/article/details/84930656)

## RRO ����ʹ��

#### ʹ�� Retrofit ʵ��

ʹ�� [wanandroid] �Ŀ��� api����ȡ��ҳ�������� https://www.wanandroid.com/article/list/2/json (ps:����ԭ����Ķ��� api ��������)

> ������GET<br/>
������ҳ�룬ƴ���������У���0��ʼ��

- ������ build.gradle �ļ���������ǵ�����

```
implementation 'com.squareup.retrofit2:retrofit:2.4.0'// retrofit
implementation 'com.google.code.gson:gson:2.8.5'    // Gson ��
implementation 'io.reactivex:rxjava:1.1.5'      // Rxjava
implementation 'io.reactivex:rxandroid:1.1.0'   // Rxandroid
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'    // ת������������ת����Model
implementation 'com.squareup.retrofit2:adapter-rxjava:2.1.0'    // ���Rxjava ʹ��
```

- ���� Retrofit ʵ��

```
public static final String BASE_URL = "https://www.wanandroid.com";
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
```

- ����һ���ӿ�

```
public interface HotArticles {
    @GET("/article/list/{page}/json")
    Call<ArticlesBean> getHotArticles(@Path("page") int page);
}
```

- �� Retrofit ���� HotArticles ʵ����Ȼ���������ķ���

```
HotArticles service = retrofit.create(HotArticles.class);
Call<ArticlesSubject> articlesCall = service.getHotArticles(0);
articlesCall.enqueue(new Callback<ArticlesSubject>() {
    @Override
    public void onResponse(Call<ArticlesSubject> call, Response<ArticlesBean> response) {
        ArticlesAdapter articlesAdapter = new ArticlesAdapter(MainActivity.this, response.body());
        articlesRecycler.setAdapter(articlesAdapter);
    }

    @Override
    public void onFailure(Call<ArticlesSubject> call, Throwable t) {
        t.printStackTrace();
    }
});
```

Ч�����£��ܼ�ª...

![Rro1-001](./assets/Rro1-001.png)

#### ��� Rxjava ʹ��

- ���Ķ���Ľӿڣ��ѷ���ֵ Call �����޸�Ϊ Observable ����

```
public interface HotArticles {
    @GET("/article/list/{page}/json")
    Observable<ArticlesBean> getHotArticles(@Path("page") int page);
}
```

- Retrofit ��ʼ��ʱ��� RxJava ֧��

```
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   // RxJava ֧��
    .addConverterFactory(GsonConverterFactory.create())         // json ת����
    .build();
```

- �� Activity ���� Fragment �д��� Subscriber �������Ĺ�ϵ

```
HotArticles service = retrofit.create(HotArticles.class);
// �������Ĺ�ϵ
Subscription subscription = service.getHotArticles(0)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ArticlesBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArticlesBean articlesBean) {
                ArticlesAdapter articlesAdapter = new ArticlesAdapter(MainActivity.this, articlesBean);
                articlesRecycler.setAdapter(articlesAdapter);
            }
        });
```

#### ���� Okhttp ����

OkHttpClient �������ó�ʱʱ�䡢���桢�������ȣ���������������һ�¡�

���������õ���  [BasicParamsInterceptor](https://github.com/jkyeo/okhttp-basicparamsinterceptor), ����ʹ����Ҫ������������������� Github

```
// ���� Okhttpclient
OkHttpClient.Builder builder = new OkHttpClient.Builder();
builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS); // ���ӳ�ʱʱ��
builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);   // ���ӳ�ʱʱ��
builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);    // ��������ʱʱ��

// ��ӹ�������������
BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
        .addHeaderParam("userName", "")     // ��ӹ�������
        .addHeaderParam("device", "")
        .build();

builder.addInterceptor(basicParamsInterceptor);

Retrofit retrofit = new Retrofit.Builder()
        .client(builder.build())    // ʹ���������õ� OkHttpClient
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   // RxJava ֧��
        .addConverterFactory(GsonConverterFactory.create())         // json ת����
        .build();
```

> ������������򵥵�ʹ�ã�ʵ��ʹ�����ǲ�����ôд�ģ�������������̣���������Ҫ����һ�·�װ��Ȼ����ʹ��

## RRO ��װ

- ����һ��ͳһ�Ľӿ�ʵ�������� `RetrofitServiceManager.java` , ÿһ��������Ҫ����һ���ӿڣ�����ȡ�ӿ�ʵ���ķ�ʽ������ͬ�ģ����ǰ���һ�����������


```
/**
 * Created by Ramon Lee on 2019/8/12.
 * ͳһ���ɽӿ�ʵ���Ĺ�����
 */
public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;      // Ĭ�ϳ�ʱʱ��
    private static final int DEFAULT_READ_TIME_OUT = 10;    // Ĭ�϶�ȡ��ʱʱ��
    private Retrofit mRetrofit;

    private RetrofitServiceManager() {
        // ���� OkhttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);     // ���ӳ�ʱʱ��
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);        // ��������ʱʱ��

        // ��ӹ�������������
        HttpCommonInterceptor basicParamsInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform","android")
                .addHeaderParams("userToken","1234343434dfdfd3434")
                .addHeaderParams("userId","123445")
                .build();

        builder.addInterceptor(basicParamsInterceptor);


        // ���� Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConfig.BASE_URL)
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * ��ȡ RetrofitServiceManager ʵ��
     */

    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
```

> ʹ�õ���ģʽʵ���� RetrofitServiceManager , �ṩ��һ�� create ���������Ĳ����Ƿ��͵ģ����Կ���ʹ�����������κ� api �ӿڵ�ʵ��


- �����ӿ�

����һ�� api �ӿڣ�ͨ������� create �������ǾͿ��Ի�ȡʵ��

```
public interface ArticleService {
    // ��ȡ wanandroid ��ҳ�����б�
    @GET("/article/list/{page}/json")
    Observable<ArticlesBean> getHotArticles(@Path("page") int page);
}
```

- ����һ��ҵ�� Loader����ȡ Observable ���������ҵ�����ÿһ�� api ��дһ���ӿڣ������ǳ��鷳����˰������߼���װ��һ��ҵ�� Loader ���棬һ�� Loader ������Դ����� api �ӿ�,�����������ﴴ��һ�� `ArticleLoader.java`

```
public class ArticleLoader extends ObjectLoader {
    private ArticleService mArticleService;

    public ArticleLoader() {
        mArticleService = RetrofitServiceManager.getInstance().create(ArticleService.class);
    }

    public Observable<List<ArticlesBean.Datas>> getArticles(int page) {
        return observe(mArticleService.getHotArticles(page))
                .map(new Func1<ArticlesBean, List<ArticlesBean.Datas>>() {
                    @Override
                    public List<ArticlesBean.Datas> call(ArticlesBean articlesBean) {
                        return articlesBean.getData().getDatas();
                    }
                });
    }


    public interface ArticleService {
        // ��ȡ wanandroid ��ҳ�����б�
        @GET("/article/list/{page}/json")
        Observable<ArticlesBean> getHotArticles(@Path("page") int page);
    }
}
```

ArticleLoader �̳��� ObjectLoader �����������һЩ�����߼�

```
/**
 * Created by Ramon Lee on 2019/8/12.
 * ���ظ��Ĵ����ȡ�������ŵ�����
 */
public class ObjectLoader {
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())         // �������û�ȡ������
                .observeOn(AndroidSchedulers.mainThread());
    }
}
```

- Activity ���� Fragment �е���

```
mArticleLoader = new ArticleLoader();
mArticleLoader.getArticles(0).subscribe(new Action1<List<ArticlesBean.Datas>>() {
            @Override
            public void call(List<ArticlesBean.Datas> datas) {
                ArticlesAdapter articlesAdapter = new ArticlesAdapter(MainActivity.this, datas);
                articlesRecycler.setAdapter(articlesAdapter);
            }
        },
        new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG,"error message:"+throwable.getMessage());
            }
        }
);
```

- ������Ϊֹ�����Ǿ����������ķ�װ������ʵ��ʹ���У����ǻ���Ҫ����ͳһ�������ʹ���


ͳһ������������ʵ����Ŀ�У����еķ��ؽ������ͳһ��ʽ�ģ������������ﷵ�صĸ�ʽ���£�

```
{
	"data": {
		"curPage": 5,
		"datas": [{
			"apkLink": "",
			"author": "xiaoyang",
			"chapterId": 440,
			"chapterName": "�ٷ�",
			"collect": false,
			"courseId": 13,
			"desc": "<p>1. ��������Ҫ֪��<span style=\"font-size: 16px;\">ͬ�����ϻ�����ʲô��</span></p><br><p>2. ˼���£�ΪʲôҪ��������ƣ�</p><br><p>����1/3������ϲ�£������ҹ���±ơ�</p>",
			"envelopePic": "",
			"fresh": false,
			"id": 8710,
			"link": "https://www.wanandroid.com/wenda/show/8710",
			"niceDate": "2019-07-23",
			"origin": "",
			"prefix": "",
			"projectLink": "",
			"publishTime": 1563874193000,
			"superChapterId": 440,
			"superChapterName": "�ʴ�",
			"tags": [{
				"name": "�ʴ�",
				"url": "/article/list/0?cid=440"
			}],
			"title": "ÿ���ʴ� HandlerӦ���Ǵ������Ϥ���������ˣ���ô�����и�ͬ�����ϻ��ƣ����˽�����أ�",
			"type": 0,
			"userId": 2,
			"visible": 1,
			"zan": 19
		}],
		"offset": 80,
		"over": false,
		"pageCount": 347,
		"size": 20,
		"total": 6922
	},
	"errorCode": 0,
	"errorMsg": ""
}
```

���� api ��ʱ���������ɹ�����ֻ���� data�������������ֶΣ�����ʧ�ܵ�ʱ�����Ǹ��� errorCode ���д��������ݷ��������ص� json ��ʽ����һ�� `BaseResponse.java` ��

```
public class BaseResponse<T> {
    public int errorCode;   // 0 ��������ɹ�
    public String errorMsg;
    public T data;

    public boolean isSuccess() {
        return errorCode == 0;
    }
}
```

����ͳһ�����ݸ�ʽ��������Ҫ����� data ���ϲ�ʹ�ã�����һ�� `PayLoad.java` ��

```
public class PayLoad<T> implements Func1<BaseResponse<T>, T> {
    @Override
    public T call(BaseResponse<T> tBaseResponse) { //��ȡ����ʧ��ʱ����װһ��Fault �׸��ϲ㴦�����
        if (!tBaseResponse.isSuccess()) {
            throw new Fault(tBaseResponse.errorCode,tBaseResponse.errorMsg);
        }
        return tBaseResponse.data;
    }
}
```

PayLoad �̳��� `Func1`,����һ�� `BaseResponse<T>` , ���ǽӿڷ��ص� JSON ���ݽṹ�����ص���T,����data,�ж��Ƿ�����ɹ�������ɹ����� Data,����ʧ�ܰ�װ��һ�� Fault ���ظ��ϲ�ͳһ�������

```
/**
 * Created by Ramon Lee on 2019/8/12.
 * �쳣�����࣬���쳣��װ��һ�� Fault ,�׸��ϲ�ͳһ����
 */
public class Fault extends RuntimeException {
    private int errorCode;

    public Fault(int errorCode,String message){
        super(message);
        errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
```

�� Loader �������ȡ�����ͨ��map ��������������

```
public class ArticleLoader extends ObjectLoader {
    private ArticleService mArticleService;

    public ArticleLoader() {
        mArticleService = RetrofitServiceManager.getInstance().create(ArticleService.class);
    }

    public Observable<ArticlesBean> getArticles(int page) {
        return observe(mArticleService.getHotArticles(page))
                .map(new PayLoad<ArticlesBean>());  // ������ʹ�� map ����� data
    }


    public interface ArticleService {
        // ��ȡ wanandroid ��ҳ�����б�
        @GET("/article/list/{page}/json")
        Observable<BaseResponse<ArticlesBean>> getHotArticles(@Path("page") int page);  // ע�����ﷵ���������������޸�
    }
}
```

ͳһ�������� PayLoad ���������ʧ��ʱ�׳���һ���쳣���ϲ㣬������ Activity ���õ�����쳣���д�����

```
new Action1<Throwable>() {
    @Override
    public void call(Throwable throwable) {
        Log.e(TAG,"error message:"+throwable.getMessage());
        Fault fault = (Fault) throwable;
        if(fault.getErrorCode() == 1) {
            // ���в�ͬ���͵Ĵ�����
        } else if (fault.getErrorCode() == 2) {
            
        }
    }
}
```

- ��ӹ���������ʵ����Ŀ�У�ÿ������ӿڶ���һЩ�������������userId��userToken��userName,deviceId������û�б�Ҫ��ÿ���ӿڶ�ȥд��Щ������������ǿ���дһ����������������������������Ϊÿ����������Ϲ��������������

```
public class HttpCommonInterceptor implements Interceptor {
    private Map<String,String> mHeaderParamsMap = new HashMap<>();
    public HttpCommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // �µ�����   
        Request.Builder requestBuilder =  oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(),
                oldRequest.body());

        //��ӹ�������,��ӵ�header��        
        if(mHeaderParamsMap.size() > 0){
            for(Map.Entry<String,String> params:mHeaderParamsMap.entrySet()){
                requestBuilder.header(params.getKey(),params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder{
        HttpCommonInterceptor mHttpCommonInterceptor;
        public Builder(){
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }
        public Builder addHeaderParams(String key, String value){
            mHttpCommonInterceptor.mHeaderParamsMap.put(key,value);
            return this;
        }
        public Builder  addHeaderParams(String key, int value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder  addHeaderParams(String key, float value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder  addHeaderParams(String key, long value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder  addHeaderParams(String key, double value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public HttpCommonInterceptor build(){
            return mHttpCommonInterceptor;
        }
    }
}
```

�� `RetrofitServiceManager` �и� OkhttpClient �������

```
// ��ӹ�������������
HttpCommonInterceptor basicParamsInterceptor = new HttpCommonInterceptor.Builder()
        .addHeaderParams("paltform","android")
        .addHeaderParams("userToken","1234343434dfdfd3434")
        .addHeaderParams("userId","123445")
        .build();

builder.addInterceptor(basicParamsInterceptor);
```


����Ŀ¼���£�

![Rro1-002](./assets/Rro1-002.png)


#### ����������

- **RecyclerView item ������һ��**

�޸�

```
view = View.inflate(mContext, R.layout.item,null); 
return new ViewHolder(view);
```
Ϊ

```
view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
```