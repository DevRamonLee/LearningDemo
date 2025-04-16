package ramon.lee.fourcomponent.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivitySingleTaskBinding
import ramon.lee.fourcomponent.databinding.ActivitySingleTopBinding

/**
 * Activity 栈内复用模式
 */
class SingleTaskActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SingleTaskActivity"
    }
    private var binding: ActivitySingleTaskBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_task)
        Log.i(TAG, "onCreate")
        initView()
    }

    private fun initView() {
        binding?.let {
            it.tvActivityAddress.text = this.toString()
            it.btnStartSingleTask.setOnClickListener {
                val intent = Intent(this, SingleTaskActivity::class.java)
                startActivity(intent)
            }
            it.btnStartStandard.setOnClickListener {
                val intent = Intent(this, StandardActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    // 第一次启动不会调用，后续启动如果在栈内会启动
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(TAG, "onNewIntent")
    }
}