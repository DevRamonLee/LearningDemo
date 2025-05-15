package top.betterramon.viewscrolldemo.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by meng.li on 2019/3/13.
 *  实现弹性滑动
 */

public class ScrollerListView extends ListView{
    Scroller mScroller;
    public ScrollerListView(Context context) {
        super(context);
    }

    public ScrollerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }


    // 缓慢滑动到指定位置
    public void smoothScrollTo(int destX, int destY) {
        // 获取当前内容相对于左边界的偏移量
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 1000 ms 内滑向 destX
        mScroller.startScroll(scrollX, 0, delta, 0, 5000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset() ) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
