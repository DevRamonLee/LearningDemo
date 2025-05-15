package top.betterramon.remoteviewdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val CHANNEL_ID = "sample_channel"
    private val NOTIFICATION_ID = 1
    private val REQUEST_NOTIFICATION_PERMISSION = 1001
    private val POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS"
    private var  button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        createNotificationChannel()
        if (!hasNotificationPermission()) {
            requestNotificationPermission()
        }
    }

    private fun initView() {
        button = findViewById(R.id.showDefaultNotify)!!
        button?.setOnClickListener {
            if (hasNotificationPermission()) {
                showCustomNotification()
            } else {
                requestNotificationPermission()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Sample Notification Channel"
            val descriptionText = "Channel for sample notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showCustomNotification() {
        val remoteViews = RemoteViews(packageName, R.layout.layout_notification)
        remoteViews.setTextViewText(R.id.msg, "Hello from RemoteViews!")
        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_collected)
        val intent = Intent(this, DemoActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        remoteViews.setOnClickPendingIntent(R.id.msg, pendingIntent)

        try {
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title")
                .setContentText("This is a fallback text.")
                .setCustomContentView(remoteViews) // 设置 RemoteViews
                .setAutoCancel(true)
                .build()

            val notifyManager = NotificationManagerCompat.from(this)
            notifyManager.notify(NOTIFICATION_ID, notification)
        } catch (e: SecurityException) {
            Log.e(TAG, "尝试发送通知时发生异常：${e.message}")
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this, POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                showCustomNotification()
            } else {
                Toast.makeText(this, "未授予通知权限", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
