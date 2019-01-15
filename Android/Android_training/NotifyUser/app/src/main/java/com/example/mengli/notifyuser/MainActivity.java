package com.example.mengli.notifyuser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button createCommonNotity;//创建一个普通的Notification
    private Button createBackStackNotify;//创建一个带有导航栈的Notification
    private Button createStartActivityNotify;//启动Activity，这个Activity只能从通知启动
    private Button updateNotify;//更新一个Notification
    private Button bigViewNotify;//创建一个bigView风格的 Notification
    private Button determinateNotify;//创建一个固定长度进度条的Notification
    private Button indeterminateNotify;//创建一个不确定进度的进度条Notification
    private Button cancelAllNotifies;//取消所有的通知

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private  void initView(){
        createCommonNotity = findViewById(R.id.create_common_notify);
        createBackStackNotify = findViewById(R.id.create_task_stack_notify);
        createStartActivityNotify = findViewById(R.id.create_start_activity_notify);
        updateNotify = findViewById(R.id.update_notify);
        bigViewNotify = findViewById(R.id.big_view_notify);
        determinateNotify = findViewById(R.id.determinate_notify);
        indeterminateNotify = findViewById(R.id.indeterminate_notify);
        cancelAllNotifies = findViewById(R.id.cancel_all_notifies);
        createCommonNotity.setOnClickListener(this);
        updateNotify.setOnClickListener(this);
        bigViewNotify.setOnClickListener(this);
        determinateNotify.setOnClickListener(this);
        indeterminateNotify.setOnClickListener(this);
        createBackStackNotify.setOnClickListener(this);
        createStartActivityNotify.setOnClickListener(this);
        cancelAllNotifies.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_common_notify:
                createCommonNotify();
                break;
            case R.id.create_task_stack_notify:
                createTaskStackNotify();
                break;
            case R.id.create_start_activity_notify:
                createStartActivityNotify();
                break;
            case R.id.update_notify:
                updateNotification();
                break;
            case R.id.big_view_notify:
                createBitViewNotify();
                break;
            case R.id.determinate_notify:
                createDeterminateNotify();
                break;
            case R.id.indeterminate_notify:
                createIndeterminateNotify();
                break;
            case R.id.cancel_all_notifies:
                cleanAllNotifies();
                break;
        }
    }
    /* 普通方式启动一个Notification */
    private void createCommonNotify(){
        int notifyId = 1;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        Intent resultIntent = new Intent(this, ResultActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        //设置自动取消
        mBuilder.setAutoCancel(true);
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notifyId, mBuilder.build());
    }

    /* 创建一个包含返回栈的 Notification */
    private void createTaskStackNotify(){
        int notifyId = 2;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        Intent resultIntent = new Intent(this, ResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // 给这个 Activity 添加他的父链，在manifest 中指定的父activity,构建一个返回栈
        stackBuilder.addParentStack(ResultActivity.class);
        // 添加这个 intent 到栈顶
        stackBuilder.addNextIntent(resultIntent);
        // 获取一个包含回退栈的 pendingIntent
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notifyId, mBuilder.build());
    }

    /* 创建一个只能从 Notification 启动的 Activity */
    private void createStartActivityNotify(){
        int notifyId = 3;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Start an activity")
                        .setContentText("Start a special activity which only can be started from this notification ");
        Intent notifyIntent =
                new Intent(this,ResultActivity2.class);
        // 设置 Activity 在一个空的，新的栈中启动
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        /* 给 builder 对象设置 pendingIntent */
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    int numMessages = 0;
    /* 更新一个 Notification */
    protected void updateNotification(){
        int notifyId = 4;
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("New Message")
                .setSmallIcon(R.mipmap.ic_launcher);

        mNotifyBuilder.setContentText("你有 "+(++numMessages)+" 个新消息！");
        mNotificationManager.notify(
                notifyId,
                mNotifyBuilder.build());
    }

    /* 创建 bigView 样式 Notification */
    protected void createBitViewNotify(){
        int notifyId = 5;
        /* 在big view 中设置Button */
        Intent dismissIntent = new Intent(this, ResultActivity.class);
        dismissIntent.setAction("dismiss");
        PendingIntent piDismiss = PendingIntent.getActivity(this, 0, dismissIntent, 0);

        Intent snoozeIntent = new Intent(this, ResultActivity.class);
        snoozeIntent.setAction("snooze");
        PendingIntent piSnooze = PendingIntent.getActivity(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("alert")
                        .setContentText("dismiss or snooze")
                        .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                        .setStyle(new NotificationCompat.BigTextStyle()//设置bigview风格
                                .bigText("Big Text!!!!!"))
                        .addAction (R.mipmap.ic_launcher,
                                "dismiss", piDismiss)
                        .addAction (R.mipmap.ic_launcher,
                                "snooze", piSnooze);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, builder.build());
    }

    /* 创建一个固定长度进度条 Notification*/
    protected  void createDeterminateNotify(){
        final int id = 6;
        final NotificationManager mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
        // 启动一个线程来更新进度
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr+=5) {
                            mBuilder.setProgress(100, incr, false);
                            mNotifyManager.notify(id, mBuilder.build());
                            try {
                                Thread.sleep(5*1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        //下载完成时更新文字并取消进度条
                        mBuilder.setContentText("Download complete")
                                .setProgress(0,0,false);
                        mNotifyManager.notify(id, mBuilder.build());
                    }
                }
        ).start();
    }

    protected  void createIndeterminateNotify(){
        final int id = 7;
        final NotificationManager mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
        // 启动一个线程来更新进度
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr+=5) {
                            //和上面的不同就只有替换这一行
                            mBuilder.setProgress(100, incr, true);
                            mNotifyManager.notify(id, mBuilder.build());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        //下载完成时更新文字并取消进度条
                        mBuilder.setContentText("Download complete")
                                .setProgress(0,0,true);
                        mNotifyManager.notify(id, mBuilder.build());
                    }
                }
        ).start();
    }

    protected void  cleanAllNotifies(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
