package ramon.lee.androidui.layout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

import ramon.lee.androidui.R;

/* 实例三： 播放动画效果  请打开对应的注释 */
public class FrameLayoutActivityDemo3 extends AppCompatActivity {
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
        setContentView(R.layout.activity_frame_layout_demo3);

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
