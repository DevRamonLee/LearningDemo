- http://hukai.me/android-training-course-in-chinese/basics/data-storage/index.html

## SharedPreference

SharedPreference 用于保存键值对。

> **注意** android7.0 给 `SharedPreferences` 设置 `Context.MODE_WORLD_READABLE` 或 `Context.MODE_WORLD_WRITEABLE`，会触发 `SecurityException` 异常，已经不能跨应用访问。

#### 创建preference文件

- 方法一：`getSharedPreferences()` 如果你需要使用名字定义多个 preference 文件，你可以使用这个方法。

第一个参数为文件名，第二个参数为访问模式。

```
context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
```
- 方法二：`getPreferences()` 如果你只需要一个 preference 文件，则可以使用这个方法，这个方法提供一个属于当前 Activity 的 preference 文件，不需要再提供名字。

```
getActivity().getPreferences(Context.MODE_PRIVATE);
```

#### 向Preference写值
```
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
SharedPreferences.Editor editor = sharedPref.edit();
editor.putInt(getString(R.string.saved_high_score), newHighScore);
editor.commit();
```

#### 读取Preference
```
SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
```

**preference文件路径为：data/data/your package name/shared_prefs**

## 保存文件

内部存储是指系统的存储空间，没有root是访问不到的，比如 sharedPreferenced 或者 database 都是保存在这里面的。外部存储是指手机自带的存储，如16G、32G、64G，还包括Sdcard的存储。

|internal storage| External storage
|-|:-:|
|总是可用的 | 不总是可以使用，因为它可能被移除。
|只有你的应用程序可以访问这里保存的文件。|这里是 world-readable，保存在这里的文件会不受控制的被外部读取。
|当卸载你的应用程序时，Android系统会移除内部存储中所有你的app的文件。|当你卸载你的应用程序的时候，系统只会移除你使用getExternalFilesDir()方法保存的文件。
|如果你不想其他应用程序访问你的文件，使用内部存储是最好的。|如果你想和其他应用共享文件或者在电脑上访问这些文件，那么使用外部存储是最好的。

**注意：在 Android 7.0（API级别24）之前，可以通过放宽文件系统权限使其他应用程序可以访问内部文件。7.0后不再是这样。如果您希望使私人文件的内容可以被其他应用程序访问，您的应用程序可能需要使用 `FileProvider`**

> 提示：默认情况下，应用程序安装在内部存储上，您可以在 AndroidManifest 中指定 `android：installLocation` 属性，以便将应用程序安装在外部存储上

- 申请权限

保存文件在内部存储的时候你不需要任何权限，你的应用程序有权限访问它的内部存储目录。保存到外部存储时，需要获得外部存储读写权限。
```
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

- 保存文件到内部存储

将文件保存到内部存储器时，可以通过调用以下两种方法之一获取相应的目录作为文件：

1. `getFilesDir()` 返回一个表示程序内部目录的文件。文件路径为： `/data/user/0/包名/files/`

2. `getCacheDir()` 返回代表应用程序的临时缓存文件的内部目录的文件。 确保在不再需要时删除文件。如果系统开始运行,在存储空间不足时，则可能会在没有警告的情况下删除缓存文件。文件路径为: `/data/user/0/包名/cache/`

在这些文件目录下创建一个新的文件，你可以使用 `File()` 构造方法，使用上面两个方法之一传递内部存储文件目录，例：

```
File file = new File(context.getFilesDir(), filename);
```

或者，您可以调用 `openFileOutput()` 获取写入内部目录中的文件的 `FileOutputStream`。例如，以下是将文本写入文件的方法：写入的路径为： `/data/user/0/包名/files`

```
String filename = "myfile";
String string = "Hello world!";
FileOutputStream outputStream;
try {
    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
    outputStream.write(string.getBytes());
    outputStream.close();
} catch (Exception e) {
    e.printStackTrace();
}
```

或者，如果你需要缓存一些文件，你应该使用 `createTempFile()`。

例如，以下方法从 URL 中提取文件名，并在应用程序的内部缓存目录中创建一个名称为该名称的文件：文件路径为：`/data/user/0/包名/cache/`

```
public File getTempFile(Context context, String url) {
    File file;
    try {
        String fileName = Uri.parse(url).getLastPathSegment();
        /*参数1：前缀
        参数2： 后缀
        参数3：在指定目录下创建
        */
        file = File.createTempFile(fileName, null, context.getCacheDir());
    } catch (IOException e) {
        // Error while creating file
    }
    return file;
}
```

- 保存文件在外部存储

因为外部存储可能不可用，需要在访问该卷之前验证该卷是否可用。通过调用 `getExternalStorageState()` 来查询外部存储状态。 如果返回的状态等于 `MEDIA_MOUNTED`，则可以读取和写入文件。

```
/* Checks if external storage is available for read and write */
public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
        return true;
    }
    return false;
}

/* Checks if external storage is available to at least read */
public boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        return true;
    }
    return false;
}
```

- 外部存储两种类型的文件

**公共文件**: 提供给其他应用程序和用户的文件。 当用户卸载您的应用程序时，这些文件应该仍然可供用户使用。例如，您的应用程序或其他程序下载的文件捕获的照片。

要将公共文件保存在外部存储器上，使用 `getExternalStoragePublicDirectory()` 方法获取表示外部存储上相应目录的文件。 该方法使用一个参数来指定要保存的文件类型（如 `DIRECTORY_MUSIC` 或 `DIRECTORY_PICTURES`）。例如：


```
public File getAlbumStorageDir(String albumName) {
    // Get the directory for the user's public pictures directory.
    File file = new File(Environment.getExternalStoragePublicDirectory(
    Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs()) {
        Log.e(LOG_TAG, "Directory not created");
    }
    return file;
}
```

**私有文件**: 文件属于您的应用程序，在用户卸载应用程序时被删除。 虽然这些文件在技术上可由用户和其他应用程序访问，因为它们在外部存储上，但它们是实际上不为应用程序外的用户提供价值的文件。例如，您的应用程序或临时媒体文件下载的其他资源。

要保存应用程序的私有的文件在外部存储上，通过调用 `getExternalFilesDir()` 并传递一个名称来指示您想要的目录的类型。例如，为单个相册创建一个目录：

```
public File getAlbumStorageDir(Context context, String albumName) {
    // Get the directory for the app's private pictures directory.
    File file = new File(context.getExternalFilesDir(
    Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs()) {
        Log.e(LOG_TAG, "Directory not created");
    }
    return file;
}
```

> 调用 `getExternalFilesDir()` 并传递 `null`。将返回应用程序在外部存储上的专用目录的根目录。

- 可用空间与文件删除

可以通过调用 `getFreeSpace()` 或 `getTotalSpace()` 来确定是否有足够的空间可用而不会导致 IOException。

一般删除文件

```
// 删除文件
myFile.delete();
```

如果文件保存在内部存储器上，可以通过调用 `Context` 的 `deleteFile() `和文件名字来删除文件

```
myContext.deleteFile(fileName);
```
## 保存数据到数据库

- 定义我们的数据库的表的字段
```
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}
    /* 通过实现BaseColumns接口，您的内部类可以继承一个称为_ID的主键字段*/
    public static class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
```

- 创建和删除表的一些典型语句

```
private static final String SQL_CREATE_ENTRIES =
    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
        FeedEntry._ID + " INTEGER PRIMARY KEY," +
        FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
        FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
```

- 使用 SQL Helper 创建数据库

```
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    /*onCreate 方法会在调用getReadableDatabase或者WriteableDatabase时并且数据库不存在时才会被调用，
    数据库如果已经存在则不会调用。*/
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        /*比较粗暴的方法，删除数据表然后调用 onCreate 重新创建，不建议使用*/
        db.execSQL(FeedReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);
        /*
        * 升级数据库的最佳实践，每一个数据库版本都会对应一个版本号，当指定的数据库版本号大于当前的版本
        * 号的时候，就会进入到 onUpgrade() 方法中去执行更新操作。这里需要为每一个版本号赋予它各自改变的内容，然后在
        * onUpgrade 方法中执行更新操作
        *
        */
        /*注意一个细节，这里我们没有写break，为了跨版本升级时都可以执行*/
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
```

- 要访问数据库，需要实例化 `SQLiteOpenHelper` 的子类

```
FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
```

- 插入数据

通过将 `ContentValues` 对象传递给 `insert()` 方法将数据插入到数据库中：

```
// Gets the data repository in write mode
SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
ContentValues values = new ContentValues();
values.put(FeedEntry.COLUMN_NAME_TITLE, title);
values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

// Insert the new row, returning the primary key value of the new row
long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
```

`insert()` 的第一个参数指示表名。

第二个参数告诉框架在 `ContentValues` 为空的情况下（即，您没有放置任何值）会做什么。 如果指定列的名称，框架将插入一行并将该列的值设置为 null。如果指定 null，就像此代码示例中一样，当没有值时，不会插入一行。

- 读取数据

要从数据库中读取，请使用 `query()` 方法，传递您的选择条件和所需的列,查询的结果将在 Cursor 对象中返回给您。

```
SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
String[] projection = {
    FeedEntry._ID,
    FeedEntry.COLUMN_NAME_TITLE,
    FeedEntry.COLUMN_NAME_SUBTITLE
};

// Filter results WHERE "title" = 'My Title'
String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

Cursor cursor = db.query(
    FeedEntry.TABLE_NAME, // The table to query
    projection, // 需要返回的列
    selection, // Where 条件中的列
    selectionArgs, // Where 条件中列的值
    null, // don't group the rows
    null, // don't filter by row groups
    sortOrder // The sort order
);
```

要查看光标中的一行，请使用其中一个 `Cursor` 移动方法，您必须始终在开始读取值之前调用该方法。 由于光标从位置-1开始，所以调用 `moveToNext()` 将“读取位置”放在结果的第一个条目上，并返回光标是否已经超过结果集中的最后一个条目。对于每一行，您可以通过调用一个`Cursor get` 方法来读取列的值，例如 `getString()` 或 `getLong()` 。对于每个 get 方法，您必须传递所需列的索引位置，您可以通过调用 `getColumnIndex()` 或 `getColumnIndexOrThrow()` 获取该索引位置。在完成迭代结果之后，调用光标上的 `close()` 来释放其资源。例如

```
List itemIds = new ArrayList<>();
while(cursor.moveToNext()) {
    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry._ID);
    itemIds.add(itemId);
}
cursor.close();
```

- 删除数据

要从表中删除行，您需要提供标识行的选择条件。
```
// Define 'where' part of query.
String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";

// Specify arguments in placeholder order.
String[] selectionArgs = { "MyTitle" };

// Issue SQL statement.
db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
```

- 更新数据

更新表将 `insert()` 的内容值语法与 `delete()` 的语法相结合。

```
SQLiteDatabase db = mDbHelper.getWritableDatabase();

// New value for one column
ContentValues values = new ContentValues();
values.put(FeedEntry.COLUMN_NAME_TITLE, title);

// Which row to update, based on the title
String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";

String[] selectionArgs = { "MyTitle" };

int count = db.update(
    FeedReaderDbHelper.FeedEntry.TABLE_NAME,
    values,
    selection,
    selectionArgs);
```

- 数据库事务操作

```
SQLiteDatabase db = fdHelper.getWritableDatabase();
db.beginTransaction();//开启事务
try{
    if(true){
        //手动抛出一个异常，让事务失败，注释掉这一句则事务执行成功
        throw new NullPointerException();
    }
    ContentValues values = new ContentValues();
    values.put(FeedEntry.COLUMN_NAME_TITLE,title.getText().toString());
    
    values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle.getText().toString());
    
    db.insert(FeedEntry.TABLE_NAME, null, values);
    db.setTransactionSuccessful(); //事务执行成功
}catch(Exception e){
    e.printStackTrace();
}finally{
    db.endTransaction();//结束事务
}
```

- 持久数据库连接

由于 `getWritableDatabase()` 和 `getReadableDatabase()` 在数据库关闭时调用成本高昂，您应该将数据库连接打开。通常，`onDestroy()` 中的关闭数据库是最佳的。

```
@Override
protected void onDestroy() {
    mDbHelper.close();
    super.onDestroy();
}
```

- 我们可以直接调用使用SQL语句操作数据库

```
SQLiteDatabase db = openOrCreateDatabase(DdName, MODE_PRIVATE, null);
db.execSQL( sql)
db.rawQuery(sql);
```

例子源码：[SavingData](./SavingData)

> 上面这种方法我们不通过 SQLiteOpenHelper 类的 API 来操作数据库，而是使用 SQL 语句操作，一般不推荐使用。