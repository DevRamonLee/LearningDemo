package top.betterramon.remoteviewdemo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

class MyAppWidgetProvider: AppWidgetProvider() {
    companion object {
        const val TAG : String = "MyAppWidgetProvider"
        const val CLICK_ACTION = "top.betterramon.remoteviewdemo.action.CLICK"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.i(TAG, "onReceive: action = ${intent?.action}")
        // 判断如果是自己的 action 则做自己的事情，如小部件单击，这里添加一个动画
        if (intent?.action.equals(CLICK_ACTION)) {
            Toast.makeText(context, " clicked it", Toast.LENGTH_LONG).show()
            Thread {
                val srcBitmap =
                    BitmapFactory.decodeResource(context?.resources, R.drawable.baby) as Bitmap
                val appWidgetManager = AppWidgetManager.getInstance(context)
                for (i in 0 until 37) {
                    val degree = ((i * 10) % 360).toFloat()
                    val remoteViews = RemoteViews(context?.packageName, R.layout.widget)
                    remoteViews.setImageViewBitmap(R.id.imageView1, rotateBitmap(srcBitmap, degree))
                    val intentClick = Intent().apply {
                        action = CLICK_ACTION
                        `package` = context?.packageName
                    }
                    val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
                        context, 0, intentClick,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent)
                    appWidgetManager.updateAppWidget(
                        ComponentName(
                            context!!,
                            MyAppWidgetProvider::class.java
                        ), remoteViews
                    )
                    SystemClock.sleep(30)
                }
            }.start()
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(TAG, "onUpdate")
        val counter = appWidgetIds?.size
        Log.i(TAG, "counter = $counter")
        for (i in 0 until counter!!) {
            val appWidgetId = appWidgetIds[i]
            onWidgetUpdate(context!!, appWidgetManager!!, appWidgetId)
        }
    }

    /**
     * 桌面小部件更新
     */
    private fun onWidgetUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        Log.i(TAG, "appWidgetId = $appWidgetId")
        val remoteViews = RemoteViews(context.packageName, R.layout.widget)

        // 桌面 widget 单击事件发送的 Intent 广播
        val intentClick = Intent().apply {
            action = CLICK_ACTION
            `package` = context.packageName
        }
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intentClick,
            PendingIntent.FLAG_IMMUTABLE)
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    private fun rotateBitmap(srcBitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.reset()
        matrix.setRotate(degree)
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.width, srcBitmap.height, matrix, true)
    }
}