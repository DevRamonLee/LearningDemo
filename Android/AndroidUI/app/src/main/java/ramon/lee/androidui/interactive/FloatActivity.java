package ramon.lee.androidui.interactive;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;

public class FloatActivity extends AppCompatActivity {
    private static final String TAG = "FloatActivity";
    private boolean hasBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
    }

    public void zoom(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                new GlobalDialogSingle(this, "", "当前未获取悬浮窗权限", "去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
                    }
                }).show();
            } else {
                moveTaskToBack(true);
                Intent intent = new Intent(FloatActivity.this, FloatWindowService.class);
                hasBind = bindService(intent, mVideoServiceConnection, Context.BIND_AUTO_CREATE);
            }
        }
    }

    ServiceConnection mVideoServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取服务的操作对象
            FloatWindowService.MyBinder binder = (FloatWindowService.MyBinder) service;
            binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(FloatActivity.this, FloatWindowService.class);
                            hasBind = bindService(intent, mVideoServiceConnection, Context.BIND_AUTO_CREATE);
                            moveTaskToBack(true);
                        }
                    }, 1000);

                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 重新显示了
        Log.d(TAG, "重新显示了");
        // 不显示悬浮窗
        if (hasBind) {
            unbindService(mVideoServiceConnection);
            hasBind = false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "重新显示，onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "被销毁");
    }
}