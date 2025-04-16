package ramon.lee.fourcomponent.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.activity.MainActivity

private const val CHANNEL_ID = "ForegroundService"
private const val ONGOING_NOTIFICATION_ID = 101

class StartService : Service() {
    companion object {
        private const val TAG = "StartService"
    }

    var threadRunning = true // 停止线程的标识

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand")
        Thread {
            try {
                var i = 0
                while (i < 100 && threadRunning) {
                    Thread.sleep(1000)
                    Log.i(TAG, "thread name = ${Thread.currentThread().id} i = $i")
                    i++
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()

        // 启动一个前台服务
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        // Create the NotificationChannel
        val name = "channel name"
        val descriptionText = "description text"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Start foreground service")
            .setContentText("hello foreground")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setTicker("ticker")
            .build()

        // Notification ID cannot be 0.
        startForeground(ONGOING_NOTIFICATION_ID, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        threadRunning = false
        stopForeground(true)
        Log.i(TAG, "onDestroy")
    }
}