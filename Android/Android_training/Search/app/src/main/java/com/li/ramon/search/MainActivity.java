package com.li.ramon.search;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    DatabaseTable db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseTable(this);
        listView = findViewById(R.id.search_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        // 关联检索配置和SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        // 添加 searchView 查询监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件回调
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                Cursor cursor = db.getWordMatches(newText,new String []{db.COL_ID,db.COL_SINGER,db.COL_SONGS});
                if(cursor != null){
                    //准备构造SimpleAdapter的参数
                    String[] from = new String[]{db.COL_SONGS};
                    int[] to = new int[]{R.id.item};
                    /* SimpleCursorAdapter 要求 _id 主键*/
                    CursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.cursor_item,cursor,from,to);
                    listView.setAdapter(adapter);
                }else{
                    listView.setAdapter(null);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
