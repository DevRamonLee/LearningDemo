package top.betterramon.binderdemo2.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import top.betterramon.binderdemo2.Book;
import top.betterramon.binderdemo2.IBookManager;
import top.betterramon.binderdemo2.IOnNewBookArrivedListener;
import top.betterramon.binderdemo2.R;
import top.betterramon.binderdemo2.server.RemoteService;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mGetBooks;
    private Button mAddBook;
    private Button mRegister;
    private Button mUnRegister;
    private boolean isServiceConnected;
    private IBookManager mBookManager;

    private static final int MESSAGE_NEW_BOOK_ARRIVED = 167;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        mGetBooks = findViewById(R.id.get_books_btn);
        mAddBook = findViewById(R.id.add_book_btn);
        mRegister = findViewById(R.id.register_btn);
        mUnRegister = findViewById(R.id.unregister_btn);

        mGetBooks.setOnClickListener(this);
        mAddBook.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mUnRegister.setOnClickListener(this);

        // 绑定服务，获取远程服务
        bindService(new Intent(this, RemoteService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_books_btn:
                if(isServiceConnected) {
                    // 新建线程处理，防止 ANR
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Book> books = null;
                            try {
                                books = mBookManager.getBookList();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            Log.d("client", "书的数量 " + books.size());
                        }
                    }).start();
                }
                break;
            case R.id.add_book_btn:
                if (isServiceConnected) {
                    try {
                        mBookManager.addBook(new Book(002, "第二行代码"));
                    } catch (RemoteException e) {

                    }
                }
                break;
            case R.id.register_btn:
                if (isServiceConnected) {
                    try {
                        mBookManager.registerListener(mIOnNewBookArrivedListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.unregister_btn:
                if(isServiceConnected) {
                    try {
                        mBookManager.unregisterListener(mIOnNewBookArrivedListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.d("client", "client 取消订阅失败");
                    }
                }
                break;
            default:
                break;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("onServiceConnected ", iBinder + "");
            isServiceConnected = true;
            mBookManager = IBookManager.Stub.asInterface(iBinder);
            try{
                iBinder.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceConnected = false;
            // 远程服务进程异常，重新连接服务
            bindService(new Intent(ClientActivity.this, RemoteService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
            Log.d("client","onServiceDisconnected 当前进程名称 " + Thread.currentThread().getName());
        }
    };

    /*
        创建 binder 实体类
     */
    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            // 该方法在 client 端线程池中运行，切换到主线程处理
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    /*
        定义 Binder 死亡代理
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {// 该方法在 client 端线程池中运行
            if(mBookManager == null) {
                return;
            }
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;
            // 远程服务进程异常，重新连接服务
            bindService(new Intent(ClientActivity.this, RemoteService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
            Log.d("client", "onServiceDisconnected 当前进程名： " + Thread.currentThread().getName());
            Log.d("client", "DeathRecipient 当前进程名称： " + Thread.currentThread().getName());
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d("client", "receive new book: " + msg.obj );
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isServiceConnected) {
            unbindService(mServiceConnection);
        }
    }
}
