package ramon.better.com.components.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ramon.better.com.components.R;

public class SingleTaskActivity extends AppCompatActivity {

    private static final String TAG = "SingleTaskActivityTag";
    private TextView showAddressTv;
    private Button singleTopBtn;
    private Button singleTaskBtn;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate called");
        setContentView(R.layout.activity_single_task);
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
        Log.i(TAG, "onNewIntent called");
    }

    private void init() {
        mContext = SingleTaskActivity.this;
        showAddressTv = (TextView) findViewById(R.id.address_show_tv);
        // 显示当前 Activity 的地址信息
        showAddressTv.setText(mContext.toString());

        singleTopBtn = (Button)findViewById(R.id.single_task_btn);
        singleTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        singleTaskBtn = (Button) findViewById(R.id.standard_btn);
        singleTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StandardActivity.class);
                startActivity(intent);
            }
        });
    }
}
