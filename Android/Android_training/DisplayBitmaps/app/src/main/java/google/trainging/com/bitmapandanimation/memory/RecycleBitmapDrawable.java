package google.trainging.com.bitmapandanimation.memory;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by meng.li on 2018/3/9.
 * Android 2.3.3  及以下版本使用引用计数法控制bitmap 内存
 * 当引用计数为0时调用 recycle 方法回收
 */

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
