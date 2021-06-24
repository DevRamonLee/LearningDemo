package ramon.lee.androidui.customview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import ramon.lee.androidui.R;

/**
 * Created by meng.li on 2019/1/17.
 * 自定义View 实例： 重写  onMeasure 方法
 * 自定义宽高相等的 View， 宽高默认值为 100像素
 * 把组件绘制为圆形并且添加自定义属性
 */

@SuppressLint("AppCompatCustomView")
public class SquareView extends TextView{
    private  int defaultsize;

    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //第二个参数就是我们在 style.xml 中定义的 <declare-styleable>标签属性集合
        //在 R 文件中的名称为 R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareView);

        //第一个参数为属性集合里的属性， R.styleable+属性名称集合 + 下划线 + 属性名
        //第二个参数为：如果没有设置这个属性的默认值
        defaultsize = a.getDimensionPixelSize(R.styleable.SquareView_default_size, 100);

        //最后将 TypeArray 对象回收
        a.recycle();
    }

    private int getCustomSize(int defaultSize, int measureSpec) {
        int customSize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                //未指定大小，给默认大小
                customSize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                //如果测量模式是 wrap_content，我们取最大值
                customSize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {
                //如果是固定大小，就不要改变它
                customSize = size;
                break;
            }
        }
        return customSize;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getCustomSize(defaultsize, widthMeasureSpec);
        int height = getCustomSize(defaultsize, heightMeasureSpec);

        if(width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //调用父 View 的 onDraw 方法，父类会实现基本的绘制。
        super.onDraw(canvas);
        int r = getMeasuredWidth() /2;//也可以是高的一半，因为我们的宽高是相等的
        //圆心的横坐标是当前 View 的左边起始位置 + 半径
        //注意不要使用 getLeft + r 和 getTop + r 这里和父布局无关，圆心指的是布局本身的位置
        int centerX = r;
        int centerY = r;

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(centerX, centerY, r, paint);
    }
}
