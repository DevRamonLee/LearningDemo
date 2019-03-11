package top.betterramon.binderdemo2.server;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import top.betterramon.binderdemo2.Book;
import top.betterramon.binderdemo2.IBookManager;
import top.betterramon.binderdemo2.IOnNewBookArrivedListener;

/**
 * 远程服务 RemoteService
 */

public class RemoteService extends Service {
    // 支持并发读写，自动线程同步
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    // 用于注册或反注册跨进程 listener，内部自动实现线程同步工作，当客户端进程终止时，会自动解除注册
    private RemoteCallbackList<IOnNewBookArrivedListener> mBookArrivedListeners = new RemoteCallbackList<>();

    // 线程安全的
    private AtomicBoolean mIsDestroy = new AtomicBoolean(false);

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(001,"艺术探索"));
        // 新建线程
        new Thread(new ServiceWorker()).start();
    }

    /*
        定时 5 秒新增一本书
     */
    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            // 服务还活着
            while (!mIsDestroy.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int price = mBookList.size() + 1;
                Book book = new Book(price, " new Book: " + price);
                try{
                    onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
        通知 client 端有新书
     */
    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);

        // beginBroadcast 将创建一个回调列表的副本，你可以从使用 getBroadcastItem 检索条目
        final int N = mBookArrivedListeners.beginBroadcast();

        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mBookArrivedListeners.getBroadcastItem(i);
            if(listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        // 和 beginBroadcast 结合使用
        mBookArrivedListeners.finishBroadcast();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(checkPermission()) {
            return bookManager;
        } else {
            return null;
        }
    }

    /*
        权限验证，有权限才能连接到此服务
     */
    private boolean checkPermission() {
        int check = checkCallingOrSelfPermission("top.betterramon.binderdemo2.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return false;
        }
        return true;
    }

    private final IBookManager.Stub bookManager = new IBookManager.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 包名验证
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if(packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if( !packageName.startsWith("top.betterramon")) {
                return false;
            }
            // 权限验证
            boolean checkPermission = checkPermission();
            return checkPermission && super.onTransact(code, data, reply, flags);
        }

        // 真正提供的服务功能
        @Override
        public List<Book> getBookList() throws RemoteException {
            // 模拟耗时工作，如果 Client 端是在主线程操作，会导致 client 端出现 ANR
            SystemClock.sleep(5000);
            // 同步
            for (Book book : mBookList) {
                Log.d("server", "getBooks: " + book.toString());
            }
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
            Log.d("server", "addBook: " + book.toString());
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            // 订阅
            mBookArrivedListeners.register(listener);

            final int N = mBookArrivedListeners.beginBroadcast();
            Log.d("server", "订阅成功，数量为: " + N);
            mBookArrivedListeners.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            // 取消订阅
            mBookArrivedListeners.unregister(listener);

            // 配对使用
            final int N = mBookArrivedListeners.beginBroadcast();
            Log.d("server", "取消订阅成功，数量为： " + N);
            mBookArrivedListeners.finishBroadcast();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy.set(true);
    }
}
