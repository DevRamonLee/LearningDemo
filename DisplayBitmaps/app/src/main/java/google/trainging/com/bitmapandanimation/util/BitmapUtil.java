package google.trainging.com.bitmapandanimation.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import google.trainging.com.bitmapandanimation.R;
import google.trainging.com.bitmapandanimation.memory.ImageCache;

/**
 * Created by meng.li on 2018/3/6.
 * 图片处理工具类
 */

public class BitmapUtil {
    /*image 数据列表*/
    public static int [] imgLists = {R.drawable.img,R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,
            R.drawable.img6,R.drawable.img7,R.drawable.img8,R.drawable.img9};

    /*使用Bitmap inBitmap 属性*/
    public static int [] imgLists2 = {R.drawable.imgtest1,R.drawable.imgtest2,R.drawable.imgtest3,R.drawable.imgtest4,R.drawable.imgtest5,R.drawable.imgtest6};

    /*计算图片实际大小和目标大小的缩放比例*/
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    /*加载图片资源，返回一个Bitmap对象*/
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
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

    /**Android 3.0 及以上使用 BitmapFactory.Options.inBitmap 字段来重
     *复利用 Bitmap 分配的内存，避免频繁的分配和销毁内存，这样可以提高性能。
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
        return  new BitmapDrawable(BitmapFactory.decodeResource(res, resId, options));
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
                Log.i("meng","inBitmap is not null");
                options.inBitmap = inBitmap;
            }
        }
    }
}
