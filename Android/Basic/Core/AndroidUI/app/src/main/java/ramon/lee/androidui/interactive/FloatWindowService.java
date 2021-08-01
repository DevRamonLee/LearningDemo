package ramon.lee.androidui.interactive;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import ramon.lee.androidui.R;

public class FloatWindowService extends Service {

    private WindowManager windowManager;
    private WindowManager.LayoutParams wmParams;
    private LayoutInflater inflater;

    // 浮动布局
    private View mFloatingLayout;
    private LinearLayout linearLayout;

    public FloatWindowService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        initWindow();
        // 悬浮窗点击事件处理
        initFloating();
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public FloatWindowService getService() {
            return FloatWindowService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 初始化窗口
     */
    private void initWindow() {
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        // 设置悬浮窗的参数
        wmParams = getParams();
        // 悬浮窗默认显示以左上角为坐标
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 悬浮窗开始位置，因为设置的是左上角，开始坐标是 0，0
        wmParams.x = windowManager.getDefaultDisplay().getWidth();
        wmParams.y = 210;
        inflater = LayoutInflater.from(getApplicationContext());
        // 获取悬浮窗视图所在布局
        mFloatingLayout = inflater.inflate(R.layout.remoteview, null);
        // 添加悬浮窗的视图
        windowManager.addView(mFloatingLayout, wmParams);
    }

    private WindowManager.LayoutParams getParams() {
        wmParams = new WindowManager.LayoutParams();
        //设置window type 下面变量2002是在屏幕区域显示，2003则可以显示在状态栏之上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置可以显示在状态栏上
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return wmParams;
    }


    /**
     * 悬浮窗点击事件
     */
    private void initFloating() {
        linearLayout = mFloatingLayout.findViewById(R.id.line1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FloatWindowService.this, FloatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // 设置悬浮窗触摸事件
        linearLayout.setOnTouchListener(new FloatingListener());
    }

    // 开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private int mTouchStartX, mTouchStartY, mTouchCurrentX, mTouchCurrentY;

    // 开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private int mStartX, mStartY, mStopX, mStopY;

    // 判断悬浮窗口是否移动，这里做一个标记，防止移动后松手触发了点击事件
    private Boolean isMove = false;

    class FloatingListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isMove = false;
                    mTouchStartX = (int) event.getRawX();
                    mTouchStartY = (int) event.getRawY();
                    mStartX = (int) event.getX();
                    mStartY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTouchCurrentX = (int) event.getRawX();
                    mTouchCurrentY = (int) event.getRawY();
                    wmParams.x += mTouchCurrentX - mTouchStartX;
                    wmParams.y += mTouchCurrentY - mTouchStartY;
                    windowManager.updateViewLayout(mFloatingLayout, wmParams);
                    mTouchStartX = mTouchCurrentX;
                    mTouchStartY = mTouchCurrentY;
                    break;
                case MotionEvent.ACTION_UP:
                    mStopX = (int) event.getX();
                    mStopY = (int) event.getY();
                    if (Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1) {
                        isMove = true;
                    }
                    break;
            }
            return isMove;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(mFloatingLayout);
    }
}
