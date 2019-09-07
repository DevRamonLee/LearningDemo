- [Messenger 源码解析](https://www.jianshu.com/p/48e212d1fde4)

# 使用 Bundle

四大组件中的三大组件（`Activity、Service、Receiver`）都是支持在 `Intent` 中传递 `Bundle` 数据的，因为 `Bundle` 实现了 `Parcelable` 接口，所以可以方便地在不同的进程间传输。

# 使用文件共享

两个进程通过读/写同一个文件来交换数据。由于 `Android` 系统基于 Linux，所以并发读/写文件可以没有限制的进行，甚至两个线程同时写一个文件也可以。除了交换一些文本信息，还可以序列化一个对象到文件系统中，然后在另一个进程恢复对象。

> `SharedPreference` 文件是个特例，由于系统对它的读/写 有一定的缓存策略，即在内存中会有一份 `SharedPreference` 文件的缓存，所以在多线程模式下，系统对它的读写是不可靠的，当面对高并发读/写访问， SharedPreference 有很大几率丢失数据，所以，不建议在进程间通信中使用 `SharedPreference`


# Messenger

`Messenger` 译为信使，通过它可以在不同进程间传递 `Message` 对象。 `Messenger` 是一种轻量级 `IPC` 方案，它的底层实现是 `AIDL`。下面是 `Messenger` 的两个构造方法，从构造方法上可以看到 `AIDL` 的痕迹。`Messenger` 封装了 `AIDL`。

```
public Messenger(Handler target) {
    mTarget = target.getIMessenger();
}
public Messenger(IBinder target) {
    mTarget = IMessenger.Stub.asInterface(target);
}
```

> `Messenger` 一次处理一个请求，因此在服务端我们不用考虑线程同步的问题，这是因为服务端不存在并发执行的情形。

## Messenger 使用步骤

#### 1.服务端进程

首先我们需要在服务端创建一个 `Service` 来处理客户端的连接请求，同时创建一个 `Handler` 并通过它来创建 `Messenger` 对象，然后在 `Service` 的 `onBind` 中返回这个 `Messenger` 对象底层的 `Binder` 即可。

#### 2.客户端进程

客户端进程中，首先要绑定服务端的 `Service`，绑定成功后用服务端返回的 `IBinder` 对象创建一个 `Messenger`，通过这个 `Messenger` 就可以向服务器发送消息了，发消息类型为 `Message` 对象。如果需要服务端能够响应客户端，就和服务端一样，我们还需要创建一个 `Handler` 并创建一个新的 `Messenger`，并把这个 `Messenger` 对象通过 `Message` 的 `replyTo` 参数传递给服务端，服务端通过 `replyto` 参数就可以回应客户端。

#### 例子

**例1：服务端无法回应客户端**

首先来看服务端代码，

1. 创建 `MessengerHandler` 用来处理客户端发送的消息，并从消息中取出客户端发来的文本信息。

2. `mMessenger` 是一个 `Messenger` 对象，把它和 `MessengerHandler` 相关联。

3. 在 `onBind` 方法中返回 `mMessenger` 底层的 `Binder` 对象。

```
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
```

> 这里 `Messenger` 的作用就是将客户端发送的消息传递给 `MessengerHandler` 处理。

接下来注册 service

```
<service
    android:name=".server.MessengerService"
    android:enabled="true"
    android:exported="true"
    android:process=":remote"/>
```

客户端实现步骤

1. 调用 `bindService` 绑定远程服务。
2. 根据服务返回的 `binder` 对象创建 `Messenger` 对象并使用此对象向服务器端发送消息。


```
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
```

> 在 Messenger 中进行数据传输必须将数据放入 Message 中，Message 中能使用的载体只有 what、arg1、arg2、Bundle 以及 replyTo。 Message 中的另一个字段 object 在同一个进程中可以用，但是在跨进程通信时，Android 2.2 之前是不支持的，2.2 以后，也仅仅是支持系统提供的实现了 Parcelable 接口的对象，我们自定义的 Parcelab 对象是无法通过 object 来传输的。但是我们可以使用 Bundle。


**例2：服务端响应客户端**

首先修改服务端，我们只需要修改 MessengerHandler，当收到客户端的消息时立马给客户端返回一条消息

```
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
```

接下来修改客户端，为了接收服务端的消息，客户端也需要准备一个接收消息的 Messenger 和 Handler

```
// 一.接收服务端返回的消息，首先我们需要创建一个 Handler 对象
private static class MessengerHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MyConstants.MSG_FROM_SERVICE:
                Log.i("client","receive msg form Service: " + msg.getData().getString("reply"));
                break;
            default:
                super.handleMessage(msg);
        }
    }
}

// 二.接收服务端返回的消息，使用 Handler 创建 Messenger 对象
private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
```

接下来是最重要的一步，当客户端发送消息的时候，需要把接收服务器回复的 Messenger 通过 Message 的 replyTo 参数传递给服务端

```
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
```

下面来看看它的工作原理图

![IPC_001](./assets/IPC_009.png)

例子源码：[MessengerDemo](./MessengerDemo)


