package ramon.lee.fourcomponent.activity

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import ramon.lee.fourcomponent.R
import ramon.lee.fourcomponent.databinding.ActivityServiceTestBinding
import ramon.lee.fourcomponent.service.BindService
import ramon.lee.fourcomponent.service.BindService.MyBinder
import ramon.lee.fourcomponent.service.MyIntentService
import ramon.lee.fourcomponent.service.StartService


class ServiceTestActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ServiceTestActivity"
    }
    private var binding: ActivityServiceTestBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_test)
        initView()
    }

    //保持所启动Service的IBinder对象
    var binder: MyBinder? = null
    //定义一个ServiceConnection对象
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.i(TAG, "service connected")
            //获取Service的onBind方法所返回的MyBinder对象
            binder = iBinder as MyBinder
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.i(TAG, "Service disconnected")
        }
    }


    private fun initView() {
        binding?.let {
            it.btnStartService.setOnClickListener {
                val intent = Intent(this, StartService::class.java)
                startService(intent)

                // 为了触发 onRebind ，startService 启动 BindService
                val bindIntent = Intent(this, BindService::class.java)
                startService(bindIntent)
            }

            it.btnStopService.setOnClickListener {
                val intent = Intent(this, StartService::class.java)
                stopService(intent)

                val bindIntent = Intent(this, BindService::class.java)
                stopService(bindIntent)
            }

            it.btnBindService.setOnClickListener {
                // 绑定服务
                val bindIntent = Intent(this, BindService::class.java)
                bindService(bindIntent, connection, Service.BIND_AUTO_CREATE)
            }

            it.btnUnbindService.setOnClickListener {
                unbindService(connection)
            }

            it.btnGetCount.setOnClickListener {
                Toast.makeText(this, "${binder?.getCount()}", Toast.LENGTH_SHORT).show()
            }

            it.btnIntentService.setOnClickListener {
                MyIntentService.handleActionOne(this, "hello ", " IntentService")
            }
        }
    }
}