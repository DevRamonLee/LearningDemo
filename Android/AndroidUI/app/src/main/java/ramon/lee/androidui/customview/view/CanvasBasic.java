package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
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
        // 绘制颜色是填充整个画布，常用于绘制底色
        canvas.drawColor(Color.YELLOW);

        // 绘制点,可以绘制一个点，也可以绘制一组点
        canvas.drawPoint(30, 10, mPaint);     //在坐标(30,10)位置绘制一个点
        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
                30,30,
                50,30,
                70,30
        },mPaint);

        // 绘制直线，绘制直线也可以绘制一条或者一组，需要两个点、起始点和终止点
        canvas.drawLine(60,60,500,60,mPaint);    // 在坐标(60,60)(500,60)之间绘制一条直线
        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
                80,80,500,80,
                100,100,500,100
        },mPaint);

        //绘制矩形，有三种绘制方法
        // 第一种
        canvas.drawRect(30,120,150,150,mPaint);
        // 第二种
        Rect rect = new Rect(160,120,280,150);
        canvas.drawRect(rect,mPaint);
        // 第三种
        RectF rectF = new RectF(290,120,410,150);
        canvas.drawRect(rectF,mPaint);

        // 绘制圆角矩形 也提供了两种重载,为什么需要两个半径参数，应为绘制是椭圆的两个圆心
        // 第一种
        RectF rectF2 = new RectF(50,190,280,320);
        canvas.drawRoundRect(rectF2,15,15,mPaint);

        // 第二种
        canvas.drawRoundRect(410,190,540,320,12,12,mPaint);

        // 绘制圆角矩形，当我们设置半径超过矩形宽和高的一半时，图形变成了椭圆
        RectF rectF3 = new RectF(50,330,250,430);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);
        // 绘制圆角矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(rectF3,110,60,mPaint);

        // 绘制椭圆，需要一个矩形作为参数,实际就是绘制这个矩形的内切椭圆
        // 第一种
        RectF rectF4 = new RectF(50,440,250,540);
        canvas.drawOval(rectF4,mPaint);
        // 第二种
        canvas.drawOval(50,550,250,650,mPaint);

        // 绘制圆，前两个是圆心坐标，第三个是半径
        canvas.drawCircle(50,700,30,mPaint);  // 绘制一个圆心坐标在(50,700)，半径为30 的圆。

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

        RectF rectF5 = new RectF(50,750,350,1000);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF5,mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        // 不使用中心
        canvas.drawArc(rectF5,0,90,false,mPaint);

        //-------------------------------------

        RectF rectF6 = new RectF(400,750,700,1000);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF6,mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        // 使用中心
        canvas.drawArc(rectF6,0,90,true,mPaint);

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
        canvas.drawCircle(200,1200,100,paint);
        // 填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(400,1200,100,paint);
        // 描边加填充（前两个之和）
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(600, 1200, 100, paint);
    }
}
