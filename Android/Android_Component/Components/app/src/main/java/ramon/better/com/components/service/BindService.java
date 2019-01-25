package ramon.better.com.components.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {
    private static final String TAG = "BindServiceTag";
    private int count;
    private boolean quit;

    //定义onBind 方法返回的对象
    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        //获取Service运行状态,count
        public int getCount() {
            return count;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //绑定该Service时回调的方法
        Log.i(TAG, "service is binded");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "service is created");
        //启动一条线程，动态修改count状态值
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                        count++;
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        //service关闭之前回调
        super.onDestroy();
        this.quit = true;
        Log.i(TAG, "service is destroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //断开连接时回调
        Log.i(TAG, "service onUnbind");
        return true;
    }
}