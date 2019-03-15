package top.betterramon.viewscrolldemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScrollActivity extends AppCompatActivity {
    private ScrollerListView scrollList;
    String [] arr = new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        scrollList = findViewById(R.id.scroll_lv);
        for (int i = 0; i < 100; i++) {
            arr[i] = String.valueOf("i equals : " + i);
        }
        scrollList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr));
        // View 自带方法
        //scrollList.scrollTo(0, -500);

        // 通过动画滑动
        //ObjectAnimator.ofFloat(scrollList, "translationY", 0, 100).setDuration(2000).start();

        // 通过改变布局参数滑动
        /*ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) scrollList.getLayoutParams();
        params.topMargin += 200;
        scrollList.requestLayout();*/

        // 实现弹性滑动
        //scrollList.smoothScrollTo(200, 0);

        // 利用动画的特性来实现弹性滑动
        final int startY = 0;
        final int deltaY = 300;
        final ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = animator.getAnimatedFraction();
                scrollList.scrollTo(0, startY + (int)(deltaY * fraction));
            }
        });
        //animator.start();

        // 使用 Handler 延时策略来实现渐进滑动
        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLLTO, DELAYED_TIME);
    }

    private static final int MESSAGE_SCROLLTO = 1;
    private static final int FRAME_COUNT = 60; // 60 帧
    private static final int DELAYED_TIME = 33; // 两帧间隔 33 毫秒

    private int mCount = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLLTO: {
                    mCount ++;
                    if (mCount <= FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollY = (int)(fraction * 1000);
                        scrollList.scrollTo(0, scrollY);
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLLTO, DELAYED_TIME);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
