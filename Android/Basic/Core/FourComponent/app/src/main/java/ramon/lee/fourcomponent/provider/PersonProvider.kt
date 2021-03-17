package ramon.lee.fourcomponent.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log

class PersonProvider : ContentProvider() {
    companion object {
        private const val TAG = "PersonProvider"
        private const val AUTHORITY = "ramon.lee.PersonProvider"
        private const val PERSON_ALL = 0
        private const val PERSON_ONE = 1
        private const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.scott.person"
        private const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.scott.person"
        private const val PERSON_TABLE = "person"
    }

    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var helper: DBHelper? = null
    private var db: SQLiteDatabase? = null

    init {
        matcher.addURI(AUTHORITY, "persons", PERSON_ALL)    //匹配记录集合
        matcher.addURI(AUTHORITY, "persons/#", PERSON_ONE);    //匹配单条记录
    }

    //数据改变后立即重新查询
    private val NOTIFY_URI =
        Uri.parse("content://$AUTHORITY/persons")

    override fun onCreate(): Boolean {
        helper = DBHelper(context!!)
        return true
    }

    override fun getType(uri: Uri): String? {
        return when (matcher.match(uri)) {
            PERSON_ALL -> CONTENT_TYPE
            PERSON_ONE -> CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        db = helper?.readableDatabase
        var selection = selection
        var selectionArgs: Array<String>? = selectionArgs
        when (matcher.match(uri)) {
            PERSON_ALL -> Log.i(TAG, "do nothing")
            PERSON_ONE -> {
                val _id = ContentUris.parseId(uri)
                selection = "_id = ?"
                selectionArgs = arrayOf(_id.toString())
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        return db?.query(PERSON_TABLE, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val match = matcher.match(uri)
        if (match != PERSON_ALL) {
            throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = helper?.writableDatabase
        var value = values
        if (value == null) {
            value = ContentValues()
            value.put("name", "no name")
            value.put("age", "1")
            value.put("info", "no info")
        }
        val rowId = db?.insert(PERSON_TABLE, null, value)
        rowId?.let {
            if (rowId > 0) {
                notifyDataChanged()
                return ContentUris.withAppendedId(uri, rowId)
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        db = helper?.writableDatabase
        var selection = selection
        var selectionArgs = selectionArgs
        val match = matcher.match(uri)
        when(match) {
            PERSON_ALL -> Log.i(TAG, "do nothing")
            PERSON_ONE -> {
                val _id = ContentUris.parseId(uri)
                selection = "_id = ?"
                selectionArgs = arrayOf(_id.toString())
            }
        }
        val count = db?.delete(PERSON_TABLE, selection, selectionArgs)
        count?.let {
            if (count > 0) {
                notifyDataChanged()
            }
            return count
        } ?: run {
            return 0
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        db = helper?.writableDatabase
        var selection = selection
        var selectionArgs = selectionArgs
        val match = matcher.match(uri)
        when(match) {
            PERSON_ALL -> Log.i(TAG, "do nothing")
            PERSON_ONE -> {
                val _id = ContentUris.parseId(uri)
                selection = "_id = ?"
                selectionArgs = arrayOf(_id.toString())
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri ")
        }
        val count = db?.update(PERSON_TABLE, values, selection, selectionArgs)
        count?.let {
            if (count > 0) {
                notifyDataChanged()
            }
            return count
        } ?: run {
            return 0
        }
    }

    //通知指定URI数据已改变
    private fun notifyDataChanged() {
        context?.contentResolver?.notifyChange(NOTIFY_URI, null)
    }
}