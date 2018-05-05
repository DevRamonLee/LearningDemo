package com.li.ramon.netdeviceconnect.wifip2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.View;

import java.net.InetAddress;
import java.net.UnknownHostException;

/* WiFi 直连广播接收类 , 监听 wifi p2p 状态变化 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = WifiDirectActivity.TAG;

    private WifiDirectActivity activity;
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager mManager;
    WifiP2pManager.PeerListListener peerListListener;

    public WiFiDirectBroadcastReceiver() {
    }

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       WifiDirectActivity activity, WifiP2pManager.PeerListListener peerListListener) {
        this.mManager = manager;
        this.mChannel = channel;
        this.activity = activity;
        this.peerListListener = peerListListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //wifi 不可用，请打开wifi
                Log.i(TAG, "wifi is closed , please open first");
            } else {
                //wifi 可用
                Log.i(TAG, "wifi is opened");
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            /*wifi p2p 框架发现的对等节点列表发生变化,更新列表*/
            Log.i(TAG, "WIFI_P2P_PEERS_CHANGED_ACTION");
            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            /*wifi p2p 连接发生改变*/
            if (mManager == null) {
                return;
            }
            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
                // WifiP2pManager.ConnectionInfoListener 监听连接状态的变化
                mManager.requestConnectionInfo(mChannel, new WifiP2pManager.ConnectionInfoListener() {

                    @Override
                    public void onConnectionInfoAvailable(WifiP2pInfo info) {
                        InetAddress groupOwnerAddress = null;
                        try {
                            // InetAddress from WifiP2pInfo struct.
                            groupOwnerAddress = InetAddress.getByName(info.groupOwnerAddress.getHostAddress());
                        } catch (UnknownHostException e) {

                        }
                        /*如果有多台设备同时连接到一台设备，则这台设备被指定为“群主”*/
                        if (info.groupFormed && info.isGroupOwner) {
                            /*作为服务设备*/
                            Log.i(TAG, "It's group owner");
                            /*更新界面显示*/
                            activity.peerList.setVisibility(View.GONE);
                            activity.serverContent.setVisibility(View.VISIBLE);
                            //启动服务端Socket服务
                            activity.startSocketServer();
                        } else if (info.groupFormed) {
                           /*其他设备为客户端设备*/
                            Log.i(TAG, "It's groupFormed");
                            activity.peerList.setVisibility(View.GONE);
                            activity.clientContent.setVisibility(View.VISIBLE);
                            /*把服务端地址传递给Client使用*/
                            activity.initClientinfo(groupOwnerAddress);
                        }
                    }
                });
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            /*设备信息发生变化*/
            Log.i(TAG, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
        }
    }
}
