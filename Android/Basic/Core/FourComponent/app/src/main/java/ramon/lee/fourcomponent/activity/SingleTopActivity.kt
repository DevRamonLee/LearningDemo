package ramon.lee.fourcomponent.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivitySingleTopBinding

/**
 * Activity 启动模式，栈顶复用模式
 */
class SingleTopActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SingleTopActivity"
    }
    private var binding: ActivitySingleTopBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_top)
        Log.i(TAG, "onCreate")
        initView()
    }

    private fun initView() {
        binding?.let {
            it.tvActivityAddress.text = this.toString()
            it.btnStartSingleTop.setOnClickListener {
                val intent = Intent(this, SingleTopActivity::class.java)
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

    // 第一次启动不会调用这个，后续启动如果 Activity 在栈顶，会调用这个方法
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(TAG, "onNewIntent")
    }
}