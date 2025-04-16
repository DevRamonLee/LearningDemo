package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class PathMeasureBasic extends View {
    private static final String TAG = "PathMeasureBasic";
    private Paint mPaint;
    public PathMeasureBasic(Context context) {
        this(context, null);
    }

    public PathMeasureBasic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();                   // 创建画笔
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);          // 平移坐标系
        Path path = new Path();
        /**
         * PathMeasure getLength forceClosed 为 false 和 为 true 时的区别
         */
        /*

        path.lineTo(0,200);
        path.lineTo(200,200);
        path.lineTo(200,0);

        PathMeasure measure1 = new PathMeasure(path,false);
        PathMeasure measure2 = new PathMeasure(path,true);

        Log.e("TAG", "forceClosed=false---->"+measure1.getLength());
        Log.e("TAG", "forceClosed=true----->"+measure2.getLength());

        canvas.drawPath(path,mPaint);*/

        /**
         * PathMeasure getSegment
         */
        /*path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path
        dst.lineTo(-300, -300);

        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联

        // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        measure.getSegment(200, 600, dst, false);
        canvas.drawPath(dst, mPaint);                        // 绘制 dst*/

        /**
         * nextContour
         */
        path.addRect(-100,-100, 100,100,Path.Direction.CW); // 小矩形
        path.addRect(-200, -200,200,200,Path.Direction.CW); // 大矩形
        canvas.drawPath(path, mPaint);

        PathMeasure measure = new PathMeasure(path, false);
        float len1 = measure.getLength();   // 获取第一条路径的长度
        measure.nextContour();  // 跳转到下一条路径
        float len2 = measure.getLength();   // 获取第二条路径的长度
        Log.i(TAG, "len1 = " + len1 + " len2 = " + len2);
    }
}
