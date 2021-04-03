package ramon.better.top.androidui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by meng.li on 2019/1/18.
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
        mPaint.setStyle(Paint.Style.FILL); // 填充
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
    }
}
