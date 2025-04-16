package ramon.lee.fourcomponent.provider

import android.database.ContentObserver
import android.os.Handler
import android.os.Message
import android.util.Log

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/17 1:31
 */
class PersonObserver(val handler: Handler) : ContentObserver(handler) {
    private val TAG = "PersonObserver"
    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.i(TAG, "data changed, try to query")
        handler.sendMessage(Message())
    }
}