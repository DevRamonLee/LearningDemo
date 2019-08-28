package google.trainging.com.savingdata.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by meng.li on 2017/10/16.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // 如果你改变了数据库的结构，那么必须要升级版本号
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate 方法会在调用 getReadableDatabase 或者 WriteableDatabase 时并且数据库不存在时才会被调用，数据库如果已经存在则不会调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果数据库只是用作在线数据的缓存，可以用比较粗暴的方法，删除数据表然后调用 oncreate 重新创建
        db.execSQL(FeedReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);

        /*
         * 如果数据库保存的是一些有价值的数据，则不能使用上面的方法
         * 升级数据库的最佳实践，每一个数据库版本都会对应一个版本号，当指定的数据库版本号大于当前的版本
         * 号的时候，就会进入到 onUpgrade() 方法中去执行更新操作。这里需要为每一个版本号赋予它各自改变的内容，然后在
         * onUpgrade 方法中执行更新操作
         *
         * 注意一个细节，这里我们没有写break，为了跨版本升级时都可以执行
         */
        /*switch (oldVersion) {
            case 1:
                //执行版本1的数据库操作
                db.execSQL(sql);
            case 2:
                //执行版本2的数据库操作
                db.execSQL(sql);
            default:
        }*/
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

