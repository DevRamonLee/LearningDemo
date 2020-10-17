package ramon.better.top.androidui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import ramon.better.top.androidui.customview.entity.PieData;

/**
 * Created by meng.li on 2019/1/18.
 * 绘制一个饼状图
 */

public class PieView extends View {
    // 颜色表（带 Alpha 通道的，也就是透明度）
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636,
            0xFF800000, 0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<PieData> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);// 设置填充模式
        mPaint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mData)
            return;
        float currentStartAngle = mStartAngle; //当前起始角度
        canvas.translate(mWidth / 2, mHeight / 2); //将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.5);//饼状图半径
        RectF rect = new RectF(-r, -r, r, r);//饼状图绘制区域

        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            // 绘制扇形
            canvas.drawArc(rect, currentStartAngle, pie.getAngle(), true, mPaint);
            //绘制文字
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(40);
            canvas.save();// 保存当前的状态
            canvas.rotate(pie.getAngle() / 2 + currentStartAngle); // 旋转画布，注意角度的计算，我们把文字绘制在扇形弧边中央
            canvas.drawText(pie.getName() + " " + pie.getValue(), r + 10 , 0, mPaint);
            canvas.restore(); // 恢复画布
            // 更新起始角度
            currentStartAngle += pie.getAngle();
        }
    }

    // 设置起始角度
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate(); // 刷新
    }

    // 设置数据
    public void setDate(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();
    }

    //初始化数据
    private void initData(ArrayList<PieData> mData) {
        if (null == mData || mData.size() == 0) // 数据有问题，直接返回
            return;
        float sumValue = 0;
        for(int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            sumValue += pie.getValue();  // 计算数值和

            int j = i % mColors.length; //设置颜色
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            float percentage = pie.getValue() / sumValue; // 百分比
            float angle = percentage * 360;  //对应的角度

            pie.setPercentage(percentage);  // 记录百分比
            pie.setAngle(angle);  //记录角度大小
            sumAngle += angle;
            Log.i("angle", " " + pie.getAngle());
        }
    }
}
