package top.betterramon.socketdemo.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.betterramon.socketdemo.R;
import top.betterramon.socketdemo.service.TCPServerService;

public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;
    private Button mSendButton;
    private TextView mMessageTextView;
    private EditText mMessageEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;
    private Handler mHandler;
    private final StringBuilder mMessageBuffer = new StringBuilder();

    private static class MyHandler extends Handler {
        private final WeakReference<TCPClientActivity> mActivityRef;
        public MyHandler(TCPClientActivity activity) {
            mActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TCPClientActivity activity = mActivityRef.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    activity.mMessageBuffer.append((String) msg.obj).append("\n");
                    activity.mMessageTextView.setText(activity.mMessageBuffer.toString());
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    activity.mSendButton.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        mMessageTextView = findViewById(R.id.msg_container);
        mSendButton = findViewById(R.id.send);
        mSendButton.setOnClickListener(this);
        mMessageEditText = findViewById(R.id.msg);
        mHandler = new MyHandler(this);
        Intent service = new Intent(this, TCPServerService.class);
        startService(service);      // 启动服务端的服务
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
                mMessageBuffer.append(showedMsg).append("\n");
                mMessageTextView.setText(mMessageBuffer.toString());
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
