package ramon.lee.fourcomponent.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivityLifeCycleTwoBinding

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/10 22:52
 */
class LifeCycleTwoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "LifeCycleTwoActivity"
    }
    private var binding: ActivityLifeCycleTwoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_life_cycle_two)
        Log.i(TAG, "onCreate")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}