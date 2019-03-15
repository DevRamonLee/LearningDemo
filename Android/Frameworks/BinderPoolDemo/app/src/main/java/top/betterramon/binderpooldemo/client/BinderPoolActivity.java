package top.betterramon.binderpooldemo.client;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import top.betterramon.binderpooldemo.BinderPool;
import top.betterramon.binderpooldemo.ComputeImpl;
import top.betterramon.binderpooldemo.ICompute;
import top.betterramon.binderpooldemo.ISecurityCenter;
import top.betterramon.binderpooldemo.R;
import top.betterramon.binderpooldemo.SecurityCenterImpl;

public class BinderPoolActivity extends AppCompatActivity {
    private static final String TAG = "BinderPoolActivity";
    ISecurityCenter mSecurityCenter;
    ICompute mCompute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(){
            @Override
            public void run() {
                doWork();
            }
        }.start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getsInsance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, " visit ISecurityCenter");
        String msg = "helloworld - 安卓";
        Log.d(TAG,"content : " + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.d(TAG,"encrypt: " + password);
            Log.d(TAG,"decrypt: " + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
             Log.d(TAG,"3 + 5 = " + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
