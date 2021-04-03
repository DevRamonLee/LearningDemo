package ramon.better.top.androidui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by meng.li on 2019/1/15.
 * 自定义视图方式二：绘制视图,实现每次点击文本，数字加1
 */

public class CounterView extends View implements View.OnClickListener{

    //定义画笔
    private Paint mPaint;
    //用于获取文本的宽和高
    private Rect mBounds;
    //计数值，每次点击文本控件，其值增加1
    private int mCount;

    //采用 xml 的使用的话，需要使用 两个参数的构造函数
    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔，Rect
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
        //文本控件的点击事件
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        //绘制一个填充色为蓝色的矩形
        canvas.drawRect(0, 0 , getWidth(), getHeight(), mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(70);
        String text = String.valueOf(mCount);
        //获取文字的宽和高
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();

        //绘制字符串
        canvas.drawText(text, getWidth()/2 - textWidth/2,
                getHeight()/2 + textHeight/2, mPaint);
    }

    @Override
    public void onClick(View view) {
        mCount ++;
        //重绘
        invalidate();
    }
}
