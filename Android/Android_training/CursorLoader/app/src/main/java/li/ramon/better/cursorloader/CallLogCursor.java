package li.ramon.better.cursorloader;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.CallLog;
import android.util.Log;

/**
 * Created by meng.li on 2018/10/10.
 */

public class CallLogCursor extends CursorWrapper {
    private static final String TAG = "CallLogCursor";
    public CallLogCursor(Cursor cursor) {
        super(cursor);
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

            if(count < endKey) {
                Log.i(TAG, "name = " + name + " number = " + number
                        + " type = " + type + " date = " + date + " duration = " + duration);
                count++;
            }
        }
    }
}
