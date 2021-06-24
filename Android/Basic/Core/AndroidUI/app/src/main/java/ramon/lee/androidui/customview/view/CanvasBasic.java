package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by meng.li on 2019/1/18.
 * Android  中 Canvas 的使用
 */

public class CanvasBasic extends View {
    //1.创建一个画笔
    private Paint mPaint = new Paint();

    //2.初始化这个画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }
    public CanvasBasic(Context context) {
        super(context);
    }

    //3.在构造函数中调用初始化
    public CanvasBasic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制颜色是填充整个画布，常用于绘制底色
        canvas.drawColor(Color.YELLOW);

        //绘制点,可以绘制一个点，也可以绘制一组点
        /*canvas.drawPoint(200, 200, mPaint);     //在坐标(200,200)位置绘制一个点
        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
                500,500,
                500,600,
                500,700
        },mPaint);*/

        //绘制直线，绘制直线也可以绘制一条或者一组，需要两个点、起始点和终止点
        /*canvas.drawLine(300,300,500,600,mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
                100,200,200,200,
                100,300,200,300
        },mPaint);*/

        //绘制矩形，有三种绘制方法
        /*// 第一种
        canvas.drawRect(100,100,800,400,mPaint);
        // 第二种
        Rect rect = new Rect(100,100,800,400);
        canvas.drawRect(rect,mPaint);
        // 第三种
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawRect(rectF,mPaint);*/

        //绘制圆角矩形 也提供了两种重载,为什么需要两个半径参数，应为绘制是椭圆的两个圆心
        // 第一种
        /*RectF rectF = new RectF(100,100,800,400);
        canvas.drawRoundRect(rectF,30,30,mPaint);

        // 第二种
        canvas.drawRoundRect(100,100,800,400,30,30,mPaint);*/

        // 绘制圆角矩形，当我们设置半径超过矩形宽和高的一半时，图形变成了椭圆
        /*RectF rectF = new RectF(100,100,800,400);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);
        // 绘制圆角矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(rectF,700,400,mPaint);*/

        // 绘制椭圆，需要一个矩形作为参数
        /*// 第一种
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawOval(rectF,mPaint);
        // 第二种
        canvas.drawOval(100,100,800,400,mPaint);*/

        // 绘制圆，前两个是圆心坐标，第三个是半径
//        canvas.drawCircle(500,500,400,mPaint);  // 绘制一个圆心坐标在(500,500)，半径为400 的圆。

        /**
         * 绘制椭圆圆弧
         * // 第一种
            public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint){}

            // 第二种
             public void drawArc(float left, float top, float right, float bottom, float startAngle,
                                        float sweepAngle, boolean useCenter, @NonNull Paint paint) {}
             startAngle  // 开始角度
             sweepAngle  // 扫过角度
             useCenter   // 是否使用中心，看效果，使用中心和不使用绘制的面积不同，不使用则直接连接
                            弧的两个端点。
         */

        /*RectF rectF = new RectF(100,100,800,400);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,90,false,mPaint);

        //-------------------------------------

        RectF rectF2 = new RectF(100,600,800,900);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2,mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2,0,90,true,mPaint);*/

        /*RectF rectF = new RectF(100,100,600,600);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,90,false,mPaint);
        //-------------------------------------
        RectF rectF2 = new RectF(100,700,600,1200);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2,mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2,0,90,true,mPaint);*/

        /*Paint 的三种效果
            STROKE                //描边
            FILL                  //填充
            FILL_AND_STROKE       //描边加填充
         */
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(40);     //为了实验效果明显，特地设置描边宽度非常大
        // 描边
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200,200,100,paint);
        // 填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200,500,100,paint);
        // 描边加填充（前两个之和）
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(200, 800, 100, paint);
    }
}
