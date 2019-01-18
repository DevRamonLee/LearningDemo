package ramon.better.top.androidui.layout;

import android.content.Intent;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ramon.better.top.androidui.R;
import ramon.better.top.androidui.fragment.LeftMenuFragment;
import ramon.better.top.androidui.fragment.RightContentFragment;
import ramon.better.top.androidui.testactivity.NextActivity;

/***
 * SlidingPaneLayout 是 Support V4 包中提供的，2013 年 Google I/O 大会期间更新的。
 */

/*实例一： 简单使用，请打开布局中的对应注释 */
/*public class SlidingPaneLayoutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout);
    }
}*/



/* 实例二：结合 Frament 实现菜单和内容 解耦  请打开布局中的对应注释*/
/*
public class SlidingPaneLayoutActivity extends AppCompatActivity implements
        LeftMenuFragment.BookMarkListener, SlidingPaneLayout.PanelSlideListener {

    private SlidingPaneLayout mRootSPL;
    private LeftMenuFragment mLeftFragment;
    private RightContentFragment mRightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout);
        initView();
        initListener();
    }

    private void initView() {
        mRootSPL = (SlidingPaneLayout) findViewById(R.id.spl_root);
        mLeftFragment = (LeftMenuFragment) getFragmentManager().findFragmentById(R.id.fragment_leftmenu);
        mRightFragment = (RightContentFragment) getFragmentManager().findFragmentById(R.id.fragment_rightcontent);
    }

    private void initListener() {
        mRootSPL.setPanelSlideListener(this);
        mLeftFragment.setListener(this);
    }

    @Override
    public void onChangeBookMark(String bookMark) {
        mRightFragment.setContent(bookMark);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
    }

    @Override
    public void onPanelOpened(View panel) {
        mLeftFragment.setHasOptionsMenu(true);
    }

    @Override
    public void onPanelClosed(View panel) {
        mLeftFragment.setHasOptionsMenu(false);
    }
}
*/


/* 实例三：进阶---实现滑动关闭 Activity
*   思路：
*       1、效果分析
        我们分析一下滑动关闭的效果：就是随着手势右移，当前 Activity 的窗口整个向右移动，而其
        左侧滑出的区域可以看到下面的 Activity。
        2、关于左滑菜单有没有感觉这和一些左滑菜单很像，只是它的左侧“菜单”是透明的，
        而且可以覆盖整个屏幕。
        3、可以这样，用一个透明的全屏布局作为左侧菜单，这样就可以看到当前 Activity 下面的
        Activity了；然后，当菜单全部打开的时候关闭当前 Activity，这样就可以实现左滑关闭的效果了。

        实现类为 BaseSlideCloseActivity
*/
public class SlidingPaneLayoutActivity extends AppCompatActivity {
    private Button nextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout);
        initView();
    }

    private void initView() {
        nextActivity = (Button)findViewById(R.id.slide_close_activity);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SlidingPaneLayoutActivity.this, NextActivity.class);
                SlidingPaneLayoutActivity.this.startActivity(intent);
            }
        });
    }
}
