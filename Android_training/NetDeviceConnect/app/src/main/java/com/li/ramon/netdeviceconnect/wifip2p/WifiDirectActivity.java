package com.li.ramon.netdeviceconnect.wifip2p;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.li.ramon.netdeviceconnect.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WifiDirectActivity extends AppCompatActivity {

    public static final String TAG = "WifiDirecDemo";

    private List peers = new ArrayList();/*对等节点列表数据源*/
    private WiFiPeerListAdapter peerAdapter;//对等节点列表适配器

    private final IntentFilter intentFilter = new IntentFilter();
    WifiP2pManager.Channel mChannel;//通过 WifiP2pManager initialize方法获取，用于与WiFi P2P框架保持通信
    WifiP2pManager mManager;
    WiFiDirectBroadcastReceiver receiver;//wifi 直连广播接收类

    protected ListView peerList;//显示对等节点的ListView
    protected LinearLayout serverContent;// 显示Socket 服务端界面
    protected LinearLayout clientContent;//显示Socket 客户端界面
    private ImageView receivedImg;//显示服务端接收到的图片
    private TextView serverStatus;//服务端用于显示图片路径
    private Button sendImg;//发送图片按钮

    InetAddress groupOwnerAddress;//“群主”机器的地址信息，客户端需要此信息进行Socket连接

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_direct);
        initFilter();
        initViewAndOthers();
        checkPermission();
    }

    /*初始化广播Filter Action*/
    protected void initFilter() {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);//wifi p2p 是否可用
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);//peers 列表发生变化
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);//wifi p2p 连接发生变化
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);//wifi p2p 设备信息发生变化，如名字
    }

    /*初始化WifiP2pManager 对象和 Channel以及页面view*/
    protected void initViewAndOthers() {
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        /*获取一个Channel对象，用于以后和WiFi P2P框架保持通信*/
        mChannel = mManager.initialize(this, getMainLooper(), null);

        /*显示对等节点列表*/
        peerList = (ListView) findViewById(R.id.peer_list);
        peerAdapter = new WiFiPeerListAdapter(WifiDirectActivity.this, peers);
        peerList.setAdapter(peerAdapter);

        serverContent = (LinearLayout) findViewById(R.id.server_content);
        clientContent = (LinearLayout) findViewById(R.id.client_content);
        receivedImg = (ImageView) findViewById(R.id.received_img);
        serverStatus = (TextView) findViewById(R.id.server_status);
        sendImg = (Button) findViewById(R.id.sendImg);
        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*点击发送图片，由于是网络操作，需要放在其他线程中*/
                Log.i(TAG, "send image click");
                FileClientAsyncTask fileClientAsyncTask = new FileClientAsyncTask(WifiDirectActivity.this, groupOwnerAddress.getHostAddress(), 8888);
                fileClientAsyncTask.execute(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.mipmap.test));
            }
        });
    }

    /*检查权限,由于需要把图片保存在外部存储上，所以需要读写外部存储权限*/
    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(WifiDirectActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(WifiDirectActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i(TAG, "write external permission had been denied last time");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "permission granted");
                } else {
                    Log.i(TAG, "permission not granted");
                }
                return;
            }
        }
    }

    /**
     * register the BroadcastReceiver with the intent values to be matched
     */
    @Override
    public void onResume() {
        super.onResume();
        /*初始化 WifiP2p 广播接收类*/
        receiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this, peerListListener);
        registerReceiver(receiver, intentFilter);
        /*启动设备发现对等节点*/
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                /*只代表发现节点的任务执行成功，没有其他信息*/
                Log.i(TAG, "discover peers successful");
            }

            @Override
            public void onFailure(int reasonCode) {
                /*只代表发现节点的任务执行失败*/
                Log.i(TAG, "discover peers failed");
            }
        });
    }

    /*requestPeers 方法的回调接口*/
    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            /*清空旧的列表*/
            peers.clear();
            /*添加新获取的列表*/
            peers.addAll(peerList.getDeviceList());
            /*通知适配器更新*/
            peerAdapter.peers = peers;
            peerAdapter.notifyDataSetChanged();
            if (peers.size() == 0) {
                Log.d(TAG, "No devices found");
                return;
            }
        }
    };

    public void connect(int position) {
        //连接列表中点击的设备
        WifiP2pDevice device = (WifiP2pDevice) peers.get(position);
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        /*WifiP2pManager.ActionListener 仅能通知我们初始化连接状态的成功或失败*/
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "connect  successful");
            }

            @Override
            public void onFailure(int reason) {
                Log.i(TAG, "Connect failed. Retry");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        if (!(peerList.getVisibility() == View.VISIBLE)) {
            peerList.setVisibility(View.VISIBLE);
        }
        if (serverContent.getVisibility() == View.VISIBLE) {
            serverContent.setVisibility(View.GONE);
        }
        if (clientContent.getVisibility() == View.VISIBLE) {
            clientContent.setVisibility(View.GONE);
        }
        super.onBackPressed();
    }

    public void initClientinfo(InetAddress address) {
        groupOwnerAddress = address;
    }

    public void startSocketServer() {
        FileServerAsyncTask fileServerAsyncTask = new FileServerAsyncTask(WifiDirectActivity.this, serverStatus);
        fileServerAsyncTask.execute();
    }

    class FileClientAsyncTask extends AsyncTask<Uri, Void, Void> {

        private Context context;
        private String host;
        private int port;

        public FileClientAsyncTask(Context context, String host, int port) {
            this.context = context;
            this.host = host;
            this.port = port;
        }

        @Override
        protected Void doInBackground(Uri... params) {
            Log.i(TAG, "doInBackground");
            int len;
            Socket socket = new Socket();
            byte buf[] = new byte[1024];
            try {
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), 500);
                Log.i(TAG, " socket address is " + socket.getLocalAddress());

                OutputStream outputStream = socket.getOutputStream();
                ContentResolver cr = context.getContentResolver();
                InputStream inputStream = null;
                inputStream = cr.openInputStream(params[0]);
                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //catch logic
            } catch (IOException e) {
                e.printStackTrace();
                //catch logic
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            //catch logic
                        }
                    }
                }
            }
            return null;
        }
    }

    class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private TextView statusText;

        public FileServerAsyncTask(Context context, TextView statusText) {
            this.context = context;
            this.statusText = statusText;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                Socket client = serverSocket.accept();

                /**
                 * If this code is reached, a client has connected and transferred data
                 * Save the input stream from the client as a JPEG file
                 */
                final File f = new File(Environment.getExternalStorageDirectory() + "/"
                        + context.getPackageName() + "/" + System.currentTimeMillis()
                        + ".jpg");


                File dirs = new File(f.getParent());
                Log.i(TAG, "dir path is  " + dirs.getAbsolutePath());
                if (!dirs.exists())
                    dirs.mkdirs();
                f.createNewFile();
                InputStream inputstream = client.getInputStream();
                copyFile(inputstream, new FileOutputStream(f));
                serverSocket.close();
                return f.getAbsolutePath();
            } catch (IOException e) {
                return null;
            }
        }

        /*从输入流读取文件，拷贝到本地*/
        protected void copyFile(InputStream inputStream, FileOutputStream fos) {
            try {
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                //is.read(buffer)代表实际读取到的字符的个数
                while ((byteCount = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                fos.close();
                inputStream.close();
            } catch (IOException e) {
                Log.i(TAG, e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                statusText.setText("Image path is " + result);
                //显示图片
                receivedImg.setImageBitmap(BitmapFactory.decodeFile(result));
            }
        }
    }
}
