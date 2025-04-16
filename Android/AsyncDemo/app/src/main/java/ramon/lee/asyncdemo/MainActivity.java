package ramon.lee.asyncdemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_async_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new DownloadFileTask().execute(new URL("http://aaaaaa.apk"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_intent_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalIntentService.startActionBaz(MainActivity.this, "后台任务1", "参数1");
                LocalIntentService.startActionFoo(MainActivity.this, "后台任务2", "参数2");
            }
        });

        new ExecutorService().newFixed
    }
}