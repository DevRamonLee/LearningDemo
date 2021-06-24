package ramon.lee.androidui.customview;

import android.os.Bundle;

import ramon.lee.androidui.R;
import ramon.lee.androidui.layout.BaseSlideCloseActivity;

/* SlidePaneLayout 实例三： 实现向右滑动关闭当前 Activity 的效果
 * 用法，只需要继承 BaseSlideCloseActivity  */
public class NextActivity extends BaseSlideCloseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
    }
}
