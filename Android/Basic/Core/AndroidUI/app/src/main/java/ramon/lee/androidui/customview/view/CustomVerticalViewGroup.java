package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by meng.li on 2019/1/17.
 * 自定义 ViewGroup，实现把组件按顺序竖直排列，类似 LinearLayout 的效果
 */

public class CustomVerticalViewGroup extends ViewGroup{
    public CustomVerticalViewGroup(Context context) {
        super(context);
    }

    public CustomVerticalViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 第一步：重写 onMeasure, 实现测量子 View 的大小和 设定 ViewGroup 的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意下面的方法需要与 measureChild区分，measureChild是对单个View进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        // 下面是计算 ViewGroup 的大小
        if(childCount == 0) {
            //如果没有子 View，当前 ViewGroup 没有存在的意义，不用占空间
            setMeasuredDimension(0, 0);
        } else {
            //如果宽高都是 wrap_content
            if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                //我们将高度设置为所有子 View 的高度相加，宽度设为 View 中的最大的宽度
                int height = getTotalHeight();
                int width = getMaxChildWidth();
                setMeasuredDimension(width, height);
            } else if ( heightMode == MeasureSpec.AT_MOST){
                //只有高度是包裹内容
                // 宽度设置为 ViewGroup自己的测量宽度，高度设置为所有子 View 的高度综合
                setMeasuredDimension(widthSize, getTotalHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {
                //只有宽度是包裹内容，宽度设置为子 View 中的宽度最大值，
                // 高度设置为 ViewGroup 自己的测量值
                setMeasuredDimension(getMaxChildWidth(), heightSize);
            }
        }
    }

    /**
     * 获取子 View 中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    /**
     * 将所有子 View 的高度相加
     * @return
     */
    private int getTotalHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }

    /**
     * 第二步： 摆放子 View
     * @param b
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int count = getChildCount();
        //记录当前的高度位置
        int currentHeight = top;
        //将子View逐个摆放
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            //摆放子 View，参数分别为矩形区域的左、上、右、下
            child.layout(left, currentHeight, left + width, currentHeight + height);
            //更新当前高度
            currentHeight += height;
        }
    }
}
