package top.betterramon.rrodemo.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ramon Lee on 2019/8/12.
 * 将重复的代码抽取出来，放到父类
 */
public class ObjectLoader {
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())         // 可以让用户取消订阅
                .observeOn(AndroidSchedulers.mainThread());
    }
}
