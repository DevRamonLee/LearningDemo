- [�� Android �����ߵ� RxJava ���-������](https://gank.io/post/560e15be2dca930e00da1083)������ת���Դ��ģ�
- [����RxJava���Ѻõ�����](https://juejin.im/post/580103f20e3dd90057fc3e6d)
- [����RxJava���Ѻõ����£����ף�](https://juejin.im/post/5818777f67f356005871ef2c)
- [RxJava2 ֻ����һƪ���¾͹���](https://juejin.im/post/5b17560e6fb9a01e2862246f)


## ���
RxJava (Reactive Extensions for the JVM)��RxJava �� GitHub ��ҳ�ϵ����ҽ����� 

```
"a library for composing asynchronous and event-based programs using observable sequences for the Java VM"��һ���� Java VM ��ʹ�ÿɹ۲������������첽�ġ������¼��ĳ���Ŀ⣩
```

�ܽ��˵����һ������ **�첽** �Ŀ⡣


��������

```
implementation 'io.reactivex:rxjava:1.1.5'
implementation 'io.reactivex:rxandroid:1.1.0'
```

> [RxJava](https://github.com/ReactiveX/RxJava) & [RxAndroid](https://github.com/ReactiveX/RxAndroid)

**Ϊʲôʹ�� RxJava �����첽**

Rxjava ��֤���첽������**�����**�������ڴ����첽��ʱ��һ�㶼��Ҫ����ص��ӿڣ������߼�Խ��Խ���ӣ�����ص�Ƕ�׻�ʹ����Խ��Խ�ѿ������롣

#### �ԱȾ���

��������һ���Զ������ͼ `imageCollectorView` ��������������ʾ����ͼƬ������ʹ�� `addImage(Bitmap)` ����������������ʾ��ͼƬ��������Ҫ����һ��������Ŀ¼���� `File[] folders` ��ÿ��Ŀ¼�µ� png ͼƬ�����س�������ʾ�� `imageCollectorView` �С�

> ע�⣺���ڶ�ȡͼƬ����һ���̽�Ϊ��ʱ����Ҫ���ں�ִ̨�У���ͼƬ����ʾ������� UI �߳�ִ�С�

```
new Thread() {
    @Override
    public void run() {
        super.run();
        for (File folder : folders) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.getName().endsWith(".png")) {
                    final Bitmap bitmap = getBitmapFromFile(file);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageCollectorView.addImage(bitmap);
                        }
                    });
                }
            }
        }
    }
}.start();
```

�����ʹ�� RxJava ��ʵ�ַ�ʽ�������ģ�
```
Observable.from(folders)
    .flatMap(new Func1<File, Observable<File>>() {
        @Override
        public Observable<File> call(File file) {
            return Observable.from(file.listFiles());
        }
    })
    .filter(new Func1<File, Boolean>() {
        @Override
        public Boolean call(File file) {
            return file.getName().endsWith(".png");
        }
    })
    .map(new Func1<File, Bitmap>() {
        @Override
        public Bitmap call(File file) {
            return getBitmapFromFile(file);
        }
    })
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Action1<Bitmap>() {
        @Override
        public void call(Bitmap bitmap) {
            imageCollectorView.addImage(bitmap);
        }
    });
```

> RxJava ��ʵ�֣���һ�����ϵ��µ���ʽ���ã�û���κ�Ƕ�ף�����**�߼����**���Ǿ������Ƶġ���������ʱ���������Ƹ������ԡ����ǻ�����ʹ�� [Retrolambda](https://github.com/evant/gradle-retrolambda) ������Ĵ�����м�д


## API ���ܺ�ԭ�����

#### �����չ�Ĺ۲���ģʽ
**RxJava ���첽ʵ�֣���ͨ��һ����չ�Ĺ۲���ģʽ��ʵ�ֵġ�**

�۲���ģʽ����������ǣ�**A ���󣨹۲��ߣ��� B ���󣨱��۲��ߣ���ĳ�ֱ仯�߶����У�A ��Ҫ�� B �仯��һ˲��������Ӧ**��
 

Android ������һ���Ƚϵ��͵������ǵ�������� OnClickListener �������� **OnClickListener** ��˵�� **View �Ǳ��۲���**�� **OnClickListener �ǹ۲���**������ͨ�� `setOnClickListener()` ������ɶ��Ĺ�ϵ������֮���û������ť��˲�䣬Android Framework �ͻὫ����¼����͸��Ѿ�ע��� `OnClickListener` ��


![RxJava_001](./assets/RxJava_001.png)

��ͼ��ʾ��ͨ�� `setOnClickListener()` ������Button ���� `OnClickListener` �����á����û����ʱ��Button �Զ����� OnClickListener �� `onClick()` ���������⣬���������ͼ�еĸ���������


��Button -> **���۲���**��OnClickListener -> **�۲���**��setOnClickListener() -> **����**��onClick() -> **�¼�**��������ר�õĹ۲���ģʽ������ֻ���ڼ����ؼ������ת�����ͨ�õĹ۲���ģʽ������ͼ��

![RxJava_002](./assets/RxJava_002.png)


**RxJava �Ĺ۲���ģʽ**��RxJava ���ĸ��������

- **Observable (�ɹ۲��ߣ������۲���)**
- **Observer (�۲���)**
- **subscribe (����)**
- **�¼�**��

Observable �� Observer ͨ�� `subscribe()` ����ʵ�ֶ��Ĺ�ϵ���Ӷ� Observable ��������Ҫ��ʱ�򷢳��¼���֪ͨ Observer��

�봫ͳ�۲���ģʽ��ͬ�� RxJava ���¼��ص�����������ͨ�¼� `onNext()` ���൱�� `onClick()`��֮�⣬������������������¼���`onCompleted()` �� `onError()`��

- **onCompleted()**: �¼�������ᡣRxJava ������ÿ���¼�����������������ǿ���һ�����С�RxJava �涨�������������µ� onNext() ����ʱ����Ҫ���� onCompleted() ������Ϊ��־��
- **onError()**: �¼������쳣�����¼���������г��쳣ʱ��onError() �ᱻ������ͬʱ�����Զ���ֹ�������������¼�������


> ��һ����ȷ���е��¼�������, onCompleted() �� onError() �ĵ���ֻ��һ�����������¼������е����һ����

RxJava �۲���ģʽ�������£�

![RxJava_003](./assets/RxJava_003.png)

#### ����ʵ��

�������ϸ��RxJava �Ļ���ʵ����Ҫ������

**1������ Observer**

Observer ���۲��ߣ��������¼�������ʱ������������Ϊ�� RxJava �е� Observer �ӿڵ�ʵ�ַ�ʽ��
```
Observer<String> observer = new Observer<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};
```

���� Observer �ӿ�֮�⣬RxJava ��������һ��ʵ���� Observer �ĳ����ࣺSubscriber�� **Subscriber �� Observer �ӿڽ�����һЩ��չ**�������ǵĻ���ʹ�÷�ʽ����ȫһ���ģ�


```
Subscriber<String> subscriber = new Subscriber<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};
```

��������ʹ�÷�ʽһ����ʵ���ϣ�**�� RxJava �� subscribe �����У�Observer Ҳ���ǻ��ȱ�ת����һ�� Subscriber ��ʹ��**�����������ֻ��ʹ�û������ܣ�ѡ�� Observer �� Subscriber ����ȫһ���ġ����ǵ��������ʹ������˵��Ҫ�����㣺

- **onStart()**: ���� Subscriber ���ӵķ����������� Subscribe �տ�ʼ�����¼���δ����֮ǰ�����ã�����������һЩ׼���������������ݵ���������á�����һ����ѡ������Ĭ�����������ʵ��Ϊ�ա���Ҫע����ǣ������׼���������߳���Ҫ�����絯��һ����ʾ���ȵĶԻ�������������߳�ִ�У��� onStart() �Ͳ������ˣ���Ϊ�������� subscribe ���������̱߳����ã�������ָ���̡߳�Ҫ��ָ�����߳�����׼������������ʹ�� doOnSubscribe() ��������������ں�������п�����


- **unsubscribe()**: ���� Subscriber ��ʵ�ֵ���һ���ӿ� `Subscription` �ķ���������ȡ�����ġ���������������ú�Subscriber �����ٽ����¼���һ���������������ǰ������ʹ�� `isUnsubscribed()` ���ж�һ��״̬�� `unsubscribe()` �����������Ҫ����Ϊ�� `subscribe()` ֮�� Observable ����� Subscriber �����ã��������������ܼ�ʱ���ͷţ������ڴ�й¶�ķ��ա�������ñ���һ��ԭ��**Ҫ�ڲ���ʹ�õ�ʱ�򾡿��ں��ʵĵط������� onPause() onStop() �ȷ����У����� unsubscribe() ��������ù�ϵ���Ա����ڴ�й¶�ķ���**��

**2������ Obervable**

Observable �����۲��ߣ�������ʲôʱ�򴥷��¼��Լ������������¼��� RxJava ʹ�� `create()` ����������һ�� Observable ����Ϊ�������¼���������

```
Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
    @Override
    public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("Hello");
        subscriber.onNext("Hi");
        subscriber.onNext("Aloha");
        subscriber.onCompleted();
    }
});
```

���Կ��������ﴫ����һ�� OnSubscribe ������Ϊ������OnSubscribe �ᱻ�洢�ڷ��ص� Observable �����У�**���������൱��һ���ƻ����� Observable �����ĵ�ʱ��OnSubscribe �� `call()` �������Զ������ã��¼����оͻ������趨���δ���**����������Ĵ��룬���ǹ۲��� Subscriber ���ᱻ�������� `onNext()` ��һ�� `onCompleted()`�����������ɱ��۲��ߵ����˹۲��ߵĻص���������ʵ�����ɱ��۲�����۲��ߵ��¼����ݣ����۲���ģʽ��

> ������Ӻܼ򵥣��¼����������ַ�����������һЩ���ӵĶ����¼����������Ѿ������˵ģ��������еĹ۲���ģʽһ���Ǵ�ȷ���ģ�������������Ľ�������󷵻�֮ǰ��δ֪�ģ��������¼���һ˲�䱻ȫ�����ͳ�ȥ�������Ǽ���һЩȷ����ȷ����ʱ�������߾���ĳ�ִ������������ġ�(�������ֻ��Ϊ��˵��ԭ��)


`create()` ������ RxJava ������Ĵ����¼����еķ������������������ RxJava ���ṩ��һЩ����������ݴ����¼����У����磺

- `just(T...)`: ������Ĳ������η��ͳ�����

```
Observable observable = Observable.just("Hello", "Hi", "Aloha");
// �������ε��ã�
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();
```
- `from(T[]) / from(Iterable<? extends T>)` : ������������ Iterable ��ֳɾ����������η��ͳ�����

```
String[] words = {"Hello", "Hi", "Aloha"};
Observable observable = Observable.from(words);
// �������ε��ã�
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();
```

> ���� `just(T...)` �����Ӻ� `from(T[])` �����ӣ�����֮ǰ�� `create(OnSubscribe)` �������ǵȼ۵ġ�

**3��Subcribe(����)**

������ Observable �� Observer ֮������ `subscribe()` ���������������������������ӾͿ��Թ����ˡ�������ʽ�ܼ򵥣�

```
observable.subscribe(observer);
// ���ߣ�
observable.subscribe(subscriber);
```
> ���˿��ܻ�ע�⵽�� `subscribe()` ��������е�֣����������ǡ�observalbe ������ observer / subscriber�������ǡ�observer / subscriber ������ observalbe�����⿴����������־�����˶��ߡ�һ���ߵ��˶����ϵ�������˶������е��Ť����������� API ��Ƴ� observer.subscribe(observable) / subscriber.subscribe(observable) ����Ȼ���ӷ���˼ά�߼�������**��ʽ API** ����ƾ����Ӱ���ˣ��Ƚ����������ǵò���ʧ�ġ�

`Observable.subscribe(Subscriber)` ���ڲ�ʵ���������ģ������Ĵ��룩��

```
// ȥ�������ܡ������ԡ���չ���йصĴ����ĺ��Ĵ���
public Subscription subscribe(Subscriber subscriber) {
    subscriber.onStart();
    onSubscribe.call(subscriber);
    return subscriber;
}
```

���Կ�����`subscribe()` ���� 3 ���£�

- ���� `Subscriber.onStart()`���������ǰ���Ѿ����ܹ�����һ����ѡ��׼��������
- ���� Observable �е� `onSubscribe.call(Subscriber)`(ǰ���ᵽ���¼��ƻ���) ��������¼����͵��߼���ʼ���С�����Ҳ���Կ������� RxJava �У� Observable �������ڴ�����ʱ���������ʼ�����¼����������������ĵ�ʱ�򣬼��� `subscribe()` ����ִ�е�ʱ��
- ������� Subscriber ��Ϊ Subscription(Subscriber ʵ�ֵ���һ���ӿ�) ���ء�����Ϊ�˷��� `unsubscribe()`.

���������ж����Ĺ�ϵ����ͼ��

![RxJava_004](./assets/RxJava_004.png)

���� `subscribe(Observer)` �� `subscribe(Subscriber)` ��`subscribe()` ��֧�ֲ���������Ļص���RxJava ���Զ����ݶ��崴���� Subscriber ����ʽ���£�


```
Action1<String> onNextAction = new Action1<String>() {
    // onNext()
    @Override
    public void call(String s) {
        Log.d(tag, s);
    }
};
Action1<Throwable> onErrorAction = new Action1<Throwable>() {
    // onError()
    @Override
    public void call(Throwable throwable) {
        // Error handling
    }
};
Action0 onCompletedAction = new Action0() {
    // onCompleted()
    @Override
    public void call() {
        Log.d(tag, "completed");
    }
};


// �Զ����� Subscriber ����ʹ�� onNextAction ������ onNext()
observable.subscribe(onNextAction);


// �Զ����� Subscriber ����ʹ�� onNextAction �� onErrorAction ������ onNext() �� onError()
observable.subscribe(onNextAction, onErrorAction);


// �Զ����� Subscriber ����ʹ�� onNextAction�� onErrorAction �� onCompletedAction ������ onNext()�� onError() �� onCompleted()
observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
```

- Action0 �� RxJava ��һ���ӿڣ���ֻ��һ������ `call()`������������޲��޷���ֵ�ģ����� onCompleted() ����Ҳ���޲��޷���ֵ�ģ���� Action0 ���Ա�����һ����װ���󣬽� `onCompleted()` �����ݴ���������Լ���Ϊһ���������� `subscribe()` ��ʵ�ֲ���������Ļص���������ʵҲ���Կ����� `onCompleted()` ������Ϊ���������� `subscribe()`���൱������ĳЩ�����е�**�հ�**��


- Action1 Ҳ��һ���ӿڣ���ͬ��ֻ��һ������ `call(T param)`���������Ҳ�޷���ֵ������һ���������� Action0 ͬ������ `onNext(T obj)` �� `onError(Throwable error)` Ҳ�ǵ������޷���ֵ�ģ���� Action1 ���Խ� `onNext(obj)` �� `onError(error)` ����������� `subscribe()` ��ʵ�ֲ���������Ļص���

>  Action0 �� Action1 �� API ��ʹ����㷺��RxJava ͬʱ�ṩ�˶�� ActionX ��ʽ�Ľӿ� (���� Action2, Action3) �ģ����ǿ��Ա����԰�װ��ͬ���޷���ֵ�ķ�������ʵ��װ�����ڷ������������ת�������Կ�[��ƪ����](https://www.cnblogs.com/huolongluo/p/6585809.html)


**4������ʾ��**

��1��**��ӡ�ַ�������**�� ���ַ������� names �е������ַ������δ�ӡ������


```
// ��1����ӡ����
String[] names = {"·��", "��¡", "�ǰ�"};
// from �ǿ�ݴ����¼�����
Observable.from(names)
        .subscribe(new Action1<String>() {      // Action1 �ǲ���������ص�
            @Override
            public void call(String s) {
                Log.i("RamonLee", s);
            }
        });
```

���н����

```
RamonLee: ·��
RamonLee: ��¡
RamonLee: �ǰ�
```

��2��**�� id ȡ��ͼƬ����ʾ**

```
final int drawableRes = R.drawable.ic_launcher_background;
Observable.create(new Observable.OnSubscribe<Drawable>() {
    @Override
    public void call(Subscriber<? super Drawable> subscriber) {     // ����������� subscribe�������ú����
        Drawable drawable = getTheme().getDrawable(drawableRes);
        subscriber.onNext(drawable);
        subscriber.onCompleted();
    }
}).subscribe(new Observer<Drawable>() {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Drawable drawable) {
        showImage.setImageDrawable(drawable);
    }
});
```

> �� RxJava ��Ĭ�Ϲ����У��¼��ķ��������Ѷ�����ͬһ���̵߳ġ�Ҳ����˵�����ֻ������ķ�����ʵ�ֳ�����ֻ��һ��ͬ���Ĺ۲���ģʽ���۲���ģʽ�����Ŀ�ľ��ǡ���̨����ǰ̨�ص������첽���ƣ�����첽���� RxJava ��������Ҫ�ġ���Ҫʵ���첽������Ҫ�õ� RxJava ����һ����� **Scheduler** ��

#### �߳̿��� Scheduler

�ڲ�ָ���̵߳�����£� RxJava ��ѭ�����̲߳����ԭ�򣬼���**���ĸ��̵߳��� subscribe()�������ĸ��߳������¼������ĸ��߳������¼��������ĸ��߳������¼�**�������Ҫ�л��̣߳�����Ҫ�õ� Scheduler ������������

Scheduler �߳̿�������RxJava ͨ������ָ��ÿһ�δ���Ӧ��������ʲô�����̡߳�RxJava �Ѿ������˼��� Scheduler
- **Schedulers.immediate()**: ֱ���ڵ�ǰ�߳����У��൱�ڲ�ָ���̡߳�����Ĭ�ϵ� Scheduler��


- **Schedulers.newThread()**: �����������̣߳��������߳�ִ�в�����


- **Schedulers.io()**: I/O ��������д�ļ�����д���ݿ⡢������Ϣ�����ȣ���ʹ�õ� Scheduler����Ϊģʽ�� `newThread()` ��࣬�������� io() ���ڲ�ʵ��������һ�����������޵��̳߳أ��������ÿ��е��̣߳���˶�������� `io()` �� `newThread()` ����Ч�ʡ���Ҫ�Ѽ��㹤������ io() �У����Ա��ⴴ������Ҫ���̡߳�


- **Schedulers.computation()**: ������ʹ�õ� Scheduler���������ָ���� CPU �ܼ��ͼ��㣬�����ᱻ I/O �Ȳ����������ܵĲ���������ͼ�εļ��㡣��� Scheduler ʹ�õĹ̶����̳߳أ���СΪ CPU ��������Ҫ�� I/O �������� `computation()` �У����� I/O �����ĵȴ�ʱ����˷� CPU��



- **AndroidSchedulers.mainThread()**����ָ���Ĳ������� Android ���߳����С�



�����⼸�� Scheduler ���Ϳ���ʹ�� `subscribeOn()` �� `observeOn()` �������������߳̽��п����ˡ� 

- **subscribeOn()**: ָ�� `subscribe()` ���������̣߳��� `Observable.OnSubscribe` ������ʱ�������̡߳����߽���**�¼��������߳�**�� 



- **observeOn()**: ָ�� Subscriber �������ڵ��̡߳����߽����¼����ѵ��߳�,Ҳ���ǻص�ִ�е��̡߳�



```
Observable.just(1, 2, 3, 4)
    .subscribeOn(Schedulers.io())   // ָ�� subscribe() ������ IO �̣߳�Ҳ���ǲ����¼��Ĳ���
    .observeOn(AndroidSchedulers.mainThread()) // ָ�� Subscriber �Ļص����������߳�
    .subscribe(new Action1<Integer>() {
        @Override
        public void call(Integer number) {
            Log.d("RamonLee", "number:" + number);
        }
    });
```

> ������δ����У����� `subscribeOn(Schedulers.io())` ��ָ�������������¼������� 1��2��3��4 ������ IO �̷߳����������� `observeOn(AndroidScheculers.mainThread())` ��ָ������� subscriber ���ֵĴ�ӡ�����������߳� ����ʵ�ϣ������� `subscribe()` ֮ǰд������ `subscribeOn(Scheduler.io())`��`observeOn(AndroidSchedulers.mainThread())` ��ʹ�÷�ʽ�ǳ��������������ڶ�����**��̨�߳�ȡ���ݣ����߳���ʾ**�ĳ�����ԡ�

�����޸�ǰ����� id ȡͼƬ������

```
final int drawableRes = R.drawable.ic_launcher_background;
Observable.create(new Observable.OnSubscribe<Drawable>() {
    @Override
    public void call(Subscriber<? super Drawable> subscriber) {     // ����������� subscribe�������ú����
        Drawable drawable = getTheme().getDrawable(drawableRes);
        subscriber.onNext(drawable);
        subscriber.onCompleted();
    }
}).subscribeOn(Schedulers.io())     // �� io �̶߳�ȡͼƬ
  .observeOn(AndroidSchedulers.mainThread())  // �����߳���ʾ
  .subscribe(new Action1<Drawable>() {
      @Override
      public void call(Drawable drawable) {
          showImage.setImageDrawable(drawable);
      }
});
```

#### �任

���潲�� Scheduler ��ԭ���ǻ��ڱ任�ģ��������������任��

**�任**��RxJava �ṩ�˶��¼����н��б任��֧�֣��������ĺ��Ĺ���֮һ��**��ν�任�����ǽ��¼������еĶ�����������н��мӹ�����ת���ɲ�ͬ���¼����¼�����**��

���ȿ�һ�� `map()` �����ӣ�

```
Observable.just("images/logo.png") // �������� String
    .map(new Func1<String, Bitmap>() {
        @Override
        public Bitmap call(String filePath) { // �������� String
            return getBitmapFromPath(filePath); // �������� Bitmap
        }
    })
    .subscribe(new Action1<Bitmap>() {
        @Override
        public void call(Bitmap bitmap) { // �������� Bitmap
            showBitmap(bitmap);
        }
    });
```

> Fun1 �� Rxjava ��һ���ӿڣ����ڰ�װ����һ�������ķ����� Func1 �� Action ���������ڣ� Func1 ��װ�����з���ֵ�ķ���

���Կ�����`map()` �����������е� String ����ת����һ�� Bitmap ����󷵻أ����ھ��� `map()` �������¼��Ĳ�������Ҳ�� String תΪ�� Bitmap������ֱ�ӱ任���󲢷��صģ��������Ҳ���������ı任������ RxJava �ı任Զ��ֹ��������������������¼����󣬻�������������¼����У���ʹ�� RxJava ��÷ǳ����оټ������õı任��

- **map()**: �¼������ֱ�ӱ任�����幦�������Ѿ����ܹ�(String ת��Ϊ��Bitmap)������ RxJava ��õı任�� map() ��ʾ��ͼ��

![RxJava_005](./assets/RxJava_005.png)

- **flatMap()**: ����һ�������õ�**�ǳ������**�ı任�� ���ȼ�����ôһ������**������һ����ѧ��������������Ҫ��ӡ��һ��ѧ��������**��ʵ�ַ�ʽ�ܼ򵥣�


```
Student[] students = ...;
Subscriber<String> subscriber = new Subscriber<String>() {
    @Override
    public void onNext(String name) {
        Log.d(tag, name);
    }
    ...
};
Observable.from(students)
    .map(new Func1<Student, String>() {
        @Override
        public String call(Student student) {
            return student.getName();
        }
    })
    .subscribe(subscriber);
```

�ܼ򵥣���ô�ټ��裺**���Ҫ��ӡ��ÿ��ѧ������Ҫ�޵����пγ̵����ƣ��������ڣ�ÿ��ѧ��ֻ��һ�����֣���ȴ�ж���γ�** ���ȿ�������ʵ�֣�


```
Student[] students = ...;
Subscriber<Student> subscriber = new Subscriber<Student>() {
    @Override
    public void onNext(Student student) {
        List<Course> courses = student.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            Log.d(tag, course.getName());
        }
    }
    ...
};
Observable.from(students)
    .subscribe(subscriber);
```


��Ȼ�ܼ򵥡���ô����Ҳ����� Subscriber ��ʹ�� for ѭ��������ϣ�� Subscriber ��ֱ�Ӵ��뵥���� Course �����أ��� `map()` ��Ȼ�ǲ��еģ���Ϊ `map()` ��һ��һ��ת�����������ڵ�Ҫ����һ�Զ��ת��������ô���ܰ�һ�� Student ת���ɶ�� Course �أ����ʱ�򣬾���Ҫ�� `flatMap()` �ˣ�


```
Student[] students = ...;
Subscriber<Course> subscriber = new Subscriber<Course>() {
    @Override
    public void onNext(Course course) {
        Log.d(tag, course.getName());
    }
    ...
};
Observable.from(students)
    .flatMap(new Func1<Student, Observable<Course>>() {
        @Override
        public Observable<Course> call(Student student) {
            return Observable.from(student.getCourses());
        }
    })
    .subscribe(subscriber);
```

> ������Ĵ�����Կ����� `flatMap()` �� `map()` ��һ����ͬ�㣺**��Ҳ�ǰѴ���Ĳ���ת��֮�󷵻���һ�����󡣵���Ҫע�⣬�� `map()` ��ͬ���ǣ� `flatMap()` �з��ص��Ǹ� Observable ���󣬲������ Observable ���󲢲��Ǳ�ֱ�ӷ��͵��� Subscriber �Ļص�������(��仰����Ҫ)**�� 


flatMap() ��ԭ���������ģ�
- 1.ʹ�ô�����¼����󴴽�һ�� Observable ����
- 2.����������� Observable, ���ǽ��������������ʼ�����¼���
- 3.ÿһ������������ Observable ���͵��¼�����������ͬһ�� Observable ������� Observable ������Щ�¼�ͳһ���� Subscriber �Ļص�������

> ���������裬���¼������������ͨ��һ���´����� Observable ����ʼ�Ķ�����ƽ��֮��ͨ��ͳһ·���ַ�����ȥ�����������ƽ������ `flatMap()` ��ν�� flat��

**flatMap()ʾ��ͼ**

![RxJava_006](./assets/RxJava_006.png)


**��չ**�����ڿ�����Ƕ�׵� Observable ������첽���룬 `flatMap()` Ҳ������Ƕ�׵��첽����������Ƕ�׵���������ʾ�����루Retrofit + RxJava����


```
networkClient.token() // ���� Observable<String>���ڶ���ʱ���� token��������Ӧ���� token
    .flatMap(new Func1<String, Observable<Messages>>() {
        @Override
        public Observable<Messages> call(String token) {
            // ���� Observable<Messages>���ڶ���ʱ������Ϣ�б�������Ӧ�������󵽵���Ϣ�б�
            return networkClient.messages();
        }
    })
    .subscribe(new Action1<Messages>() {
        @Override
        public void call(Messages messages) {
            // ������ʾ��Ϣ�б�
            showMessages(messages);
        }
    });
```
��ͳ��Ƕ��������Ҫʹ��Ƕ�׵� Callback ��ʵ�֡���ͨ�� flatMap() �����԰�Ƕ�׵�����д��һ�����У��Ӷ����ֳ����߼���������

- **throttleFirst()**: ��ÿ���¼��������һ��ʱ�����ڶ����µ��¼���������ȥ��������(���ô��ظ�ҳ��)�����簴ť�ĵ������

```
RxView.clickEvents(button) // RxBinding ���룬����������н���
    .throttleFirst(500, TimeUnit.MILLISECONDS) // ���÷������Ϊ 500ms
    .subscribe(subscriber); 
```

> ����һЩ�������¼����еı任���Բ鿴 [�ٷ��ĵ�](https://github.com/ReactiveX/RxJava)

#### �任��ԭ�� lift()

> ע�⣺ ( �ɰ汾ԭ���°��Ѳ�ʹ��)������ѧϰ�����

�°汾������ԭ�����Կ�[��ƪ����](https://juejin.im/post/5818777f67f356005871ef2c)

������Щ�任��ʵ�ʶ�������¼����еĴ�����ٷ��ͣ��� Rxjava �ڲ��������ǻ���ͬһ���任���� `lift(Operator)` ʵ�ֵ�

```
public <R> Observable<R> lift(Operator<? extends R, ? super T> operator) {
    return Observable.create(new OnSubscribe<R>() {
        @Override
        public void call(Subscriber subscriber) {
            Subscriber newSubscriber = operator.call(subscriber);   // ����һ���µĶ�����
            newSubscriber.onStart();
            onSubscribe.call(newSubscriber);    // ԭʼ onSubscribe ���¶����߹���
        }
    });
}
```

�������δ���֮ǰ�����������ع��� `subscribe()` ��Դ��

```
// ȥ�������ܡ������ԡ���չ���йصĴ����ĺ��Ĵ���
public Subscription subscribe(Subscriber subscriber) {
    subscriber.onStart();
    onSubscribe.call(subscriber);   // call ��������¼����� onNext/onComplete
    return subscriber;
}
```

������������Ĵ���

- `subscribe()` ����仰�� `onSubscribe` ָ���� `Observable` �е� `onSubscribe` �������û�����⣬���� `lift()` ֮�������͸����˵㡣


- ������ `lift()` ʱ
    - `lift()` ������һ�� Observable������֮ǰ�� Observable�� �����Ѿ������� Observable �ˡ�
    - ͬ���ģ��� Observable �е� OnSubscribe ����֮ǰ�� OnSubscribe���Ѿ��������� OnSubscribe��
    - ���û����þ��� `lift()` ��� Observable �� `subscribe()` ��ʱ��ʹ�õ��� `lift()` �����ص��µ� Observable���������������� `onSubscribe.call(subscriber)`��Ҳ���õ��� Observable �е��� OnSubscribe������ `lift()` �����ɵ��Ǹ� OnSubscribe
    - ����� OnSubscribe �� `call()` �����е� onSubscribe ������ָ��ԭʼ Observable �е�ԭʼ OnSubscribe ������� `call()` ������� OnSubscribe ���� `operator.call(subscriber)` ������һ���µ� Subscriber��Operator ���������ͨ���Լ��� call() �������� Subscriber ��ԭʼ Subscriber ���й������������Լ��ġ��任��������ʵ�ֱ任����Ȼ����������� Subscriber ��ԭʼ Observable ���ж��ġ�


> `lift()` ���̣��е���һ�ִ�����ƣ�ͨ���¼����غʹ���ʵ���¼����еı任��

![RxJava_007](./assets/RxJava_007.png)

���� һ�����¼��е� Integer ����ת���� String(������Զ���ʵ���� `operator.call(subscriber)` �������Լ�ʵ��)

```
observable.lift(new Observable.Operator<String, Integer>() {
    @Override
    public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
        // ���¼������е� Integer ����ת��Ϊ String ����
        return new Subscriber<Integer>() {
            @Override
            public void onNext(Integer integer) {
                subscriber.onNext("" + integer);
            }

            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }
        };
    }
});
```


#### Compose �� Observable ������任

���� `lift()` ֮�⣬ `Observable` ����һ���任�������� `compose(Transformer)`������ `lift()` ���������ڣ� `lift()` ������¼�����¼����еģ��� `compose()` ����� Observable ������б任���ٸ����ӣ������ڳ������ж�� Observable ���������Ƕ���ҪӦ��һ����ͬ�� `lift()` �任���������ôд��

```
observable1
    .lift1()
    .lift2()
    .lift3()
    .lift4()
    .subscribe(subscriber1);
observable2
    .lift1()
    .lift2()
    .lift3()
    .lift4()
    .subscribe(subscriber2);
observable3
    .lift1()
    .lift2()
    .lift3()
    .lift4()
    .subscribe(subscriber3);
observable4
    .lift1()
    .lift2()
    .lift3()
    .lift4()
    .subscribe(subscriber1);
```

���������̫ low������������˺�����װ

```
private Observable liftAll(Observable observable) {
    return observable
        .lift1()
        .lift2()
        .lift3()
        .lift4();
}
...
liftAll(observable1).subscribe(subscriber1);
liftAll(observable2).subscribe(subscriber2);
liftAll(observable3).subscribe(subscriber3);
liftAll(observable4).subscribe(subscriber4);
```

�ɶ��ԡ���ά���Զ�����ˡ����� Observable ��һ�����������������ַ�ʽ���� Observale ��������ƺ�������������ô�����ơ���ô�죿���ʱ�򣬾�Ӧ���� `compose()` ������ˣ�

```
public class LiftAllTransformer implements Observable.Transformer<Integer, String> {
    @Override
    public Observable<String> call(Observable<Integer> observable) {
        return observable
            .lift1()
            .lift2()
            .lift3()
            .lift4();
    }
}
...
Transformer liftAll = new LiftAllTransformer();
observable1.compose(liftAll).subscribe(subscriber1);
observable2.compose(liftAll).subscribe(subscriber2);
observable3.compose(liftAll).subscribe(subscriber3);
observable4.compose(liftAll).subscribe(subscriber4);
```

���� Observalble ����Ͳ��ر����ڷ�������


#### �߳̿���

`observeOn()` ָ������ Subscriber ���̣߳������ Subscriber ��һ���� `subscribe()` �����е� Subscriber ������ `observeOn()` ִ��ʱ�ĵ�ǰ Observable ����Ӧ�� Subscriber ,����������Ҫ���ǿ��Զ���л��̵߳�������ô���Զ�ε��� `oberveOn()`

```
Observable.just(1, 2, 3, 4) // IO �̣߳��� subscribeOn() ָ��
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.newThread())
    .map(mapOperator) // ���̣߳��� observeOn() ָ��
    .observeOn(Schedulers.io())
    .map(mapOperator2) // IO �̣߳��� observeOn() ָ��
    .observeOn(AndroidSchedulers.mainThread) 
    .subscribe(subscriber);  // Android ���̣߳��� observeOn() ָ��
```

> ��ͬ�� `observeOn()` �� `subscribeOn()` ��λ�÷������ﶼ���ԣ�������ֻ�ܵ���һ�εġ�Ϊʲô�����潲


#### Scheduler ��ԭ��

��ʵ�� `subscribeOn()` �� `observeOn()` ���ڲ�ʵ�֣�Ҳ���õ� `lift()`�����忴ͼ����ͬ��ɫ�ļ�ͷ��ʾ��ͬ���̣߳���

`subscribeOn()` ԭ��ͼ��

![RxJava_008](./assets/RxJava_008.png)

`observeOn()` ԭ��ͼ��

![RxJava_009](./assets/RxJava_009.png)

`subscribeOn()` ���߳��л������� OnSubscribe �У�������֪ͨ��һ�� OnSubscribe ʱ����ʱ�¼���û�п�ʼ���ͣ���� `subscribeOn()` ���߳̿��ƿ��Դ��¼������Ŀ��˾����Ӱ�죻�� `observeOn()` ���߳��л����������ڽ��� Subscriber �У�������������������һ�� Subscriber �����¼�ʱ����� `observeOn()` ���Ƶ�����������̡߳�

����� `subscribeOn()` �� `observeOn()` ���ʹ��ʱ���̵߳�������ô������

![RxJava_010](./assets/RxJava_010.png)


ͼ�й��� 5 �����ж��¼��Ĳ�������ͼ�п��Կ������ٺ͢������ܵ�һ�� subscribeOn() Ӱ�죬�����ں�ɫ�̣߳��ۺܴ͢��ܵ�һ�� `observeOn()` ��Ӱ�죬��������ɫ�̣߳��ݴ��ܵڶ��� `onserveOn()` Ӱ�죬��������ɫ�̣߳����ڶ��� `subscribeOn()` ��������֪ͨ�������߳̾ͱ���һ�� `subscribeOn()` �ضϣ���˶��������̲�û���κ�Ӱ�졣����Ҳ�ͻش���ǰ������⣺��ʹ���˶�� `subscribeOn()` ��ʱ��ֻ�е�һ�� `subscribeOn()` �����á�



#### ���죺doOnSubscribe()

��Ȼ����һ���� `subscribeOn()` ���¼����������û��Ӱ�죬��������֮ǰȴ�ǿ������õġ�

��ǰ�潲 Subscriber ��ʱ���ᵽ�� Subscriber �� `onStart()` �����������̿�ʼǰ�ĳ�ʼ����Ȼ�� `onStart()` ������ `subscribe()` ����ʱ�ͱ������ˣ���˲���ָ���̣߳�����ֻ��ִ���� `subscribe()` ������ʱ���̡߳���͵������ `onStart()` �к��ж��߳���Ҫ��Ĵ��루�����ڽ�������ʾһ�� ProgressBar������������߳�ִ�У����������̷߳Ƿ��ķ��գ���Ϊ��ʱ���޷�Ԥ�� `subscribe()` ������ʲô�߳�ִ�С�

���� `Subscriber.onStart()` ���Ӧ�ģ���һ������ `Observable.doOnSubscribe()` ������ `Subscriber.onStart()` ͬ������ `subscribe()` ���ú�������¼�����ǰִ�У�����������������ָ���̡߳�Ĭ������£� `doOnSubscribe()` ִ���� `subscribe()` �������̣߳�������� `doOnSubscribe()` ֮���� `subscribeOn()` �Ļ�������ִ������������� `subscribeOn()` ��ָ�����̡߳�

���� �����߳���ʾһ�� PregressBar

```
Observable.create(onSubscribe)
    .subscribeOn(Schedulers.io())
    .doOnSubscribe(new Action0() {
        @Override
        public void call() {
            progressBar.setVisibility(View.VISIBLE); // ��Ҫ�����߳�ִ��
        }
    })
    .subscribeOn(AndroidSchedulers.mainThread()) // ָ�����߳�
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(subscriber);
```

> ���滹��Ҫ�࿴����

## RxJava ���ó�����ʹ�÷���

#### �� Retrofit ���

Retrofit �����ṩ��ͳ�� Callback ��ʽ�� api�����ṩ�� Rxjava ��ʽ�� Observable ��ʽ��api�����������������ַ�ʽ�Ĳ�ͬ��

�Ի�ȡһ�� User ����Ϊ����ʹ�� Retrofit Callback API, ����������һ������

```
@GET("/user")
public void getUser(@Query("userId") String userId, Callback<User> callback);
```

ʹ���������

```
getUser(userId, new Callback<User>() {
    @Override
    public void success(User user) {
        userView.setUser(user);
    }

    @Override
    public void failure(RetrofitError error) {
        // Error handling
        ...
    }
};
```

ʹ�� Rxjava ��ʽ

```
@GET("/user")
public Observable<User> getUser(@Query("userId") String userId);
```

ʹ�õ�ʱ����������

```
getUser(userId)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Observer<User>() {
        @Override
        public void onNext(User user) {
            userView.setUser(user);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable error) {
            // Error handling
            ...
        }
    });
```

�� RxJava ��ʽ��ʱ��Retrofit �������װ�� Observable ���������������� `onNext()` ��������ʧ�ܺ���� `onError()`���Ϳ�����Ĵ��� Rxjava ��ʽ����û��ʲô���ƣ�������Ϊ�������������

���磺����ȡ���� User ����Ӧ��ֱ����ʾ��������Ҫ�������ݿ��е����ݽ��бȶԺ�����������ʾ��ʹ�� Callback ��ʽ��ſ�����ôд��

```
getUser(userId, new Callback<User>() {
    @Override
    public void success(User user) {
        processUser(user); // �������� User ����
        userView.setUser(user);
    }

    @Override
    public void failure(RetrofitError error) {
        // Error handling
        ...
    }
};
```

�������ܼ�㣬����ǧ��Ҫ��ô������Ϊ��ȡ���ݿ��Ǹ���ʱ��������ô����Ӱ�����ܣ�����һ��Ҫ�����߳��д�����������Ĵ�����Ҫ��д��

```
getUser(userId, new Callback<User>() {
    @Override
    public void success(User user) {
        new Thread() {
            @Override
            public void run() {
                processUser(user); // �������� User ����
                runOnUiThread(new Runnable() { // �л� UI �߳�
                    @Override
                    public void run() {
                        userView.setUser(user);
                    }
                });
            }).start();
    }

    @Override
    public void failure(RetrofitError error) {
        // Error handling
        ...
    }
};
```

�����������ˣ����Ǵ���̫���ˣ����ѿ��������������߼��������ӣ���ʱ�򿴿� Rxjava ����ô����

```
getUser(userId)
    .doOnNext(new Action1<User>() {
        @Override
        public void call(User user) {
            processUser(user);
        })
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Observer<User>() {
        @Override
        public void onNext(User user) {
            userView.setUser(user);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable error) {
            // Error handling
            ...
        }
    });
```

ǰ̨��̨������һ�����У��߼������˺öࡣ

���� ���� /user �ӿڲ�����ֱ�ӷ��ʣ�����Ҫ����һ�����߻�ȡ�� token ������Ӧ����ôд��

Callback ��ʽ������ʹ��Ƕ�׵� Callback��

```
@GET("/token")
public void getToken(Callback<String> callback);

@GET("/user")
public void getUser(@Query("token") String token, @Query("userId") String userId, Callback<User> callback);

...

getToken(new Callback<String>() {
    @Override
    public void success(String token) {
        getUser(token, userId, new Callback<User>() {
            @Override
            public void success(User user) {
                userView.setUser(user);
            }

            @Override
            public void failure(RetrofitError error) {
                // Error handling
                ...
            }
        };
    }

    @Override
    public void failure(RetrofitError error) {
        // Error handling
        ...
    }
});
```

ʹ�� Rxjava ��������

```
@GET("/token")
public Observable<String> getToken();

@GET("/user")
public Observable<User> getUser(@Query("token") String token, @Query("userId") String userId);

...

getToken()
    .flatMap(new Func1<String, Observable<User>>() {
        @Override
        public Observable<User> onNext(String token) {
            return getUser(token, userId);
        })
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Observer<User>() {
        @Override
        public void onNext(User user) {
            userView.setUser(user);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable error) {
            // Error handling
            ...
        }
    });
```

ʹ��һ�� flatMap ����ת�����������뻹����һ�����ϡ�


#### Rxbinding

[RxBinding](https://github.com/JakeWharton/RxBinding) �� Jake Wharton ��һ����Դ�⣬���ṩ��һ���� Android ƽ̨�ϵĻ��� RxJava �� Binding API����ν Binding�������������� OnClickListener ������ TextWatcher ������ע��󶨶���� API��

����ʹ�� Rxbinding �����õ������

```
Button button = ...;
RxView.clickEvents(button) // �� Observable ��ʽ����������¼�
    .subscribe(new Action1<ViewClickEvent>() {
        @Override
        public void call(ViewClickEvent event) {
            // Click handling
        }
    });
```

������ֻ����ʽ�ı仯������Դ������Ҳ���Ƿ�װ�� `setOnClickListener()` ��ʵ�ֵġ�Ȼ����������һ����ʽ�ĸı䣬ȴǡ�þ��� RxBinding ��Ŀ�ģ�**��չ��**��ͨ�� RxBinding �ѵ������ת���� Observable ֮�󣬾����˶���������չ�Ŀ��ܡ���չ�ķ�ʽ�кܶ࣬�������������һ��������ǰ���ᵽ���� `throttleFirst()` ������ȥ������Ҳ���������ֶ����µĿ������������

```
RxView.clickEvents(button)
    .throttleFirst(500, TimeUnit.MILLISECONDS)
    .subscribe(clickAction);
```

#### RxBus

RxBus ���ֿ�������һ���⣬����������һ���⣬����һ��ģʽ������˼����ʹ�� RxJava ��ʵ���� EventBus��


#### ����������

- `RxJava OnErrorNotImplementedException �Ĵ���`

������� https://www.jianshu.com/p/436cb79eace5

����һ�� �� Application �� onCreate �����м����쳣�������

```
RxJavaPlugins.setErrorHandler {
    //�쳣����
}
```

��������Ĭ����дonError���ṩ����Ľӿ�

