package ramon.lee.androidui.customview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import ramon.lee.androidui.R;
import ramon.lee.androidui.customview.view.CustomListView;
import ramon.lee.androidui.customview.adapter.CustomListViewAdapter;

/**
 *  Android 自定义视图的三种实现方式
 *   1.组合视图
 *   2.继承 View
 *   3.自绘方式
 */

/**
 * 方式一： 组合视图的方式，请打开布局中对应的注释代码
 */
/*public class CustomViewThreeTypesActivity extends AppCompatActivity {
    private TitleView mTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_three_types);
        mTitleBar = (TitleView) findViewById(R.id.title_bar);

        mTitleBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomViewThreeTypesActivity.this, "You clicked the back button!" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}*/

/**
 * 方式二： 绘制视图,实现每次点击文本，数字加1
 */
/*public class CustomViewThreeTypesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_three_types);
    }
}*/

/**
 * 方式三：继承系统视图，实现可以横向滑动删除列表项的 ListView ,请打开布局中的对应注释
 */

public class CustomViewThreeTypesActivity extends AppCompatActivity {
    //自定义 ListView
    private CustomListView mCustomListView;
    //自定义适配器
    private CustomListViewAdapter mAdapter;
    //内容列表
    private List<String> contentList = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_view_three_types);

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
