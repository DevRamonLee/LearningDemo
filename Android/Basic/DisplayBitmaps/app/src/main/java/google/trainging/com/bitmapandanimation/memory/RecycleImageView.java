package google.trainging.com.bitmapandanimation.memory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by meng.li on 2018/4/3.
 */

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
