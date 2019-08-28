package top.betterramon.eventbusdemo.activity;

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

import top.betterramon.eventbusdemo.MyApplication;
import top.betterramon.eventbusdemo.event.MessageEvent;
import top.betterramon.eventbusdemo.R;

public class MainActivity extends AppCompatActivity {
    private Button sendEventBtn;
    private Button asyncExecutorBtn;
    private Button asyncExecutorExceptionBtn;
    private Button sendStickyEventBtn;
    private Button cancelStickyEventBtn;
    private Button goStickyActivityBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndEvent();
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

    private void initViewAndEvent() {
        sendEventBtn = findViewById(R.id.send_event_btn);
        sendEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送一个事件
                EventBus.getDefault().post(new MessageEvent("Hello event bus"));
            }
        });

        asyncExecutorBtn = findViewById(R.id.async_executor_btn);
        asyncExecutorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asyncExecutor();
            }
        });
        asyncExecutorExceptionBtn = findViewById(R.id.async_executor_exception_btn);
        asyncExecutorExceptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 使用 AsyncExecutor 调度发送事件，抛出一个异常
                asyncExecutorException();
            }
        });

        sendStickyEventBtn = findViewById(R.id.send_sticky_event_btn);
        sendStickyEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送一个粘性事件，后一个事件在 StickyActivity 启动的时候会被处理
                EventBus.getDefault().postSticky(new MessageEvent("Send sticky event old"));
                EventBus.getDefault().postSticky(new MessageEvent("Send sticky event new"));
            }
        });

        cancelStickyEventBtn = findViewById(R.id.cancel_sticky_event_btn);
        cancelStickyEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 取消粘性事件，有两种方法
                cancelStickyEvent1();
            }
        });

        // 启动处理粘性事件的 Activity
        goStickyActivityBtn = findViewById(R.id.sticky_activity_btn);
        goStickyActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StickyActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    // AsyncExecutor 调度事件,这个也抛出异常了？暂时不清楚原因
    private void asyncExecutor() {
        MyApplication application = (MyApplication) this.getApplication();
        application.getAsyncExecutor().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                EventBus.getDefault().postSticky(new MessageEvent("asyncExecutor"));
            }
        });
    }

    // AsycnExecutor 处理异常事件
    private void asyncExecutorException() {
        MyApplication application = (MyApplication) this.getApplication();
        application.getAsyncExecutor().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                int x = 100 / 0; // 故意抛出一个异常
                EventBus.getDefault().postSticky(new MessageEvent("asyncExecutorException"));
            }
        });
    }

    // 取消粘性事件方法一
    private void cancelStickyEvent1() {
        MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
            // Now do something with it.
            Toast.makeText(this, "Cancel sticky event successfully, try to start StickyActivity", Toast.LENGTH_SHORT).show();
        }
    }

    // 取消粘性事件方法二
    private void cancelStickyEvent2() {
        MessageEvent stickyEvent = EventBus.getDefault().removeStickyEvent(MessageEvent.class);
        if (stickyEvent != null) {
            // do more things.
            Toast.makeText(this, "Cancel sticky event successfully, try to start StickyActivity", Toast.LENGTH_SHORT).show();
        }
    }


    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this,"onMessageEvent" + event.message, Toast.LENGTH_SHORT).show();
    }

    // 指定优先级
    @Subscribe(priority = 1, threadMode = ThreadMode.POSTING)
    public void onMessageEventPriority(MessageEvent event) {
        Toast.makeText(this,"onMessageEventPriority : " + event.message, Toast.LENGTH_SHORT).show();
        // 取消后续事件的分发
        EventBus.getDefault().cancelEventDelivery(event);
    }

    // 接收 AsyncExecutor 发送的事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessageEvent(MessageEvent event) {
        Toast.makeText(this,"handleMessageEvent : " + event.message, Toast.LENGTH_SHORT).show();
    }

    // 接收 AsyncExecutor 异常的事件消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleFailureEvent(ThrowableFailureEvent event) {
        // do something
        Toast.makeText(this, "Received a ThrowableFailureEvent", Toast.LENGTH_SHORT).show();
    }
}
