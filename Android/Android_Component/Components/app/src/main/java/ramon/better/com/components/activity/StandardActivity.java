package ramon.better.com.components.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ramon.better.com.components.R;

/**
 * Activity 的四种启动模式
 */
public class StandardActivity extends AppCompatActivity {
    private TextView showAddressTv;
    private Button standardBtn;
    private Button singleTopBtn;
    private Button singleTaskBtn;
    private Button singleInstanceBtn;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        init();
    }

    private void init() {
        mContext = StandardActivity.this;
        showAddressTv = (TextView) findViewById(R.id.address_show_tv);
        standardBtn = (Button) findViewById(R.id.standard_btn);
        singleTopBtn = (Button) findViewById(R.id.single_top_btn);
        singleTaskBtn = (Button) findViewById(R.id.single_task_btn);
        // 显示当前 Activity 的地址信息
        showAddressTv.setText(StandardActivity.this.toString());
        // 使用 Standard 模式启动 Activity
        standardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StandardActivity.class);
                mContext.startActivity(intent);
            }
        });
        // 使用 singleTop 模式启动 Activity
        singleTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SingleTopActivity.class);
                mContext.startActivity(intent);
            }
        });
        // 使用 singleTask 启动模式
        singleTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SingleTaskActivity.class);
                mContext.startActivity(intent);
            }
        });
        singleInstanceBtn = (Button)findViewById(R.id.single_instance_btn);
        // 使用 singleInstance 启动模式
        singleInstanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SingleInstanceActivity.class);
                startActivity(intent);
            }
        });
    }
}
