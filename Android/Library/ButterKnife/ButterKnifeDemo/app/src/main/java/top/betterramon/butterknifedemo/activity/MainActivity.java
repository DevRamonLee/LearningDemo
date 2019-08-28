package top.betterramon.butterknifedemo.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.betterramon.butterknifedemo.R;

public class MainActivity extends AppCompatActivity {
    @BindString(R.string.app_name)
    String appName;
    @BindDrawable(R.drawable.ic_launcher_foreground)
    Drawable iconImg;
    @BindColor(R.color.red)
    int red;
    @BindDimen(R.dimen.dimen_10)
    int margin;
    @BindView(R.id.icon_img)
    ImageView imageView;
    @BindView(R.id.app_name_tv)
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imageView.setImageDrawable(iconImg);
        textView.setText(appName);
        textView.setTextColor(red);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.setMargins(margin, margin, margin, margin);
    }

    @OnClick(R.id.go_fragment_btn)
    public void goFragmentActivity() {
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
    }

    /*
        绑定多个 id 事件，可以使用 AS 的 ButterKnife 的 zelezny 插件
     */
    @OnClick({R.id.button1, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Toast.makeText(this, "multi binding button1 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(this, "multi binding button2 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                Toast.makeText(this, "multi binding button3 clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 当找不到 test_lv 时候不会报错
    @Nullable
    @BindView(R.id.test_lv)
    ListView test;
}
