package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;

import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 *  对画布的操作
 */

public class CanvasOperation extends View {
    private Paint mPaint = new Paint();
    public CanvasOperation(Context context) {
        this(context, null);
    }

    public CanvasOperation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.STROKE); // 填充
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#FFCC00"));  // 绘制画布颜色
        // 绘制一个 x 轴 和 y 轴的坐标系,方便看效果
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        // 绘制 x 轴
        canvas.drawLine(0f, getHeight()/2f, getWidth(), getHeight() /2f, mPaint);
        // 绘制 y 轴
        canvas.drawLine(getWidth()/2f, 0f, getWidth()/2f, getHeight(), mPaint);

        /** translate --start--**/
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
        // 恢复现场，将上面平移的坐标系回到左上角
        canvas.translate(-400, -400);
        /** translate --end--**/


        /** scale --start--**/
        // 将坐标系原点移动到画布正中心
        canvas.translate(getWidth() / 2, getHeight() / 2);
        RectF rect = new RectF(0,-400,400,0);   // 矩形区域

        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);

//        canvas.scale(0.5f,0.5f);         // 画布缩放
//        canvas.scale(0.5f,0.5f,200,0);  // 画布缩放, 缩放中心向右偏移了200个单位
//        canvas.scale(-0.5f,-0.5f);   // 画布缩放, 负数会翻转
        canvas.scale(-0.5f,-0.5f,200,0); // 画布缩放,缩放中心向右偏移了200个单位

        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);


        // 绘制一个逐渐缩小的隧道效果
        mPaint.setStyle(Paint.Style.STROKE);    // 设置描边
        mPaint.setColor(Color.GREEN);
        RectF rect2 = new RectF(-400,-400,400,400);   // 矩形区域
        for (int i = 0; i <= 20; i++) {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rect2,mPaint);
        }

        // 恢复现场，只恢复了画布大小，坐标系原点还在画布中心
        canvas.scale((float) (Math.pow(10, 21)/ Math.pow(9, 21)),(float) (Math.pow(10, 21)/ Math.pow(9, 21)));
        // 注意这里的 200，0 上面是在 200，0 缩小的，这里就在 200，0 恢复回去
        canvas.scale(-2f, -2f, 200, 0);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect,mPaint);
        /**scale --end--**/

        /**rotate --start--**/
        RectF rect3 = new RectF(0,-200,200,0);   // 矩形区域

        mPaint.setColor(Color.parseColor("#ffff00"));
        canvas.drawRect(rect3,mPaint);

//        canvas.rotate(180);                     // 旋转180度 <-- 默认旋转中心为原点
        canvas.rotate(180,100,0);               // 旋转180度 <-- 旋转中心向右偏移100个单位

        mPaint.setColor(Color.parseColor("#0ffff0"));
        canvas.drawRect(rect3,mPaint);

        // 恢复现场
        canvas.rotate(-180, 100, 0);
        // 旋转也是可以叠加的
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0,0,400,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,380,mPaint);

        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,380,0,400,mPaint);
            canvas.rotate(10);
        }
        // 恢复现场
        canvas.rotate(-10);
        /**rotate --end--**/

        /**skew --start--**/
        RectF rect4 = new RectF(0,0,200,200);   // 矩形区域
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rect4,mPaint);

        canvas.skew(1,0); // 水平错切 45 度
        canvas.skew(0,1); // 垂直错切 45 度

        mPaint.setColor(Color.parseColor("#ff0000"));
        canvas.drawRect(rect4,mPaint);
        /**skew --end--**/
    }
}
