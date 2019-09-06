package top.betterramon.activitydemo;

import android.app.Application;
import android.content.Context;

public class Program extends Application {
    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }
}
