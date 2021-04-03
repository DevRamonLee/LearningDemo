- [Retrofit 官方文档](https://square.github.io/retrofit/)
- [这是一份很详细的 Retrofit 2.0 使用教程（含实例讲解）](https://blog.csdn.net/carson_ho/article/details/73732076)

## 简介

官网对 Retrofit 的定义是：**把你的 Http 请求转换为 java 接口**,直白的说：**Retrofit 是一个 [RESTful](http://www.ruanyifeng.com/blog/2018/10/restful-api-best-practices.html) 的 HTTP 网络请求框架的封装**。


> 网络请求的工作本质是 Okhttp 完成的，Retrofit 只负责网络请求接口的封装。

## 使用步骤

使用 Retrofit 有 7 个步骤

1. 添加 Retrofit 库的依赖
2. 创建接收服务器返回数据的类
3. 创建用于描述网络请求接口的类
4. 创建 Retrofit 实例
5. 创建网络请求接口实例并配置网络请求参数
6. 发送网络请求（异步/同步）
> Retrofit 封装了数据转换、线程切换的操作
7. 处理服务器返回数据



#### 添加依赖

在 Gradle 中添加依赖

```
implementation 'com.squareup.retrofit2:retrofit:2.4.0'  // retrofit
```

添加网络权限

```
<uses-permission android:name="android.permission.INTERNET"/>
```


#### 创建接收服务器返回数据的类

```java
public class OfficialAccounts {
    // 详细数据看后面的例1
}
```

#### 创建用于描述网络请求的接口

Retrofit 将网络请求抽象成 java 接口，采用注解的方式来定义。

> Retrofit 用动态代理的方式动态的将接口的注解转换成一个 Http 请求。

GetRequest_Interface.java

```java
public interface GetRequest_Interface {
    @GET("wxarticle/chapters/json")
    Call<OfficialAccounts> getOfficialAccounts();
}
```

注解类型包括：


![retrofit-1-001](F700FE33359D44518FD93E3395DCFBB2)

**第一类：网络请求方法**

![retrofit-1-002](D3FFC9AC90124CB9B9C7C5DAB3662411)


- Retrofit把 网络请求的URL 分成了两部分设置：

```java
// 第一部分
@GET("users/{user}/repos")
Call<List<Repo>> listRepos(@Path("user") String user);

// 第二部分 baseUrl
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();
```

-  `@HTTP` 作用：替换`@GET、@POST、@PUT、@DELETE、@HEAD` 注解的作用及更多功能拓展

```java
public interface GetRequest_Interface {
    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getCall(@Path("id") int id);
}
```

**第二类： 标记**

![retrofit-1-003](18E20BB9A0354EAAA5BB961F6B4FB3A8)

- `@FormUrlEncoded` 作用：表示发送form-encoded的数据,每个键值对需要用 `@Filed` 来注解键名


-  `@Multipart` 作用：表示发送form-encoded的数据（适用于有文件上传的场景）每个键值对需要用 `@Part` 来注解


```java
public interface GetRequest_Interface {
    /**
     *表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);
     
    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

}

// 具体使用
GetRequest_Interface service = retrofit.create(GetRequest_Interface.class);
// @FormUrlEncoded 
Call<ResponseBody> call1 = service.testFormUrlEncoded1("Carson", 24);

//  @Multipart
RequestBody name = RequestBody.create(textType, "Carson");
RequestBody age = RequestBody.create(textType, "24");

MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);

```


**第三类，网络请求参数**

![retrofit-1-004](F134289266CB49728BFA8E6949CBF509)


-  `@Header & @Headers` 作用：添加请求头 &添加不固定的请求头

```java
@GET("user")
Call<User> getUser(@Header("Authorization") String authorization)

@Headers("Authorization: authorization")
@GET("user")
Call<User> getUser()

// 以上的效果是一致的。
// 区别在于使用场景和使用方式
// 1. 使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
// 2. 使用方式：@Header作用于方法的参数；@Headers作用于方法
```


- `@Body` 作用：以 Post方式传递自定义数据类型 给服务器
特别注意：如果提交的是一个Map，那么作用相当于 `@Field`
不过 Map 要经过 `FormBody.Builder` 类处理成为符合 Okhttp 格式的表单，如：

```java
FormBody.Builder builder = new FormBody.Builder();
builder.add("key","value");
```

- `@Field & @FieldMap` 作用：发送 Post请求 时提交请求的表单字段
具体使用：与 `@FormUrlEncoded` 注解配合使用

```java
public interface GetRequest_Interface {
/**
 *表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
 * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
 */
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

/**
 * Map的key作为表单的键
 */
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

}

// 具体使用
// @Field
Call<ResponseBody> call1 = service.testFormUrlEncoded1("Carson", 24);

// @FieldMap
// 实现的效果与上面相同，但要传入Map
Map<String, Object> map = new HashMap<>();
map.put("username", "Carson");
map.put("age", 24);
Call<ResponseBody> call2 = service.testFormUrlEncoded2(map);
```

- `@Part & @PartMap` 作用：发送 Post请求时提交请求的表单字段。与@Field的区别：功能相同，但携带的参数类型更加丰富，包括数据流，所以适用于 有文件上传的场景, 与 @Multipart 注解配合使用。


```java
public interface GetRequest_Interface {

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);
}

// 具体使用
 MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "Carson");
        RequestBody age = RequestBody.create(textType, "24");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

        // @Part
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
        Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);
        ResponseBodyPrinter.printResponseBody(call3);

        // @PartMap
        // 实现和上面同样的效果
        Map<String, RequestBody> fileUpload2Args = new HashMap<>();
        fileUpload2Args.put("name", name);
        fileUpload2Args.put("age", age);
        //这里并不会被当成文件，因为没有文件名(包含在Content-Disposition请求头中)，但上面的 filePart 有
        //fileUpload2Args.put("file", file);
        Call<ResponseBody> call4 = service.testFileUpload2(fileUpload2Args, filePart); //单独处理文件
        ResponseBodyPrinter.printResponseBody(call4);
```

- `@Query & @QueryMap` 作用：用于 @GET 方法的查询参数（Query = Url 中 ‘?’ 后面的 key-value）

```java
@GET("/")    
Call<String> cate(@Query("cate") String cate);
```

- `@Path` 作用：URL地址的缺省值

```java
@GET("users/{user}/repos")
Call<ResponseBody>  getBlog（@Path("user") String user ）;
```

- `@Url` 作用：直接传入一个请求的 URL变量 用于URL设置

```java
@GET
Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);
// 当有URL注解时，@GET传入的URL就可以省略
// 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
```

#### 创建 Retrofit 实例

```java
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();
```

- 关于数据解析器（Converter）: Retrofit 支持多种数据解析方式，解析时需要在 Gradle 添加依赖。

![retrofit-1-005](F82AFF7941DD4F1F963E3BD3252A93BD)


- Retrofit支持多种网络请求适配器方式：guava、Java8 和 rxjava。使用时如果的是 Android 默认的 CallAdapter 不用额外添加依赖，其他的适配器都需要去 gradle 中添加依赖。


```
![retrofit-1-006](226E9FD97014405E86126BD6B2497015)
```

#### 创建网络请求接口

```java
// 创建 网络请求接口 的实例
GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

//对 发送请求 进行封装
Call<Reception> call = request.getCall();
```

#### 发送网络请求（异步、同步）

> 内部封装了数据转换、线程切换的操作

```java
//发送网络请求(异步)
call.enqueue(new Callback<OfficialAccounts>() {
    @Override
    public void onResponse(Call<OfficialAccounts> call, Response<OfficialAccounts> response) {
        // 处理返回的数据
        Log.i("RamonLee", response.body().toString());
    }

    @Override
    public void onFailure(Call<OfficialAccounts> call, Throwable t) {

    }
});

// 发送网络请求（同步）
Response<Reception> response = call.execute();
```

#### 处理返回数据

通过response类的 body（）对返回的数据进行处理

```java
//发送网络请求(异步)
call.enqueue(new Callback<OfficialAccounts>() {
    //请求成功时回调
    @Override
    public void onResponse(Call<OfficialAccounts> call, Response<OfficialAccounts> response) {
        // 对返回数据进行处理
        response.body().show();
    }

    //请求失败时候的回调
    @Override
    public void onFailure(Call<OfficialAccounts> call, Throwable throwable) {
        System.out.println("连接失败");
    }
});

// 发送网络请求（同步）
Response<Reception> response = call.execute();
// 对返回数据进行处理
response.body().show();
```

## 实例

下面两个实例分别使用 Retrofit get 和 Retrofit post 的方式进行网络请求。

#### 实例1： 使用 wanandroid 获取公众号列表

url: https://wanandroid.com/wxarticle/chapters/json 

json 数据格式

```
{
	"data": [{
		"children": [],
		"courseId": 13,
		"id": 408,
		"name": "鸿洋",
		"order": 190000,
		"parentChapterId": 407,
		"userControlSetTop": false,
		"visible": 1
	},
	{
		"children": [],
		"courseId": 13,
		"id": 409,
		"name": "郭霖",
		"order": 190001,
		"parentChapterId": 407,
		"userControlSetTop": false,
		"visible": 1
	}],
	"errorCode": 0,
	"errorMsg": ""
}
```

根据 json 数据我们创建实体类

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

创建用于描述网络请求的接口

`GetRequest_Interface.java`

```java
public interface GetRequest_Interface {

    @GET("wxarticle/chapters/json")
    Call<OfficialAccounts> getOfficialAccounts();
}
```

接下来我们在 `MainActivity.java` 使用接口，发送请求

```java
private void request() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://wanandroid.com/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) // 设置使用Gson解析(记得加入依赖)
            .build();

    // 创建网络请求接口
    GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

    // 对发送的请求进行封装
    Call<OfficialAccounts> call = request.getOfficialAccounts();

    // 异步发送网络请求
    call.enqueue(new Callback<OfficialAccounts>() {
        @Override
        public void onResponse(Call<OfficialAccounts> call, Response<OfficialAccounts> response) {
            // 处理返回的数据
            Log.i("RamonLee", response.body().toString());
        }

        @Override
        public void onFailure(Call<OfficialAccounts> call, Throwable t) {

        }
    });
}
```

运行结果(截取了部分)：

```
{data=[Data{children=[], courseId=13, id=408, name='鸿洋', order=190000, parentChapterId=407, userControlSetTop=false, visible=1}], errorCode=0, errorMsg=''}
```


#### 实例2： 使用有道 api 发送 post 请求翻译

接口文档

```java
// URL
http://fanyi.youdao.com/translate

// URL实例
http://fanyi.youdao.com/translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=


// 参数说明
// doctype：json 或 xml
// jsonversion：如果 doctype 值是 xml，则去除该值，若 doctype 值是 json，该值为空即可
// xmlVersion：如果 doctype 值是 json，则去除该值，若 doctype 值是 xml，该值为空即可
// type：语言自动检测时为 null，为 null 时可为空。英译中为 EN2ZH_CN，中译英为 ZH_CN2EN，日译中为 JA2ZH_CN，中译日为 ZH_CN2JA，韩译中为 KR2ZH_CN，中译韩为 ZH_CN2KR，中译法为 ZH_CN2FR，法译中为 FR2ZH_CN
// keyform：mdict. + 版本号 + .手机平台。可为空
// model：手机型号。可为空
// mid：平台版本。可为空
// imei：???。可为空
// vendor：应用下载平台。可为空
// screen：屏幕宽高。可为空
// ssid：用户名。可为空
// abtest：???。可为空

// 请求方式说明
// 请求方式：POST
// 请求体：i
// 请求格式：x-www-form-urlencoded
```

首先创建实体类

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

创建请求接口 `PostRequest_Interface.java`

```java
public interface PostRequest_Interface {
    //采用@Post表示Post方法进行请求（传入部分url地址）
    // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
    // 需要配合@Field 向服务器提交需要的字段
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Translation> getCall(@Field("i") String targetSentence);
}
```

接下来去调用

```java
private void postRequest() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build();

    // 创建 网络请求接口 的实例
    PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

    // 对发送请求进行封装(设置需要翻译的内容)
    Call<Translation> call = request.getCall("Tomorrow is Friday.");

    // 发送网络请求(异步)
    call.enqueue(new Callback<Translation>() {

        // 请求成功时回调
        @Override
        public void onResponse(Call<Translation> call, Response<Translation> response) {
            // 处理返回的数据结果：输出翻译的内容
            Log.i("RamonLee", response.body().getTranslateResult().get(0).get(0).getTgt());
        }

        // 请求失败时回调
        @Override
        public void onFailure(Call<Translation> call, Throwable throwable) {
            System.out.println("请求失败");
            System.out.println(throwable.getMessage());
        }
    });
}
```

运行结果：

```
RamonLee: 明天是星期五。
```