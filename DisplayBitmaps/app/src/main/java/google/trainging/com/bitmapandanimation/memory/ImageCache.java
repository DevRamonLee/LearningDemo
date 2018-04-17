package google.trainging.com.bitmapandanimation.memory;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by meng.li on 2018/4/8.
 * Android 3.0 及以上使用 BitmapFactory.Options.inBitmap 字段来重
 * 复利用 Bitmap 分配的内存，避免频繁的分配和销毁内存，这样可以提高性能。
 */

public class ImageCache {
    private final static int MAX_MEMORY = 3 * 1024 * 1024; //3 MB
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
