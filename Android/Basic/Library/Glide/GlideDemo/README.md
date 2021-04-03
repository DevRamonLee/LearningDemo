- [Android图片加载框架最全解析（八），带你全面了解Glide 4的用法](https://blog.csdn.net/guolin_blog/article/details/78582548)


## 简介

Glide 是一个开源的图片加载库，这篇文章『文章转载自郭霖的[文章](https://blog.csdn.net/guolin_blog/article/details/78582548)』主要讲解 Glide4 以下几个方面的内容

- 基本用法
- 缓存机制
- 回调与监听
- 图片变换
- 自定义模块


引入 Gradle，修改 `app/build.gradle` 文件

```
dependencies {
    implementation 'com.github.bumptech.glide:glide:4.4.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.4.0'
}
```

> 上面的 compiler 的库，这个库是用于生成 Generated API 的，下面会介绍到它的使用

我们还需要网络权限, 修改 `AndroidManifest.xml`

```
<uses-permission android:name="android.permission.INTERNET"/>
```

## 加载图片

接下来使用 Glide 来加载图片，例如图片地址 `http://guolin.tech/book.png`


我们来写一个布局，布局只包含一个 ImageView 和一个 Button（很简单，这里不贴了），然后我们在代码中点击按钮加载这张图片,代码非常简单！！

```
String url = "http://guolin.tech/book.png";
Glide.with(MainActivity.this).load(url).into(imageBook);
```

> Glide 加载图片分三步：先 with() 再 load() 最后 into()。 仔细观察我们的运行效果，你会发现第二次运行的时候几乎没有加载时间，这就是 Glide 强大的缓存功能。

## 占位图

首先我们准备一张占位图 `loading.png`, 接下来修改一下我们的代码

```
RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.loading);
Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

我们创建了一个 RequestOptions 对象，并为它指定了 placehoder，然后在加载图片的时候使用了 apply 方法将 RequestOptions 设置了进去。

运行代码，我们发现并没有看到占位图，因为 Glide 的缓存机制在第一次加载的时候就将图片缓存了下来，下一次再加载就直接从缓存中读取，因为加载速度非常快，所以可能看不到占位图，我们修改下代码，让我们有机会看到占位图

```
String url = "http://guolin.tech/book.png";
RequestOptions requestOptions = new RequestOptions()
        .placeholder(R.drawable.loading)
        .diskCacheStrategy(DiskCacheStrategy.NONE);     // 指定缓存策略

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

> 我禁用了缓存，但是没有明显效果，第二次加载还是很快

除了加载占位图，还有异常占位图，异常占位图是指由于一些原因导致图片加载失败时显示的图片, 通过 `error()` 指定

```
RequestOptions requestOptions = new RequestOptions()
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .diskCacheStrategy(DiskCacheStrategy.NONE);     // 指定缓存策略

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

> Glide 4 不同于 Glide3 的是它引入了一个 RequestOptions 对象

Glide 4 中引入 `RequestOptions` 对象，将这一系列的 API 都移动到了 `RequestOptions` 当中。这样做的好处是可以使我们摆脱冗长的 `Glide` 加载语句，而且还能进行自己的 API 封装，因为 `RequestOptions` 是可以作为参数传入到方法中的，我们可以做如下封装

```
public class GlideUtil {
    public static void load(Context context,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {
        Glide.with(context)
             .load(url)
             .apply(options)
             .into(imageView);
    }
}
```

## 指定图片大小

实际中，Glide 大多数情况下不需要我们指定图片大小，因为 Glide 会根据 ImageView 的大小来决定图片的大小，防止发生 OOM，但是如果我们必须给图片指定一个固定的大小，这也是可以做到的

```
RequestOptions requestOptions = new RequestOptions()
        .override(400, 400);

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

> 现在图片会将图片加载成 400 * 400 尺寸，而不会管 ImageView 的尺寸

如果我们要加载一张图片的原图可以使用 `Target.SIZE_ORIGINAL`,但这是有风险的，可能会导致 OOM

```
RequestOptions requestOptions = new RequestOptions()
        .override(Target.SIZE_ORIGINAL);

Glide.with(MainActivity.this)
        .load(url)
        .apply(requestOptions)
        .into(imageBook);
```

## 缓存机制

Glide 的缓存分为两个部分：

- **内存缓存**：内存缓存的主要作用是防止应用重复将图片数据读取到内存当中。
- **硬盘缓存**：硬盘缓存的主要作用是防止应用重复从网络或其他地方下载和读取数据。


Glide 默认是开启内存缓存的，当我们使用 Glide 加载一张图片之后，这张图片就会缓存在了内存缓存中，比如 RecyleView 使用 Glide 加载了图片，我们反复上下滑动就会直接从内存中读取数据，这样大大提高了加载效率。

我们不用做任何操作就享受了 Glide 的内存缓存效果，但是如果我们由于需求需要禁用内存缓存，Glide 也提供了接口

```
RequestOptions options = new RequestOptions()
        .skipMemoryCache(true);
```

我们上面使用占位图时使用了 `diskCacheStrategy()` 方法，这个方法就是 Glide 的硬盘缓存功能，它可以接收五种参数

- DisCacheStrategy.NONE: 表示不缓存任何内容
- DisCacheStrategy.DATA: 表示只缓存原始图片
- DisCacheStrategy.RESOUCE: 表示只缓存转换过后的图片
- DisCacheStrategy.ALL: 表示即缓存原始图片，也缓存转换过后的图片
- DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。

> 转换过后的图片什么意思呢? 就是当我们使用 Glide 去加载一张图片的时候，Glide 默认并不会将原始图片展示出来，而是会对图片进行压缩和转换, 经过这一系列后的操作的图片叫做转换图片。

## 指定加载格式

Glide 是支持加载 GIF 图片的，而 Picasso 是不支持的，而且 Glide 加载 GIF 图片是不需要编写额外的代码，Glide 内部会自动判断图片的格式，如替换掉我们的 url 为 `"http://guolin.tech/test.gif"` 就可以直接显示 GIF 图片了。

如果我们不需要 Glide 为我们自动判断图片的类型，我们就是要加载一张静态的图片，可以使用 `asBitmap()` 方法， 这个方法表示只允许加载静态图片，如果传入的是一个 GIF 图片，则只会显示它的第一张

```
Glide.with(this)
     .asBitmap()
     .load("http://guolin.tech/test.gif")
     .into(imageView);
```

> 注意：这里是有坑的，Glide3 是先 load 再 asBitmap，Glide4 是先 asBitmap 再 load，写错了是会报错的。`asGif()` 强制指定加载动态图片。而Glide4 中又新增了`asFile()`方法和 `asDrawable()` 方法，分别用于强制指定文件格式的加载和Drawable格式的加载


## 回调与监听

#### Into 方法

Glide 的 `into()` 方法是传入一个 ImageView 的，我们可以传入其他参数吗？答案是肯定的，这里需要用到自定义 Target 功能。

这里使用一个简单的 SimpleTarget

```
SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
    @Override
    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
        imageBook.setImageDrawable(resource);
    }
};

String url = "http://guolin.tech/test.gif";

Glide.with(MainActivity.this)
        .load(url)
        .into(simpleTarget);
```

#### Preload 方法

如果我们想要对图片进行一个预加载，等到需要使用的时候直接去从缓存读取，而不是从网络加载，Glide 给我们提供了预加载接口 preload 方法

`preload()` 方法有两个方法重载，一个不带参数，表示将会加载图片的原始尺寸，另一个可以通过参数指定加载图片的宽和高。

使用非常简单，直接替换 into 方法就可以

```
Glide.with(this)
     .load("http://guolin.tech/book.png")
     .preload();    
```

> 调用了 preload 方法之后，下次在加载图片就会从缓存中取读取

#### submit 方法

`submit()` 方法其实就是对应的 Glide 3 中的 `downloadOnly()` 方法,和 preload 方法一样，它也可以替换 into 方法，submint 会下载图片，在图片下载完之后，我们可以得到图片的存储路径。submit 方法也有两个方法重载

- submit(): 下载原始尺寸
- submit(int width, int height)： 指定下载图片的尺寸

例： 下载一张图片，并显示路径

```
 new Thread(
        new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://www.guolin.tech/book.png";
                    final Context context = getApplicationContext();
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();  // submit 方法返回一个 FutureTarget 对象
                    final File imageFile = target.get();    // 获取文件，如果没有下载完则会阻塞，直到下载完毕
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e) {

                }
            }
        }
).start();
```

解读下上面的代码：

- `submit()` 方法必须要用在子线程当中, 因为 FutureTarget 的 get 方法会阻塞线程
- 使用 Application Context，这个时候不能再用 Activity 作为 Context 了，因为会有 Activity 销毁了但子线程还没执行完这种可能出现。
- 将 `into()` 方法替换成了 `submit()` 方法，并且还使用了一个 `asFile()` 方法来指定加载格式


#### listener 方法

listener 用来监听图片加载的状态，比如我们使用 preload 与加载图片，我们怎么知道加载成功还是失败呢?此时就可以使用 listener，它的基本用法如下：

```
Glide.with(this)
     .load("http://www.guolin.tech/book.png")
     .listener(new RequestListener<Drawable>() {
         @Override
         public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
             return false;
         }

         @Override
         public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
             return false;
         }
     })
     .into(imageView);
```

- onResourceReady()方法: 图片加载成功会回调。
- onLoadFailed()方法: 图片加载失败会回调。


> 注意上面的方法中我们都返回了 false，它代表这个事件没有被处理，还会继续向下传递，如果返回了 true 就代表事件被处理掉了，不会再向下传递例如：如果我们给 into 指定了 target，如果返回 true，那么 target 的 onResourceReady 就不会回调了。


## 图片变换

图片变换的意思是 Glide 从加载原始图片到显示之前，又进行了一些处理，如圆角、圆形、模糊等。

添加图片变换非常简单，只需要在 `RequestOptions` 中串接 `transforms()` 方法，并将需要进行的图片变换操作作为参数传入。

```
RequestOptions options = new RequestOptions()
        .transforms(...);
Glide.with(this)
     .load(url)
     .apply(options)
     .into(imageView);
```

具体要进行什么样的变换，通常使由我们自己实现的，Glide 已经内置了几种常见的变换，如： CenterCrop、FitCenter、CircleCrop 等。

我们可以直接调用封装好的 API （其实内部封装了 transform）

```
RequestOptions options = new RequestOptions()
        .centerCrop();

RequestOptions options = new RequestOptions()
        .fitCenter();

RequestOptions options = new RequestOptions()
        .circleCrop();
```

例： 实现一个圆形效果

```
RequestOptions options = new RequestOptions()
        .circleCrop();

Glide.with(context)
        .load(url)
        .apply(options)
        .into(imageBook);
```


> [glide-transformations](https://github.com/wasabeef/glide-transformations)。它实现了很多通用的图片变换效果，如裁剪变换、颜色变换、模糊变换等等，使得我们可以非常轻松地进行各种各样的图片变换。

## 自定义模块



## 使用Generated API

Generated API 是 Glide 4 中全新引入的一个功能，它的工作原理是使用注解处理器 (Annotation Processor) 来生成出一个API，在 Application 模块中可使用该流式 API 一次性调用到 RequestBuilder，RequestOptions 和集成库中所有的选项。

这么解释有点拗口，简单点说，就是 Glide 4仍然给我们提供了一套和 Glide 3 一模一样的流式 API 接口。

Generated API 基本和 Glide3 用法一致，不过需要把 Glide 替换为 GlideApp 关键字

```
GlideApp.with(this)
        .load(url)
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .override(Target.SIZE_ORIGINAL)
        .circleCrop()
        .into(imageView);
```

GlideApp 这个类是编译器自动生成的，为了生成这个类，首先要确保我们的有一个自定义的模块，并且给它加上了 `@GlideModule` 注解, 有了它之后，在 AS 上 build rebuild 就会生成 GlideApp 类

Generated API 的用法不止这一点，它还可以对 API 进行扩展，定制属于你的 API

例： 比如我们要求项目中所有图片的缓存策略都要使用原始图片，那么每次加载的时候都要指定  `diskCacheStrategy(DiskCacheStrategy.DATA)` 这样比较麻烦，我们可以去指定一个自己的 api


定制自己的API需要借助@GlideExtension和@GlideOption这两个注解。创建一个我们自定义的扩展类

```
@GlideExtension
public class MyGlideExtension {

    private MyGlideExtension() {

    }

    @GlideOption
    public static void cacheSource(RequestOptions options) {
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
    }

}
```

上面这个类存在很多限制规则，如下：

- 类要加上 `@GlideExtension` 注解，然后要将这个类的构造函数声明成 private
- 自定义 api 必须使用 `@GlideOption` 注解。注意自定义 API 的方法都必须是静态方法，而且第一个参数必须是 `RequestOptions`，后面你可以加入任意多个你想自定义的参数。
- `cacheSource()` 方法中，我们仍然还是调用的 `diskCacheStrategy(DiskCacheStrategy.DATA)`方法，所以说 `cacheSource()` 就是一层简化 API 的封装而已。


最后在 AS 上 build - rebuild project 我们就可以使用这个自定义的 api 了

```
GlideApp.with(this)
        .load(url)
        .cacheSource()
        .into(imageView);
```