## 使用 Socket

Socket 也被称为“套接字”，它分为流式套接字和用户数据报套接字两种，分别对应网络的传输控制层的 TCP 和 UDP 协议。

- TCP 是面向连接的协议，可以稳定双向通信，TCP 的建立需要“三次握手”才能完成，为了提供稳定的数据传输功能，其本身有超时重传机制，因此具有很高的稳定性。
- UDP 是无连接的，提供不稳定的单向通信功能（UDP 也可以实现双向通信），UDP 在性能上有更好的效率，缺点是不能保证数据一定能够正确传输，尤其是在网络拥塞的情况下。

> [Android：这是一份很详细的Socket使用攻略](https://blog.csdn.net/carson_ho/article/details/53366856)

#### 例子：一个跨进程的聊天程序，两个进程通过 Socket 通信

整体流程是：首先在远程 Service 建立一个 TCP 服务，然后在主界面中连接 TCP 服务，连接上了以后，给服务器发消息，服务器会随机回应我们一句话。

- 1.声明网络权限

```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

- 2.服务器端，当 Service 启动，在线程中建立 TCP 服务，监听 8086 端口，然后就可以等待客户端的连接请求，当有客户端连接时，就会生成一个新的 Socket，通过每次新创建的 Socket 就可以和不同的客户端通信。服务端代码

```
public class TCPServerService extends Service {
    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[]{
            "你知道吗，我可以和多个人聊天",
            "你好啊，又是美好的一天",
            "请问你叫什么名字",
            "请先自我介绍一下吧"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                System.err.println("establish tcp server failed, port: 8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestoryed) {
                try {
                    final Socket client = serverSocket.accept();
                    System.out.println("accept");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        // 用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(
                client.getInputStream()));
        // 用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                client.getOutputStream())), true);
        out.println("欢迎来到聊天室");
        while (!mIsServiceDestoryed) {
            String str = in.readLine();
            System.out.println("msg from client:" + str);
            /*if (str == null) {
                // 客户端断开连接
                break;
            }*/
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            System.out.println("send: " + msg);
        }
        System.out.println("client quit.");
        // 关闭流
        out.close();
        in.close();
        client.close();
    }
}
```

- 3.客户端 Activity 在 onCreate 中开启一个线程去连接服务端 Socket，为了能够连接成功，这里采用超时重连机制，每次连接失败都会重新建立尝试建立连接。

```
Socket socket = null;
while (socket == null) {
    try {
        socket = new Socket("localhost", 8688);
        mClientSocket = socket;
        mPrintWriter = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())), true);
        mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
        System.out.println("connect server success");
    }catch (IOException e) {
        // 如果没有连接成功，则隔一秒重连
        SystemClock.sleep(1000);
        e.printStackTrace();
    }
}
```

- 4.服务端连接成功后就可以和服务端进行通信，在线程中通过 while 循环不断去读取服务端发送过来的消息，当 Activity 退出时，退出循环并终止线程。

```
// 接收服务端的消息
BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
while (!TCPClientActivity.this.isFinishing()) {
    String msg = br.readLine();
    System.out.println("receive: " + msg);
    if (msg != null) {
        String time = formatDateTime(System.currentTimeMillis());
        final String showMsg = "server " + time + " : " + msg + " \n";
        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showMsg).sendToTarget();
    }
}
```

当 Activity 退出时，需要关闭当前的 Socket
```
@Override
protected void onDestroy() {
    if(mClientSocket != null) {
        try {
            mClientSocket.shutdownInput();
            mClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    super.onDestroy();
}
```

**客户端完整代码**
```
public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Button mSendButton;
    private TextView mMessageTextView;
    private EditText mMessageEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG: {
                    mMessageTextView.setText(mMessageTextView.getText()
                            + (String) msg.obj);
                    break;
                }
                case MESSAGE_SOCKET_CONNECTED: {
                    mSendButton.setEnabled(true);
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        mMessageTextView = findViewById(R.id.msg_container);
        mSendButton = findViewById(R.id.send);
        mSendButton.setOnClickListener(this);
        mMessageEditText = findViewById(R.id.msg);
        Intent service = new Intent(this, TCPServerService.class);
        startService(service);
        new Thread() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if(mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view == mSendButton) {
            final String msg = mMessageEditText.getText().toString();
            if(!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                new Thread(){
                    @Override
                    public void run() {
                        mPrintWriter.println(msg);
                    }
                }.start();
                mMessageEditText.setText("");
                String time = formatDateTime(System.currentTimeMillis());
                final String showedMsg = "self " + time + " : " + msg + "\n";
                mMessageTextView.setText(mMessageTextView.getText() + showedMsg);
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success");
            }catch (IOException e) {
                // 如果没有连接成功，则隔一秒重连
                SystemClock.sleep(1000);
                e.printStackTrace();
            }
        }
        try {
            // 接收服务端的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TCPClientActivity.this.isFinishing()) {
                String msg = br.readLine();
                System.out.println("receive: " + msg);
                if (msg != null) {
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showMsg = "server " + time + " : " + msg + " \n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showMsg).sendToTarget();
                }
            }
            System.out.println("quit...");
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

例子源码：[SocketDemo](./SocketDemo)
