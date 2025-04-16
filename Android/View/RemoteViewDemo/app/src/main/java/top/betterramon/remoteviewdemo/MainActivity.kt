package top.betterramon.remoteviewdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import top.betterramon.remoteviewdemo.config.Constants
import top.betterramon.remoteviewdemo.notification.DemoActivity_1

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val NOTIFICATION_CHANNEL = "100021"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        showDefaultNotify.setOnClickListener {
            showDefaultNotify()
        }
        showSimulateNotify.setOnClickListener {
            showSimulateNotify()
        }
        val filter = IntentFilter(Constants.REMOTE_ACTION)
        registerReceiver(mRemoteViewsReceiver, filter)
    }

    // 发送通知栏消息
    private fun showDefaultNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, DemoActivity_1::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val remoteView = RemoteViews(packageName, R.layout.layout_notification)
        remoteView.setTextViewText(R.id.msg, "Open DemoActivity_1")
        remoteView.setImageViewResource(R.id.icon, R.drawable.ic_collected)
        remoteView.setOnClickPendingIntent(R.id.msg, pendingIntent)
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
        } else {
            NotificationCompat.Builder(this)
        }
        notificationBuilder.setContentTitle("Hello notification")   // 设置标题
            .setContentText("This is my notification")  // 设置内容
            .setDefaults(Notification.DEFAULT_ALL)      // 设置通知灯光/铃声/震动
            .setLights(Color.RED, 200, 200) // 灯光的三个参数，颜色 argb，亮时间（毫秒）灭时间（毫秒）
            .setSound(Uri.parse(""))    // 铃声，可以是本地，也可以是网上
            .setVibrate(longArrayOf(0, 200, 200, 200, 200))   // 震动，传入一个数组，表示停/震/停/震
            .setAutoCancel(true)    // 点击通知栏自动消失
            .setContentIntent(pendingIntent)    // 设置 intent
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContent(remoteView)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground))   // 下拉显示的图片

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(NOTIFICATION_CHANNEL, "Android O", NotificationManager.IMPORTANCE_DEFAULT)
            channel.apply {
                enableLights(true)  // 是否在桌面 icon 右上角展示小红点
                lightColor = Color.GREEN    // 小红点颜色
                setShowBadge(false)         // 是否在久按桌面图标时显示此渠道的通知
            }
            manager.createNotificationChannel(channel)
        }
        var notification = notificationBuilder.build()
        manager.notify(1, notification)
    }

    // 使用 RemoteView 发送模拟通知栏消息
    private fun showSimulateNotify() {
        val remoteViews = RemoteViews(packageName, R.layout.layout_notification)
        remoteViews.setTextViewText(R.id.msg, "msg from simulate notification")
//        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_collected) // 这个会导致报错
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, DemoActivity_1::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        remoteViews.setOnClickPendingIntent(R.id.msg, pendingIntent)
        val intent = Intent(Constants.REMOTE_ACTION)
        intent.putExtra(Constants.EXTRA_REMOTE_VIEWS, remoteViews)
        sendBroadcast(intent)
    }

    private val mRemoteViewsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val remoteViews = intent?.getParcelableExtra<RemoteViews>(Constants.EXTRA_REMOTE_VIEWS)
            if (remoteViews != null) {
                updateUI(remoteViews)
            }
        }
    }

    private fun updateUI(remoteViews: RemoteViews) {
        // 调用 apply 来更新界面
        val view = remoteViews.apply(this, remoteViewContent)
        remoteViewContent?.addView(view)
    }

    override fun onDestroy() {
        unregisterReceiver(mRemoteViewsReceiver)
        super.onDestroy()
    }
}
