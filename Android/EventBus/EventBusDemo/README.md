# EventBus(һ)������ʶ

- [EventBus github ��ַ](https://github.com/greenrobot/EventBus)
- [EventBus ����](http://greenrobot.org/eventbus)
- [EventBus �ص�](http://greenrobot.org/eventbus/features/)
- [EventBus �ĵ�](http://greenrobot.org/eventbus/documentation/)
- [EventBus ����](http://greenrobot.org/eventbus/documentation/proguard)
- [EventBus ������־](http://greenrobot.org/eventbus/changelog/)
- [EventBus FAQ](http://greenrobot.org/eventbus/documentation/faq/)

## ���

���������ٷ� Github �ϵ�һ��ͼ��

![event_bus_001](./assets/event_bus_001.png)

EventBus ������ Android ���� Java �Ͻ����¼��Ķ����뷢�������ڹ۲���ģʽ��

EventBus ��һЩ�ص㣨���� github��

- �������Ľ�����
    - ����¼��ķ�����������ߣ�����һ���м��ߵ����á�
    - ������ Activity��Fragment����̨�̡߳�
    - ���⸴�ӵĺ����׳���������Լ������������⡣
- �򻯴��롢�졢С��~50k jar������ʹ���� 100000000+ �Ѱ�װ app �С�
- �߼����ԣ���ַ��̡߳����������ȼ��ȡ�

## ��� EventBus �� AndroidStudio

ֻ��Ҫ�� gradle ���������������

```
implementation 'org.greenrobot:eventbus:3.1.1'
```

������ Maven Central ���� [JAR ��](https://search.maven.org/remote_content?g=org.greenrobot&a=eventbus&v=LATEST)

## 3 ��ʹ�� EventBus

#### Step 1: �����¼�

```
public class MessageEvent {
    public final String message;

    public MessageEvent(String message) {
        this.message = message;
    }
}
```

#### Step 2: ׼��������

������ʵ�ֶ��ķ�����ʹ�� @Subscribe ������ָ�������ߡ�

> ע���� EventBus 3 �з���������û�����ƣ�û�� EventBus 2 �е�����Լ����

```
// This method will be called when a MessageEvent is posted (in the UI thread for Toast)
@Subscribe(threadMode = ThreadMode.MAIN)
public void onMessageEvent(MessageEvent event) {
    Toast.makeText(this,event.message, Toast.LENGTH_SHORT).show();
}

// This method will be called when a SomeOtherEvent is posted
@Subscribe
public void handleSomethingElse(SomeOtherEvent event) {
    // doSomethingWith(event);
}
```

Subscribers ��Ҫ�� bus ��ע��ͽ�������Լ���ֻ��ע����� Subscribers ���Խ��յ��¼����� Android �� Activity ���� fragment ��ͨ�������� onStart/onStop ��ʹ�á�

```
@Override
protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
}

@Override
protected void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
}
```

#### Step 3: �����¼�

����Ĵ��������һ���ַ����¼�������ע��Ķ����߶����յ�����¼���

```
EventBus.getDefault().post(new MessageEvent("Hello event bus"));
```

## �ַ��̣߳�ThreadMode��

EventBus ���԰������Ǵ����̼߳�ͨ�����⡣**�¼����Է������봦���̲߳�ͬ���߳���**��������֪�ģ�Android UI ����ֻ���� UI �߳��н��С������������һЩ��ʱ���������� UI �߳��н��У�EventBus ���԰������Ǵ����̼߳�ͨ����ͬ�����⣬��������Ҫʹ�� AsyncTask��

�������� ThreadMode ���ļ���

- ThreadMode: POSTING
- ThreadMode��MAIN
- ThreadMode: MAIN_ORDERED
- ThreadMode: BACKGROUND
- ThreadMode: ASYNC

#### ThreadMode: POSTING

**�����߷����������¼������ߵ��߳��б����ã�������Ĭ�ϵķ�ʽ**���¼�������ͬ����ɵģ����еĶ����߷������¼��������ʱ�ᱻ���á����ַ����Ŀ�������С�ģ���Ϊ����ȫ�������л��̡߳�����ģʽ�ʺϴ���һЩ��ʱ�ǳ��̵ļ����񣬿��Կ��ٷ��ض����������ַ��̡߳����磺��־��¼

```
// Called in the same thread (default)
// ThreadMode is optional here
@Subscribe(threadMode = ThreadMode.POSTING)
public void onMessage(MessageEvent event) {
    log(event.message);
}
```


#### ThreadMode: MAIN

**�����߷��������� UI �߳��б�����**������������� UI �߳��������� POSTING ģʽһ�����������ᱻͬ����ֱ�ӵ��á�

```
// Called in Android UI's main thread
@Subscribe(threadMode = ThreadMode.MAIN)
public void onMessage(MessageEvent event) {
    textField.setText(event.message);
}
```

#### ThreadMode: MAIN_ORDERED

���ַ�ʽ�����߷���Ҳ�������߳��б����á�**�������Ǹ��̷߳����¼����¼������Ƚ�����У�Ȼ��ͨ�� Handler �л������̣߳����δ���**��

```
// Called in Android UI's main thread
@Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
public void onMessage(MessageEvent event) {
    textField.setText(event.message);
}
```

#### ThreadMode: BAACKGROUND

**�����߷��������ں�̨�߳��б����ã�����¼������̲߳������̣߳������߷�������ֱ���ڷ����߳��б����á�**����¼������������̣߳�EventBus ʹ��һ�������ĺ�̨�߳�˳��ַ��¼���ʹ��ʱҪע�ⶩ���߷�����Ҫ������̨�̡߳�

```
// Called in the background thread
@Subscribe(threadMode = ThreadMode.BACKGROUND)
public void onMessage(MessageEvent event){
    saveToDisk(event.message);
}
```

#### ThreadMode: ASYNC

**�¼�������������һ���������߳��б����ã�����߳����Ƕ������¼��ַ��̺߳����̣߳�����ģʽ�¼��������������������**�����ַ���������һЩ��ʱ�������������������Ϊ�˱�������̫����̣߳�EventBus ʹ�����̳߳��������߳���Դ��

```
// Called in a separate thread
@Subscribe(threadMode = ThreadMode.ASYNC)
public void onMessage(MessageEvent event){
    backend.send(event.message);
}
```

## Configuration

ʹ�� EventBus Builder �������� EventBus�����磺�����Ǵ���һ����Ĭ�� EventBus �ѷ��������¼�û�ж����ߡ�

```
EventBus eventBus = EventBus.builder()
    .logNoSubscriberMessages(false)
    .sendNoSubscriberEvent(false)
    .build();
```

��һ�������ǵ�һ���������׳��쳣ʱ�׳��쳣ָʾʧ�ܡ�

```
EventBus eventBus = EventBus.builder().throwSubscriberException(true).build();
```

> ע�⣺Ĭ������£�EventBus �� catch subscriber �����׳����쳣,����һ������ SubscriberExceptionEvent �쳣��

��������ÿ��Բο� [EventBusBuilder ����ĵ�](http://greenrobot.org/files/eventbus/javadoc/3.0/org/greenrobot/eventbus/EventBusBuilder.html)

#### ����Ĭ�ϵ� EventBus ʵ��

����ʹ�� `EventBus.getDefault()` ������ app ���κ�λ�û�ȡһ�� EventBus ��ʵ����EventBusBuilder ͬ������ʹ�÷��� `installDefaultEventBus()` ������Ĭ�ϵ�ʵ����

���磺���ý����� DEBUG ����ģʽʱ�׳��쳣��

```
EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
```

> ע�⣺����� default EventBus ʵ����ʹ��ǰֻ�ܵ���һ�Σ��ٴε��� `installDefaultEventBus()`�������׳��쳣���Ƽ��� Application ������ default EventBus ʵ��ʹ��ǰ��������


## Sticky Events

һЩ���ͳ�ȥ���¼�Я����һЩ���Ǹ���Ȥ����Ϣ�����磺����һЩλ�ô��������������뱣�����µ�ֵ��ʹ��ճ���¼��������Լ�ȥʵ�ֻ��棬EventBus Ϊ�������ڴ��б������һ��ճ���¼������ݡ�

#### Sticky Example

���磺һ��ʱ��ǰ������һ��ճ���¼�
```
EventBus.getDefault().postSticky(new MessageEvent("Send sticky event old"));
EventBus.getDefault().postSticky(new MessageEvent("Send sticky event new"));
```

����������һ�� StickyActivity����ע���ڼ����е�ճ�Զ��ķ����������յ�����ǰ�淢�͵�ճ���¼���(����ֻ���յ� ��Send sticky event new�� ����¼�)

```
@Override
protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
}

@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
public void onEvent(MessageEvent event) {
    Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
}

@Override
protected void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
}
```

#### �ֶ���ȡ�����Ƴ� sticky Event

��������ע��ʱ�����һ��ճ���¼��ᱻ�Զ��ַ���������ʱ�ֶ���ȥ���ճ���¼����ӷ��㣬�ֶ�ȥ�Ƴ�һ��ճ���¼��Ա��������ٴηַ�Ҳ�Ǻ��б�Ҫ�ġ����磺

```
MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
// Better check that an event was actually posted before
if(stickyEvent != null) {
    // "Consume" the sticky event
    EventBus.getDefault().removeStickyEvent(stickyEvent);
    // Now do something with it
}
```

���� `removeStickyEvent` �����أ����㴫����ʱ������������ǰ���е�ճ���¼��� ʹ��������� �����ǿ��ԸĽ�ǰ���ʾ��

```
MessageEvent stickyEvent = EventBus.getDefault().removeStickyEvent(MessageEvent.class);
// Better check that an event was actually posted before
if(stickyEvent != null) {
    // �Ƿ���Ҫ������¼�����һ������
}
```

## ���ȼ����¼�ȡ��

EventBus �Ĵ����ʹ�ó�����������Ҫ���ȼ������¼�ȡ�������Ǳ���������һЩ����ĳ��������磺��� app ��ǰ̨һ���¼����ܻᴥ��һЩ UI �߼����������app ���û��Ƿ�ɼ������в�ͬ����Ӧ��

#### ���������ȼ�

��ע���ڼ�ͨ�� priority ���Կ��Ըı䶩���ߵ����ȼ���

```
@Subscribe(priority = 1);
public void onEvent(MessageEvent event) {
    ...
}
```
��ͬһ�� ThreadMode �У������ȼ��Ķ����߽����յ��¼���Ĭ�ϵ����ȼ��� 0

> ע�⣺���ȼ�����Ӱ��ӵ�в�ͬ ThreadMode �Ķ����ߡ�


#### ȡ���¼��ַ�

ͨ���ڶ����ߵĴ������е��� `cancelEventDelivery(Object event)` ��ȡ��һ���¼��ַ��������Ķ����߽��������յ�����¼���

```
// Called in the same thread (default)
@Subscribe
public void onEvent(MessageEvent event){
    // Process the event
    ...
    // Prevent delivery to other subscribers
    EventBus.getDefault().cancelEventDelivery(event) ;
}
```

һ���ڸ����ȼ��Ķ��ķ�����ȡ���¼���ȡ������ֻ�� ThreadMode.POSTING ����Ч����Ĭ�Ͼ��� POSTING��


## Subscriber Index 

subscriber index �� EventBus 3 �������ԡ�**����һ����ѡ�ļ��ٶ����߳�ʼ��ע��ķ���**��

**����ʹ�� EventBus ע�⴦�����ڹ����ڼ䴴��������������������Ǳ���ģ����ǽ����� Android ��ʹ���Ի����ѵ����ܱ��֣�������������ʱʹ�÷��������Ҷ����߷�����**


#### ����������ǰ������

- ֻ�б� @Subscriber ע�����εķ������Ҷ����ߵ�����¼�����Ҫ�� public �ġ�
- ���� Java ע�⴦���������ƣ�@Subscriber ע�����������в��ܱ�ʶ��

> �� EventBus �޷�ʹ������ʱ������������ʱ�Զ�ʹ�÷��䣬������������һ�㡣

#### ʹ�� annotationProcessor ��������

������ Android gradle plugin �汾�� 2.2.0 ���£�ʹ�� android-apt ���á�

- ʹ�� `annotationProcessor` ������� EventBus annotation processor �����ǵ���Ŀ��

```
dependencies {
    implementation 'org.greenrobot:eventbus:3.1.1'
    annotationProcessor 'org.greenrobot:eventbus-annotation-processor:3.1.1'
}
```

- ���ò��� `eventBusIndex`��ָ�������������������ȫ�޶�������ע������������ܺʹ����е��κ�һ������ͬ����Ȼ�ᱨ�����ظ��Ĵ��󣩣����£�

```
android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [eventBusIndex: 'top.betterramon.eventbusdemo.MyEventBusIndex']
            }
        }
    }
}
```
Ȼ�󹹽����򣬻��� `build/generated/source/apt/` ������������ָ���������,��һ�������,���Կ����ڹ���ʱ������ͳ�������еĶ������Լ���Ӧ�ķ�����Ϣ������������ʹ�õ�ʱ��Ͳ���Ҫ��ʹ�÷���ȥ���Ҷ�Ӧ�ķ����ˡ�

```
public class MyEventBusIndex implements SubscriberInfoIndex {
    private static final Map<Class<?>, SubscriberInfo> SUBSCRIBER_INDEX;

    static {
        SUBSCRIBER_INDEX = new HashMap<Class<?>, SubscriberInfo>();

        putIndex(new SimpleSubscriberInfo(MainActivity.class, true, new SubscriberMethodInfo[] {
            new SubscriberMethodInfo("onMessageEvent", MessageEvent.class),
            new SubscriberMethodInfo("onMessageEventPriority", MessageEvent.class, ThreadMode.POSTING, 1, false),
            new SubscriberMethodInfo("handleSomethingElse", SomeOtherEvent.class),
        }));

        putIndex(new SimpleSubscriberInfo(StickyActivity.class, true, new SubscriberMethodInfo[] {
            new SubscriberMethodInfo("onEvent", MessageEvent.class, ThreadMode.MAIN, 0, true),
        }));

    }

    private static void putIndex(SubscriberInfo info) {
        SUBSCRIBER_INDEX.put(info.getSubscriberClass(), info);
    }

    @Override
    public SubscriberInfo getSubscriberInfo(Class<?> subscriberClass) {
        SubscriberInfo info = SUBSCRIBER_INDEX.get(subscriberClass);
        if (info != null) {
            return info;
        } else {
            return null;
        }
    }
}
```

#### ʹ�� kapt

������� Kotlin ��ʹ�� EventBus����Ҫʹ�� `kapt` ����� `annotationProcessor`

```
apply plugin: 'kotlin-kapt' // ensure kapt plugin is applied
 
dependencies {
    implementation 'org.greenrobot:eventbus:3.1.1'
    kapt 'org.greenrobot:eventbus-annotation-processor:3.1.1'
}
 
kapt {
    arguments {
        arg('eventBusIndex', 'com.example.myapp.MyEventBusIndex')
    }
}
```

#### ʹ�� android-apt

�������ķ������㶼�����ã�����ʹ�� android-apt gradle plugin, ����� gradle �ű���������´���

```
buildscript {
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}
 
apply plugin: 'com.neenbedankt.android-apt'
 
dependencies {
    compile 'org.greenrobot:eventbus:3.1.1'
    apt 'org.greenrobot:eventbus-annotation-processor:3.1.1'
}
 
apt {
    arguments {
        eventBusIndex "com.example.myapp.MyEventBusIndex"
    }
}
```

#### ���ʹ������

�ڳɹ���������Ŀ֮��`eventBusIndex` ����ָ������ᱻ�Զ����ɣ�����������ֻ��Ҫ������ഫ�ݸ����ǵ� EventBus��ʹ�÷������£�

```
EventBus eventBus = EventBus.builder().addIndex(new MyEventBusIndex()).build();
```

���ߣ��������Ҫʹ��Ĭ�ϵ�ʵ����������ʹ��,�� Application �� onCreate ����������Ĭ�ϵ� EventBus ʵ��

```
EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
```

#### �� Libraries �������

������ͬ���ķ�������� library ������������ַ�ʽ����ж�������࣬ʹ�÷������£�

```
EventBus eventBus = EventBus.builder()
    .addIndex(new MyEventBusAppIndex())
    .addIndex(new MyEventBusLibIndex()).build();
```

## ProGuard(����)

ProGuard �������������ֲ��һ��Ƴ�һЩû�б����õķ�������Ϊ Sbbscriber method ����ֱ�ӱ����õģ����� ProGuard ����Ϊ������û�б��õ��ġ�����������Ҫ���� Proguard ������Щ������

```
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
 
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
```

> ע�⣺�����Ƿ�ʹ�ö���������������Ҫ��Ӵ����á�

## AsyncExecutro ���첽ִ�У�

�첽ִ�������̳߳أ��������쳣������ơ�AsyncExecutor ���װ���е��쳣��һ���¼��У�Ȼ���Զ���������¼���

> AsyncExecutor ��һ���Ǻ��ĵ�ʵ���࣬���ں�̨�߳��б���һЩ������Ĵ��룬���������� EventBus �ĺ����ࡣͨ����ʹ�� `AsyncExecutor.create()` ��������һ�� Application ��Χ�ڵ�ʵ����

��������е㲻�ö���ֱ����������ôʹ��

```
AsyncExecutor.create().execute(
    new AsyncExecutor.RunnableEx() {
        @Override
        public void run() throws Exception {
            // No need to catch any Exception
            int x = 100 / 0;
            EventBus.getDefault().postSticky(new MessageEvent());
        }
    }
);
```
��� `RunnableEx` ����ʵ�������׳���һ���쳣������쳣�ᱻ��׽���Ұ�װ�� `ThrowableFailureEvent` �¼���EventBus �������¼��ַ���ȥ��

```
@Subscribe(threadMode = ThreadMode.MAIN)
public void handleLoginEvent(MessageEvent event) {
    // do something
}
 
@Subscribe(threadMode = ThreadMode.MAIN)
public void handleFailureEvent(ThrowableFailureEvent event) {
    // do something
}
```

#### AsyncExecutor Buider

ͨ�� `AsyncExecutor.buider()` ���ص�ʵ�����Զ��� EventBus ʵ�����̳߳ء������¼������ࡣ

��һ���Զ���ѡ����ִ�з�Χ����Ϊʧ���¼��ṩ��������Ϣ�� ���磬ʧ���¼����ܽ����ض���Activityʵ��������ء�

��������Զ���ʧ���¼���ʵ��HasExecutionScope�ӿڣ�AsyncExecutor���Զ�����ִ�з�Χ�� �����������Ķ����߿��Բ�ѯʧ���¼���ִ�з�Χ��������������Ӧ��