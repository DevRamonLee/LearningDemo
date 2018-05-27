package com.li.ramon.search;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by limeng on 2018/5/26.
 * 读取 definition.txt 的内容构建一个数据库
 * 提供查询方法。
 */

public class DatabaseTable {
    private static final String TAG = "DictionaryDatabase";

    //字典的表中将要包含的列项
    public static final String COL_ID = "_id";//主键id
    public static final String COL_SINGER = "SINGER";//歌手
    public static final String COL_SONGS= "SONGS";//歌曲

    private static final String DATABASE_NAME = "DICTIONARY";//数据库名
    private static final String FTS_VIRTUAL_TABLE = "FTS";//数据表名
    private static final int DATABASE_VERSION = 1;

    private final DatabaseOpenHelper mDatabaseOpenHelper;

    public DatabaseTable(Context context) {
        mDatabaseOpenHelper = new DatabaseOpenHelper(context);
        mDatabaseOpenHelper.getWritableDatabase();//调用该方法会调用 oncreate 方法
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        COL_ID + " int PRIMARY KEY ,"+
                        COL_SINGER + ", " +
                        COL_SONGS + ")";

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(FTS_TABLE_CREATE);
            loadDictionary();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }
        /* 在非 UI 线程中读取文件数据并插入到数据库 */
        private void loadDictionary() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        loadWords();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void loadWords() throws IOException {
            final Resources resources = mHelperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.definitions);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, "-");
                    if (strings.length < 2) continue;
                    long id = addWord(strings[0].trim(), strings[1].trim());
                    if (id < 0) {
                        Log.e(TAG, "unable to add word: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
        }

        public long addWord(String word, String definition) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_SINGER, word);
            initialValues.put(COL_SONGS, definition);

            return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
        }
    }
    /* 根据key值和需要返回的列值进行查询操作*/
    public Cursor getWordMatches(String query, String[] columns) {
        String selection = COL_SINGER + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};
        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        /**
         * columns: 查询的行
         * selection: 查询条件
         * selectionArgs: 查询条件参数
         */
        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }
}
