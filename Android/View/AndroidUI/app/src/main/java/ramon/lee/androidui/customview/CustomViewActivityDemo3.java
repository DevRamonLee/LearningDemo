package ramon.lee.androidui.customview;

import android.app.Activity;
import android.os.Bundle;

import ramon.lee.androidui.R;

/**
 * 2021：06：26
 * 自定义 View ，自定义属性的优先级
 * 直接在XML中定义 > style定义 > 由defStyleAttr定义的值 > defStyleRes指定的默认值 > 直接在Theme中指定的值
 */
public class CustomViewActivityDemo3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_demo3);
    }
}
