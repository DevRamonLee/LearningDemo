package top.betterramon.eventbusdemo;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sy on 2019/5/5.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // 使用索引
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}
