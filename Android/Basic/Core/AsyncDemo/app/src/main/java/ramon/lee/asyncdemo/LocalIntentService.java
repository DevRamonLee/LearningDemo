package ramon.lee.asyncdemo;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class LocalIntentService extends IntentService {
    private static final String TAG = "LocalIntentService";

    private static final String ACTION_FOO = "ramon.lee.asyncdemo.action.FOO";
    private static final String ACTION_BAZ = "ramon.lee.asyncdemo.action.BAZ";

    private static final String EXTRA_PARAM1 = "ramon.lee.asyncdemo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "ramon.lee.asyncdemo.extra.PARAM2";

    public LocalIntentService() {
        super("LocalIntentService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, LocalIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, LocalIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    private void handleActionFoo(String param1, String param2) {
        Log.d(TAG, "handleActionFoo: param1 = " + param1 + " param2 = " + param2);
        // 模拟耗时操作
        SystemClock.sleep(3000);
    }

    private void handleActionBaz(String param1, String param2) {
        Log.d(TAG, "handleActionBaz: param1 = " + param1 + " param2 = " + param2);
        // 模拟耗时操作
        SystemClock.sleep(3000);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "service destroyed");
        super.onDestroy();
    }
}
