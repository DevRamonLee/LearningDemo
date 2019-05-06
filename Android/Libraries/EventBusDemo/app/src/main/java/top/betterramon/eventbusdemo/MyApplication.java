package top.betterramon.eventbusdemo;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

/**
 * Created by Ramon on 2019/5/5.
 */
public class MyApplication extends Application {

    private AsyncExecutor asyncExecutor;
    @Override
    public void onCreate() {
        super.onCreate();
        initEventBus();
    }

    private void initEventBus() {
        EventBus eventBus = EventBus.builder()
                .logNoSubscriberMessages(false) // 配置发布的事件没有订阅者时 EventBus 保持安静
                .sendNoSubscriberEvent(false) // 配置发布的事件没有订阅者时 EventBus 保持安静
                .throwSubscriberException(true) // 配置异常处理
                .throwSubscriberException(BuildConfig.DEBUG) // 配置仅在调试模式可以抛出异常
                .addIndex(new MyEventBusIndex())
                .installDefaultEventBus();

        // 创建 AsyncExecutor 实例
        asyncExecutor = AsyncExecutor.create();
    }

    public AsyncExecutor getAsyncExecutor() {
        return asyncExecutor;
    }
}
