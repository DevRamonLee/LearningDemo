package com.li.ramon.addanimation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.li.ramon.addanimation.R;

public class ViewAnimBasicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView basicAnimView;
    private Button resetBtn;
    private Button cancelBtn;
    private Button startBtn;

    private Animation basic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim_basic);

        basicAnimView = findViewById(R.id.basic_anim_img);
        resetBtn = findViewById(R.id.reset);
        cancelBtn = findViewById(R.id.cancel);
        startBtn = findViewById(R.id.start);

        resetBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);

        basic = AnimationUtils.loadAnimation(this, R.anim.view_anim_basic);
//        basicAnimView.setAnimation(basic);
        basicAnimView.startAnimation(basic);
    }

    // 这里没有效果，暂未找到原因
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                basic.reset();
                break;
            case R.id.cancel:
                basic.cancel();
                break;
            case R.id.start:
                basic.start();
                break;
        }
    }
}
