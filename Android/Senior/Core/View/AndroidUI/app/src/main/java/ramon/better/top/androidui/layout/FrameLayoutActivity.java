package ramon.better.top.androidui.layout;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

import ramon.better.top.androidui.R;
import ramon.better.top.androidui.customview.MosquitoView;

/**
 * FrameLayout 的例子，打开对应的注释可以选择运行不同的例子
 */

/* 实例一：最简单的帧布局  请打开布局中的对应注释*/
/*public class FrameLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);
    }
}*/



/*实例二： 实现 蚊子跟随手指移动的效果 请打开布局中对应的注释*/

/*public class FrameLayoutActivity extends AppCompatActivity {
    static FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        frame = (FrameLayout) findViewById(R.id.frame_container);

        final MosquitoView mosquitoView = new MosquitoView(FrameLayoutActivity.this);
        //为我们的蚊子添加触摸事件监听器
        mosquitoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //设置蚊子的位置
                mosquitoView.bitmapX = motionEvent.getX();
                mosquitoView.bitmapY = motionEvent.getY();
                //调用重绘方法
                mosquitoView.invalidate();
                return true;
            }
        });
        frame.addView(mosquitoView);
    }
}*/

/* 实例三： 播放动画效果  请打开对应的注释 */

public class FrameLayoutActivity extends AppCompatActivity {
    static FrameLayout frame;
    Handler handler;
    static Drawable[] drawables;
    int[] drawableIds = {R.drawable.img001, R.drawable.img002, R.drawable.img003, R.drawable.img004,
                    R.drawable.img005, R.drawable.img006, R.drawable.img007, R.drawable.img008,
                    R.drawable.img009, R.drawable.img010, R.drawable.img011, R.drawable.img012,
                    R.drawable.img013, R.drawable.img014};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        frame = (FrameLayout) findViewById(R.id.frame_container);

        initDrawable();
        handler = new MyHandler();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //发送一条空消息通知系统更改前景图片
                handler.sendEmptyMessage(0x123);
            }
        },0, 170);
    }
    private static class  MyHandler extends Handler {
        int count = 0;
        @Override
        public void handleMessage(Message msg) {
            //判断信息是否是本应用发出的
            if(msg.what == 0x123) {
                count ++;
                move(count % 14);
            }
            super.handleMessage(msg);
        }
    };

    //定义走路时切换图片方法
    private static void move(int count) {
        frame.setForeground(drawables[count]);
    }

    //实例化 Drawable 对象
    private void initDrawable() {
        if (drawables == null) {
            drawables = new Drawable[drawableIds.length];
        }
        for (int i = 0; i < drawableIds.length; i ++) {
            drawables[i] = getResources().getDrawable(drawableIds[i]);
        }
    }
}