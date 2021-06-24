package ramon.lee.androidui.layout;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ramon.lee.androidui.R;
import ramon.lee.androidui.customview.NextActivity;

/* 实例三：进阶---实现滑动关闭 Activity
*   思路：
*       1、效果分析
        我们分析一下滑动关闭的效果：就是随着手势右移，当前 Activity 的窗口整个向右移动，而其
        左侧滑出的区域可以看到下面的 Activity。
        2、关于左滑菜单有没有感觉这和一些左滑菜单很像，只是它的左侧“菜单”是透明的，
        而且可以覆盖整个屏幕。
        3、可以这样，用一个透明的全屏布局作为左侧菜单，这样就可以看到当前 Activity 下面的
        Activity了；然后，当菜单全部打开的时候关闭当前 Activity，这样就可以实现左滑关闭的效果了。

        实现类为 BaseSlideCloseActivity
*/
public class SlidingPaneActivityDemo3 extends AppCompatActivity {
        private Button nextActivity;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sliding_pane_demo3);
            initView();
        }

        private void initView() {
            nextActivity = (Button)findViewById(R.id.slide_close_activity);
            nextActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SlidingPaneActivityDemo3.this, NextActivity.class);
                    SlidingPaneActivityDemo3.this.startActivity(intent);
                }
            });
        }
}