package top.betterramon.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private ImageView showImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImage = findViewById(R.id.show_image);

        // 例1：打印数组
        String[] names = {"路飞", "索隆", "乔巴"};
        // from 是快捷创建事件序列
        Observable.from(names)
                .subscribe(new Action1<String>() {      // Action1 是不完整定义回调
                    @Override
                    public void call(String s) {
                        Log.i("RamonLee", s);
                    }
                });

        // 例2： 由 id 加载图片
        final int drawableRes = R.drawable.ic_launcher_background;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {     // 这个方法会在 subscribe（）调用后调用
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

        // 例3：使用 Scheduler 进行线程控制
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())   // 指定 subscribe() 发生在 IO 线程，也就是产生事件的操作
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.d("RamonLee", "number:" + number);
                    }
                });

        // 例4：修改前面根据 id 加载图片的例子
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {     // 这个方法会在 subscribe（）调用后调用
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())     // 在 io 线程读取图片
          .observeOn(AndroidSchedulers.mainThread())  // 在主线程显示
          .subscribe(new Action1<Drawable>() {
              @Override
              public void call(Drawable drawable) {
                  showImage.setImageDrawable(drawable);
              }
          });
    }
}
