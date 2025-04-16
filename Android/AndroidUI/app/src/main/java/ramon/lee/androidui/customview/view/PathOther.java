package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Path 的一些其他用法
 */
public class PathOther extends View {
    private Paint mPaint;

    public PathOther(Context context) {
        this(context, null);
    }

    public PathOther(Context context, @Nullable AttributeSet attrs) {
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
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        Path path = new Path();
        /**
         * rXxx 方法，相对移动
         */
        /*path.moveTo(100,100);
        // 相对于 100，100 的位移
        path.rLineTo(100,200);
        canvas.drawPath(path,mPaint);*/

        /**
         * 奇偶规则和反奇偶规则
         */
        /*mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);                   // 设置画布模式为填充
//          path.setFillType(Path.FillType.EVEN_ODD);                   // 设置Path填充模式为 奇偶规则
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);            // 反奇偶规则
        path.addRect(-200,-200,200,200, Path.Direction.CW);         // 给Path中添加一个矩形
        canvas.drawPath(path, mPaint);*/

        /**
         * 非零环绕规则
         */
        /*mPaint.setStyle(Paint.Style.FILL);
        // 添加小正方形 (通过这两行代码来控制小正方形边的方向,从而演示不同的效果)
         path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        path.addRect(-200, -200, 200, 200, Path.Direction.CCW);

        // 添加大正方形
        path.addRect(-400, -400, 400, 400, Path.Direction.CCW);
        path.setFillType(Path.FillType.WINDING);                    // 设置Path填充模式为非零环绕规则
        canvas.drawPath(path, mPaint);*/

        /**
         * 布尔操作，绘制太极鱼
         */
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();

        path1.addCircle(0, 0, 200, Path.Direction.CW);
        path2.addRect(0, -200, 200, 200, Path.Direction.CW);
        path3.addCircle(0, -100, 100, Path.Direction.CW);
        path4.addCircle(0, 100, 100, Path.Direction.CCW);


        path1.op(path2, Path.Op.DIFFERENCE);
        path1.op(path3, Path.Op.UNION);
        path1.op(path4, Path.Op.DIFFERENCE);

        canvas.drawPath(path1, mPaint);
    }
}
