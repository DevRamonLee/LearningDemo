package ramon.lee.fourcomponent.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log

/**
 * 在广播中执行耗时的异步任务
 */
class GoAsyncReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // 通过 goAsync 方法可以让广播在后台执行耗时任务，减少优先级降低被回收的概率
        val pendingResult = goAsync()
        val task = Task(pendingResult, intent)
        task.execute()
    }

    private class Task(
        private val pendingResult: PendingResult,
        private val intent: Intent
    ) : AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg params: String?): String {
            val sb = StringBuilder()
            sb.append("Action: ${intent.action}\n")
            sb.append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            return toString().also { log ->
                Log.d("GoAsyncReceiver", log)
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // 必须调用 finish() 让 BroadcastReceiver 可以被回收
            pendingResult.finish()
        }
    }
}