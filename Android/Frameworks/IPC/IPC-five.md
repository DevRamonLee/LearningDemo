## ContentProvider 实现 IPC

ContentProvider 底层实现也是 Binder，系统对它进行了封装。接下来我们实现一个自定义的 ContentProvider。


> 注意：ContentProvider 的底层数据看起来很像一个 SQLite 数据库，但是其实 ContentProvider 底层的数据存储方式可以是 SQLite 数据库，也可以是普通的文件，甚至可以是内存中的一个对象来进行存储。

- 1.创建 `BookProvider` 继承 `ContentProvider` 并实现六个抽象方法：`onCreate、query、update、insert、delete、getType`
    - `onCreate` 代表 `ContentProvider` 的创建；`getType` 用来返回一个 `Uri` 请求所对应的 `MIME` 类型；剩下四个方法对应 `CRUD` 操作。

```
public class BookProvider extends ContentProvider{
    private static final String TAG = "BookProvider";
    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreate , current thread: " + Thread.currentThread().getName());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query, current thead: " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG,"getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG,"insert");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG,"delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG,"update");
        return 0;
    }
}
```

- 2.接下来注册这个 `BookProvider`。其中 `android:authorities` 是 `ContentProvider` 的唯一标识，通过这个属性外部应用就可以访问我们的 `BookProvider`，所以它**必须是唯一的**。通过 `android:permission`属性我们给 `BookProvider` 添加了权限，外界应用想要访问 `BookProvider`，就必须声明权限。`ContentProvider` 的权限还可以细分成读权限和写权限，分别对应 `android:readPermission` 和 `android:writePermission`。

```
<provider
    android:authorities="top.betterramon.contentproviderdemo.provider"
    android:name=".provider.BookProvider"
    android:permission="top.betterramon.PROVIDER"
    android:process=":provider"/>
```

- 3.注册完成后我们创建外部应用来访问它。

```
public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri uri = Uri.parse("content://top.betterramon.contentproviderdemo.provider");
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
    }
}
```
上面的代码中我们通过 `ContentResolve` 对象的 `query` 方法去查询了 `BookProvider` 中的数据，其中 `content://top.betterramon.contentproviderdemo.provider` 就是我们在前面 `android:authorities` 属性所指定的值。

运行上面的程序，从 log 可以看出， `BookProvider` 中的 `query` 方法被调用了三次，并且这三次调用不在同一个线程中。可以看出，它们运行在 `Binder` 线程中。`onCreate` 运行在 `main` 线程中，也就是 `UI` 线程，所以我们不能在 `onCreate` 中做耗时操作。

```
D/BookProvider: onCreate , current thread: main
D/BookProvider: query, current thead: Binder:7905_2
D/BookProvider: query, current thead: Binder:7905_2
D/BookProvider: query, current thead: Binder:7905_1
```

到这里，`ContentProvider` 的所有流程就跑通了，不过 `ContentProvider` 没有返回任何数据。接下来我们完善它，使它能够返回数据。


- 4.创建一个数据库来管理图书和用户信息。

```
public class DbOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";

    private static final int DB_VERSION = 1;

    // 创建图书和用户信息表
    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
            + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + " name TEXT)";

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY, " + " name TEXT, " +
            "sex INTEGER)";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
```

上面的代码中我们借用 `SQLiteOpenHelper` 来管理数据库的创建、升级和降级。


- 5.通过 `BookProvider` 向外界提供上述数据库中的信息。在这个例子中支持外界对 `BookProvider` 中的 `book` 表和 `user` 表进行访问，为了知道外界要访问的是那个表，我们需要为它们定义单独的 `Uri` 和 `Uri_Code` 并使用 `UriMatcher` 的 `addURI` 方法将 `Uri` 和 `Uri_Code` 关联起来。当外界请求时，可以根据请求的 `Uri` 来得到 `Uri_Code`,有了 `Uri_Code` 就可以知道外界想访问那个表，然后就可以进行相关的数据操作

```
public class BookProvider extends ContentProvider{
    private static final String TAG = "BookProvider";

    public static final String AUTHORITY = "top.betterramon.contentproviderdemo.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"user", USER_URI_CODE);
    }
...
}
```
上面的代码中我们分别为 `book` 表和 `user` 表指定了 `Uri`, 分别为：

```
content://top.betterramon.contentproviderdemo.provider/book
content://top.betterramon.contentproviderdemo.provider/user
```

这两个 `Uri` 所关联的 `Uri_Code` 分别为 0 和 1

```
sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
sUriMatcher.addURI(AUTHORITY,"user", USER_URI_CODE);
```

将 `Uri` 和 `Uri_Code` 关联之后，我们可以根据 `Uri` 先取出 `Uri_Code`,根据 `Uri_Code` 得到数据表的名字，知道了外界要访问的表，接下来就可以响应外界的增删改查请求了。

```
private String getTableName(Uri uri) {
    String tableName = null;
    switch (sUriMatcher.match(uri)) {
        case BOOK_URI_CODE:
            tableName = DbOpenHelper.BOOK_TABLE_NAME;
            break;
        case USER_URI_CODE:
            tableName = DbOpenHelper.USER_TABLE_NAME;
            break;
        default:
            break;
    }
    return tableName;
}
```

- 6.接下来实现 `query、update、insert、delete` 方法，首先从 `Uri` 中取出外界要访问的表的名称，然后根据外界传递的参数查询进行查询操作。

```
@Nullable
@Override
public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Log.d(TAG, "query, current thead: " + Thread.currentThread().getName());
    String table = getTableName(uri);
    if (table == null) {
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }
    return mDb.query(table, projection, selection, selectionArgs,
            null, null, sortOrder, null);
}
```

另外三个方法和 `query` 的实现思路是一致的，唯一的区别就是它们会引起数据源的改变，这个时候我们需要通过 `ContentResolver` 的 `notifyChange` 方法来通知外界当前 `ContentProvider` 中的数据源已经发生改变。要观察一个 `ContentProvider` 中的数据改变情况，可以通过 `ContentResolver` 的 `registerContentObserver` 方法来注册观察者。


完整的代码

```
public class BookProvider extends ContentProvider{
    private static final String TAG = "BookProvider";

    public static final String AUTHORITY = "top.betterramon.contentproviderdemo.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"user", USER_URI_CODE);
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreate , current thread: " + Thread.currentThread().getName());
        mContext = getContext();
        // ContentProvider 创建时，初始化数据库。这里只是为了演示，实际中不推荐在主线程进行耗时数据库操作
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3, 'Android');");
        mDb.execSQL("insert into book values(4, 'Ios');");
        mDb.execSQL("insert into book values(5, 'Html5');");
        mDb.execSQL("insert into user values(1, 'Jake',1);");
        mDb.execSQL("insert into user values(2, 'Ramon',0);");

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query, current thead: " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(table, projection, selection, selectionArgs,
                null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG,"getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG,"insert");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table, null, contentValues);
        // 通知数据源发生了改变
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG,"delete");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, s, strings);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG,"update");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table, contentValues, s, strings);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
```

> query、update、insert、delete 是存在多线程并发访问的，因此方法内部要做好线程同步。这个例子中为什么没做呢？因为采用的是 SQLite 且只有一个 SQLiteDataBase 的连接，SQLiteDatabase 内部对数据的操作是有同步处理的，但是如果通过多个 SQLiteDatabase 对象来操作数据库就无法保证同步，因为不同的 SQLiteDatabase 对象之间无法进行线程同步。假如 ContentProvider 的底层数据是一块内存的话，比如 List ，这种情况下对 List 的遍历、插入、删除操作就需要进行线程同步。


- 7.`BookProvider` 已经完成了，接下来在外部来访问它

```
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
```

运行结果如下
```
D/ProviderActivity: query book: Book{bookId=3, bookName='Android'}
D/ProviderActivity: query book: Book{bookId=4, bookName='Ios'}
D/ProviderActivity: query book: Book{bookId=5, bookName='Html5'}
D/ProviderActivity: query book: Book{bookId=6, bookName='Android 开发艺术探索'}
D/ProviderActivity: query user : User{userId=1, userName='Jake', isMale=true}
D/ProviderActivity: query user : User{userId=2, userName='Ramon', isMale=false}
```

更新操作可以这样写
```
ContentValues updateUser = new ContentValues();
updateUser.put("_id", 2);
updateUser.put("name","Ramon");
updateUser.put("sex", 1);
getContentResolver().update(userUri, updateUser, "_id = ?", new String[] {"2"} );
```

例子源码：[ContentProviderDemo](./ContentProviderDemo)