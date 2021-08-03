package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ramon.lee.androidui.R;

/**
 * 绘制图片： drawPicture 和  drawBitmap
 * 绘制文字
 */
public class CanvasPictureBitmapText extends View {
    private Picture mPicture = new Picture();
    private Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.thumb);
    private Paint textPaint;
    // 文本(要绘制的内容)
    String str = "ABCDEFG";

    public CanvasPictureBitmapText(Context context) {
        this(context, null);
    }

    public CanvasPictureBitmapText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        recording();
        initTextPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#CCCCCC"));
        // 这种方法不推荐，对 Canvas 有影响，如缩放，skew 等，可操作性弱
//        mPicture.draw(canvas);
        // 下面的绘制图形会缩放
//        canvas.drawPicture(mPicture,new RectF(0,0,mPicture.getWidth(),200));

        // 包装成为Drawable
        PictureDrawable drawable = new PictureDrawable(mPicture);
        // 设置绘制区域 -- 注意此处所绘制的实际内容不会缩放
        drawable.setBounds(0,0,500,mPicture.getHeight());
        // 绘制
        drawable.draw(canvas);

        // 绘制图片
//        canvas.drawBitmap(bitmap,new Matrix(),new Paint());
        // 绘制图片，指定了与坐标原点的距离
//        canvas.drawBitmap(bitmap,200,500,new Paint());

        // 将画布坐标系移动到画布中央
        canvas.translate(getWidth()/2,getHeight()/2);

        // 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(0,0,bitmap.getWidth()/2,bitmap.getHeight()/2);
        // 指定图片在屏幕上显示的区域
        Rect dst = new Rect(0,0,100,200);
        // 绘制图片
        canvas.drawBitmap(bitmap,src,dst,null);

        // 绘制文字
        // 参数分别为 (文本 基线x 基线y 画笔)
        canvas.drawText(str,-300,0,textPaint);

        canvas.save();
        // 指定每一个字符的位置
        canvas.drawPosText(str,new float[]{
                100,100,    // 第一个字符位置
                200,200,    // 第二个字符位置
                300,300,    // ...
                400,400,
                500,500,
                550,550,
                600,600
        },textPaint);
        canvas.restore();
    }

    private void initTextPaint() {
        textPaint = new Paint();          // 创建画笔
        textPaint.setColor(Color.BLACK);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(50);              // 设置字体大小
    }

    private void recording() {
        // 开始录制 (接收返回值Canvas)
        Canvas canvas = mPicture.beginRecording(800, 800);
        // 创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        // 在Canvas中具体操作
        canvas.translate(400,250);
        // 绘制一个圆
        canvas.drawCircle(0,0,100,paint);

        mPicture.endRecording();
    }
}
