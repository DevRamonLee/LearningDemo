package google.trainging.com.savingdata.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import google.trainging.com.savingdata.database.FeedReaderContract.FeedEntry;

import google.trainging.com.savingdata.R;

public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText title;
    private EditText subtitle;
    private Button insert_btn;
    private Button query_btn;
    private TextView query_message;
    private EditText query_key;
    private EditText delete_input_key;
    private Button delete_btn;
    private EditText update_input;
    private Button update_btn;

    FeedReaderDbHelper fdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        initView();
        initDb();
        /*执行事务*/
        dbTransaction();
    }

    public void initView(){
        title = (EditText)findViewById(R.id.title);
        subtitle = (EditText)findViewById(R.id.subtitle);
        insert_btn = (Button)findViewById(R.id.insert_btn);
        query_btn = (Button)findViewById(R.id.query_btn);
        query_message = (TextView)findViewById(R.id.db_message);
        query_key = (EditText)findViewById(R.id.query_key);
        delete_input_key = (EditText)findViewById(R.id.delete_key_input);
        delete_btn = (Button)findViewById(R.id.delete);
        update_input = (EditText)findViewById(R.id.update_input);
        update_btn = (Button)findViewById(R.id.update);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        insert_btn.setOnClickListener(this);
        query_btn.setOnClickListener(this);
    }

    public void initDb(){
        fdHelper = new FeedReaderDbHelper(DatabaseActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert_btn:
                insertDataToDb();
                break;
            case R.id.query_btn:
                query_message.setText("Ids of query results : " + queryDb());
                break;
            case R.id.delete:
                deleteDataFromDb();
                break;
            case R.id.update:
                updateDbMessage();
                break;
        }
    }
    /*向数据库插入信息*/
    private void insertDataToDb(){
        // Gets the data repository in write mode
        SQLiteDatabase db = fdHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title.getText().toString());
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle.getText().toString());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
        Toast.makeText(DatabaseActivity.this,"insert db succeed, row id is "+ newRowId,Toast.LENGTH_LONG).show();
    }

    /*查询数据库*/
    private String queryDb(){
        SQLiteDatabase db = fdHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_SUBTITLE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { query_key.getText().toString() };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FeedEntry._ID));
            itemIds.add(itemId);
        }
        cursor.close();

        return itemIds.toString();
    }
    /*删除数据库信息*/
    private void deleteDataFromDb(){
        SQLiteDatabase db = fdHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { delete_input_key.getText().toString() };
        // Issue SQL statement.
        int nums = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
        Toast.makeText(DatabaseActivity.this,"delete "+ nums +" items",Toast.LENGTH_LONG).show();
    }
    /*更新数据库信息*/
    private void updateDbMessage(){
        SQLiteDatabase db = fdHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, update_input.getText().toString());

        // Which row to update, based on the title
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { "Name" };

        int count = db.update(
                FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        Toast.makeText(DatabaseActivity.this,"update "+ count +" items",Toast.LENGTH_LONG).show();

    }

    /*使用数据库事务*/
    private void dbTransaction(){
        SQLiteDatabase db = fdHelper.getWritableDatabase();
        db.beginTransaction();//开启事务
        try{
            if(true){
                //手动抛出一个异常，让事务失败，注释掉这一句则事务执行成功
                      throw new NullPointerException();
            }
            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_TITLE, title.getText().toString());
            values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle.getText().toString());
            db.insert(FeedEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful(); //事务执行成功
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();//结束事务
        }
    }

    @Override
    protected void onDestroy() {
        /*getWritableDatabase 和 getReadableDatabase 在数据库关闭时调用代价高，所以最好的关闭连接的地方是这里*/
        fdHelper.close();
        super.onDestroy();
    }
}
