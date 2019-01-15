package li.ramon.better.cursorloader;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CursorLoaderActivity extends AppCompatActivity {
    private static final String TAG = "CursorLoaderActivity";
    private static final int REQUEST_READ_CALL_LOG = 1;

    ArrayList<HashMap<String, Object>> callLogItems;
    private ListView callLogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor_loader);
        callLogItems = new ArrayList<HashMap<String,Object>>();
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALL_LOG)) {
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG }, REQUEST_READ_CALL_LOG);
            }
        }else {
            initLoader();
        }
        callLogList = findViewById(R.id.call_log_lv);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CALL_LOG: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLoader();
                } else {
                }
                return;
            }
        }
    }

    private void initLoader(){
        LoaderManager loaderManager = getLoaderManager();
        //这里初始化一个 id = 0 的 loader
        Bundle bundle = new Bundle();
        bundle.putString("key","this is a bundle value");
        loaderManager.initLoader(0,bundle,new CallLogCallBack());
    }

    /**
     * 利用cursorloader加载手机联系人的电话记录
     * Cursor 是 onCreateLoader()返回的参数，也将是onLoadFinished()方法中需要操作的数据源对象
     * 如果 Cursor换成String，那么 onLoadFinished()第二个参数将返回String
     */
    private class  CallLogCallBack implements LoaderManager.LoaderCallbacks<Cursor>{
        /**
         * 这个方法是在后台去执行你需要的操作，最终的操作都是在工作线程中
         * @param i 这个是此 callback的 id
         * @param bundle getLoadManager().initLoader()初始化时传入的
         * @return Cursor
         */
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            //在这里可以接收initLoader()第二个参数传过来的数据
            if(bundle != null){
                String value = bundle.getString("key");
                Log.i(TAG,"bundle value is : " + value);
            }
            //系统手机通话记录的 URL 是 content://call_log/calls
            //这里开始执行，查询手机通话记录的操作，执行完成后，将会回调 onLoadFinished()方法
            //方法一：
            CursorLoader cursorLoader = new CursorLoader(CursorLoaderActivity.this,
                    CallLog.Calls.CONTENT_URI, null, null, null,null);
            //方法二：重写 loadInBackground()方法
            /*CursorLoader cursorLoader = new CursorLoader(CursorLoaderActivity.this,
                          CallLog.Calls.CONTENT_URI, null, null, null,null){
                @Override
                public Cursor loadInBackground() {
                    //此方法将会在工作线程中进行，可以进行方法重写
                    Cursor cursor = super.loadInBackground();
                    CallLogCursor cursorLoader1 = new CallLogCursor(cursor);
                    return cursorLoader1;
                }
            };*/
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            int endKey = 20,count = 0;
            cursor.moveToFirst();
            while (!cursor.isLast()){
                cursor.moveToNext();
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);
                String type = cursor.getString(typeIndex);
                String date = cursor.getString(dateIndex);
                String duration = cursor.getString(durationIndex);

                //只显示20个通话记录
                if(count < endKey) {
                    Log.i(TAG, "name = " + name + " number = " + number
                            + " type = " + type + " date = " + date + " duration = " + duration);
                    count++;

                    HashMap<String, Object> map = new HashMap<String, Object>();
                    if(name == null){
                        map.put("name","未知");
                    }else {
                        map.put("name", name);
                    }
                    map.put("number", number);
                    map.put("type", type);
                    map.put("date", date);
                    map.put("duration", duration);
                    callLogItems.add(map);
                }
            }
            CallLogAdapter callLogAdapter = new CallLogAdapter(CursorLoaderActivity.this);
            callLogList.setAdapter(callLogAdapter);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private class CallLogAdapter extends BaseAdapter {
        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        public CallLogAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return callLogItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.call_log_item,null);
                holder = new ViewHolder();
                holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
                holder.nmberTv = (TextView) convertView.findViewById(R.id.number_tv);
                holder.typeTv = (TextView) convertView.findViewById(R.id.type_tv);
                holder.dateTv = (TextView) convertView.findViewById(R.id.date_tv);
                holder.durationTv = (TextView) convertView.findViewById(R.id.duration_tv);
                convertView.setTag(holder);//绑定ViewHolder对象
            }else{
                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            }
            //设置TextView显示的内容
            if(callLogItems.size() > 0) {
                holder.nameTv.setText(callLogItems.get(position).get("name").toString());
                holder.nmberTv.setText(callLogItems.get(position).get("number").toString());
                holder.typeTv.setText(callLogItems.get(position).get("type").toString());
                holder.dateTv.setText(callLogItems.get(position).get("date").toString());
                holder.durationTv.setText(callLogItems.get(position).get("duration").toString());
            }
            return convertView;
        }
    }/*存放控件*/
    public final class ViewHolder{
        public TextView nameTv;
        public TextView nmberTv;
        public TextView typeTv;
        public TextView dateTv;
        public TextView durationTv;
    }
}
