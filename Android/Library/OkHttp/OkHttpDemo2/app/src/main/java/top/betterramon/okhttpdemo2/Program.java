package top.betterramon.okhttpdemo2;

import android.app.Application;
import android.os.Handler;

import top.betterramon.okhttpdemo2.net.HttpLoader;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class Program extends Application {
    private static Program appContext;
    public static Application application = null;
    public static Handler handler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        application = this;
        handler = new Handler();    // 默认获取所在线程的 looper
        HttpLoader.getInstace().getmOkHttpClient(); // 初始化 OkHttpClient
    }

    public static Program getAppContext() {
        return appContext;
    }
}
