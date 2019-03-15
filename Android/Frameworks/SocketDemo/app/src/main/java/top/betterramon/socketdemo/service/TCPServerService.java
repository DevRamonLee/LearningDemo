package top.betterramon.socketdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by meng.li on 2019/3/12.
 */

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
