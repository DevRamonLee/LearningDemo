package top.betterramon.activitydemo.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import top.betterramon.activitydemo.R;

public class ActivityLifeCycle2 extends AppCompatActivity {
    private static final String TAG = "ActivityLifeCycleDemo";

    // Activity创建时被调用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ActivityTwo onCreate called.");
        setContentView(R.layout.activity_life_cycle2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "ActivityTwo onStart called.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "ActivityTwo onRestart called.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "ActivityTwo onResume called.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "ActivityTwo onPause called.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "ActivityTwo onStop called.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ActivityTwo onDestory called.");
    }
}
