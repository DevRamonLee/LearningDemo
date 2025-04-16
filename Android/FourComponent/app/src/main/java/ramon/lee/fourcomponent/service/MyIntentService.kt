package ramon.lee.fourcomponent.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log


private const val ACTION_ONE = "ramon.lee.fourcomponent.service.action.ONE"

private const val EXTRA_PARAM1 = "PARAM1"
private const val EXTRA_PARAM2 = "PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_ONE -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionOne(param1, param2)
            }
        }
    }

    private fun handleActionOne(param1: String, param2: String) {
        Log.i(TAG, "Current thread: ${Thread.currentThread().name} param1 = $param1 param2 = $param2")
    }

    companion object {
        private const val TAG = "MyIntentService"
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        @JvmStatic
        fun handleActionOne(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_ONE
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
    }
}