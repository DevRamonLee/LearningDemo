package ramon.lee.androidui.animation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;

/**
 * Activity 转场动画
 */
public class ActivityConvertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
    }

    public void pendingTransition(View v) {
        Intent intent = new Intent(this, ActivityConvertActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }
}
