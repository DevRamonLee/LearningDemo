# EventBus(一)初步认识

- [EventBus github 地址](https://github.com/greenrobot/EventBus)
- [EventBus 官网](http://greenrobot.org/eventbus)
- [EventBus 特点](http://greenrobot.org/eventbus/features/)
- [EventBus 文档](http://greenrobot.org/eventbus/documentation/)
- [EventBus 混淆](http://greenrobot.org/eventbus/documentation/proguard)
- [EventBus 更新日志](http://greenrobot.org/eventbus/changelog/)
- [EventBus FAQ](http://greenrobot.org/eventbus/documentation/faq/)

## 简介

首先来看官方 Github 上的一张图：

![event_bus_001](./assets/event_bus_001.png)

EventBus 可以在 Android 或者 Java 上进行事件的订阅与发布，基于观察者模式。

EventBus 的一些特点（译自 github）

- 简化组件间的交流。
    - 解绑事件的发送者与接受者，它起到一个中间者的作用。
    - 适用于 Activity、Fragment、后台线程。
    - 避免复杂的和容易出错的依赖以及生命周期问题。
- 简化代码、快、小（~50k jar）、已使用在 100000000+ 已安装 app 中。
- 高级特性，如分发线程、订阅者优先级等。

## 添加 EventBus 到 AndroidStudio

只需要在 gradle 中添加如下依赖：

```
implementation 'org.greenrobot:eventbus:3.1.1'
```

或者在 Maven Central 下载 [JAR 包](https://search.maven.org/remote_content?g=org.greenrobot&a=eventbus&v=LATEST)

## 3 步使用 EventBus

#### Step 1: 定义事件

```
public class MessageEvent {
    public final String message;

    public MessageEvent(String message) {
        this.message = message;
    }
}
```

#### Step 2: 准备订阅者

订阅者实现订阅方法，使用 @Subscribe 声明来指定订阅者。

> 注意在 EventBus 3 中方法的命名没有限制（没有 EventBus 2 中的命名约定）

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

Subscribers 需要在 bus 中注册和解绑他们自己，只有注册过的 Subscribers 可以接收到事件。在 Android 的 Activity 或者 fragment 中通常建议在 onStart/onStop 中使用。

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

#### Step 3: 发送事件

在你的代码的任意一部分发送事件，所有注册的订阅者都将收到这个事件。

```
EventBus.getDefault().post(new MessageEvent("Hello event bus"));
```

## 分发线程（ThreadMode）

EventBus 可以帮助我们处理线程间通信问题。**事件可以发布在与处理线程不同的线程中**。我们熟知的，Android UI 更新只能在 UI 线程中进行、而网络操作和一些耗时操作则不能在 UI 线程中进行，EventBus 可以帮助我们处理线程间通信与同步问题，而不再需要使用 AsyncTask。

先来看看 ThreadMode 有哪几种

- ThreadMode: POSTING
- ThreadMode：MAIN
- ThreadMode: MAIN_ORDERED
- ThreadMode: BACKGROUND
- ThreadMode: ASYNC

#### ThreadMode: POSTING

**订阅者方法将会在事件发布者的线程中被调用，这种是默认的方式**。事件传递是同步完成的，所有的订阅者方法在事件发送完成时会被调用。这种方法的开销是最小的，因为它完全避免了切换线程。这种模式适合处理一些耗时非常短的简单任务，可以快速返回而不会阻塞分发线程。例如：日志记录

```
// Called in the same thread (default)
// ThreadMode is optional here
@Subscribe(threadMode = ThreadMode.POSTING)
public void onMessage(MessageEvent event) {
    log(event.message);
}
```


#### ThreadMode: MAIN

**订阅者方法将会在 UI 线程中被调用**。如果发布者在 UI 线程则和上面的 POSTING 模式一样，处理方法会被同步的直接调用。

```
// Called in Android UI's main thread
@Subscribe(threadMode = ThreadMode.MAIN)
public void onMessage(MessageEvent event) {
    textField.setText(event.message);
}
```

#### ThreadMode: MAIN_ORDERED

这种方式订阅者方法也会在主线程中被调用。**无论在那个线程发送事件，事件都会先进入队列，然后通过 Handler 切换到主线程，依次处理**。

```
// Called in Android UI's main thread
@Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
public void onMessage(MessageEvent event) {
    textField.setText(event.message);
}
```

#### ThreadMode: BAACKGROUND

**订阅者方法将会在后台线程中被调用，如果事件发送线程不是主线程，则订阅者方法将会直接在发送线程中被调用。**如果事件发送是在主线程，EventBus 使用一个单独的后台线程顺序分发事件，使用时要注意订阅者方法不要阻塞后台线程。

```
// Called in the background thread
@Subscribe(threadMode = ThreadMode.BACKGROUND)
public void onMessage(MessageEvent event){
    saveToDisk(event.message);
}
```

#### ThreadMode: ASYNC

**事件处理方法总是在一个单独的线程中被调用（这个线程总是独立于事件分发线程和主线程，这种模式事件处理方法不会造成阻塞）**（这种方法适用于一些耗时操作，如网络操作）。为了避免启动太多的线程，EventBus 使用了线程池来复用线程资源。

```
// Called in a separate thread
@Subscribe(threadMode = ThreadMode.ASYNC)
public void onMessage(MessageEvent event){
    backend.send(event.message);
}
```

## Configuration

使用 EventBus Builder 类来配置 EventBus。例如：下面是创建一个静默的 EventBus 已防发布的事件没有订阅者。

```
EventBus eventBus = EventBus.builder()
    .logNoSubscriberMessages(false)
    .sendNoSubscriberEvent(false)
    .build();
```

另一个例子是当一个订阅者抛出异常时抛出异常指示失败。

```
EventBus eventBus = EventBus.builder().throwSubscriberException(true).build();
```

> 注意：默认情况下，EventBus 会 catch subscriber 方法抛出的异常,但不一定发送 SubscriberExceptionEvent 异常。

更多的配置可以参考 [EventBusBuilder 类的文档](http://greenrobot.org/files/eventbus/javadoc/3.0/org/greenrobot/eventbus/EventBusBuilder.html)

#### 配置默认的 EventBus 实例

我们使用 `EventBus.getDefault()` 方法在 app 的任何位置获取一个 EventBus 的实例。EventBusBuilder 同样允许使用方法 `installDefaultEventBus()` 来配置默认的实例。

例如：配置仅仅在 DEBUG 构建模式时抛出异常。

```
EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
```

> 注意：这个在 default EventBus 实例被使用前只能调用一次，再次调用 `installDefaultEventBus()`方法会抛出异常。推荐在 Application 类中在 default EventBus 实例使用前配置它。


## Sticky Events

一些发送出去的事件携带了一些我们感兴趣的信息。例如：你有一些位置传感器的数据你想保持最新的值，使用粘性事件而不必自己去实现缓存，EventBus 为我们在内存中保存最后一个粘性事件的数据。

#### Sticky Example

假如：一段时间前发送了一个粘性事件
```
EventBus.getDefault().postSticky(new MessageEvent("Send sticky event old"));
EventBus.getDefault().postSticky(new MessageEvent("Send sticky event new"));
```

现在新启动一个 StickyActivity，在注册期间所有的粘性订阅方法会立即收到我们前面发送的粘性事件。(这里只会收到 “Send sticky event new” 这个事件)

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

#### 手动获取或者移除 sticky Event

当订阅者注册时，最后一个粘性事件会被自动分发。但是有时手动的去检查粘性事件更加方便，手动去移除一个粘性事件以避免它被再次分发也是很有必要的。例如：

```
MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
// Better check that an event was actually posted before
if(stickyEvent != null) {
    // "Consume" the sticky event
    EventBus.getDefault().removeStickyEvent(stickyEvent);
    // Now do something with it
}
```

方法 `removeStickyEvent` 被重载：当你传入类时，它将返回先前持有的粘性事件。 使用这个特性 ，我们可以改进前面的示例

```
MessageEvent stickyEvent = EventBus.getDefault().removeStickyEvent(MessageEvent.class);
// Better check that an event was actually posted before
if(stickyEvent != null) {
    // 是否需要对这个事件做进一步处理
}
```

## 优先级和事件取消

EventBus 的大多数使用场景都不会需要优先级或者事件取消，它们被用来处理一些特殊的场景。例如：如果 app 在前台一个事件可能会触发一些 UI 逻辑，但是针对app 对用户是否可见可能有不同的响应。

#### 订阅者优先级

在注册期间通过 priority 属性可以改变订阅者的优先级。

```
@Subscribe(priority = 1);
public void onEvent(MessageEvent event) {
    ...
}
```
在同一种 ThreadMode 中，高优先级的订阅者将先收到事件，默认的优先级是 0

> 注意：优先级不会影响拥有不同 ThreadMode 的订阅者。


#### 取消事件分发

通过在订阅者的处理方法中调用 `cancelEventDelivery(Object event)` 来取消一个事件分发，后续的订阅者将不会再收到这个事件。

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

一般在高优先级的订阅方法中取消事件，取消方法只在 ThreadMode.POSTING 中有效。（默认就是 POSTING）


## Subscriber Index 

subscriber index 是 EventBus 3 的新特性。**它是一个可选的加速订阅者初始化注册的方案**。

**可以使用 EventBus 注解处理器在构建期间创建订阅者索引，这个不是必须的，但是建议在 Android 上使用以获得最佳的性能表现（避免了在运行时使用反射区查找订阅者方法）**


#### 创建索引的前提条件

- 只有被 @Subscriber 注解修饰的方法并且订阅者的类和事件类需要是 public 的。
- 由于 Java 注解处理器的限制，@Subscriber 注解在匿名类中不能被识别。

> 当 EventBus 无法使用索引时，它会在运行时自动使用反射，不过这样会慢一点。

#### 使用 annotationProcessor 创建索引

如果你的 Android gradle plugin 版本是 2.2.0 以下，使用 android-apt 配置。

- 使用 `annotationProcessor` 属性添加 EventBus annotation processor 到我们的项目。

```
dependencies {
    implementation 'org.greenrobot:eventbus:3.1.1'
    annotationProcessor 'org.greenrobot:eventbus-annotation-processor:3.1.1'
}
```

- 设置参数 `eventBusIndex`来指定你想生成索引的类的全限定类名（注意这个类名不能和代码中的任何一个类相同，不然会报类名重复的错误），如下：

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
然后构建程序，会在 `build/generated/source/apt/` 下面生成上面指定的这个类,看一下这个类,可以看到在构建时帮我们统计了所有的订阅者以及对应的方法信息，这样我们在使用的时候就不需要再使用反射去查找对应的方法了。

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

#### 使用 kapt

如果是在 Kotlin 中使用 EventBus，需要使用 `kapt` 来替代 `annotationProcessor`

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

#### 使用 android-apt

如果上面的方法对你都不管用，可以使用 android-apt gradle plugin, 在你的 gradle 脚本中添加如下代码

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

#### 如何使用索引

在成功构建了项目之后，`eventBusIndex` 参数指定的类会被自动生成，接下来我们只需要把这个类传递给我们的 EventBus，使用方法如下：

```
EventBus eventBus = EventBus.builder().addIndex(new MyEventBusIndex()).build();
```

或者，如果你想要使用默认的实例，则这样使用,在 Application 的 onCreate 方法中配置默认的 EventBus 实例

```
EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
```

#### 对 Libraries 添加索引

可以用同样的方法给你的 library 添加索引，这种方式你就有多个索引类，使用方法如下：

```
EventBus eventBus = EventBus.builder()
    .addIndex(new MyEventBusAppIndex())
    .addIndex(new MyEventBusLibIndex()).build();
```

## ProGuard(混淆)

ProGuard 混淆方法的名字并且会移除一些没有被调用的方法，因为 Sbbscriber method 不是直接被调用的，但是 ProGuard 会认为他们是没有被用到的。所以我们需要告诉 Proguard 保留这些方法。

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

> 注意：不论是否使用订阅者索引，都需要添加此配置。

## AsyncExecutro （异步执行）

异步执行类似线程池，但是有异常处理机制。AsyncExecutor 会包装所有的异常到一个事件中，然后自动发送这个事件。

> AsyncExecutor 是一个非核心的实用类，它在后台线程中保存一些错误处理的代码，但是它不是 EventBus 的核心类。通常，使用 `AsyncExecutor.create()` 方法创建一个 Application 范围内的实例。

上面可能有点不好懂，直接来看看怎么使用

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
如果 `RunnableEx` 匿名实现类中抛出了一个异常，这个异常会被捕捉并且包装成 `ThrowableFailureEvent` 事件，EventBus 会把这个事件分发出去。

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

通过 `AsyncExecutor.buider()` 返回的实例来自定义 EventBus 实例、线程池、错误事件处理类。

另一个自定义选项是执行范围，它为失败事件提供上下文信息。 例如，失败事件可能仅与特定的Activity实例或类相关。

如果您的自定义失败事件类实现HasExecutionScope接口，AsyncExecutor将自动设置执行范围。 像这样，您的订阅者可以查询失败事件的执行范围并根据它做出反应。