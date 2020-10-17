package ramon.better.top.androidui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import ramon.better.top.androidui.R;

/**
 * Created by meng.li on 2019/1/15.
 * 自定义视图实例三：继承控件，实现一个可以横向滑动删除列表项的 ListView
 */

public class CustomListView extends ListView
        implements View.OnTouchListener, GestureDetector.OnGestureListener {

    //手势动作探测器
    private GestureDetector mGestureDetector;
    //删除事件监听器
    public interface OnDeleteListener {
        void onDelete(int index);
    }

    private OnDeleteListener mOndeleteListener;
    //删除按钮
    private View mDeleteBtn;
    //列表项布局
    private ViewGroup mItemLayout;
    //选择的列表项
    private int mSelectedItem;
    //当前删除按钮是否显示出来了
    private boolean isDeleteShown;

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //创建手势监听器对象
        mGestureDetector = new GestureDetector(getContext(), this);
        //监听onTouch事件
        setOnTouchListener(this);
    }
    //设置删除监听事件
    public void setOndeleteListener(OnDeleteListener listener) {
        mOndeleteListener = listener;
    }

    // 触摸监听事件
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(isDeleteShown) {
            hideDelete();
            return false;
        } else {
            return mGestureDetector.onTouchEvent(motionEvent);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if(!isDeleteShown) {
            // 根据触摸点的坐标找到是那个 item 被选中
            mSelectedItem = pointToPosition((int) motionEvent.getX(),(int) motionEvent.getY());
            Log.i("meng", "mSelectedItem is " + mSelectedItem);
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
          float velocityY) {
        //如果当前删除按钮没有显示出来，并且 x 方向滑动的速度大于 y 方向的滑动速度
        if(!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)) {
            // 初始化 Button 视图
            mDeleteBtn = LayoutInflater.from(getContext()).inflate(
                    R.layout.delete_btn,null);
            mDeleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemLayout.removeView(mDeleteBtn);
                    mDeleteBtn = null;
                    isDeleteShown = false;
                    mOndeleteListener.onDelete(mSelectedItem);
                }
            });
            // getChildAt 在ListView 当前可见集合中返回指定位置的视图
            // getFirstVisiblePosition 返回的是当前可见的第一个
            // mSelectedItem 选中的位置 减去 第一个可见的就代表当前选中的布局在可见列表中的位置
            mItemLayout = (ViewGroup) getChildAt(mSelectedItem
                    - getFirstVisiblePosition());

            // 设置 Button 的布局属性，右部居中
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            if(mItemLayout != null) {
                mItemLayout.addView(mDeleteBtn, params);
                isDeleteShown = true;
            }
        }
        return false;
    }
    // 隐藏删除按钮
    public void hideDelete() {
        mItemLayout.removeView(mDeleteBtn);
        mDeleteBtn = null;
        isDeleteShown = false;
    }
    public boolean isDeleteShown() {
        return isDeleteShown;
    }

    // 后面几个方法这个例子中没有用到

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }
}
