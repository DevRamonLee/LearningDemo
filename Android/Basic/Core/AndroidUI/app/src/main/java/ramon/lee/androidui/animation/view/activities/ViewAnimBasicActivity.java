package ramon.lee.androidui.animation.view.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;

/**
 * View 视图动画，旋转、平移、透明度、缩放
 */
public class ViewAnimBasicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView basicAnimView;
    private Button cancelBtn;
    private Button startBtn;

    private Animation basic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim_basic);

        basicAnimView = findViewById(R.id.basic_anim_img);
        cancelBtn = findViewById(R.id.cancel);
        startBtn = findViewById(R.id.start);

        cancelBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);

        basic = AnimationUtils.loadAnimation(this, R.anim.view_anim_basic);
        basicAnimView.setAnimation(basic);
        basicAnimView.startAnimation(basic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                basicAnimView.clearAnimation();
                basicAnimView.setAnimation(null);
                break;
            case R.id.start:
                basicAnimView.setAnimation(basic);
                basicAnimView.startAnimation(basic);
                break;
        }
    }
}
