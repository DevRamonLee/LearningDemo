package com.li.ramon.netdeviceconnect.wifip2p.discoverservice;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.li.ramon.netdeviceconnect.R;

import java.util.HashMap;
import java.util.Map;

/*使用 Wifi p2p 注册和发现服务*/
public class P2pDiscoverServiceActivity extends AppCompatActivity {
    private int SERVER_PORT = 9999;
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel channel;

    WifiP2pDnsSdServiceRequest serviceRequest;

    private Button registerBtn;// 注册服务
    private Button discoverBtn;//发现服务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p_discover_service);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        /*获取一个Channel对象，用于以后和WiFi P2P框架保持通信*/
        channel = mManager.initialize(this, getMainLooper(), null);

        registerBtn = (Button) findViewById(R.id.register_service);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegistration();
            }
        });
        discoverBtn = (Button) findViewById(R.id.discover_service);
        discoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discoverService();
            }
        });
    }

    /*使用 wifi p2p 注册一个服务*/
    private void startRegistration() {
        /*创建一个包含你的服务信息的 map 对象*/
        Map record = new HashMap();
        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", "Ramon" + (int) (Math.random() * 1000));
        record.put("available", "visible");

        /*服务信息：设置一个唯一的服务名称
        * 服务类型 _protocol._transportlayer _协议._传输层
        * Map 信息*/
        WifiP2pDnsSdServiceInfo serviceInfo =
                WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);

        /*添加一个本地服务，发送服务信息、网络 channel 对象
        * 添加一个回调接口用来指示服务添加成功或者失败*/
        mManager.addLocalService(channel, serviceInfo, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Command successful! Code isn't necessarily needed here,
                // Unless you want to update the UI or add logging statements.
                toast("Register successful");
            }

            @Override
            public void onFailure(int arg0) {
                // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
                toast("Register failed");
            }
        });
    }

    /*该数据结构用来存储其他设备的服务和地址信息*/
    final HashMap<String, String> buddies = new HashMap<String, String>();

    private void discoverService() {
        /* 1.创建WifiP2pManager.DnsSdTxtRecordListener 实例监听实时收到的记录（record）。
        这些记录可以是来自其他设备的广播。当收到记录时，将其中的设备地址和其他
        相关信息拷贝到当前方法之外的外部数据结构中，供之后使用*/
        WifiP2pManager.DnsSdTxtRecordListener txtListener = new WifiP2pManager.DnsSdTxtRecordListener() {
            @Override
            public void onDnsSdTxtRecordAvailable(String fullDomain, Map record, WifiP2pDevice device) {
                Log.i("meng", "onDnsSdTxtRecordAvailable");
                buddies.put(device.deviceAddress, record.get("buddyname").toString());
            }
        };

        /* 2.创建 WifiP2pManager.DnsSdServiceResponseListener 对象，用以获取服务的信息。
        这个对象将接收服务的实际描述以及连接信息。上一段代码构建了一个包含设备地址和“buddyname”键值对的 Map 对象。
        WifiP2pManager.DnsSdServiceResponseListener 对象使用这些配对信息将 DNS 记录和对应的服务信息对应起来。
        当上述两个 listener 构建完成后，调用 setDnsSdResponseListeners() 将他们加入到 WifiP2pManager。*/
        WifiP2pManager.DnsSdServiceResponseListener servListener = new WifiP2pManager.DnsSdServiceResponseListener() {
            @Override
            public void onDnsSdServiceAvailable(String instanceName, String registrationType,
                                                WifiP2pDevice resourceType) {
                Log.i("meng", "onDnsSdServiceAvailable");
                resourceType.deviceName = buddies
                        .containsKey(resourceType.deviceAddress) ? buddies
                        .get(resourceType.deviceAddress) : resourceType.deviceName;
                /*这里我们就可以用 deviceAdress 来去连接这个设备，利用 WifiDirectActivity 中的操作*/
                /*打印设备名字*/
                toast(resourceType.deviceName);
            }
        };
        /*注册上面两个回调*/
        mManager.setDnsSdResponseListeners(channel, servListener, txtListener);

        /* 3. 创建服务请求, Listener 用来指示请求成功或失败*/
        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        mManager.addServiceRequest(channel,
                serviceRequest,
                new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        // Success!
                        toast("Service request successfully");
                    }

                    @Override
                    public void onFailure(int code) {
                        // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
                        toast("Service request failed");
                    }
                });
        /* 4. 调用服务发现*/
        mManager.discoverServices(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Success!
                toast("Discover service successfully");
            }

            @Override
            public void onFailure(int code) {
               /* P2P_UNSUPPORTED 当前的设备不支持 Wi-Fi P2P
                  BUSY 系统忙，无法处理当前请求
                  ERROR 内部错误导致操作失败*/
                if (code == WifiP2pManager.P2P_UNSUPPORTED) {
                    toast("P2P_UNSUPPORTED");
                } else if (code == WifiP2pManager.BUSY) {
                    toast("BUSY");
                } else if (code == WifiP2pManager.ERROR) {
                    toast("ERROR");
                }
            }
        });
    }

    public void toast(String text) {
        Toast.makeText(P2pDiscoverServiceActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
