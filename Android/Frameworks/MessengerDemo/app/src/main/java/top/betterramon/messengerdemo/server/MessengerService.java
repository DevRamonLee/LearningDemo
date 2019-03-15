package top.betterramon.messengerdemo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";

    //  1.首先创建一个 Handler
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.i(TAG, "receive msg from client: " + msg.getData().getString("msg"));
                    // 服务端响应客户端
                    Messenger client = msg.replyTo; // 获取客户端的 Messenger 对象
                    Message replyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","已收到你的消息，稍后会回复你！");
                    replyMessage.setData(bundle);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    // 2.通过 Handler 来创建 Messenger 对象
    private final Messenger mMessenger = new Messenger(new MessengerHandler());
    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 3. 返回 Messenger 对象底层的 Binder
       return mMessenger.getBinder();
    }
}
