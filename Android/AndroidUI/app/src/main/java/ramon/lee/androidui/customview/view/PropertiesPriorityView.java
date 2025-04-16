package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.AttributeSet;
import android.util.Log;

import ramon.lee.androidui.R;

/**
 * Created by meng.li on 2019/1/18.
 * 验证自定义属性值的优先级
 * 直接在XML中定义 > style定义 > 由defStyleAttr定义的值 > defStyleRes指定的默认值 > 直接在Theme中指定的值
 */

public class PropertiesPriorityView extends AppCompatTextView {
    private static final String TAG = PropertiesPriorityView.class.getSimpleName();
    public PropertiesPriorityView(Context context) {
        this(context, null);
    }

    public PropertiesPriorityView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.PropertiesPriorityViewStyle);
    }

    public PropertiesPriorityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PropertiesPriorityView, defStyleAttr, R.style.DefaultPropertiesPriorityView);
        String one = a.getString(R.styleable.PropertiesPriorityView_attr_one);
        String two = a.getString(R.styleable.PropertiesPriorityView_attr_two);
        String three = a.getString(R.styleable.PropertiesPriorityView_attr_three);
        String four = a.getString(R.styleable.PropertiesPriorityView_attr_four);
        Log.i(TAG, "one:" + one);
        Log.i(TAG, "two:" + two);
        Log.i(TAG, "three:" + three);
        Log.i(TAG, "four:" + four);
        a.recycle();
    }
}
