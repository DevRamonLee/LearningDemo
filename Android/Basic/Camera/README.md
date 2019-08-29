- http://hukai.me/android-training-course-in-chinese/multimedia/camera/index.html
- [Android自定义Camera最佳入门实例](http://blog.csdn.net/u013647382/article/details/51778306)

## Camera 的简单使用（一）

- 请求相机功能

在 Manifest 文件中使用 `<uses-feature>` 标签表明你的应用程序依赖于具有相机的设备。

```
<manifest ... >
    <uses-feature android:name="android.hardware.camera" android:required="true" />
</manifest>
```

> 将 `android:required` 设置为 false，Google play 将会允许没有相机的设备下载你的应用程序。这样做就需要在运行时使用 `hasSystemFeature(PackageManager.FEATURE_CAMERA)`.检查是否有相机功能。如果相机不可用，你应该禁用相机功能。

例：

```
private boolean checkCameraHardware(Context context) {
    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
        return true;
    } else {
        return false;
    }
}
```

#### 使用 Camera app 拍摄照片

最简单的是使用意图来调用其他相机拍摄照片，通过 Intent 的 `resolveActivity(pm)` 方法来判断是否有可执行意图的活动，如果没有则启动Activity会报错。

```
static final int REQUEST_IMAGE_CAPTURE = 1;
private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    /*判断是否有可执行意图的活动*/
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
}
```

- 获取缩略图

拍摄完成后，照片以 Intent 的形式传递给 `onActivityResult()`，作为 `extras` 中的一个小的位图，位于关键字 `data` 下

```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(imageBitmap);
    }
}
```

> 注意：这里获取的图片是被压缩过的，通过 Intent 来传递图片是有限制的，因为是存在内存里的，所以这个图片作为个人头像还可以，如果需要高清图则需要用到下面的方法

#### 保存全尺寸图片

- [申请权限](Part1/Android_Basic/Android-quan-xian-md.md)

读取和写入需要分别具有 `READ_EXTERNAL_STORAGE` 和 `WRITE_EXTERNAL_STORAGE` 权限。写入权限隐式允许读取，所以写入外部存储，只需要申请一个权限：

```
<manifest>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
</manifest>
```

如果你希望照片仅为你的应用保留，则可以使用 `getExternalFileDir()` 提供的目录。在Android 4.3及更低版本上，写入此目录还需要 `WRITE_EXTERNAL_STORAGE` 权限。从 Android 4.4 开始，不再需要该权限，因为该目录不能被其他应用程序访问，所以你可以通过添加 `maxSdkVersion` 属性来声明仅在较低版本上请求权限：

```
<manifest ...>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18" />
</manifest>
```

> 注意：保存在 `getExternalFileDir()` 或者 `getFilesDir()`下的文件会随应用卸载被删除。

- 创建文件名

决定了文件的目录后，需要创建一个唯一的文件名。可以将路径保存在成员变量中供以后使用。下面是一个方法，该方法使用日期时间戳为新照片返回唯一文件名：

```
String mCurrentPhotoPath;
private File createImageFile() throws IOException {
    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

    File image = File.createTempFile(
            imageFileName,         // prefix
            ".jpg",         // suffix
            storageDir             // directory
    );
    // Save a file: path for use with ACTION_VIEW intents
    mCurrentPhotoPath = image.getAbsolutePath();
    return image;
}
```

使用这种方法可以为照片创建一个文件，然后使用 Intent 去使用这个文件:


```
static final int REQUEST_TAKE_PHOTO = 1;
private void dispatchTakeFullSizePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "google.trainging.com.capturingphotos.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);      // 提供给相机应用可以读写的文件
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }
}
```

> 注意：我们使用 `getUriForFile(Context，String，File)` 返回 `content:// URI`对于 Android 7.0（API级别24）及更高版本的应用，跨应用传递 `file：//` URI 会导致 `FileUriExposedException`。 因此，我们使用 FileProvider 方法

- 在Manifest中配置FileProvider

```
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="google.trainging.com.capturingphotos.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>
```

确保权限字符串与 `getUriForFile（Context，String，File）`的第二个参数相匹配。在元数据部分，`android:resource` 配置可以访问的合法路径

```
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="my_images" path="/" />
</paths>
```

路径组件对应于使用 `Environment.getExternalStorageDirectory() + /path/` 返回的路径。也就是对应 `/storage/emulated/0/Android/data/com.example.package.name/files/Pictures` 这时候调用拍照 Action，全尺寸照片就会被保存到这个目录下了。

#### 将照片添加到画廊

当你通过意图创建一张照片时，你知道你的图片所在的位置。对于其他人而言，访问照片最简单方法是通过系统的 MediaProvicer 访问它。

以下示例方法演示了如何调用系统的媒体扫描程序将照片添加到媒体提供程序的数据库

```
private void galleryAddPic() {
    File f = new File(mCurrentPhotoPath);
    try {
        // 把文件插入到系统图库
        MediaStore.Images.Media.insertImage(PhotoActivity.this.getContentResolver(),
                f.getAbsolutePath(), f.getName(), null);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    // 最后通知图库更新
    PhotoActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));
}
```

#### 解码缩放图像

管理多个全尺寸的图像可能会内存不够。如果在显示一些图像后发现应用程序内存不足，则可以通过算法根据 ImageView 组件的大小来调整图像以减少内存占用。

以下示例方法演示了这种技术。更加详细的图片加载内容请看[高效显示Bitmap](Part1/Android_trainning/gao-xiao-xian-shi-bitmap-4sad-md.md)


```
private void setPic() {
    // Get the dimensions of the View
    int targetW = captureImg.getWidth();
    int targetH = captureImg.getHeight();

    // Get the dimensions of the bitmap
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();

    // inJustDecodeBounds 设置为true 表示 bmOptions 只加载图片的宽和高信息
    bmOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    int photoW = bmOptions.outWidth;
    int photoH = bmOptions.outHeight;

    // Determine how much to scale down the image
    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

    // 上面改为了 true，这里需要加载图片，需要改为 false
    bmOptions.inJustDecodeBounds = false;
    bmOptions.inSampleSize = scaleFactor;   // 设置缩放比例

    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    captureImg.setImageBitmap(bitmap);
}
```

首先设置 `inJustDecodeBounds` 为 true，表示 `decodeFile` 时只加载图片的宽高信息，然后根据 ImageView 的大小计算出缩放比例，接下来需要把 `inJustDecodeBounds` 改为 false，此时会根据参数缩放并生成压缩后的 Bitmap。

这里我们发现一个问题，返回的照片被旋转了，我们需要对照片进行旋转处理，[这篇文章](https://www.jianshu.com/p/a360b32ec9b3)有详细方案, 修改一下我们的代码

```
else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
    Toast.makeText(PhotoActivity.this, "scanning files", Toast.LENGTH_SHORT).show();

    mCurrentPhotoPath = PhotoUtil.amendRotatePhoto(mCurrentPhotoPath, captureImg, this);    // 处理图片的旋转问题
    galleryAddPic();    // 更新图库
    setPic();
}
```

先介绍下 PhotoUtils 这个类，它根据原始图片的路径计算图片的旋转角度，然后对图片进行压缩，再对压缩后的图片进行矩阵转换，旋转为正确的角度。

```
public class PhotoUtil {

    /**
     * 存放拍摄图片的文件夹
     */
    private static final String FILES_NAME = "/MyPhoto";
    /**
     * 获取的时间格式
     */
    public static final String TIME_STYLE = "yyyyMMddHHmmss";
    /**
     * 图片种类
     */
    public static final String IMAGE_TYPE = ".jpg";

    // 防止实例化
    private PhotoUtil() {}

    /**
     * 获取手机可存储路径
     *
     * @param context 上下文
     * @return 手机可存储路径
     */
    private static String getPhoneRootPath(Context context) {
        // 是否有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            // 获取SD卡根目录
            return context.getExternalCacheDir().getPath();
        } else {
            // 获取apk包下的缓存路径
            return context.getCacheDir().getPath();
        }
    }

    /**
     * 使用当前系统时间作为上传图片的名称
     *
     * @return 存储的根路径+图片名称
     */
    public static String getPhotoFileName(Context context) {
        File file = new File(getPhoneRootPath(context) + FILES_NAME);
        // 判断文件是否已经存在，不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 设置图片文件名称
        SimpleDateFormat format = new SimpleDateFormat(TIME_STYLE, Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        String photoName = "/" + time + IMAGE_TYPE;
        return file + photoName;
    }


    /**
     * 保存Bitmap图片在SD卡中
     * 如果没有SD卡则存在手机中
     *
     * @param mbitmap 需要保存的Bitmap图片
     * @return 保存成功时返回图片的路径，失败时返回null
     */
    public static String savePhotoToSD(Bitmap mbitmap, Context context) {
        FileOutputStream outStream = null;
        String fileName = getPhotoFileName(context);
        try {
            outStream = new FileOutputStream(fileName);
            // 把数据写入文件，100表示不压缩
            mbitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outStream != null) {
                    // 记得要关闭流！
                    outStream.close();
                }
                if (mbitmap != null) {
                    mbitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把原图按1/10的比例压缩
     *
     * @param path 原图的路径
     * @return 压缩后的图片
     */
    public static Bitmap getCompressPhoto(String path, ImageView captureImg) {

        // Get the dimensions of the View
        int targetW = captureImg.getWidth();
        int targetH = captureImg.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        // inJustDecodeBounds 设置为true 表示 bmOptions 只加载图片的宽和高信息
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // 上面改为了 true，这里需要加载图片，需要改为 false
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;   // 设置缩放比例

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }

    /**
     * 处理旋转后的图片
     *
     * @param originpath 原图路径
     * @param context    上下文
     * @return 返回修复完毕后的图片路径
     */
    public static String amendRotatePhoto(String originpath, ImageView captureImg, Context context) {
        // 取得图片旋转角度
        int angle = readPictureDegree(originpath);
        // 把原图压缩后得到Bitmap对象
        Bitmap bmp = getCompressPhoto(originpath, captureImg);
        // 修复图片被旋转的角度
        Bitmap bitmap = rotaingImageView(angle, bmp);
        // 保存修复后的图片并返回保存后的图片路径
        return savePhotoToSD(bitmap, context);
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }
}
```

#### 简单录制视频

- 使用相机APP录制视频
 
```
private void dispatchTakeVideoIntent() {
    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
    }
}
```
- 查看视频

Android Camera 应用程序将 Intent 中的视频作为 Uri 返回到 `onActivityResult()`，并指向存储器中的视频位置。 以下代码检索此视频并将其显示在 VideoView 中。

```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
        Uri videoUri = intent.getData();
        mVideoView.setVideoURI(videoUri);
        /*play the video*/
        if (!mVideoView.isPlaying()) {
            mVideoView.start();
        }
        /*pause the video*/
        //mVideoView.pause();
        /*stop the video*/
        //mVideoView.stopPlayback();
        /*replay the video*/
        //mVideoView.resume();
        /*release the resource*/
        //mVideoView.suspend();
    }
}
```

## Camera 的简单使用（二）

这里我们使用了SurfaceView 和 SurfaceHolder.Callback ，详细内容请看文章[Surface、SurfaceView、SurfaceHolder及SurfaceHolder.Callback之间的关系](Part1/Android_advanced/surface-surfaceview-surfaceholder-srufaceholder-callback-md.md)

#### Google官方入门实例

- 引入权限
```
<uses-permission android:name="android.permission.CAMERA"/>
<uses-feature android:name="android.hardware.camera" android:required="false"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
- 打开相机 `ControlCameraActivity.java` 中添加

```
// 判断相机是否支持
private boolean checkCameraHardware(Context context) {
    if (context.getPackageManager().hasSystemFeature(
            PackageManager.FEATURE_CAMERA)) {
        return true;
    } else {
        return false;
    }
}


// 获取相机实例
public Camera getCameraInstance() {
    Camera c = null;
    try {
        c = Camera.open(mCameraId);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return c;
}
```

- 创建 preview `CameraPreview .java`

```
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        // 当前最小 api 已经大于 HONEYCOMB, 所以这个判断是无效的
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//            // API 11及以后废弃，需要时自动配置，配置作用是 Camera 直接提供数据
//            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null){
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
```

- 创建相机视图 `activity_camera.xml`

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent" >
    <FrameLayout
        android:id="@+id/camera_preview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true">

    </FrameLayout>

    <Button
        android:id="@+id/button_capture"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:text="@string/capture" />
</RelativeLayout>
```

- 在 Activity 中引用视图 `ControlCameraActivity.java`
```
public class ControlCameraActivity extends Activity {
    public static final String TAG = "CameraSimple";
    private Camera mCamera;
    private CameraPreview mPreview;
    private FrameLayout mCameralayout;
    private Button mTakePictureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (!checkCameraHardware(this)) {
            Toast.makeText(CameraActivity.this, "相机不支持", Toast.LENGTH_SHORT).show();
        } else {
            mCamera = getCameraInstance();
            mPreview = new CameraPreview(CameraActivity.this, mCamera);
            mCameralayout = (FrameLayout) findViewById(R.id.camera_preview);
            mCameralayout.addView(mPreview);
        }
    }
}
```

- 添加拍照功能 Android 中拍照可通过回调来完成, camera 拍照回调如下:

```
// 拍照回调
private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
    @Override
    public void onPictureTaken(final byte[] data, Camera camera) {
        File pictureDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        final String picturePath = pictureDir
                + File.separator
                + new DateFormat().format("yyyyMMddHHmmss", new Date())
                .toString() + ".jpg";
        FileOutputStream fos = new FileOutputStream(picturePath);
        fos.write(data);
        fos.close();
    }
};
```

> 拍完照会停下来，如果需要继续预览, 需要在拍照结束后再次调用 `startPreview()` 方法.

- 添加按钮事件:

```
 mTakePictureBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mCamera.takePicture(null, null, mPictureCallback);
    }
});
```


#### 添加功能

- 将相机设置成竖屏

```
public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {
    android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
    android.hardware.Camera.getCameraInfo(cameraId, info);
    int rotation = activity.getWindowManager().getDefaultDisplay()
            .getRotation();
    Log.i(TAG, "rotation is " + rotation);
    int degrees = 0;
    switch (rotation) {
        case Surface.ROTATION_0:
            degrees = 0;
            break;
        case Surface.ROTATION_90:
            degrees = 90;
            break;
        case Surface.ROTATION_180:
            degrees = 180;
            break;
        case Surface.ROTATION_270:
            degrees = 270;
            break;
    }

    int result;
    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
        result = (info.orientation + degrees) % 360;
        result = (360 - result) % 360;
    } else {
        result = (info.orientation - degrees + 360) % 360;
    }
    camera.setDisplayOrientation(result);
}
```

在第一次初始化时可在onCreate() 调用.

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    setCameraDisplayOrientation(this, mCameraId, mCamera);
}
```

把打开相机和释放操作抽取出来

```
// 开始预览相机
public void openCamera(){
    if(mCamera == null){
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(CameraActivity.this, mCamera);
        mCameralayout = (FrameLayout) findViewById(R.id.camera_preview);
        mCameralayout.addView(mPreview);
    }
}

// 释放相机
public void releaseCamera() {
    if (mCamera != null) {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }
}
```
- 修改图片保存方向，现在拍的照片, 保存到图库中都是横向的, 如果我们要根据当前相机的方向来保存图片怎么办呢?修改拍照回调的保存图片方法.

```
// 旋转图片,默认保存的照片是横向的，需要根据相机当前方向来保存
public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
    Bitmap returnBm = null;
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    try {
        returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                bm.getHeight(), matrix, true);
    } catch (OutOfMemoryError e) {
    }
    if (returnBm == null) {
        returnBm = bm;
    }
    if (bm != returnBm) {
        bm.recycle();
    }
    return returnBm;
}
```

修改拍照回调, 注意这里将保存图片放到了线程中处理, 避免卡顿:

```
 // 拍照回调
private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
    @Override
    public void onPictureTaken(final byte[] data, Camera camera) {
        File pictureDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        final String picturePath = pictureDir
                + File.separator
                + new DateFormat().format("yyyyMMddHHmmss", new Date())
                .toString() + ".jpg";
        // 处理照片放在线程中处理，避免卡顿
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(picturePath);
                try {
                    // 获取当前旋转角度, 并旋转图片
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    
                    bitmap = rotateBitmapByDegree(bitmap, 90);
                    
                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
                    bitmap.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 继续进行拍照
        mCamera.startPreview();
    }
};
```

- 前置摄像头和后置摄像头的切换

```
// 切换前置和后置摄像头
public void switchCamera() {
    CameraInfo cameraInfo = new CameraInfo();
    Camera.getCameraInfo(mCameraId, cameraInfo);
    if(cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK){
        mCameraId = CameraInfo.CAMERA_FACING_FRONT;
    }
    else{
        mCameraId = CameraInfo.CAMERA_FACING_BACK;
    }
    mCameralayout.removeView(mPreview);
    releaseCamera();
    openCamera();
    setCameraDisplayOrientation(CameraActivity.this, mCameraId, mCamera);
}
```

mCameraId 是一个成员变量, 记录当前是前置还是后置摄像头, 程序启动默认初始化为后置摄像头: `CameraInfo.CAMERA_FACING_BACK`.

添加了前置摄像头后会发现,我们拍的照片成倒立的了,这与我们前面旋转图片有关系,如果是前置摄像头的话, 旋转应该是 -90 度. 所以需要添加保存图片时的前置和后置判断:

```
if (cameraid == CameraInfo.CAMERA_FACING_BACK) {
    bitmap = rotateBitmapByDegree(bitmap, 90);
} else {
    bitmap = rotateBitmapByDegree(bitmap, -90);
}
```

- 为相机添加聚焦功能

android 相机中已经提供了聚焦的功能, 使用 `camera.autoFocus(null)` 方法即可, 如果需要得到聚焦成功或失败的回调,需要自行传入.
我们这里添加两个聚焦, 一个是点击相机画面时只聚焦不拍照, 另外一个是点击拍照按钮, 先聚焦, 聚焦成功后再拍照, 代码如下:
为 mPreview添加聚焦功能

```
// 开始预览相机
public void openCamera() {
    if (mCamera == null) {
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(CameraActivity.this, mCamera);
    
    mPreview.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mCamera.autoFocus(null);
            return false;
        }});
        
        mCameralayout = (FrameLayout) findViewById(R.id.camera_preview);
        mCameralayout.addView(mPreview);
        mCamera.startPreview();
    }
}
```

为拍照按钮添加聚焦, 将原来的拍照放到聚焦成功后再调用:

```
mTakePictureBtn = (ImageView) findViewById(R.id.btn_capture);
mTakePictureBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mCamera.autoFocus(mAutoFocusCallback);
    }
});

// 聚焦回调
private AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback() {
    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            mCamera.takePicture(null, null, mPictureCallback);
        }
    }
};
```