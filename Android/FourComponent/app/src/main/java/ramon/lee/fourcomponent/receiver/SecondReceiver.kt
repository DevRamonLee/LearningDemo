package ramon.lee.fourcomponent.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SecondReceiver : BroadcastReceiver() {
    private val TAG = "OrderedReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "SecondReceiver: Hello receiver")
    }
}