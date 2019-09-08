package top.betterramon.messengerdemo.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import top.betterramon.messengerdemo.R;
import top.betterramon.messengerdemo.server.MessengerService;
import top.betterramon.messengerdemo.server.MyConstants;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";

    private Messenger mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 2. 根据服务端返回的 binder 对象创建 Messenger对象并使用此对象向服务器发送消息
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","hello, this is client");
            msg.setData(data);
            // 三. 接收服务端返回的消息，最重要的一步，发送消息的时候把客户端的 Messenger 发送过去
            msg.replyTo = mGetReplyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(this, MessengerService.class);
        // 1. 绑定远程进程 MessengerService
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    // 一 .接收服务端返回的消息，首先我们需要创建一个 Handler 对象
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG,"receive msg form Service: " + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    // 二.接收服务端返回的消息，使用 Handler 创建 Messenger 对象
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
