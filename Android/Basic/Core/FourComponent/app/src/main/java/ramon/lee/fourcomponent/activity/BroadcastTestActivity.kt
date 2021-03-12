package ramon.lee.fourcomponent.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivityBroadcastTestBinding

class BroadcastTestActivity : AppCompatActivity() {
    private var binding: ActivityBroadcastTestBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_broadcast_test)
        initView()
    }

    private fun initView() {
        binding?.let {
            it.btnSendBroadcast.setOnClickListener {
                Intent().also { intent ->
                    intent.action = "android.broadcast.action.ASYNC"
                    sendBroadcast(intent)
                }
            }
        }
    }
}