package ramon.lee.fourcomponent.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.onClick = OnClick()
    }

    inner class OnClick {
        fun onClick(v: View) {
            when(v.id) {
                R.id.btn_life_cycle -> {
                    startActivity(Intent(this@MainActivity, LifeCycleActivity::class.java))
                }
                R.id.btn_launch_mode -> {
                    startActivity(Intent(this@MainActivity, StandardActivity::class.java))
                }
                R.id.btn_service_test -> {
                    startActivity(Intent(this@MainActivity, ServiceTestActivity::class.java))
                }
                R.id.btn_receiver_test -> {
                    startActivity(Intent(this@MainActivity, BroadcastTestActivity::class.java))
                }
                R.id.btn_provider_test -> {
                    startActivity(Intent(this@MainActivity, ProviderTestActivity::class.java))
                }
                R.id.btn_start_provider_activity -> {
                    val intent = Intent("ramon.lee.fourcomponent.activity.ProviderTestActivity")
                    val uri = Uri.parse("content://ramon.lee.PersonProvider/persons/1")
                    intent.data = uri
                    startActivity(intent)
                }
            }
        }
    }
}