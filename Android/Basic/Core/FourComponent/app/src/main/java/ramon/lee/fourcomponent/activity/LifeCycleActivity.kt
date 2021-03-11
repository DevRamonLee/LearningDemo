package ramon.lee.fourcomponent.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivityLifeCycleBinding

/**
 * @Desc : Activity 生命周期
 * @Author : Ramon
 * @create 2021/3/10 22:52
 */
class LifeCycleActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ActivityLifeCycle"
        private const val KEY_PARAM = "param"
        private const val EXTRA_STRING = "extra_string"
    }
    private var param = 1
    private var binding: ActivityLifeCycleBinding? = null

    // Activity 创建时被调用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_life_cycle)
        initView()
        savedInstanceState?.let {
            Log.i(TAG, "onCreate: savedInstanceState extra =  ${it.getString(EXTRA_STRING)}")
        }
    }

    private fun initView() {
        binding?.btnGoActivityTwo?.setOnClickListener {
            val intent = Intent(this, LifeCycleTwoActivity::class.java)
            startActivity(intent)
        }
    }

    // Activity 从后台重新回到前台时被调用
    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    // Activity 创建或者从后台重新回到前台时被调用
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    // Activity 创建或者从被覆盖、后台重新回到前台时被调用
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    // Activity 被覆盖到下面或者锁屏时被调用
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        // 有可能在执行完 onPause 或 onStop 后,系统资源紧张将 Activity 杀死,所以有必要在此保存持久数据
    }

    // 退出当前 Activity 或者跳转到新 Activity 时被调用
    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    // 退出当前 Activity 时被调用,调用之后 Activity 就结束了
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    // Activity 窗口获得或失去焦点时被调用,在 onResume 之后或 onPause 之后
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.i(TAG, "onWindowFocusChanged")
    }

    /**
     * Activity被系统杀死时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死.
     * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态.
     * 在onPause之前被调用.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_PARAM, param)
        Log.i(TAG, "onSaveInstanceState: put param = $param")
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_STRING, "test")
    }

    /**
     * Activity被系统杀死后再重建时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity.
     * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        param = savedInstanceState.getInt(KEY_PARAM)
        Log.i(TAG, "onRestoreInstanceState: get param = $param")
        super.onRestoreInstanceState(savedInstanceState)
        val extraString = savedInstanceState.getString(EXTRA_STRING)
        Log.i(TAG, "onRestoreInstanceState: extraString = $extraString")
    }
}