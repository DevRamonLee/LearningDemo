package betterramon.top.binderdemo.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import betterramon.top.binderdemo.Book;
import betterramon.top.binderdemo.R;
import betterramon.top.binderdemo.server.IBookManager;
import betterramon.top.binderdemo.server.RemoteService;
import betterramon.top.binderdemo.server.Stub;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mGetBook;
    private Button mAddBook;
    private boolean isServiceConnected;
    private IBookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        mGetBook = (Button)findViewById(R.id.get_books);
        mAddBook = (Button)findViewById(R.id.add_books);

        mGetBook.setOnClickListener(this);
        mAddBook.setOnClickListener(this);

        // 绑定服务，即获取远程服务
        Intent intent = new Intent("betterramon.top.frameworks.remote.service");
        intent.setClass(this, RemoteService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_books:
                if (isServiceConnected) {
                    try {
                        List<Book> books = mBookManager.getBooks();
                        Log.d("client", "书的数量： " + books.size());
                    }catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.add_books:
                if (isServiceConnected) {
                    try {
                        mBookManager.addBook(new Book(002, "第一行代码"));
                    }catch (RemoteException e) {

                    }
                }
                break;
        }

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isServiceConnected = true;
            mBookManager = Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceConnected = false;
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
