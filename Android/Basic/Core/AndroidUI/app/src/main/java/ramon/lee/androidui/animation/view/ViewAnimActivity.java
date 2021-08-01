package ramon.lee.androidui.animation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;
import ramon.lee.androidui.animation.view.activities.ActivityConvertActivity;
import ramon.lee.androidui.animation.view.activities.PopUpWindowActivity;
import ramon.lee.androidui.animation.view.activities.ViewAnimBasicActivity;
import ramon.lee.androidui.animation.view.activities.ViewGroupActivity;

/**
 * 视图动画
 */
public class ViewAnimActivity extends AppCompatActivity implements View.OnClickListener {

    private Button viewAnimBasic;
    private Button viewAnimPop;
    private Button viewAnimActivity;
    private Button viewAnimGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim);

        initView();
    }

    private void initView() {
        viewAnimBasic = findViewById(R.id.view_anim_basic);
        viewAnimPop = findViewById(R.id.view_anim_popup_window);
        viewAnimActivity = findViewById(R.id.view_anim_activity);
        viewAnimGroup = findViewById(R.id.view_anim_group);

        viewAnimBasic.setOnClickListener(this);
        viewAnimPop.setOnClickListener(this);
        viewAnimActivity.setOnClickListener(this);
        viewAnimGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.view_anim_basic:
                intent = new Intent(this, ViewAnimBasicActivity.class);
                startActivity(intent);
                break;
            case R.id.view_anim_popup_window:
                intent = new Intent(this, PopUpWindowActivity.class);
                startActivity(intent);
                break;
            case R.id.view_anim_activity:
                intent = new Intent(this, ActivityConvertActivity.class);
                startActivity(intent);
                break;
            case R.id.view_anim_group:
                intent = new Intent(this, ViewGroupActivity.class);
                startActivity(intent);
                break;
        }
    }
}
