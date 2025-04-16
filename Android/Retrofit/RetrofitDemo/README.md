- [Retrofit �ٷ��ĵ�](https://square.github.io/retrofit/)
- [����һ�ݺ���ϸ�� Retrofit 2.0 ʹ�ý̳̣���ʵ�����⣩](https://blog.csdn.net/carson_ho/article/details/73732076)

## ���

������ Retrofit �Ķ����ǣ�**����� Http ����ת��Ϊ java �ӿ�**,ֱ�׵�˵��**Retrofit ��һ�� [RESTful](http://www.ruanyifeng.com/blog/2018/10/restful-api-best-practices.html) �� HTTP ���������ܵķ�װ**��


> ��������Ĺ��������� Okhttp ��ɵģ�Retrofit ֻ������������ӿڵķ�װ��

## ʹ�ò���

ʹ�� Retrofit �� 7 ������

1. ��� Retrofit �������
2. �������շ������������ݵ���
3. ��������������������ӿڵ���
4. ���� Retrofit ʵ��
5. ������������ӿ�ʵ�������������������
6. �������������첽/ͬ����
> Retrofit ��װ������ת�����߳��л��Ĳ���
7. �����������������



#### �������

�� Gradle ���������

```
implementation 'com.squareup.retrofit2:retrofit:2.4.0'  // retrofit
```

�������Ȩ��

```
<uses-permission android:name="android.permission.INTERNET"/>
```


#### �������շ������������ݵ���

```java
public class OfficialAccounts {
    // ��ϸ���ݿ��������1
}
```

#### ��������������������Ľӿ�

Retrofit �������������� java �ӿڣ�����ע��ķ�ʽ�����塣

> Retrofit �ö�̬����ķ�ʽ��̬�Ľ��ӿڵ�ע��ת����һ�� Http ����

GetRequest_Interface.java

```java
public interface GetRequest_Interface {
    @GET("wxarticle/chapters/json")
    Call<OfficialAccounts> getOfficialAccounts();
}
```

ע�����Ͱ�����


![retrofit-1-001](F700FE33359D44518FD93E3395DCFBB2)

**��һ�ࣺ�������󷽷�**

![retrofit-1-002](D3FFC9AC90124CB9B9C7C5DAB3662411)


- Retrofit�� ���������URL �ֳ������������ã�

```java
// ��һ����
@GET("users/{user}/repos")
Call<List<Repo>> listRepos(@Path("user") String user);

// �ڶ����� baseUrl
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();
```

-  `@HTTP` ���ã��滻`@GET��@POST��@PUT��@DELETE��@HEAD` ע������ü����๦����չ

```java
public interface GetRequest_Interface {
    /**
     * method����������ķ��������ִ�Сд��
     * path�����������ַ·��
     * hasBody���Ƿ���������
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getCall(@Path("id") int id);
}
```

**�ڶ��ࣺ ���**

![retrofit-1-003](18E20BB9A0354EAAA5BB961F6B4FB3A8)

- `@FormUrlEncoded` ���ã���ʾ����form-encoded������,ÿ����ֵ����Ҫ�� `@Filed` ��ע�����


-  `@Multipart` ���ã���ʾ����form-encoded�����ݣ����������ļ��ϴ��ĳ�����ÿ����ֵ����Ҫ�� `@Part` ��ע��


```java
public interface GetRequest_Interface {
    /**
     *������һ������ʽ������Content-Type:application/x-www-form-urlencoded��
     * <code>Field("username")</code> ��ʾ������� <code>String name</code> ��name��ȡֵ��Ϊ username ��ֵ
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);
     
    /**
     * {@link Part} ����֧���������ͣ�{@link RequestBody}��{@link okhttp3.MultipartBody.Part} ����������
     * �� {@link okhttp3.MultipartBody.Part} ���⣬�������Ͷ�������ϱ��ֶ�({@link okhttp3.MultipartBody.Part} ���Ѿ������˱��ֶε���Ϣ)��
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

}

// ����ʹ��
GetRequest_Interface service = retrofit.create(GetRequest_Interface.class);
// @FormUrlEncoded 
Call<ResponseBody> call1 = service.testFormUrlEncoded1("Carson", 24);

//  @Multipart
RequestBody name = RequestBody.create(textType, "Carson");
RequestBody age = RequestBody.create(textType, "24");

MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);

```


**�����࣬�����������**

![retrofit-1-004](F134289266CB49728BFA8E6949CBF509)


-  `@Header & @Headers` ���ã��������ͷ &��Ӳ��̶�������ͷ

```java
@GET("user")
Call<User> getUser(@Header("Authorization") String authorization)

@Headers("Authorization: authorization")
@GET("user")
Call<User> getUser()

// ���ϵ�Ч����һ�µġ�
// ��������ʹ�ó�����ʹ�÷�ʽ
// 1. ʹ�ó�����@Header������Ӳ��̶�������ͷ��@Headers������ӹ̶�������ͷ
// 2. ʹ�÷�ʽ��@Header�����ڷ����Ĳ�����@Headers�����ڷ���
```


- `@Body` ���ã��� Post��ʽ�����Զ����������� ��������
�ر�ע�⣺����ύ����һ��Map����ô�����൱�� `@Field`
���� Map Ҫ���� `FormBody.Builder` �ദ���Ϊ���� Okhttp ��ʽ�ı����磺

```java
FormBody.Builder builder = new FormBody.Builder();
builder.add("key","value");
```

- `@Field & @FieldMap` ���ã����� Post���� ʱ�ύ����ı��ֶ�
����ʹ�ã��� `@FormUrlEncoded` ע�����ʹ��

```java
public interface GetRequest_Interface {
/**
 *������һ������ʽ������Content-Type:application/x-www-form-urlencoded��
 * <code>Field("username")</code> ��ʾ������� <code>String name</code> ��name��ȡֵ��Ϊ username ��ֵ
 */
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

/**
 * Map��key��Ϊ���ļ�
 */
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

}

// ����ʹ��
// @Field
Call<ResponseBody> call1 = service.testFormUrlEncoded1("Carson", 24);

// @FieldMap
// ʵ�ֵ�Ч����������ͬ����Ҫ����Map
Map<String, Object> map = new HashMap<>();
map.put("username", "Carson");
map.put("age", 24);
Call<ResponseBody> call2 = service.testFormUrlEncoded2(map);
```

- `@Part & @PartMap` ���ã����� Post����ʱ�ύ����ı��ֶΡ���@Field�����𣺹�����ͬ����Я���Ĳ������͸��ӷḻ������������������������ ���ļ��ϴ��ĳ���, �� @Multipart ע�����ʹ�á�


```java
public interface GetRequest_Interface {

    /**
     * {@link Part} ����֧���������ͣ�{@link RequestBody}��{@link okhttp3.MultipartBody.Part} ����������
     * �� {@link okhttp3.MultipartBody.Part} ���⣬�������Ͷ�������ϱ��ֶ�({@link okhttp3.MultipartBody.Part} ���Ѿ������˱��ֶε���Ϣ)��
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * PartMap ע��֧��һ��Map��Ϊ������֧�� {@link RequestBody } ���ͣ�
     * ��������������ͣ��ᱻ{@link retrofit2.Converter}ת������������ܵ� ʹ��{@link com.google.gson.Gson} �� {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * ����{@link MultipartBody.Part} �Ͳ�������,�����ļ�ֻ����<b> @Part MultipartBody.Part </b>
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);
}

// ����ʹ��
 MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "Carson");
        RequestBody age = RequestBody.create(textType, "24");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "������ģ���ļ�������");

        // @Part
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
        Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);
        ResponseBodyPrinter.printResponseBody(call3);

        // @PartMap
        // ʵ�ֺ�����ͬ����Ч��
        Map<String, RequestBody> fileUpload2Args = new HashMap<>();
        fileUpload2Args.put("name", name);
        fileUpload2Args.put("age", age);
        //���ﲢ���ᱻ�����ļ�����Ϊû���ļ���(������Content-Disposition����ͷ��)��������� filePart ��
        //fileUpload2Args.put("file", file);
        Call<ResponseBody> call4 = service.testFileUpload2(fileUpload2Args, filePart); //���������ļ�
        ResponseBodyPrinter.printResponseBody(call4);
```

- `@Query & @QueryMap` ���ã����� @GET �����Ĳ�ѯ������Query = Url �� ��?�� ����� key-value��

```java
@GET("/")    
Call<String> cate(@Query("cate") String cate);
```

- `@Path` ���ã�URL��ַ��ȱʡֵ

```java
@GET("users/{user}/repos")
Call<ResponseBody>  getBlog��@Path("user") String user ��;
```

- `@Url` ���ã�ֱ�Ӵ���һ������� URL���� ����URL����

```java
@GET
Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);
// ����URLע��ʱ��@GET�����URL�Ϳ���ʡ��
// ��GET��POST...HTTP�ȷ�����û������Urlʱ�������ʹ�� {@link Url}�ṩ
```

#### ���� Retrofit ʵ��

```java
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/") // �������������Url��ַ
                .addConverterFactory(GsonConverterFactory.create()) // �������ݽ�����
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // ֧��RxJavaƽ̨
                .build();
```

- �������ݽ�������Converter��: Retrofit ֧�ֶ������ݽ�����ʽ������ʱ��Ҫ�� Gradle ���������

![retrofit-1-005](F82AFF7941DD4F1F963E3BD3252A93BD)


- Retrofit֧�ֶ�������������������ʽ��guava��Java8 �� rxjava��ʹ��ʱ������� Android Ĭ�ϵ� CallAdapter ���ö����������������������������Ҫȥ gradle �����������


```
![retrofit-1-006](226E9FD97014405E86126BD6B2497015)
```

#### ������������ӿ�

```java
// ���� ��������ӿ� ��ʵ��
GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

//�� �������� ���з�װ
Call<Reception> call = request.getCall();
```

#### �������������첽��ͬ����

> �ڲ���װ������ת�����߳��л��Ĳ���

```java
//������������(�첽)
call.enqueue(new Callback<OfficialAccounts>() {
    @Override
    public void onResponse(Call<OfficialAccounts> call, Response<OfficialAccounts> response) {
        // �����ص�����
        Log.i("RamonLee", response.body().toString());
    }

    @Override
    public void onFailure(Call<OfficialAccounts> call, Throwable t) {

    }
});

// ������������ͬ����
Response<Reception> response = call.execute();
```

#### ����������

ͨ��response��� body�����Է��ص����ݽ��д���

```java
//������������(�첽)
call.enqueue(new Callback<OfficialAccounts>() {
    //����ɹ�ʱ�ص�
    @Override
    public void onResponse(Call<OfficialAccounts> call, Response<OfficialAccounts> response) {
        // �Է������ݽ��д���
        response.body().show();
    }

    //����ʧ��ʱ��Ļص�
    @Override
    public void onFailure(Call<OfficialAccounts> call, Throwable throwable) {
        System.out.println("����ʧ��");
    }
});

// ������������ͬ����
Response<Reception> response = call.execute();
// �Է������ݽ��д���
response.body().show();
```

## ʵ��

��������ʵ���ֱ�ʹ�� Retrofit get �� Retrofit post �ķ�ʽ������������

#### ʵ��1�� ʹ�� wanandroid ��ȡ���ں��б�

url: https://wanandroid.com/wxarticle/chapters/json 

json ���ݸ�ʽ

```
{
	"data": [{
		"children": [],
		"courseId": 13,
		"id": 408,
		"name": "����",
		"order": 190000,
		"parentChapterId": 407,
		"userControlSetTop": false,
		"visible": 1
	},
	{
		"children": [],
		"courseId": 13,
		"id": 409,
		"name": "����",
		"order": 190001,
		"parentChapterId": 407,
		"userControlSetTop": false,
		"visible": 1
	}],
	"errorCode": 0,
	"errorMsg": ""
}
```

���� json �������Ǵ���ʵ����

`OfficialAccounts.java`

```java
public class OfficialAccounts {
    private List<Data> data;
    private int errorCode;
    private String errorMsg;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
}
```

`Data.java`

```java
public class Data {
    private List<String> children;
    private int courseId;
    private int id;
    private String name;
    private long order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;
    public void setChildren(List<String> children) {
        this.children = children;
    }
    public List<String> getChildren() {
        return children;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getCourseId() {
        return courseId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setOrder(long order) {
        this.order = order;
    }
    public long getOrder() {
        return order;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }
    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }
    public boolean getUserControlSetTop() {
        return userControlSetTop;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
    public int getVisible() {
        return visible;
    }
}
```

��������������������Ľӿ�

`GetRequest_Interface.java`

```java
public interface GetRequest_Interface {

    @GET("wxarticle/chapters/json")
    Call<OfficialAccounts> getOfficialAccounts();
}
```

������������ `MainActivity.java` ʹ�ýӿڣ���������

```java
private void request() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://wanandroid.com/") // ���� �������� Url
            .addConverterFactory(GsonConverterFactory.create()) // ����ʹ��Gson����(�ǵü�������)
            .build();

    // ������������ӿ�
    GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

    // �Է��͵�������з�װ
    Call<OfficialAccounts> call = request.getOfficialAccounts();

    // �첽������������
    call.enqueue(new Callback<OfficialAccounts>() {
        @Override
        public void onResponse(Call<OfficialAccounts> call, Response<OfficialAccounts> response) {
            // �����ص�����
            Log.i("RamonLee", response.body().toString());
        }

        @Override
        public void onFailure(Call<OfficialAccounts> call, Throwable t) {

        }
    });
}
```

���н��(��ȡ�˲���)��

```
{data=[Data{children=[], courseId=13, id=408, name='����', order=190000, parentChapterId=407, userControlSetTop=false, visible=1}], errorCode=0, errorMsg=''}
```


#### ʵ��2�� ʹ���е� api ���� post ������

�ӿ��ĵ�

```java
// URL
http://fanyi.youdao.com/translate

// URLʵ��
http://fanyi.youdao.com/translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=


// ����˵��
// doctype��json �� xml
// jsonversion����� doctype ֵ�� xml����ȥ����ֵ���� doctype ֵ�� json����ֵΪ�ռ���
// xmlVersion����� doctype ֵ�� json����ȥ����ֵ���� doctype ֵ�� xml����ֵΪ�ռ���
// type�������Զ����ʱΪ null��Ϊ null ʱ��Ϊ�ա�Ӣ����Ϊ EN2ZH_CN������ӢΪ ZH_CN2EN��������Ϊ JA2ZH_CN��������Ϊ ZH_CN2JA��������Ϊ KR2ZH_CN�����뺫Ϊ ZH_CN2KR�����뷨Ϊ ZH_CN2FR��������Ϊ FR2ZH_CN
// keyform��mdict. + �汾�� + .�ֻ�ƽ̨����Ϊ��
// model���ֻ��ͺš���Ϊ��
// mid��ƽ̨�汾����Ϊ��
// imei��???����Ϊ��
// vendor��Ӧ������ƽ̨����Ϊ��
// screen����Ļ��ߡ���Ϊ��
// ssid���û�������Ϊ��
// abtest��???����Ϊ��

// ����ʽ˵��
// ����ʽ��POST
// �����壺i
// �����ʽ��x-www-form-urlencoded
```

���ȴ���ʵ����

```java
public class Translation {
    private String type;
    private int errorCode;
    private int elapsedTime;
    private List<List<TranslateResultBean>> translateResult;
    ...
    
    public static class TranslateResultBean {
        public String src;
        public String tgt;
        ...
    }
}
```

��������ӿ� `PostRequest_Interface.java`

```java
public interface PostRequest_Interface {
    //����@Post��ʾPost�����������󣨴��벿��url��ַ��
    // ����@FormUrlEncodedע���ԭ��:API�涨���������ʽx-www-form-urlencoded,������ʽ
    // ��Ҫ���@Field ��������ύ��Ҫ���ֶ�
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Translation> getCall(@Field("i") String targetSentence);
}
```

������ȥ����

```java
private void postRequest() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/") // ���� �������� Url
            .addConverterFactory(GsonConverterFactory.create()) //����ʹ��Gson����(�ǵü�������)
            .build();

    // ���� ��������ӿ� ��ʵ��
    PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

    // �Է���������з�װ(������Ҫ���������)
    Call<Translation> call = request.getCall("Tomorrow is Friday.");

    // ������������(�첽)
    call.enqueue(new Callback<Translation>() {

        // ����ɹ�ʱ�ص�
        @Override
        public void onResponse(Call<Translation> call, Response<Translation> response) {
            // �����ص����ݽ����������������
            Log.i("RamonLee", response.body().getTranslateResult().get(0).get(0).getTgt());
        }

        // ����ʧ��ʱ�ص�
        @Override
        public void onFailure(Call<Translation> call, Throwable throwable) {
            System.out.println("����ʧ��");
            System.out.println(throwable.getMessage());
        }
    });
}
```

���н����

```
RamonLee: �����������塣
```