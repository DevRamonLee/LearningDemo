- Android 艺术探索第 5 章
- [Android Notification通知使用（从基本到高级）](https://blog.csdn.net/qi85481455/article/details/82895507)(https://blog.csdn.net/qi85481455/article/details/82895507)
- [Android开发安卓10不显示通知栏Notification不显示](https://blog.csdn.net/congcongguniang/article/details/105705271)
- [NotificationCompat.Builder（）过时，失效](https://blog.csdn.net/zwk_sys/article/details/79661045)

## RemoteView

RemoteView 可以用于跨进程更新 View，它在 Android 中主要有两种使用场景：**通知栏和桌面 Widget**

### 通知栏

以下为在 Android O 上展示通知，注意我们需要创建一个 channel，Channel 是一组 notification 的集合，定义了 notification 与系统设置的交互，比如震动，闪光灯等。

```
private val NOTIFICATION_CHANNEL = "100021"

fun showDefaultNotify() {
    var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    var intent = Intent(this, DemoActivity_1::class.java)
    var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    var notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
    } else {
        NotificationCompat.Builder(this)
    }
    notificationBuilder.setContentTitle("Hello notification")   // 设置标题
        .setContentText("This is my notification")  // 设置内容
        .setDefaults(Notification.DEFAULT_ALL)      // 设置通知灯光/铃声/震动
        .setLights(Color.RED, 200, 200) // 灯光的三个参数，颜色 argb，亮时间（毫秒）灭时间（毫秒）
        .setSound(Uri.parse(""))    // 铃声，可以是本地，也可以是网上
        .setVibrate(longArrayOf(0, 200, 200,200,200))   // 震动，传入一个数组，表示停/震/停/震
        .setAutoCancel(true)    // 点击通知栏自动消失
        .setContentIntent(pendingIntent)    // 设置 intent
        .setWhen(System.currentTimeMillis())
        .setSmallIcon(R.mipmap.ic_launcher)
        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground))   // 下拉显示的图片

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var channel = NotificationChannel(NOTIFICATION_CHANNEL, "Android O", NotificationManager.IMPORTANCE_DEFAULT)
        channel.apply {
            enableLights(true)  // 是否在桌面 icon 右上角展示小红点
            lightColor = Color.GREEN    // 小红点颜色
            setShowBadge(false)         // 是否在久按桌面图标时显示此渠道的通知
        }
        manager.createNotificationChannel(channel)
    }
    var notification = notificationBuilder.build()
    manager.notify(1, notification)
}
```

上面的代码会展示一个默认的通知，点击通知会启动 DemoActivity 并清除通知，一般我们都需要自定义通知的布局，首先需要提供一个布局文件，然后通过 RemoteView 来加载这个布局文件。

```
val remoteView = RemoteViews(packageName, R.layout.layout_notification)
remoteView.setTextViewText(R.id.msg, "Open DemoActivity_1")
remoteView.setImageViewResource(R.id.icon, R.drawable.xiangce)
remoteView.setOnClickPendingIntent(R.id.msg, pendingIntent)

// 设置 remoteView
notificationBuilder. .setContent(remoteView)
```

- 只需要提供包名和布局资源文件id来加载布局
- 更新 View 和普通 view 不同，不能直接获取 view 对象，而要通过 RemoteViews 提供的一些列方法来更新 View，如 `remoteView.setTextViewText(R.id.msg, "")`,如果要实现一个点击事件则要通过 pendingInent 来实现。

> 有关通知的详细用法可以参考置顶链接

### 桌面 Widget

AppWidgetProvider 是 Android 中提供的用于实现桌面小部件的类，它继承自 BroadcastReceiver，所以它本质是一个广播。开发一个桌面小部件需要如下几步

#### 1. 定义桌面小部件

在 res/layout 下建立一个xml 文件 widget.xml ,这个是我们小部件的布局

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent">
    <ImageView
            android:id="@+id/imageView1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_collected"/>
</LinearLayout>
```

#### 2. 定义小部件的配置信息

在 res/xml 下新建 appwidget_provider_info.xml

```
<?xml version="1.0" encoding="utf-8"?>
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
        android:initialLayout="@layout/widget"  // 小工具初始化布局
        android:minHeight="168dp"               // 最小尺寸
        android:minWidth="168dp"                
        android:updatePeriodMillis="86400000">  // widget 自动更新周期，单位为毫秒
</appwidget-provider>
```

#### 3. 定义小部件的实现类

需要继承 AppWidgetProvider

```
class MyAppWidgetProvider: AppWidgetProvider() {
    companion object {
        val TAG : String = "MyAppWidgetProvider"
        val CLICK_ACTION = "top.betterramon.remoteviewdemo.action.CLICK"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.i(TAG, "onReceive: action = ${intent?.action}")
        // 判断如果是自己的 action 则做自己的事情，如小部件单击，这里添加一个动画
        if (intent?.action.equals(CLICK_ACTION)) {
            Toast.makeText(context, " clicked it", Toast.LENGTH_LONG).show()
            Thread(Runnable {
                val srcBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.baby) as Bitmap
                val appWidgetManager = AppWidgetManager.getInstance(context)
                for(i in 0 until 37) {
                    var degree = ((i * 10) % 360).toFloat()
                    var remoteViews = RemoteViews(context?.packageName, R.layout.widget)
                    remoteViews.setImageViewBitmap(R.id.imageView1, rotateBitmap(srcBitmap, degree))
                    val intentClick = Intent()
                    intentClick.action = CLICK_ACTION
                    val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0)
                    remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent)
                    appWidgetManager.updateAppWidget(ComponentName(context!!, MyAppWidgetProvider::class.java), remoteViews)
                    SystemClock.sleep(30)
                }
            }).start()
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(TAG, "onUpdate")
        val counter = appWidgetIds?.size
        Log.i(TAG, "counter = $counter")
        for (i in 0 until counter!!) {
            val appWidgetId = appWidgetIds[i]
            onWidgetUpdate(context!!, appWidgetManager!!, appWidgetId)
        }
    }

    /**
     * 桌面小部件更新
     */
    private fun onWidgetUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        Log.i(TAG, "appWidgetId = $appWidgetId")
        val remoteViews = RemoteViews(context.packageName, R.layout.widget)

        // 桌面 widget 单击事件发送的 Intent 广播
        val intentClick = Intent()
        intentClick.action = CLICK_ACTION
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0)
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    private fun rotateBitmap(srcBitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.reset()
        matrix.setRotate(degree)
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.width, srcBitmap.height, matrix, true)
    }
}
```

#### 4. 在 AndroidManifest.xml 中注册

因为小部件的本质是广播，所以需要我们在清单文件中进行注册

```
<receiver android:name=".widget.MyAppWidgetProvider">
    <meta-data
        android:name="android.appwidget.provider"
        android:resource="@xml/appwidget_provider_info">
    </meta-data>
    <intent-filter>
        <action android:name="top.betterramon.remoteviewdemo.action.CLICK"/>
        <action android:name="android.appwidget.action.APPWIDGET_UPDATE"/>
    </intent-filter>
</receiver>
```

上面的两个 action，一个用于识别小部件的点击事件，另一个则是标志 widget 而必须存在

AppWidgetProvider 的几个常用方法

- onEnable: 当该窗口小部件第一次被添加到桌面时调用此方法，可添加多次，但只在第一次调用
- onUpdate：小部件被添加或者每次小部件更新时都会调用一次该方法，小部件的更新时机由 updatePeriodMillis 指定，每个周期小部件都会更新一次
- onDelete: 每删除一次桌面小部件就调用一次
- onDisable: 当最有一个该类型的桌面小部件被删除时调用
- onReceive：广播的方法，用于分发具体的事件给其他方法


### PendingIntent

PendingIntent 表示一个意图处于 pending 状态，即待定，等待，即将发生的意思，即一个 Intent 将在某个待定时刻发生。

PendingInent 支持三种待定意图：启动 Activity，启动 Service ，发送广播

pendingIntent | 场景
--|--
static PendingIntent | getActivity(Context context,int requestCode, Intent intent,int flags), 当该意图发生时，效果相当于 Context.startActivity(intent)
static PendingIntent | getService(Context context, int requestCode, Intent intent, int flags),当该意图发生时，效果相当于 Context.startService(intent)
static PendingIntent | getBoradcast(Context context, int requestCode, Intent intent, int flags),当该意图发生时，效果相当于 Context.sendBroadcast(intent)


- requestCode: 请求码，多数情况下设置为 0 即可，另外 requestCode 会影响 flags 的效果。
- flags：常见的类型有如下几种，要理解这几种标记位，要理解 PendingIntent 匹配规则（什么情况下两个 PendingIntent 是相同的）如果两个 PendingIntent 相同那么它们内部的 Intent 相同并且 requestCode 也相同。Intent 相同是指：如果两个 Intent 的 ComponentName 和 intent-filter 相同，那么这两个 intent 就相同，Extra 不参与匹配。
    -  FLAG_ONE_SHOT: 当前描述的 PendingIntent 只能被使用一次，然后它就会被自动 cancel，后续的 PendingInent 的 send 方法会调用失败。例如：通知栏消息如果设置此标志位，则只能使用一次，后续的通知单击后无法打开。
    -  FLAG_NO_CREATE: 当前 PendingInent 不会主动创建，即如果之前不存在，那么通过 getActivity,getService, getBroadcast 创建会返回 null，这个标记位用的很少且无法单独使用。
    -  FLAG_CANCEL_CURRENT: 当前描述的 PendingInent 如果已经存在，那么它们都会被 cancel，然后系统会创建一个新的 PendingInent。
    -  FLAG_UPDATE_CURRENT: 当前描述的 PendingIntent 如果已经存在，那么它们都会被更新，它们中 Intent 的 Extras 会被替换成新的。


### RemoteView 内部机制

RemoteView 的作用是在其他进程中显示并更新 View 界面。它的最常用的构造方法如下：

```
public RemoteViews(String packageName, int layoutId)
```

RemoteViews 目前不能支持所有的 View 类型，它支持的类型如下：Layout: ` FrameLayout/LinearLayout/RelativeLayout/GridLayout` View: `AnlogClock/Button/Chronometer/ImageButton/ImageView/ProgressBar/TextView/ViewFlipper/ListView/GridView/StackView/AdapterViewFlipper/ViewStub`

> RemoteViews 不支持上述 View 的子类型以及其他类型并且也不能使用自定义 View

因为 RemoteViews 在远程进程里显示，所以无法直接使用 findViewById，我们需要通过 RemoteViews 提供的一些方法来更新 View，下面是一些常见的方法

方法名 | 作用
---|---
setTextViewText(int viewId, CharSequence text) | 设置文本
setTextViewTextSize(int viewId, int units, float size) |m 设置字体大小
setTextColor(int viewId, int color) | 设置颜色
setImageViewResource(int viewId, int srcId) | 设置图片资源
setImageViewBitmap(int viewId, Bitmap bitmap) | 设置图片
setInt(int viewId, String methodName, int value) | 反射调用 View 对象参数类型为 int 的方法
setLong(int viewId, String methodName, long value) | 类似上
setBoolean(int viewId, String methodName, boolean value) | 类似上
setOnClickPendingInent(int viewId, PendingInent pendingIntent) | 为 View 添加单击事件，事件类型只能为 PendingIntent

> 大部分 set 方法是通过反射来完成的

理论层面：

- 通知栏和桌面小部件分别由 NotificationManager 和 AppWidgetManager 管理，这两个 Manager 通过 Binder 分别和 SystemServer 进程中的 NotificationManagerService 以及 AppWidgetService 进行通信，所以通知栏和桌面小部件中的布局文件实际是在对应的 Service 中被加载的，也就是它们都运行在 SystemServer 中，这就构成了跨进程通信的场景。
- RemoteView（实现了 Parcelable 接口） 通过 Binder 被传输到 SystemServer 进程，系统根据 RemoteViews 中的包名得到该应用的资源，然后通过 LayoutInflater 去加载 RemoteViews 的布局文件。接着系统对 View 执行一系列的更新任务，这些任务就是之前我们通过上面的一系列 set 方法来提交的。set 方法对 View 进行的操作不会立即执行，RemoteViews 内部会记录所有的更新操作，等到 RemoteView 被加载后会执行。
- 理论上讲，系统可以通过 Binder 操作来支持所有的 View 和View 操作，但是这样代价太大，View 方法太多，另外大量的 IPC 操作会影响效率，为了解决这个问题，系统没有通过 Binder 直接去支持 View 的跨进程访问，而是提出了一个 Action 的概念，Action 代表一个 View 操作，Action 也实现了 Parcelable 接口，系统将 View 操作封装成 Action并发送到远程进程。我们每调用一次 set 方法，就会在 RemoteView 中添加一个对应的 Action 对象，当调用 NotificationManager 和 AppWidgetManager 来提交我们的更新时，这些 Action 对象就会传输到远程进程并依次执行。然后调用通过远程调用 RemoteView 的 apply 方法，apply 方法内部会去遍历所有的 Action 对象并调用它们的 apply 方法。（这样的设计有两个好处，首先避免了定义大量的 Binder 接口，其次通过远程批量执行而避免了大量的 IPC 操作，提高了性能）

源码层面：

- 我们来看一个 RemoteView 提供的 set 方法

```
public void setTextViewText(int viewId, CharSequence text) {
    setCharSequence(viewId, "setText", text);
}

// 没有对 view 直接操作，而是添加了一个 Action
public void setCharSequence(int viewId, String methodName, CharSequence value) {
    addAction(new ReflectionAction(viewId, methodName, ReflectionAction.CHAR_SEQUENCE, value);
}

// RemoteView 有一个 mActions 成员，每调用一次 set 就会保存一个 Action
private void addAction(Action a) {
    ...
    if (mActions == null) {
        mActions = new ArrayList<Action>();
    }
    mActions.add(a);
    
    // update the memory usage state
    a.updateMemoryUsageEstimate(mMemoryUsageCounter);
}
```

- 可以看到关键的操作都在 ReflectionAction 类里，在看这个类之前，我们先来看以下 RemoteView 的 apply 方法

```
public View apply(Context context, ViwGroup parent, OnClickHandler handler) {
    RemoteView rvToApply = getRemoteViewToApply(context);
    View result;
    ..
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    ...
    // 加载 RemoteView 布局文件
    result = inflater.inflate(rvToApply.getLayoutId(), parent, false);
    // 加载完布局文件后会执行更新操作
    rvToApply.performApply(result, parent, handler);
    return result;
}

// 遍历 Action 列表并执行每一个 Action 的 apply 方法
private void performApply(View v, ViewGroup parent, OnClickHandler handler) {
    if (mActions != null) {
        handler = handler== null ? DEFAULT_ON_CLICK_HANDLER : handler;
        final int count = mAction.size();
        for (int i = 0; i < count; i++) {
            Action a = mActions.get(i);
            a.apply(v, parent, hander);
        }
    }
}
```

> 调用 RemoteView 的 set 方法时界面不会立即更新，我们需要调用 NotificationManager 的 notify 或者 AppWidgetManager 的 updateAppWidget 才能更新界面，它们内部是通过 apply 及 reapply 来更新界面的，这两个方法的区别在于 apply 会加载界面并更新界面，后者只会更新界面。

- 接下来我们再看 ReflectionAction 的实现

```
private final class ReflectionAction extends Action {
    ReflectionAction(int viewId, String methodName, int type, Object value) {
        this.valueId = viewId;
        this.methodName = methodName;
        this.type = type;
        this.value = value;
    }
    
    ...
    @Override
    public void apply(View root, ViewGroup rootProject, OnClickHandler handler) {
        final View view = root.findViewById(viewId);
        if (view == null) return;
        
        Class<?> param = getParameterType();
        if (param == null) {
            throw new ActionException("bad type: " + this.type);
        }
        
        try{
            getMethod(view, this.methodName, param).invoke(view, wrapArg(this.value);
        }catch(ActionException e) {
            throw e;
        }catch(Exception ex) {
            throw new ActionException(ex);
        }
    }
}
```

通过反射获取 View 的方法并执行。再来看一个其他的 Action

```
private class TextViewSizeAction extends Action {
    public TextViewSizeAction(int viewId, int units, float size) {
        this.viewId = viewId;
        this.units = units;
        this.size = size;
    }
    ...
    @Override
    public void apply(View root, ViewGroup rootParent, OnClickHandler handler) {
        final TextView target = (TextView) root.findViewById(viewId);
        if (target == null) return;
        target.setTextSize(units, size);
    }
    
    public String getActionName() {
        return "TextViewSizeAction";
    }
    
    int units;
    float size;
    
    public final static int TAG = 13;
}
```

> 这个方法比较简单，它之所以不用反射是因为这个方法参数有两个，无法复用 ReflectionAction 反射只有一个参数。

RemoteView 单击事件

- setOnClickPendingIntent 用于给普通 View 设置单击事件，但是不能给集合（ListView）中的 View 设置单击事件，开销较大
- 如果要给 ListView 和 StackView 中的 item 添加单击事件，必须将 setPendingIntentTemplate 和 setOnClickFillInIntent 组合使用才可以。

> 例子： 用 RemoteView 实现模拟通知栏效果并实现跨进程的 UI 更新。A B 两个 Activity 运行在不同的进程， A 模拟通知栏角色， B 不停向 A 发送消息，这样就模拟了多进程通信的情况。在 B 中创建 RemoteView 对象，然后通知 A 显示这个 RemoteView 对象，这个通知过程我们采用广播来实现，这个过程和通知栏显示的过程一致。