package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PathBasic extends View {
    private Paint mPaint;
    public PathBasic(Context context) {
        this(context, null);
    }

    public PathBasic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();                   // 创建画笔
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(10);              // 边框宽度 - 10
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#CCFFCC"));
        mPaint.setColor(Color.RED);
        // 绘制 x 轴
        canvas.drawLine(0f, getHeight()/2f, getWidth(), getHeight() /2f, mPaint);
        // 绘制 y 轴
        canvas.drawLine(getWidth()/2f, 0f, getWidth()/2f, getHeight(), mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.translate(getWidth() / 2, getHeight() / 2);  // 移动坐标系到屏幕中心(宽高数据在onSizeChanged中获取)

        Path path = new Path();                     // 创建Path

        /**
         * lineTo start
         */
//        path.lineTo(200, 200); // lineTo, 默认以原点开始
//        path.lineTo(200,0);    // 这次的起点是上次的终点 200，200
        /**
         * lineTo end
         */

        /**
         * moveTo start
         */
//        path.lineTo(200, 200);
//        path.moveTo(200,100);
//        path.lineTo(200,0);
        /**
         * moveTo end
         */

        /**
         * setLastPoint start
         */
//        path.lineTo(200, 200);
//        path.setLastPoint(200,100);
//        path.lineTo(200,0);
        /**
         * setLastPoint end
         */
        /**
         * close start
         */
//        path.lineTo(200, 200);                      // lineTo
//        path.lineTo(200,0);                         // lineTo
//        path.close();
        /**
         * close end
         */

        // 第二类：绘制图形，设置绘制顺时针和逆时针
//        path.addRect(-200,-200,200,200, Path.Direction.CW);
//        path.addRect(200,200,-200,-200, Path.Direction.CCW);
//        path.setLastPoint(-300,300);                // <-- 重置最后一个点的位置

        // 合并 path
        /*canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴

        Path src = new Path();

        path.addRect(-200,-200,200,200, Path.Direction.CW);
        src.addCircle(0,0,100, Path.Direction.CW);

        path.addPath(src,0,200);

        mPaint.setColor(Color.BLACK);           // 绘制合并后的路径
        canvas.drawPath(path,mPaint);*/

        // arcTo forceMoveTo
        canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
        path.lineTo(100,100);

        RectF oval = new RectF(0,0,300,300);

//        path.addArc(oval,0,270);
         path.arcTo(oval,0,270,false);

        canvas.drawPath(path, mPaint);
    }
}
