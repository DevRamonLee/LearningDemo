package ramon.better.com.components.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 *  IntentService 的使用
 */
public class RSSPullService extends IntentService{


    public RSSPullService(String name) {
        super(name);
    }
    //这里需要一个默认的构造方法,否则会提示：has no default constructor
    public RSSPullService(){
        super("RSSPullService");//关键在这句
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String dataString = intent.getDataString();
        // 发送任务的状态
        Intent localIntent =
                new Intent(Constants.BROADCAST_ACTION)
                        .putExtra(Constants.EXTENDED_DATA_STATUS, "send from intentservice");
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        Log.i("meng", "The thread of onHandleIntent " + Thread.currentThread().toString());
    }
    public static class  Constants {
        public static final String BROADCAST_ACTION = "intent.service.broadcast.action";
        public static final String EXTENDED_DATA_STATUS = "extended_data_status";
    }
}


