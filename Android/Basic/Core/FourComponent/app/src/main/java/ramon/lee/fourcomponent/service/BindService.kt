package ramon.lee.fourcomponent.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BindService : Service() {
    companion object {
        private const val TAG = "BindService"
    }
    private var count = 0
    private var quit = false
    private val binder: MyBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        // 启动一条线程，动态的修改 count 的值
        Thread{
            while (!quit) {
                try {
                    Thread.sleep(1000)
                    count++
                } catch (e: InterruptedException) {
                    print(e.message)
                }
            }
        }.start()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind")
        return binder
    }

    inner class MyBinder: Binder() {
        fun getCount(): Int {
            return count
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        // 断开连接时回调
        Log.i(TAG, "onUnbind")
//        return super.onUnbind(intent)
        // return true ,如果 Service 还活着，下次再次绑定 Service 时， onRebind 会被调用。
        return true
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i(TAG, "onRebind")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        quit = true
        super.onDestroy()
    }
}