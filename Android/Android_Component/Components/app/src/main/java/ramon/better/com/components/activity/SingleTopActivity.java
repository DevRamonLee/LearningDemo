package ramon.better.com.components.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ramon.better.com.components.R;
/**
 * Activity 的四种启动模式
 */
public class SingleTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
    }
}
