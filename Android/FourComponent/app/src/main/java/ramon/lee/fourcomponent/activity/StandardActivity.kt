package ramon.lee.fourcomponent.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivityStandardBinding

/**
 * Activity 启动模式，标准模式
 */
class StandardActivity : AppCompatActivity() {
    private var binding: ActivityStandardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_standard)
        initView()
    }

    private fun initView() {
        binding?.let {
            // 展示当前 Activity 地址
            it.tvActivityAddress.text = this.toString()

            // 使用 standard 模式启动 activity
            it.btnStartStandard.setOnClickListener {
                val intent = Intent(this, StandardActivity::class.java)
                startActivity(intent)
            }

            // 使用 SingleTop 启动 activity
            it.btnStartSingleTop.setOnClickListener {
                val intent = Intent(this, SingleTopActivity::class.java)
                startActivity(intent)
            }

            // 使用 SingleTask 启动 activity
            it.btnStartSingleTask.setOnClickListener {
                val intent = Intent(this, SingleTaskActivity::class.java)
                startActivity(intent)
            }

            // 使用 SingleInstance 启动 activity
            it.btnStartSingleInstance.setOnClickListener {
                val intent = Intent(this, SingleInstanceActivity::class.java)
                startActivity(intent)
            }
        }
    }
}