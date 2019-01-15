package com.li.ramon.netdeviceconnect.nsd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by meng.li on 2018/4/25.
 * NSD (Network Service Discovery) 服务帮助类
 */

public class NsdHelper {
    private static final String TAG = "NsdHelper";
    private NsdManager mNsdManager;
    private NsdManager.RegistrationListener mRegistrationListener;
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private NsdManager.ResolveListener mResolveListener;
    private String mServiceName;
    private ServerSocket mServerSocket;
    private int mLocalPort = 0;
    private static final String SERVICE_TYPE = "_http._tcp.";
    private NsdServiceInfo mService;
    private static volatile NsdHelper nsdHelper = null;

    private NsdHelper(Context context){
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    /*单例模式*/
    public static NsdHelper getInstance(Context context){
        if(nsdHelper == null){
            synchronized (NsdHelper.class) {
                if(nsdHelper == null)
                nsdHelper = new NsdHelper(context);
            }
        }
        return  nsdHelper;
    }
    /*注册一个 NSD 服务*/
    public  void registerService() {
        initializeRegistrationListener();
        try {
            mServerSocket = new ServerSocket(mLocalPort);
            mLocalPort = mServerSocket.getLocalPort();
        }catch (IOException e){
            e.printStackTrace();
        }
        // 创建一个 NsdServiceInfo 对象并且初始化它
        NsdServiceInfo serviceInfo  = new NsdServiceInfo();
        serviceInfo.setServiceName("NsdChat");
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(mLocalPort);
        if(mNsdManager != null) {
            mNsdManager.registerService(
                    serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
        }
    }

    public void discoverService(){
        initializeDiscoveryListener();//初始化服务发现回调
        initializeResolveListener();//初始化服务连接回调
        if(mNsdManager != null) {
            mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
        }
    }

    /*实现 RegistrationListener 接口通知应用程序服务注册和注销的成功或者失败。*/
    public void initializeRegistrationListener() {
        mRegistrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                // Save the service name.  Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                mServiceName = NsdServiceInfo.getServiceName();
                Log.i(TAG,"mServiceName is " + mServiceName);
            }
            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed!  Put debugging code here to determine why.
            }
            @Override
            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                // Service has been unregistered.  This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
                Log.i(TAG,"onServiceUnregistered");
            }
            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed.  Put debugging code here to determine why.
                Log.i(TAG,"onUnregistrationFailed");
            }
        };
    }
    /*初始化发现服务回调*/
    public void initializeDiscoveryListener() {
        mDiscoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found!  Do something with it.
                Log.d(TAG, "Service discovery success" + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(mServiceName)) {
                    Log.d(TAG, "Same machine: " + mServiceName);
                } else if (service.getServiceName().contains("NsdChat")){
                    /*获得该服务的连接信息*/
                    mNsdManager.resolveService(service, mResolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "service lost" + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    /*连接到网络上的服务,获取服务信息，判断是否连接*/
    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(mServiceName)) {
                    Log.d(TAG, "Same IP.");
                    return;
                }
                mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
            }
        };
    }

    /*注销服务*/
    public void tearDown() {
        mNsdManager.unregisterService(mRegistrationListener);
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }
}
