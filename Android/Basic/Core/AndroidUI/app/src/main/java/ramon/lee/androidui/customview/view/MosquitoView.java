package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import ramon.lee.androidui.R;

/**
 * Created by meng.li on 2019/1/11.
 * 自定义视图，蚊子随着手指移动
 */

public class MosquitoView extends View {
    //定义相关变量，显示蚊子的 x y 坐标
    public float bitmapX;
    public float bitmapY;

    public MosquitoView(Context context) {
        super(context);
        //设置初始坐标
        bitmapX = 60;
        bitmapY = 100;
    }

    //重写View类的 onDraw()方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建，并且实例化 Paint 的对象
        Paint paint = new Paint();
        //根据图片生成图像
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.mosquito);
        //绘制蚊子,这里减去蚊子的宽高的 1/2 ，让蚊子绘制在我们手指点的地方
        canvas.drawBitmap(bitmap, bitmapX - bitmap.getWidth()/2, bitmapY - bitmap.getHeight()/2, paint);
        //判断图片是否回收，没有回收的话强制回收
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
