package com.li.ramon.testsurfaceview;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by limeng on 2018/4/20.
 */

public class MySurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private MyThread myThread;

    public MySurfaceView(Context context) {
        super(context);
        // 通过SurfaceView获得SurfaceHolder对象
        holder = getHolder();
        // 为holder添加回调结构SurfaceHolder.Callback
        holder.addCallback(this);
        // 创建一个绘制线程，将holder对象作为参数传入，这样在绘制线程中就可以获得holder
        // 对象，进而在绘制线程中可以通过holder对象获得Canvas对象，并在Canvas上进行绘制
        myThread = new MyThread(holder);
    }

    // 实现SurfaceHolder.Callback接口中的三个方法，都是在主线程中调用，而不是在绘制线程中调用的
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // 启动线程。当这个方法调用时，说明Surface已经有效了
        myThread.setRun(true);
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // 结束线程。当这个方法调用时，说明Surface即将要被销毁了
        myThread.setRun(false);
    }
}
