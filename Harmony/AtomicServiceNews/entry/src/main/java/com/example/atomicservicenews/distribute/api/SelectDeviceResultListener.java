package com.example.atomicservicenews.distribute.api;

import ohos.distributedschedule.interwork.DeviceInfo;

public interface SelectDeviceResultListener {
    void onSuccess(DeviceInfo info);
    void onFail(DeviceInfo info);
}
