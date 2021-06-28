package ramon.lee.androidui.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ramon.lee.androidui.R;
import ramon.lee.androidui.customview.adapter.CustomListViewAdapter;
import ramon.lee.androidui.customview.view.CustomListView;
import ramon.lee.androidui.customview.view.TitleView;

/**
 * 实现自定义 View 的三种方式：
 * 1. 组合 View
 * 2. 继承 View 自绘 view
 * 3. 继承现有组件
 */
public class CustomViewActivityDemo1 extends AppCompatActivity {

    private TitleView mTitleBar;

    //自定义 ListView
    private CustomListView mCustomListView;
    //自定义适配器
    private CustomListViewAdapter mAdapter;
    //内容列表
    private List<String> contentList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_demo1);
        mTitleBar = (TitleView) findViewById(R.id.title_bar);

        mTitleBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomViewActivityDemo1.this, "You clicked the back button!" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        initContentList();

        mCustomListView = (CustomListView) findViewById(R.id.custom_lv);
        mCustomListView.setOndeleteListener(new CustomListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                contentList.remove(index);
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter = new CustomListViewAdapter(this, 0, contentList);
        mCustomListView.setAdapter(mAdapter);
    }

    // 初始化内容列表
    private void initContentList() {
        for(int k = 0; k < 20; k++) {
            contentList.add("内容项 " + k);
        }
    }

    @Override
    public void onBackPressed() {
        // 如果删除按钮显示，先隐藏删除按钮
        if(mCustomListView.isDeleteShown()) {
            mCustomListView.hideDelete();
            return;
        }
        super.onBackPressed();
    }
}