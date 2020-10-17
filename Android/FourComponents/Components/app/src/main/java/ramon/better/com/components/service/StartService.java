package ramon.better.com.components.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by meng.li on 2019/1/25.
 * 通过 StartService 方式启动 Service
 */

public class StartService extends Service{
    private static final String TAG = "StartServiceTag";
    boolean threadRunning = true; // 停止线程的标识
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "StartService onCreate called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "StartService onBind called");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "StartService onStartCommand called");
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < 100 && threadRunning; i++) {
                        Thread.sleep(1000);
                        Log.i(TAG, "i = " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadRunning = false;
        Log.i(TAG, "StartService onDestroy called");
    }
}
