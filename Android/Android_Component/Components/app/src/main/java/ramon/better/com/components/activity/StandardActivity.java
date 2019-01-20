package ramon.better.com.components.activity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        init();
    }

    private void init() {
        showAddressTv = (TextView) findViewById(R.id.address_show_tv);
        standardBtn = (Button) findViewById(R.id.standard_btn);
        // 显示当前 Activity 的地址信息
        showAddressTv.setText(StandardActivity.this.toString());
        // 使用 Standard 模式启动 Activity
        standardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StandardActivity.this, StandardActivity.class);
                StandardActivity.this.startActivity(intent);
            }
        });
    }
}
