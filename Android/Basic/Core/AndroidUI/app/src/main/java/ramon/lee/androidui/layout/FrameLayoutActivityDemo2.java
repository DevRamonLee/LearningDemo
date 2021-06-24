package ramon.lee.androidui.layout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import ramon.lee.androidui.R;
import ramon.lee.androidui.customview.view.MosquitoView;

public class FrameLayoutActivityDemo2 extends AppCompatActivity {

    static FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_demo2);

        frame = (FrameLayout) findViewById(R.id.frame_container);

        final MosquitoView mosquitoView = new MosquitoView(FrameLayoutActivityDemo2.this);
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
}