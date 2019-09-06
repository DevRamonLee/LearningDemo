# MIME 介绍

#### 概念：

多功能 Internet 邮件扩充服务(Multipurpose Internet Mail Extensions)，它是一种多用途网际邮件扩充协议，在1992年最早应用于电子邮件系统，但后来也应用到浏览器。MIME类型就是设定某种扩展名的文件用一种应用程序来打开的方式类型，当该扩展名文件被访问的时候，浏览器会自动使用指定应用程序来打开。多用于指定一些客户端自定义的文件名，以及一些媒体文件打开方式。

#### 组成：
**Content-Type/subtype ，比如：text/plain（纯文本）**

- Content-Type的种类：
    - Text：用于标准化地表示的文本信息，文本消息可以是多种字符集和或者多种格式的；
    - Multipart：用于连接消息体的多个部分构成一个消息，这些部分可以是不同类型的数据；
    - Application：用于传输应用程序数据或者二进制数据；
    - Message：用于包装一个E-mail消息；
    - Image：用于传输静态图片数据；
    - Audio：用于传输音频或者音声数据；
    - Video：用于传输动态影像数据，可以是与音频编辑在一起的视频数据格式。

- Subtype的常用种类：

    - text/plain（纯文本）
    - text/html（HTML文档）
    - application/xhtml+xml（XHTML文档）
    - image/gif（GIF图像）
    - image/jpeg（JPEG图像）【PHP中为：image/pjpeg】
    - image/png（PNG图像）【PHP中为：image/x-png】
    - video/mpeg（MPEG动画）
    - application/octet-stream（任意的二进制数据）
    - application/pdf（PDF文档）
    - application/msword（Microsoft Word文件）
    - message/rfc822（RFC 822形式）
    - multipart/alternative（HTML邮件的HTML形式和纯文本形式，相同内容使用不同形式表示）
    - application/x-www-form-urlencoded（使用HTTP的POST方法提交的表单）
    - multipart/form-data（同上，但主要用于表单提交时伴随文件上传的场合）

> 以上的 Content-Type/subtype 在 MimeUtils.java 工具类中有更全的列表

#### 用处：

主要使用在 AndroidManifest.xml 中，包含于`<intent-filter>`,使用`<data>`标识。如下：
```
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android: mimeType=" text/plain " />
    <data android: mimeType=" text/html " />
</intent-filter>
```

以上的`<intent-filter>`说明，此Activity可以处理 text/plain 和 text/html 的文件类型，也就是扩展名为.txt和.html的文件。如果要让此应用能处理所有类型的文件，可以这样写：

```
<data android:scheme="*/* " />
```

至于`<action android:name="android.intent.action.VIEW" />`这个表示此Activity能接收android.intent.action.VIEW这种类型的Intent。那么这个Intent是什么时候发出来的呢？

答案是当从文件管理器中点击打开文件时，就会触发这个 startActivity(intent) 的发出。因为这个 Intent 是隐式的,它定义了 android.intent.action.VIEW 这个动作，定义了这个动作的 Acitivity 将会被定位到并且被打开。如果有多个 Activity 定义了 android.intent.action.VIEW 类型，那么将会弹出一个列表供用户选择。

#### MIME的相关类：

- `/frameworks/base/core/java/android/webkit/MimeTypeMap.java`

- `/packages/apps/Nfc/src/com/android/nfc/beam/MimeTypeUtil.java`

- `/libcore/luni/src/main/java/libcore/net/MimeUtils.java`

**MimeTypeMap**主要方法有：
```
//通过url获取文件扩展名
public static String getFileExtensionFromUrl(String url) {
    if (!TextUtils.isEmpty(url)) {
        int fragment = url.lastIndexOf('#');
        if (fragment > 0) {
            url = url.substring(0, fragment);
        }

        int query = url.lastIndexOf('?');
        if (query > 0) {
            url = url.substring(0, query);
        }

        int filenamePos = url.lastIndexOf('/');
        String filename =
            0 <= filenamePos ? url.substring(filenamePos + 1) : url;

        // if the filename contains special characters, we don't
        // consider it valid for our matching purposes:
        if (!filename.isEmpty() &&
            Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", filename)) {
            int dotPos = filename.lastIndexOf('.');
            if (0 <= dotPos) {
                return filename.substring(dotPos + 1);
            }
        }
    }

    return "";
}
//判断在表中是否有这种 mimeType 
public boolean hasMimeType(String mimeType) {
    return MimeUtils.hasMimeType(mimeType);
}

// 使用扩展名获取 mimeType 类型
public String getMimeTypeFromExtension(String extension) {
    return MimeUtils.guessMimeTypeFromExtension(extension);
}
```

它的很多方法都是使用MimeUtils工具类的方法来实现的。



**MimeTypeUtil** 只有一个方法，在实现上也是依靠MimeUtils工具类，如下：

```
public static String getMimeTypeForUri(Context context, Uri uri) {
        if (uri.getScheme() == null) return null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            return cr.getType(uri);
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(uri.getPath()).toLowerCase();
            if (extension != null) {
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            } else {
                return null;
            }
        } else {
            Log.d(TAG, "Could not determine mime type for Uri " + uri);
            return null;
        }
    }
```

**MimeUtils**

```
public final class MimeUtils {
    private static final Map<String, String> mimeTypeToExtensionMap = new HashMap<String, String>();

    private static final Map<String, String> extensionToMimeTypeMap = new HashMap<String, String>();

    static {
        // The following table is based on /etc/mime.types data minus
        // chemical/* MIME types and MIME types that don't map to any
        // file extensions. We also exclude top-level domain names to
        // deal with cases like:
        //
        // mail.google.com/a/google.com
        //
        // and "active" MIME types (due to potential security issues).
        
        // Note that this list is _not_ in alphabetical order and must not be sorted.
        // The "most popular" extension must come first, so that it's the one returned
        // by guessExtensionFromMimeType.

        add("application/andrew-inset", "ez");
        add("application/dsptype", "tsp");
        add("application/epub+zip", "epub");
        ...
    }

    private static void add(String mimeType, String extension) {
    // If we have an existing x -> y mapping, we do not want to
    // override it with another mapping x -> y2.
    // If a mime type maps to several extensions
    // the first extension added is considered the most popular
    // so we do not want to overwrite it later.
        if (!mimeTypeToExtensionMap.containsKey(mimeType)) {
            mimeTypeToExtensionMap.put(mimeType, extension);
        }
        if (!extensionToMimeTypeMap.containsKey(extension)) {
            extensionToMimeTypeMap.put(extension, mimeType);
        }
    }

    private MimeUtils() {
    }

    /**
     * Returns true if the given case insensitive MIME type has an entry in the map.
     * @param mimeType A MIME type (i.e. text/plain)
     * @return True if a extension has been registered for
     * the given case insensitive MIME type.
     */
    public static boolean hasMimeType(String mimeType) {
        return (guessExtensionFromMimeType(mimeType) != null);
    }

    /**
     * Returns the MIME type for the given case insensitive file extension.
     * @param extension A file extension without the leading '.'
     * @return The MIME type has been registered for
     * the given case insensitive file extension or null if there is none.
     */
    public static String guessMimeTypeFromExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return null;
        }
        extension = extension.toLowerCase(Locale.US);
        return extensionToMimeTypeMap.get(extension);
    }

    /**
     * Returns true if the given case insensitive extension has a registered MIME type.
     * @param extension A file extension without the leading '.'
     * @return True if a MIME type has been registered for
     * the given case insensitive file extension.
     */
    public static boolean hasExtension(String extension) {
        return (guessMimeTypeFromExtension(extension) != null);
    }

    /**
     * Returns the registered extension for the given case insensitive MIME type. Note that some
     * MIME types map to multiple extensions. This call will return the most
     * common extension for the given MIME type.
     * @param mimeType A MIME type (i.e. text/plain)
     * @return The extension has been registered for
     * the given case insensitive MIME type or null if there is none.
     */
    public static String guessExtensionFromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return null;
        }
        mimeType = mimeType.toLowerCase(Locale.US);
        return mimeTypeToExtensionMap.get(mimeType);
    }
}
```

比如我们在点击文件的时候的流程是从先从文件获取它的扩展名，然后通过扩展名获取有哪些 mimeType 可以处理它，从而构建 Intent。

intent-filter 中的 mimeType 会被系统解析，从而系统可以知道这个程序可以处理后缀名比如为 .txt 的文件。