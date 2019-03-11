package top.betterramon.messengerdemo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
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
