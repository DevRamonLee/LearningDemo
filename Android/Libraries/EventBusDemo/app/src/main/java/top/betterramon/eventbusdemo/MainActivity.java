package top.betterramon.eventbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.AsyncExecutor;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

public class MainActivity extends AppCompatActivity {
    private Button postBtn;
    private Button sendStickyBtn;
    private Button stickyActivityBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        postBtn = findViewById(R.id.post_btn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 使用 AsyncExecutor 调度发送事件，抛出一个异常
                asyncExecutorException();
                // 发送一个事件
//                EventBus.getDefault().post(new MessageEvent("Hello event bus"));
            }
        });

        sendStickyBtn = findViewById(R.id.send_sticky_btn);
        sendStickyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送一个粘性事件，后一个事件在 StickyActivity 启动的时候会被处理
                EventBus.getDefault().postSticky(new MessageEvent("Send sticky event old"));
                EventBus.getDefault().postSticky(new MessageEvent("Send sticky event new"));
            }
        });

        // 启动处理粘性事件的 Activity
        stickyActivityBtn = findViewById(R.id.sticky_activity_btn);
        stickyActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StickyActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void asyncExecutorException() {
        // AsycnExecutor 使用
        AsyncExecutor.create().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                int x = 100 / 0;
                EventBus.getDefault().postSticky(new MessageEvent("AsyncExecutor test"));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this,event.message, Toast.LENGTH_SHORT).show();
    }

    // 指定优先级
    @Subscribe(priority = 1, threadMode = ThreadMode.POSTING)
    public void onMessageEventPriority(MessageEvent event) {
        Toast.makeText(this,"priority : " + event.message, Toast.LENGTH_SHORT).show();
        // 取消后续事件的分发
        EventBus.getDefault().cancelEventDelivery(event);
    }

    // This method will be called when a SomeOtherEvent is posted
    @Subscribe
    public void handleSomethingElse(SomeOtherEvent event) {
        // doSomethingWith(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleFailureEvent(ThrowableFailureEvent event) {
        // do something
        Toast.makeText(this, "recive a ThrowableFailureEvent", Toast.LENGTH_SHORT).show();
    }
}
