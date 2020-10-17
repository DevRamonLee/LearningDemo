package ramon.better.top.androidui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ramon.better.top.androidui.R;

/**
 * Created by meng.li on 2019/1/15.
 * 自定义视图方式一： 组合已有系统视图的方式
 */

public class TitleView extends RelativeLayout {
    //返回按钮控件
    private Button mLeftBtn;
    //标题 TextView
    private TextView mTitleView;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);

        //获取控件
        mLeftBtn = (Button) findViewById(R.id.left_btn);
        mTitleView = (TextView) findViewById(R.id.title_tv);
    }

    //为左侧返回按钮添加自定义点击事件
    public void setLeftButtonListener(OnClickListener listener) {
        mLeftBtn.setOnClickListener(listener);
    }

    //设置标题的方法
    public void setTitleText(String title) {
        mTitleView.setText(title);
    }
}
