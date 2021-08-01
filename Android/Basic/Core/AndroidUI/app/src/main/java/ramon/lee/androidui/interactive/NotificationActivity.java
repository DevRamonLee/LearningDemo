package ramon.lee.androidui.interactive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ramon.lee.androidui.Constant;
import ramon.lee.androidui.R;
import ramon.lee.androidui.interactive.receiver.MyReceiver;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class NotificationActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "1";
    private static final int BASIC_NOTIFICATION_ID = 1;
    private static final int BASIC_NOTIFICATION_FULLSCREEN_ID = 2;
    private static final int BIG_PICTURE_NOTIFICATION_ID = 3;
    private static final int BIG_PICTURE_NOTIFICATION_WITH_ICON_ID = 4;
    private static final int LARGE_TEXT_NOTIFICATION_ID = 5;
    private static final int IN_BOX_NOTIFICATION_ID = 6;
    private static final int CONVERSATION_NOTIFICATION_ID = 7;
    private static final int MEDIA_CONTROL_NOTIFICATION_ID = 8;
    private static final int CUSTOM_CONTENT_NOTIFICATION_ID = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        createNotificationChannel();
        findViewById(R.id.btn_basic_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(BASIC_NOTIFICATION_ID, buildBasicNotification());
            }
        });
        findViewById(R.id.btn_basic_notification_fullscreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyMsg(BASIC_NOTIFICATION_FULLSCREEN_ID, buildFullScreenNotification());
                    }
                }, 3000);
            }
        });
        findViewById(R.id.btn_big_picture_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(BIG_PICTURE_NOTIFICATION_ID, buildBigPicNotification());
            }
        });
        findViewById(R.id.btn_big_picture_notification2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(BIG_PICTURE_NOTIFICATION_WITH_ICON_ID, buildBigPicNotificationWithBigIcon());
            }
        });
        findViewById(R.id.btn_large_text_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(LARGE_TEXT_NOTIFICATION_ID, buildLargeTextNotification());
            }
        });
        findViewById(R.id.btn_in_box_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(IN_BOX_NOTIFICATION_ID, buildInBoxNotification());
            }
        });
        findViewById(R.id.btn_conversation_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(CONVERSATION_NOTIFICATION_ID, buildConversationNotification());
            }
        });
        findViewById(R.id.btn_media_control_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(MEDIA_CONTROL_NOTIFICATION_ID, buildMediaControlNotification());
            }
        });
        findViewById(R.id.btn_custom_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyMsg(CUSTOM_CONTENT_NOTIFICATION_ID, buildCustomContentAreaNotification());
            }
        });
    }

    /**
     * 创建 Notification channel
     */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification test";
            String description = "This is notification test channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;    // 设置重要程度
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 创建基本通知
     */
    private NotificationCompat.Builder buildBasicNotification() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent snoozeIntent = new Intent(this, MyReceiver.class);
        snoozeIntent.setAction(Constant.ACTION_SNOOZE);
        snoozeIntent.putExtra(Constant.EXTRA_NOTIFICATION_ID, BASIC_NOTIFICATION_ID);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.back_ic)   // 设置小图标
                .setContentTitle("基本通知")        // 设置标题
                .setContentText("我展示一行内容...........................")       // 正文文本
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 通知优先级
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)   // 点击后自动消除
                .addAction(R.drawable.img001, "Snooze",snoozePendingIntent);
        return builder;
    }

    /**
     * 创建全屏通知
     * @return
     */
    private NotificationCompat.Builder buildFullScreenNotification() {
        Intent fullScreenIntent = new Intent(this, NotificationActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img001)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setFullScreenIntent(fullScreenPendingIntent, true);
        return builder;
    }

    /**
     * 大图通知
     */
    private NotificationCompat.Builder buildBigPicNotification() {
        Drawable drawable = getResources().getDrawable(R.drawable.img005);
        BitmapDrawable bd = (BitmapDrawable) drawable;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img001)
                .setContentTitle("大图通知")
                .setContentText("展示大图")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bd.getBitmap()));
        return builder;
    }

    /**
     * 收起时展示大图缩略图
     * @return
     */
    private NotificationCompat.Builder buildBigPicNotificationWithBigIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.img005);
        BitmapDrawable bd = (BitmapDrawable) drawable;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img001)
                .setContentTitle("大图通知")
                .setContentText("大图图标")
                .setLargeIcon(bd.getBitmap())   // 设置大图标
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(null));     // 这里传递 null
        return builder;
    }

    /**
     * 展示大段文本
     * @return
     */
    private NotificationCompat.Builder buildLargeTextNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img001)
                .setContentTitle("大段文本")
                .setContentText("展示大段文本")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("第四金！施廷懋王涵夺冠】东京奥运会跳水女子双人三米板决赛中，中国队施廷懋、王涵夺冠！为中国奥运军团取得第四金。自2004年，中国跳水队已实现该项目奥运5连冠。\n" +
                                "\n" +
                                "【第五金！李发彬男子举重61公斤级金牌】男子61公斤级举重比赛中，李发彬为中国队再添一枚金牌！并打破奥运会纪录！\n" +
                                "\n" +
                                "【第六金！逆转胜利！谌利军夺得举重男子67公斤级金牌！】逆转胜利！谌利军夺得举重男子67公斤级金牌！"));
        return builder;
    }

    /**
     * 展示收件箱样式的通知，一条一条的
     * @return
     */
    private NotificationCompat.Builder buildInBoxNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img001)
                .setContentTitle("未读消息")
                .setContentText("你有5条未读消息")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(Html.fromHtml("<b>去那吃饭</b>", FROM_HTML_MODE_LEGACY))
                        .addLine("周末打球")
                        .addLine("自驾游")
                        .addLine("几点下班")
                        .addLine("台风来了"));

        return builder;
    }

    /**
     * 创建对话样式的通知
     * @return
     */
    private NotificationCompat.Builder buildConversationNotification() {
        NotificationCompat.MessagingStyle.Message message1 =
                new NotificationCompat.MessagingStyle.Message("消息 1",
                        System.currentTimeMillis(),
                        "john");
        NotificationCompat.MessagingStyle.Message message2 =
                new NotificationCompat.MessagingStyle.Message("消息 2",
                        System.currentTimeMillis(),
                        "Tom");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.img001)
                .setStyle(new NotificationCompat.MessagingStyle("回复他")
                        .addMessage(message1)
                        .addMessage(message2));
        return builder;
    }

    /**
     * 创建媒体控件通知
     * @return
     */
    private Notification.Builder buildMediaControlNotification() {
        Drawable drawable = getResources().getDrawable(R.drawable.human);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        MediaSession session = new MediaSession(this, "TestMediaSession");

        Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID)
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.gridlayout_logo)
                // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.back_ic, "Previous", null) // #0 这里需要设置 pendingIntent 去响应
                .addAction(R.drawable.back_ic, "Pause", null)  // #1
                .addAction(R.drawable.back_ic, "Next", null)     // #2
                // Apply the media style template
                .setStyle(new Notification.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession(session.getSessionToken()))
                .setContentTitle("Wonderful music")
                .setContentText("My Awesome Band")
                .setLargeIcon(bd.getBitmap());
        return builder;
    }

    /**
     * 自定义内容区域通知
     * @return
     */
    private NotificationCompat.Builder buildCustomContentAreaNotification() {
        // Get the layouts to use in the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);

        // Apply the layouts to the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.gridlayout_logo)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)   // 自定义内容区域
                .setCustomBigContentView(notificationLayoutExpanded);   // 自定义展示区域
        return builder;
    }

    /**
     * 展示通知
     * @param notificationId 唯一 id
     * @param builder
     */
    private void notifyMsg(int notificationId, NotificationCompat.Builder builder) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

    private void notifyMsg(int notificationId, Notification.Builder builder) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
}