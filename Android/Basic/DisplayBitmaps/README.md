- http://hukai.me/android-training-course-in-chinese/graphics/displaying-bitmaps/index.html
- [DiskLruCache的简单用法](http://blog.csdn.net/piglite/article/details/50683028)
- [Android 那些你所不知道的Bitmap对象详解]( http://blog.csdn.net/xiaanming/article/details/41084843)

## 知识储备

先来了解一些图片方面相关的知识点。

#### 读取位图的尺寸与类型

设置 `inJustDecodeBounds` 属性为 true 可以在解码的时候避免内存的分配，它会返回一个 null 的 Bitmap，但是可以获取到 `outWidth, outHeight` 与 `outMimeType`

```
BitmapFactory.Options options = new BitmapFactory.Options();
options.inJustDecodeBounds = true;

// 除了decodeResource 外还有 decodeByteArray(), decodeFile() 用于从不同资源中构建一个Bitmap对象
BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
int imageHeight = options.outHeight;
int imageWidth = options.outWidth;
String imageType = options.outMimeType;
```

#### 一个Bitmap占用多大的内存，系统给每个应用程序分配多大内存

Bitmap 占用的内存公式为：`像素总数 * 每个像素占用的内存`。

Bitmap有四种像素类型：
- ARGB_8888: 4 byte
- ARGB_4444: 2 byte
- ARGB_565:  2 byte
- ALPHA_8:   1 byte

> 例：512 x 384 的 ARGB_8888 类型的Bitmap占用的内存为
`512 * 384 * 4 = 0.75MB`

Android根据设备屏幕尺寸和 dpi 的不同，给应用程序分配的内存大小也不同

|屏幕尺寸|	DPI|	应用内存
|--|:-:|:-:|
|small / normal / large	|ldpi / mdpi|	16MB
|small / normal / large|	tvdpi / hdpi|	32MB
|small / normal / large|	xhdpi|	64MB
|small / normal / large|	400dpi|	96MB
|small / normal / large|	xxhdpi|	128MB
|xlarge	|mdpi|	32MB
|xlarge	|tvdpi / hdpi|	64MB
|xlarge	|xhdpi|	128MB
|xlarge|	400dpi|	192MB
|xlarge|	xxhdpi	|256MB

**dpi 简介**： dpi 表示屏幕像素点密度，表示每英寸屏幕上的像素点的个数

|名称|	像素密度范围|
|-|:-:|
|mdpi|	120dpi~160dpi
|hdpi|	160dpi~240dpi
|xhdpi|	240dpi~320dpi
|xxhdpi|	320dpi~480dpi
|xxxhdpi|	480dpi~640dpi


可以用这样一个简单的公式计算像素和 dp 的转换：`pixels = dips * (density / 160)`

一般在160dpi的屏幕上，1dp = 1px 因为 1px = 1dp *(160dpi /160)

## 加载一个按比例缩小的Bitmap到内存中

下面这段代码根据目标图片大小来计算 Sample 图片大小：

```
public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // 获取图片的原始宽高
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // 设置 inSampleSize 为 2 的幂是因为解码器最终还是会对非 2 的幂的数进行向下处理，获取到最靠近2的幂的数
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }
    return inSampleSize;
}
```

为了使用该方法，首先需要设置 `inJustDecodeBounds` 为 true, 把 options 的值传递过来，然后设置 `inSampleSize`的值并设置 `inJustDecodeBounds` 为 false，之后重新调用相关的解码方法。

```
public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options);
    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeResource(res, resId, options);
}
```
给ImageView设置缩略图

```
sampleImg.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.img, 368, 495));
```

## 非 UI 线程处理 Bitmap (并发问题）

**问题**：在 `ListView` 或者 `GridView` 上使用 `AsyncTask` 加载图片时，由于为了效率，`ListView` 或 `GridView` 会在用户滑动屏幕时循环使用 item，如果每一个 item 都触发一个 `AsyncTask` 任务，则无法保证当 item 被重用时，它前一个触发的 `AsyncTask` 已经执行完了，此时看到的现象就是图片显示会混乱，比如第五个 item 先显示第一个 item 绑定的图片（由于第五个重用了第一个item对象），然后再显示第五个的图片。

上面的现象就是并发加载图片的问题，下面将介绍如何处理这种并发带来的问题。

- 创建一个 Drawable 的子类来存储异步任务的引用。
```
static class AsyncDrawable extends BitmapDrawable {
    private final WeakReference bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
        super(res, bitmap);
        // 存储任务的引用
        bitmapWorkerTaskReference = new WeakReference(bitmapWorkerTask);
    }

    public BitmapWorkerTask getBitmapWorkerTask() {
        return (BitmapWorkerTask) bitmapWorkerTaskReference.get();
    }
}
```
- 在执行异步任务之前，创建一个 `AsyncDrawable` 并设置到 `imageView` 中，把异步任务和该 `AsyncDrawable` 对象绑定。

```
// 在执行 BitmapWorkerTask 之前，你需要创建一个 AsyncDrawable 并且将它绑定到目标控件 ImageView 中
public void loadBitmap(int resId, ImageView imageView) {
    if (cancelPotentialWork(resId, imageView)) {
        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        // 创建一个 AsyncDrawable 并绑定到 imageView 上
        final AsyncDrawable asyncDrawable =
                new AsyncDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), task);
        imageView.setImageDrawable(asyncDrawable);      // 显示占位图
        task.execute(resId);
    }
}
```
- `cancelPotentialWork` 方法检查是否有另一个正在执行的任务与该 ImageView 关联了起来，如果的确是这样，它通过执行 `cancel()` 方法来取消另一个任务。在少数情况下, 新创建的任务数据可能会与已经存在的任务相吻合，这样的话就不需要进行下一步动作了

```
// 判断当前的 ImageView 是否有任务正在执行，如果有则设置 AsyncTask 的状态为取消
public static boolean cancelPotentialWork(int data, ImageView imageView) {
    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

    if (bitmapWorkerTask != null) {
        final int bitmapData = bitmapWorkerTask.data;
        if (bitmapData == 0 || bitmapData != data) {
            // 调用 cancel() 其实是给 AsyncTask 设置一个 "canceled" 状态
            bitmapWorkerTask.cancel(true);
        } else {
            // The same work is already in progress
            return false;
        }
    }
    // No task associated with the ImageView, or an existing task was cancelled
    return true;
}
```

`getBitmapWorkerTask()`，用来检索 ImageView 已经分配到的 AsyncTask 任务。

```
// 获取 imageView 绑定的异步任务
private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
    if (imageView != null) {
        final Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AsyncDrawable) {
            final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
            return asyncDrawable.getBitmapWorkerTask();
        }
    }
    return null;
}
```

- 在 AsyncTask 的 `onPostExecute()` 方法中执行更新。
 
```
@Override
protected void onPostExecute(Bitmap bitmap) {
    // 判断任务是否被取消了
    if (isCancelled()) {
        bitmap = null;
    }

    if (bitmap != null) {
        final ImageView imageView = (ImageView) imageViewReference.get();
        final BitmapWorkerTask bitmapWorkerTask =
                getBitmapWorkerTask(imageView);
        if (this == bitmapWorkerTask) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
```

## 使用 LruCache 内存缓存

内存缓存以花费宝贵的内存为代价来快速访问图片，`LruCache` 使用一个强引用的(strong referenced)的 `LinkedHashMap` 保存最近引用的对象，并且在缓存超出设置大小的时候剔除最近最少使用到的对象。

缓存大小我们需要分析实际的情况来给出，缓存太小会导致额外的花销却没有明显的好处，缓存太大同样会导致 `java.lang.OutOfMemory` 的异常，并且使得你的程序只留下小部分的内存用来工作（缓存占用太多内存，导致其他操作会因为内存不够而抛出异常）。


> 注意： 这里我遇到一个问题，就是当加载的图片像素较大时，一个手机屏幕的图片内存可能已经超过了分配的缓存大小，此时滑动的话消失的图片会被从内存缓存中释放掉来存储屏幕新加载的图片，所以此时的缓存不但浪费了内存而且没有起到快速访问图片的效果。因此使用内存缓存时，一定要综合考虑各种情况（应用剩余内存；一个屏幕显示多少图片，有多少图片需要提前准备好；设备密度；图片大小；图片访问频率）

- 创建内存缓存
```
private LruCache<String, Bitmap> mMemoryCache;
 @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cache_bitmap);

    // 获取 VM 最大可用内存
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // 使用最大可用内存的 1/8 作为缓存大小。这里有个问题，如果设置的缓存过小而单个图片过大的话，会看不到缓存的效果
    final int cacheSize = maxMemory / 8;
    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // 计算每次添加图片的大小,注意单位和 cacheSize 保持一致
            return bitmap.getByteCount() / 1024;
        }
    };
}
```

- 添加和获取Bitmap对象

```
// 向缓存中添加图片
public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
    if (getBitmapFromMemCache(key) == null) {
        mMemoryCache.put(key, bitmap);
    }
}

// 从缓存读取图片
public Bitmap getBitmapFromMemCache(String key) {
    return mMemoryCache.get(key);
}
```

- 在加载一个图片前，首先从缓存中获取这个图片，如果获取到了则直接设置，如果没有获取到，则触发后台线程来处理图片加载任务。

```
public void loadBitmap(int resId, ImageView imageView) {
    if (cancelPotentialWork(resId, imageView)) {
        // 从缓存读取图片,如果找到了则直接加载，没有找到则执行异步任务加载
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            Log.e("RamonLee", "set from cache memory");
            imageView.setImageBitmap(bitmap);
        } else {
            final CacheBitmapActivity.BitmapWorkerTask task = new CacheBitmapActivity.BitmapWorkerTask(imageView);

            final CacheBitmapActivity.AsyncDrawable asyncDrawable =
                    new CacheBitmapActivity.AsyncDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }
}
```

- 在 BitmapWorkTask 中需要把处理好的图片加载到内存缓存中。

```
class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    ...
    @Override
    protected Bitmap doInBackground(Integer... params) {
        final Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), params[0], 100, 100));
        addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
        return bitmap;
    }
    ...
}
```

## 使用 [DiskLruCache](https://github.com/JakeWharton/DiskLruCache) 硬盘缓存

- 首先获取硬盘缓存目录，优先尝试使用外部存储，如果没有则使用内部存储

```
public static File getDiskCacheDir(Context context, String uniqueName) {
    // 判断外部存储是否存在，存在的话使用外部存储，不存在则使用内部存储
    final String cachePath =
            Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                    !isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
                    context.getCacheDir().getPath();
    return new File(cachePath + File.separator + uniqueName);
}
```
- 创建和打开硬盘缓存目录，因为是文件读取操作，所以我们把它放在异步线程里。

```
private DiskLruCache mDiskLruCache;
private final Object mDiskCacheLock = new Object();
private boolean mDiskCacheStarting = true;

/*初始化DiskLruCache */
class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
    @Override
    protected Void doInBackground(File... params) {
        synchronized (mDiskCacheLock) {
            File cacheDir = params[0];
            try {
                mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE);
                mDiskCacheStarting = false; // Finished initialization
                mDiskCacheLock.notifyAll(); // Wake any waiting threads
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
```

解释一下上面的代码：由于硬盘缓存初始化不是在主线程中完成的，所以在初始化完成之前，缓存可以被访问，所以我们要使用 `synchronized(mDiskCacheLock)` 对象锁来确保在缓存完成初始化之前，应用无法对其进行读取。

`DiskLruCache.open(File file, int version,int number,int maxsize)`

    - file 缓存目录
    - version 应用版本号
    - number 这里指一个 key 对应几个缓存内容，一般是一张图片1
    - maxsize 硬盘缓存的大小

- 添加图片到硬盘缓存
先获得一个 Editor 对象，然后通过 Editor 对象缓存内容

```
DiskLruCache.Editor editor = mDiskLruCache.edit(key);
```

调用 editor 的 `newOutputStream` 方法。获得一个输出流对象。将要缓存的内容以 `InputStream` 的方式流到该 `outputStream` 对象中。` newOutputStream` 方法需要一个 int 参数，这个参数的意思是：**创建 editor 对象的时候需要一个 key，而创建 DiskLruCache 对象的时候指定了一个 key 对应几个缓存内容，这里的 int 值指的就是，接下来即将缓存的内容是该 key 对应的第几个内容。如果一个 key 只对应一个缓存内容，则这里传入0**


```
Outputstream out = editor.newOutputStream(0);
bitmap.compress(Compress.JPEG,100,out);
```

流操作完毕，需要提交

```
editor.commit();
```

提交日志文件

```
mDiskLruCache.flush();
```

完整代码如下：
```
// 添加到硬盘缓存
try {
    synchronized (mDiskCacheLock) {
        if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream out = editor.newOutputStream(0);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                editor.commit();
            }
            mDiskLruCache.flush();
        }
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

- 读取硬盘缓存图片
利用 `DiskLruCache` 的 `get` 方法获得指定 key 对应的内容，但是该内容是以 `DisrkLruCache.Snapshot` 类型来呈现的

```
Snapshot snap = cache.get(key);
```

要获取 `Snapshot` 中的具体内容，需要调用 `Snapshot` 的 `getInputStream`方法获得一个输入流，以 IO 流的方式获得具体内容。`getInputStream` 方法同样需要一个 int 类型的参数，该参数的意义与之前 Editor 对象获得输出流时提供的参数意义相同，不再赘述。

```
InputStream in = snap.getInputStream(0);
```
利用 `BitmapFactory` 来进行获取图片

```
Bitmap bitmap = BitmapFactroy.decodeStream(in);
```
完整代码如下：
```
public Bitmap getBitmapFromDiskCache(String key) {
    synchronized (mDiskCacheLock) {
        // 硬盘缓存如果正在写的话执行等待操作
        while (mDiskCacheStarting) {
            try {
                mDiskCacheLock.wait();
            } catch (InterruptedException e) {
            }
        }
        try {
            DiskLruCache.Snapshot snap = mDiskLruCache.get(key);
            if (mDiskLruCache != null && snap != null) {
                InputStream in = snap.getInputStream(0);
                Log.e("RamonLee"," get form disk cache.");
                return BitmapFactory.decodeStream(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    return null;
}
```

> **注意** :对磁盘的读写操作都不应该放在UI线程中，而要放在异步线程中。

## 处理配置改变

我们知道在屏幕旋转时 Activity 会被销毁然后重绘，为了避免在重绘时重新处理所有的图片，我们可以调 `setRetainInstance(true)` 保留一个 Fragment 实例的方法把缓存传递给新的 Activity

> 注意： 设置了`setRetainInstance(true)`后该fragment 在屏幕旋转时就不会被重绘

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    // 创建一个 fragment
    RetainFragment retainFragment =
            RetainFragment.findOrCreateRetainFragment(getFragmentManager());
    // 从 fragment 里获取缓存
    mMemoryCache = retainFragment.mRetainedCache;
    if (mMemoryCache == null) {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        retainFragment.mRetainedCache = mMemoryCache;
    }

    imgList = (ListView) findViewById(R.id.img_list);
    HandleCfgChangesActivity.ListAdapter listAdapter = new HandleCfgChangesActivity.ListAdapter(HandleCfgChangesActivity.this);
    imgList.setAdapter(listAdapter);
}
```

`RetainFragment.java`

```
public class RetainFragment extends Fragment {
    private static final String TAG = "RetainFragment";
    public LruCache<String, Bitmap> mRetainedCache;

    public RetainFragment() {}

    public  static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
        RetainFragment fragment = (RetainFragment) fm.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new RetainFragment();
            fm.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为 true 后，该fragment在屏幕旋转后不会被重绘
        setRetainInstance(true);
    }
}
```

## 管理 Bitmap 的内存使用

#### Android 管理 Bitmap 的演变过程

在 Android 2.2 (API level 8)以及之前，当垃圾回收发生时，应用的线程是会被暂停的，这会导致一个延迟滞后，并降低系统效率。 从Android 2.3开始，添加了并发垃圾回收的机制， 这意味着在一个 Bitmap 不再被引用之后，它所占用的内存会被立即回收。

在 Android 2.3.3 (API level 10) 以及之前, 一个 Bitmap 的像素级数据（pixel data）是存放在 Native 内存空间中的。 这些数据与 Bitmap 本身是隔离的，Bitmap 本身被存放在 Dalvik 堆中。我们无法预测在 Native 内存中的像素级数据何时会被释放，这意味着程序容易超过它的内存限制并且崩溃。我们可以调用 `recycle()`方法来对 `Native Memory` 中的像素数据进行释放，前提是你可以清楚的确定 Bitmap 已不再使用了，如果你调用了 Bitmap 对象 `recycle()` 之后再将 Bitmap 绘制出来，就会出现

```
Canvas: trying to use a recycled bitmap
```

自 Android 3.0 (API Level 11)开始， 像素级数据则是与 Bitmap 本身一起存放在 Dalvik 堆中，所以我们不用手动调用 `recycle()` 来释放 bitmap 对象，内存的释放都交给垃圾回收器来做，也许你会问，为什么我在显示Bitmap对象的时候还是会出现 `OutOfMemoryError` 呢？

假如系统启动了垃圾回收线程去收集垃圾，而此时我们一下子产生大量的 Bitmap 对象，此时是有可能会产生 `OutOfMemoryError`，因为垃圾回收器首先要判断某个对象是否还存活(JAVA 语言判断对象是否存活使用的是根搜索算法 GC Root Tracing, 可达性分析)，然后利用垃圾回收算法来对垃圾进行回收，不同的垃圾回收器具有不同的回收算法，这些都是需要时间的， 发生 `OutOfMemoryError`的时候，我们要明确到底是因为内存泄露(Memory Leak)引发的还是内存溢出(Memory overflow)引发的，如果是内存泄露我们需要利用工具(比如MAT)查明内存泄露的代码并进行改正，如果不存在泄露，换句话来说就是内存中的对象确实还必须活着，那我们可以看看是否可以通过某种途径，减少对象对内存的消耗，比如我们在使用 Bitmap 的时候，应该根据 View 的大小利用 `BitmapFactory.Options` 计算合适的 `inSimpleSize` 来对 Bitmap 进行相对应的裁剪，以减少 Bitmap 对内存的使用，如果上面都做好了还是存在 `OutOfMemoryError`(一般这种情况很少发生)的话，那我们只能调大 Dalvik heap 的大小了，在 Android 3.1 以及更高的版本中，我们可以在 `AndroidManifest.xml` 的 `<application>` 标签中增加

```
android:largeHeap="true"
```

该属性来通知 Dalvik 虚拟机应用程序需要使用较大的 Java Heap。

## Android2.3 及以下管理Bitmap

在Android 2.3 及以下我们使用 `recycle()` 方法来释放内存，我们在使用 ListView 或者GridView 的时候，什么时候去调用 `recycle()` 呢，我们采用引用计数，使用一个变量 `displayRefCount` 来记录 Bitmap 显示情况，如果 Bitmap 绘制在View上面 `displayRefCount` 加一，否则就减一，只有在 `displayRefCount` 为0 且 Bitmap 不为空且 Bitmap 没有调用过 `recycle()` 的时候，我们才需要对该 Bitmap 对象进行 `recycle()`,所以我们需要用一个类来包装Bitmap对象。


```
public class RecycleBitmapDrawable extends BitmapDrawable {
    private int displayResCount = 0;
    private boolean mHasBeenDisplayed;

    public RecycleBitmapDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
    }


    /**
     * @param isDisplay
     */
    public void setIsDisplayed(boolean isDisplay){
        synchronized (this) {
            if(isDisplay){
                mHasBeenDisplayed = true;
                displayResCount ++;
                android.util.Log.i("meng","displayResCount ++ is " + displayResCount);
            }else{
                displayResCount --;
                android.util.Log.i("meng","displayResCount -- is " + displayResCount);
            }
        }
        checkState();
    }

    /**
     * 检查图片的一些状态，判断是否需要调用recycle
     */
    private synchronized void checkState() {
        if (displayResCount <= 0 && mHasBeenDisplayed
                && hasValidBitmap()) {
            android.util.Log.i("meng","call recycle");
            getBitmap().recycle();
        }
    }


    /**
     * 判断Bitmap是否为空且是否调用过recycle()
     * @return
     */
    private synchronized boolean hasValidBitmap() {
        Bitmap bitmap = getBitmap();
        return bitmap != null && !bitmap.isRecycled();
    }
}
```

除了上面这个 `RecycleBitmapDrawable` 之外，我们还需要自定义一个 `ImageView` 来使用这个 Bitmap，什么时候显示以及什么时候隐藏。

```
public class RecycleImageView extends AppCompatImageView {
    public RecycleImageView(Context context) {
        super(context);
    }

    public RecycleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        Drawable previousDrawable = getDrawable();
        super.setImageDrawable(drawable);

        //显示新的drawable
        notifyDrawable(drawable, true);

        //回收之前的图片
        notifyDrawable(previousDrawable, false);
    }

    @Override
    protected void onDetachedFromWindow() {
        //当View从窗口脱离的时候,清除drawable
        setImageDrawable(null);
        super.onDetachedFromWindow();
    }

    /**
     * 通知该drawable显示或者隐藏
     *
     * @param drawable
     * @param isDisplayed
     */
    public static void notifyDrawable(Drawable drawable, boolean isDisplayed) {
        if (drawable instanceof RecycleBitmapDrawable) {
            ((RecycleBitmapDrawable) drawable).setIsDisplayed(isDisplayed);
        } else if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            for (int i = 0, z = layerDrawable.getNumberOfLayers(); i < z; i++) {
                notifyDrawable(layerDrawable.getDrawable(i), isDisplayed);
            }
        }
    }
}
```

这个自定类也比较简单，重写了 `setImageDrawable()` 方法，在这个方法中我们先获取 ImageView 上面的图片，然后通知之前显示在 ImageView 的 Drawable 不再显示了，`Drawable`会判断是否需要调用 `recycle()`，当 View 从 Window 脱离的时候会回调 `onDetachedFromWindow()`，我们在这个方法中回收显示在 ImageView 的图片，

具体的使用方法如下：
创建一个布局文件，通过两个按钮进行上一张和下一张图片之间的切换，在切换的时候我们进行 bitmap 内存的回收。

```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="google.trainging.com.bitmapandanimation.memory.RecycleBitmapActivity">
    <google.trainging.com.bitmapandanimation.memory.RecycleImageView
        android:layout_width="368dp"
        android:layout_height="400dp"
        android:id="@+id/recycleImageView"
        android:layout_marginLeft="8dp"
        app:layout_constraintLeft_toLeftOf="parent"
        android:layout_marginRight="8dp"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_marginTop="8dp" />

    <Button
        android:id="@+id/previous"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="100dp"
        android:text="previous"
        app:layout_constraintTop_toBottomOf="@+id/recycleImageView"
        app:layout_constraintLeft_toLeftOf="parent" />

    <Button
        android:id="@+id/next"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="next"
        app:layout_constraintTop_toBottomOf="@+id/recycleImageView"
        app:layout_constraintLeft_toRightOf="@+id/previous" />

</android.support.constraint.ConstraintLayout>
```

创建Activity文件

```
public class RecycleBitmapActivity extends AppCompatActivity
                                    implements View.OnClickListener{

    private Button nextBtn;
    private Button previousBtn;
    private ImageView mImg;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_bitmap);
        init();
    }
    protected void init(){
        nextBtn = (Button) findViewById(R.id.next);
        previousBtn = (Button) findViewById(R.id.previous);
        mImg = (ImageView) findViewById(R.id.recycleImageView);
        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        loadBitmap(BitmapUtil.imgLists[0],mImg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous:
                index--;
                if(index >= 0) {
                    loadBitmap(BitmapUtil.imgLists[index], mImg);
                }else {
                    index = 0;
                }
                break;
            case R.id.next:
                index++;
                if(index < BitmapUtil.imgLists.length) {
                    loadBitmap(BitmapUtil.imgLists[index], mImg);
                }else{
                    index = BitmapUtil.imgLists.length - 1;
                }
                break;
        }
    }

    /*在非UI线程处理加载图片*/
    class BitmapWorkerTask extends AsyncTask<Integer,Nullable,Bitmap> {
        private final WeakReference imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            /*使用一个弱引用来确保ImageView可以被回收*/
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            /*在非UI线程中加载图片*/
            return BitmapUtil.decodeSampledBitmapFromResource(getResources(), data , 368, 495);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageDrawable(new RecycleBitmapDrawable(getResources(),bitmap));
                }
            }
        }
    }

    /*开始异步加载位图*/
    public void loadBitmap(int resId, ImageView imageView) {
        RecycleBitmapActivity.BitmapWorkerTask task = new RecycleBitmapActivity.BitmapWorkerTask(imageView);
        task.execute(resId);
    }
}
```

## Android 3.0 以上管理 Bitmap

由于在 Android3.0 及以上的版本中，Bitmap 的像素数据也存储在 Dalvik heap 中，所以内存的管理就直接交给垃圾回收器了，我们并不需要手动的去释放内存。

下面讲解 `BitmapFactory.Options.inBitmap` 的这个字段，假如这个字段被设置了，我们在解码 Bitmap 的时候，他会去重用 `inBitmap`设置的 Bitmap 对应的内存，而不会重新去给这个 Bitmap 分配内存，这样就减少内存的分配和释放，提高了应用的性能。

> 注意：使用这个属性时inMutable 属性需要设置为true.

在 Android 4.4 之前，`BitmapFactory.Options.inBitmap` 设置的 Bitmap 必须和我们需要解码的 Bitmap 的宽高一致，并且 `inSampleSize = 1`

在Android4.4以后，`BitmapFactory.Options.inBitmap` 设置的 Bitmap 大于或者等于我们需要解码的 Bitmap 的大小就 OK 了。

假设我们在 `ListView` 中加载大量的图片，为了提高应用的效率，我们通常会做相对应的内存缓存和硬盘缓存，这里我们只使用内存缓存，我们使用官方推荐的LruCache, 代码如下：


```
public class ImageCache {
    private final static int MAX_MEMORY = 9 * 1024 * 1024; // 3 MB
    private LruCache<String, BitmapDrawable> mMemoryCache;

    private Set<SoftReference<Bitmap>> mReusableBitmaps;

    protected void init() {
        if (hasHoneycomb()) {
            mReusableBitmaps = Collections
                    .synchronizedSet(new HashSet<SoftReference<Bitmap>>());
        }

        mMemoryCache = new LruCache<String, BitmapDrawable>(MAX_MEMORY) {
            @Override
            protected int sizeOf(String key, BitmapDrawable bitmap) {
                Log.i("meng","size of is " + bitmap.getBitmap().getByteCount());
                return bitmap.getBitmap().getByteCount();
            }

            /**
             * 当保存的BitmapDrawable对象从LruCache中移除出来的时候回调的方法
             */

            @Override
            protected void entryRemoved(boolean evicted, String key,
                                        BitmapDrawable oldValue, BitmapDrawable newValue) {

                if (hasHoneycomb()) {
                    Log.i("meng","mReusableBitmaps add");
                    mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue
                            .getBitmap()));
                }
            }

        };
    }


    /**
     * 从mReusableBitmaps中获取满足 能设置到BitmapFactory.Options.inBitmap上面的Bitmap对象
     * @param options
     * @return
     */
    public Bitmap getBitmapFromReusableSet(BitmapFactory.Options options) {
        Bitmap bitmap = null;

        if (mReusableBitmaps != null && !mReusableBitmaps.isEmpty()) {
            synchronized (mReusableBitmaps) {
                final Iterator<SoftReference<Bitmap>> iterator = mReusableBitmaps
                        .iterator();
                Bitmap item;

                while (iterator.hasNext()) {
                    item = iterator.next().get();

                    if (null != item && item.isMutable()) {
                        if (canUseForInBitmap(item, options)) {
                            bitmap = item;
                            iterator.remove();
                            break;
                        }
                    } else {
                        iterator.remove();
                    }
                }
            }
        }
        return bitmap;
    }

    /**
     * 判断该Bitmap是否可以设置到BitmapFactory.Options.inBitmap上
     *
     * @param candidate
     * @param targetOptions
     * @return
     */
    public static boolean canUseForInBitmap(Bitmap candidate,
                                            BitmapFactory.Options targetOptions) {

        // 在Anroid4.4以后，如果要使用inBitmap的话，只需要解码的Bitmap比inBitmap设置的小就行了，对inSampleSize
        // 没有限制
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i("meng","after android 4.4");
            int width = targetOptions.outWidth / targetOptions.inSampleSize;
            int height = targetOptions.outHeight / targetOptions.inSampleSize;
            int byteCount = width * height
                    * getBytesPerPixel(candidate.getConfig());
            return byteCount <= candidate.getAllocationByteCount();
        }

        // 在Android
        // 4.4之前，如果想使用inBitmap的话，解码的Bitmap必须和inBitmap设置的宽高相等，且inSampleSize为1
        return candidate.getWidth() == targetOptions.outWidth
                && candidate.getHeight() == targetOptions.outHeight
                && targetOptions.inSampleSize == 1;
    }

    /**
     * 获取每个像素所占用的Byte数
     *
     * @param config
     * @return
     */
    public static int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        } else if (config == Bitmap.Config.RGB_565) {
            return 2;
        } else if (config == Bitmap.Config.ARGB_4444) {
            return 2;
        } else if (config == Bitmap.Config.ALPHA_8) {
            return 1;
        }
        return 1;
    }

    /*大于 Android 3.0*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public void setmMemoryCache(String key, BitmapDrawable bitmapDrawable){
        if(getmMemoryCache(key) == null)
            mMemoryCache.put(key,bitmapDrawable);
    }

    public BitmapDrawable getmMemoryCache(String key){
        return mMemoryCache.get(key);
    }
}
```

上面的代码中我们将从 LruCache 中移除的 BitmapDrawable 对象的弱引用保存在一个 set 中，然后从 set 中获取满足 `BitmapFactory.Options.inBitmap` 条件的 Bitmap 对象用来重用它的内存，使用如下

```
/**
 * Android 3.0 及以上使用 BitmapFactory.Options.inBitmap 字段来重
 * 复利用 Bitmap 分配的内存，避免频繁的分配和销毁内存，这样可以提高性能。
 */
public static BitmapDrawable decodeSampledBitmapFromResource(Resources res, int resId,
                                                             int reqWidth, int reqHeight, ImageCache cache) {

    final BitmapFactory.Options options = new BitmapFactory.Options();
    /*只加载图片的信息，不分配内存*/
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options);
    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
    // If we're running on Honeycomb or newer, try to use inBitmap.
    if (ImageCache.hasHoneycomb()) {
        addInBitmapOptions(options, cache);
    }

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return new BitmapDrawable(BitmapFactory.decodeResource(res, resId, options));
}

private static void addInBitmapOptions(BitmapFactory.Options options,
                                       ImageCache cache) {
    // inBitmap only works with mutable bitmaps, so force the decoder to
    // return mutable bitmaps.
    options.inMutable = true;

    if (cache != null) {
        // Try to find a bitmap to use for inBitmap.
        Bitmap inBitmap = cache.getBitmapFromReusableSet(options);

        if (inBitmap != null) {
            // If a suitable bitmap has been found, set it as the value of
            // inBitmap.
            Log.i("meng", "inBitmap is not null");
            options.inBitmap = inBitmap;
        }
    }
}
```

> 注意：由于在 ListView 中复用了 Bitmap 对象，会导致图片显示混乱

## 在 UI 上显示 Bitmap

下面是一个使用 ViewPager 与 ImageView 作为子视图的示例。主 Activity 包含有 ViewPager 和 Adapter

```
public class ImageDetailActivity extends FragmentActivity {
    public static final String EXTRA_IMAGE = "extra_image";
    private  static int intentPosition = -1;

    private ImagePagerAdapter mAdapter;
    private ViewPager mPager;

    /*增加一个内存缓存*/
    private LruCache<String, Bitmap> mMemoryCache;

    // A static dataset to back the ViewPager adapter
    public final static Integer[] imageResIds = new Integer[] {R.drawable.img, R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
            R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        Intent intent = getIntent();
        intentPosition = intent.getIntExtra(EXTRA_IMAGE,-1);


        final int cacheSize = 20*1024*1024;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                /*计算每次添加图片的大小*/
                return bitmap.getByteCount();
            }
        };

        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), imageResIds.length);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPager.setAdapter(mAdapter);
    }
    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            if(intentPosition != -1){
                position = intentPosition;
                intentPosition = -1;
            }
            return ImageDetailFragment.newInstance(position);
        }
    }

    /*使用异步线程来加载图片，避免直接在 UI 线程中操作*/
    public void loadBitmap(int resId, ImageView mImageView) {
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            Log.i("meng","set from memory cache");
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.mipmap.ic_launcher);
            BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
            task.execute(resId);
        }
    }

    /*在非UI线程处理加载图片*/
    class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap> {
        private final WeakReference imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            /*使用一个弱引用来确保ImageView可以被回收*/
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            /*在非UI线程中加载图片*/
            Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), data , 100, 200);
            addBitmapToMemoryCache(String.valueOf(params[0]),bitmap);
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    /*从缓存中读取图片*/
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /*向缓存中添加图片*/
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }
}
```

Fragment 里只包含了一个 ImageView 控件

```
public class ImageDetailFragment extends Fragment {
    private static final String IMAGE_DATA_EXTRA = "resId";
    private int mImageNum;
    private ImageView mImageView;

    static ImageDetailFragment newInstance(int imageNum) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, imageNum);
        f.setArguments(args);
        return f;
    }

    // Empty constructor, required as per Fragment docs
    public ImageDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNum = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // image_detail_fragment.xml contains just an ImageView
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //下面这种方法直接在UI线程中设置图片会导致内存溢出，页面卡顿
        /*final int resId = ImageDetailActivity.imageResIds[mImageNum];
        mImageView.setImageResource(resId); // Load image into ImageView
        */
        if (ImageDetailActivity.class.isInstance(getActivity())) {
            final int resId = ImageDetailActivity.imageResIds[mImageNum];
            // Call out to ImageDetailActivity to load the bitmap in a background thread
            ((ImageDetailActivity) getActivity()).loadBitmap(resId, mImageView);
        }
    }
}
```

## 实现加载图片到GridView

下面这个例子演示了GridView显示图片，GridView 不但需要用异步的方式来加载图片，还要处理item循环机制所带来的并发加载问题，前面我们介绍过处理多并发加载图片问题，完整代码如下：

```
public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ImageAdapter mAdapter;

    // A static dataset to back the GridView adapter
    public final static Integer[] imageResIds = new Integer[]{
            R.drawable.img, R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
            R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9};

    // Empty constructor as per Fragment docs
    public ImageGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ImageAdapter(getActivity());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_grid_fragment, container, false);
        final GridView mGridView = (GridView) v.findViewById(R.id.gridView);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        final Intent i = new Intent(getActivity(), ImageDetailActivity.class);
        i.putExtra(ImageDetailActivity.EXTRA_IMAGE, position);
        startActivity(i);
    }

    private class ImageAdapter extends BaseAdapter {
        private final Context mContext;

        public ImageAdapter(Context context) {
            super();
            mContext = context;
        }

        @Override
        public int getCount() {
            return imageResIds.length;
        }

        @Override
        public Object getItem(int position) {
            return imageResIds[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ImageView imageView;
            if (convertView == null) { // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                imageView = (ImageView) convertView;
            }
            //请注意下面的代码,这样可能会造成页面无反应，阻塞
            //imageView.setImageResource(imageResIds[position]); // Load image into ImageView
            //使用异步加载的方式
            loadBitmap(imageResIds[position], imageView);
            return imageView;
        }
    }

    public void loadBitmap(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(getResources(), BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher), task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return (BitmapWorkerTask)bitmapWorkerTaskReference.get();
        }
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /*在非UI线程处理加载图片*/
    class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap> {
        private final WeakReference imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            /*使用一个弱引用来确保ImageView可以被回收*/
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            /*在非UI线程中加载图片*/
            Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), data , 100, 200);
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
```
