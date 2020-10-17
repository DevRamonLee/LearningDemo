package com.li.ramon.addanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.li.ramon.addanimation.frame.FrameAnimActivity;
import com.li.ramon.addanimation.property.LayoutChangesActivity;
import com.li.ramon.addanimation.property.PropertyActivity;
import com.li.ramon.addanimation.view.ViewAnimActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button viewAnimation;       // 视图动画
    private Button frameAnimation;      // 帧动画
    private Button propertyAnimation;   // 属性动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    protected void init() {
        viewAnimation = findViewById(R.id.view_animation);
        frameAnimation = findViewById(R.id.frame_animation);
        propertyAnimation = findViewById(R.id.property_animation);

        viewAnimation.setOnClickListener(this);
        frameAnimation.setOnClickListener(this);
        propertyAnimation.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.view_animation:   // 视图动画
                intent = new Intent(MainActivity.this, ViewAnimActivity.class);
                startActivity(intent);
                break;
            case R.id.frame_animation:  // 帧动画
                intent = new Intent(MainActivity.this, FrameAnimActivity.class);
                startActivity(intent);
                break;
            case R.id.property_animation:   // 属性动画
                intent = new Intent(MainActivity.this, PropertyActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_change:        // 布局改变动画
                intent = new Intent(MainActivity.this, LayoutChangesActivity.class);
                startActivity(intent);
                break;
        }
    }
}
