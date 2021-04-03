package com.li.ramon.addanimation.frame;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.li.ramon.addanimation.R;

/**
 * 帧动画
 */
public class FrameAnimActivity extends AppCompatActivity {

    private ImageView loadingImg;
    private AnimationDrawable loadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_anim);

        loadingImg = findViewById(R.id.loading_anim_img);

        // 设置帧动画
        loadingImg.setBackgroundResource(R.drawable.anim_loading_view);
        loadingAnim = (AnimationDrawable)loadingImg.getBackground();
        loadingAnim.start();
    }
}
