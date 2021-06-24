package ramon.lee.androidui.layout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.view.View;

import ramon.lee.androidui.R;
import ramon.lee.androidui.fragment.LeftMenuFragment;
import ramon.lee.androidui.fragment.RightContentFragment;

/* 实例二：结合 Frament 实现菜单和内容 解耦  请打开布局中的对应注释*/
public class SlidingPaneActivityDemo2 extends AppCompatActivity implements
        LeftMenuFragment.BookMarkListener, SlidingPaneLayout.PanelSlideListener {

    private SlidingPaneLayout mRootSPL;
    private LeftMenuFragment mLeftFragment;
    private RightContentFragment mRightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_demo2);
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


