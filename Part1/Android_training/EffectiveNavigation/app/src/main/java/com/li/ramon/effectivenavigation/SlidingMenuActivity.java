package com.li.ramon.effectivenavigation;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SlidingMenuActivity extends AppCompatActivity {
    //声明SlideMenu对象
    private SlidingMenu slidingMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);
        //替换主界面内容
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new MainFragment()).commit();
        //实例化菜单控件
        slidingMenu=new SlidingMenu(this);
        //设置相关属性
        slidingMenu.setMode(SlidingMenu.LEFT);//菜单靠左
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏支持触摸拖拉
        slidingMenu.setBehindOffset(350);//SlidingMenu划出时主页面显示的剩余宽度
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//不包含ActionBar
        slidingMenu.setMenu(R.layout.sliding_menu_left_content);

        TextView menuText1= (TextView) findViewById(R.id.menutext1);
        TextView menuText2= (TextView) findViewById(R.id.menutext2);
        menuText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new MainFragment()).commit();
                slidingMenu.toggle();
            }
        });
        menuText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new SecondFragment()).commit();
                slidingMenu.toggle();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            // 对action bar的Up/Home按钮做出反应
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);*/
        switch (item.getItemId()) {
            // 对action bar的Up/Home按钮做出反应
            case android.R.id.home:
                Toast.makeText(this,"click home",Toast.LENGTH_SHORT).show();
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    /* 这个activity不是这个app任务的一部分, 所以当向上导航时创建一个新任务栈*/
                    TaskStackBuilder.create(this)
                            // 添加这个activity的所有父activity到后退栈中
                            .addNextIntentWithParentStack(upIntent)
                            // 向上导航到最近的一个父activity
                            .startActivities();
                } else {
                    /* 这个activity是这个app任务的一部分, 所以向上导航至逻辑父activity.*/
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重写了Menu监听，实现按下手机Menu键弹出和关闭侧滑菜单
        if(keyCode== KeyEvent.KEYCODE_MENU){
            slidingMenu.toggle();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 左侧菜单栏
     */
    public static class MenuFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.sliding_menu_left_content, container, false);
        }
    }
    /**
     * 第一个菜单栏主页面
     */
    public static class MainFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.main_content,container, false);
        }
    }
    /**
     * 第二个菜单栏主页面
     */
    public static class SecondFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.second_content,container, false);
        }
    }
}
