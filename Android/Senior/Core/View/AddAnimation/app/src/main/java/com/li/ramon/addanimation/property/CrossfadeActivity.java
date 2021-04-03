package com.li.ramon.addanimation.property;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.li.ramon.addanimation.R;

public class CrossfadeActivity extends AppCompatActivity {
    private View mContentView;
    private View mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossfade);
        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);
        // 对于被淡入的组件，我们先设置它为 GONE 防止她占据空间，加快布局计算过程
        mContentView.setVisibility(View.GONE);
        crossfade();
    }

    private void crossfade() {
        // 对于正在淡入的View，设置它的alpha值为0并且设置visibility为
        // VISIBLE（记住他起初被设置成了 GONE）。这样View就变成可见的了，
        // 但是此时它是透明的。
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        // 对于正在淡入的 View，把 alpha 值从 0 动态改变到 1
        mContentView.animate()
                .alpha(1f)
                .setDuration(3000)
                .setListener(null);

        // 设置淡出 View 的 alpha 值为 0 ，同时动画执行结束后把 view 设置为 GONE，防止其再占据空间
        mLoadingView.animate()
                .alpha(0f)
                .setDuration(3000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingView.setVisibility(View.GONE);
                    }
                });
    }
}
