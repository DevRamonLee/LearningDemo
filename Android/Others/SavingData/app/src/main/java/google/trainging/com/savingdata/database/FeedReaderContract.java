package google.trainging.com.savingdata.database;

import android.provider.BaseColumns;

/**
 * Created by meng.li on 2017/10/16.
 */
public final class FeedReaderContract {
    // make the constructor private.
    private FeedReaderContract() {}


    // 实现 BaseColumns 接口可以继承一个 _ID 字段
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }

    // 创建表语句
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    // 删除表语句
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

}

