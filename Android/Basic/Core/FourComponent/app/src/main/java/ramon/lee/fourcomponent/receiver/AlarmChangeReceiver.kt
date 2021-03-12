package ramon.lee.fourcomponent.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast

/**
 * 监听系统闹钟变化广播
 */
class AlarmChangeReceiver : BroadcastReceiver() {
    private val TAG = "AlarmChangeReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "AlarmChangeReceiver: $intent")
    }
}