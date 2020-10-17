package betterramon.top.binderdemo.server;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import betterramon.top.binderdemo.Book;

/**
 * 远程服务
 */
public class RemoteService extends Service{

    private List<Book> mBookList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(001,"Android 艺术探索"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bookManager;
    }

    private final Stub bookManager = new Stub() {

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            //权限验证
            int check = checkCallingOrSelfPermission("betterramon.top.binderdemo.ACCESS_BOOK_SERVICE");
            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }
            //其他验证
            return super.onTransact(code, data, reply, flags);
        }

        // 真正提供的服务功能
        @Override
        public List<Book> getBooks() throws RemoteException {
            // 同步
            synchronized (mBookList) {
                for (Book book : mBookList) {
                    Log.d("server","getBooks: " + book.toString());
                }
                return mBookList;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            // 同步
            synchronized (mBookList) {
                mBookList.add(book);
                Log.d("server","addBook: " + book.toString());
            }
        }
    };
}
