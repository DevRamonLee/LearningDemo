package top.betterramon.activitydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import top.betterramon.activitydemo.R;
import top.betterramon.activitydemo.util.ToastUtils;

public class SingleInstanceActivity extends AppCompatActivity {
    private static final String TAG = "SingleInstanceTag";
    private TextView showAddressTv;
    private Button standardBtn;
    private Button singleInstanceBtn;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume called");
    }

    // 第一次启动不会调用这个，后续启动如果 Activity 在栈顶，会调用这个方法
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToastUtils.showToast("Single Instance, onNewIntent called.");
        Log.i(TAG, "onNewIntent called");
    }

    private void init() {
        mContext = SingleInstanceActivity.this;
        showAddressTv = findViewById(R.id.address_show_tv);
        showAddressTv.setText(SingleInstanceActivity.this.toString());
        standardBtn = (Button) findViewById(R.id.standard_btn);
        standardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StandardActivity.class);
                mContext.startActivity(intent);
            }
        });
        singleInstanceBtn = (Button) findViewById(R.id.single_instance_btn);
        singleInstanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SingleInstanceActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
