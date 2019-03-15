package top.betterramon.contentproviderdemo.client;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import top.betterramon.contentproviderdemo.R;

public class ProviderActivity extends AppCompatActivity {
    private static final String TAG = "ProviderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri bookUri = Uri.parse("content://top.betterramon.contentproviderdemo.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name","Android 开发艺术探索");
        getContentResolver().insert(bookUri,values);
        Cursor bookCursor = getContentResolver().query(bookUri,
                new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG, "query book: " + book.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://top.betterramon.contentproviderdemo.provider/user");
        ContentValues updateUser = new ContentValues();
        updateUser.put("_id", 2);
        updateUser.put("name","Ramon");
        updateUser.put("sex", 1);
        getContentResolver().update(userUri, updateUser, "_id = ?", new String[] {"2"} );
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"},
                null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1;
            Log.d(TAG, "query user : " + user.toString());
        }
        userCursor.close();
    }

    class Book {
        int bookId;
        String bookName;
        @Override
        public String toString() {
            return "Book{" +
                    "bookId=" + bookId +
                    ", bookName='" + bookName + '\'' +
                    '}';
        }
    }

    class User {
        int userId;
        String userName;
        boolean isMale;
        @Override
        public String toString() {
            return "User{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", isMale=" + isMale +
                    '}';
        }
    }
}
