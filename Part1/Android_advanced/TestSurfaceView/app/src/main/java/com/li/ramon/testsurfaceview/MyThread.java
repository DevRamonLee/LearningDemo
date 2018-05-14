package com.li.ramon.testsurfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * Created by limeng on 2018/4/20.
 * 绘制线程
 */

public class MyThread extends Thread {
    private SurfaceHolder holder;
    private boolean run;

    public MyThread(SurfaceHolder holder) {
        this.holder = holder;
        run = true;
    }

    @Override
    public void run() {
        int counter = 0;
        Canvas canvas = null;
        while (run) {
            // 具体绘制工作
            try {
                // 获取Canvas对象，并锁定之
                canvas = holder.lockCanvas();

                // 设定Canvas对象的背景颜色
                canvas.drawColor(Color.WHITE);

                // 创建画笔
                Paint p = new Paint();
                // 设置画笔颜色
                p.setColor(Color.BLACK);
                // 设置文字大小
                p.setTextSize(80);

                // 创建一个Rect对象rect
                Rect rect = new Rect(100, 50, 880, 830);
                // 在canvas上绘制rect
                canvas.drawRect(rect, p);
                // 在canvas上显示时间
                canvas.drawText("Interval = " + (counter++) + " seconds.", 100, 900, p);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    // 解除锁定，并提交修改内容
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
