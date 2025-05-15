package ramon.lee.androidui.interactive;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;
import ramon.lee.androidui.interactive.fragment.MyDialogFragment;

public class DialogActivity extends AppCompatActivity {
    static final String TAG = "DialogActivity@test";

    static final int PROGRESS_DIALOG_WITH_NUMBER = 0;
    static final int ALERT_DIALOG = 1;
    static final int ALERT_DIALOG_WITH_LIST = 2;
    static final int ALERT_DIALOG_WITH_CHOICE = 3;
    static final int PROGRESS_DIALOG = 4;
    static final int CUSTOM_DIALOG = 5;
    static final int CUSTOM_DIALOG_WITH_TITLE = 6;

    ProgressDialog progressDialog;
    ProgressThread progressThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_progress_dialog_with_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG_WITH_NUMBER);
            }
        });

        findViewById(R.id.btn_alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ALERT_DIALOG);
            }
        });

        findViewById(R.id.btn_alert_dialog_with_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ALERT_DIALOG_WITH_LIST);
            }
        });

        findViewById(R.id.btn_alert_dialog_with_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ALERT_DIALOG_WITH_CHOICE);
            }
        });

        findViewById(R.id.btn_progress_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);
            }
        });

        findViewById(R.id.btn_custom_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG);
            }
        });

        findViewById(R.id.btn_custom_dialog_with_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_WITH_TITLE);
            }
        });

        findViewById(R.id.btn_dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "dialog");
                dialogFragment.setMyDialogClickCallback(new MyDialogFragment.MyDialogFragmentCallback() {
                    @Override
                    public void onPositiveButtonClick() {
                        Toast.makeText(getApplicationContext(), "你是一个勇者", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNegativeButtonClick() {
                        Toast.makeText(getApplicationContext(), "就放弃了吗？", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id, dialog, args);
        Log.i(TAG, "onPrepareDialog id = " + id + " dialog = " + dialog);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PROGRESS_DIALOG_WITH_NUMBER:
                return createProgressDialogWithNumber();
            case ALERT_DIALOG:
                return createAlertDialog();
            case ALERT_DIALOG_WITH_LIST:
                return createAlertDialogWithList();
            case ALERT_DIALOG_WITH_CHOICE:
                return createAlertDialogWithChoice();
            case PROGRESS_DIALOG:
                return createProgressDialog();
            case CUSTOM_DIALOG:
                return createCustomDialog();
            case CUSTOM_DIALOG_WITH_TITLE:
                return createCustomDialogWithTitle();
            default:
                return null;
        }
    }

    /**
     * 创建警告对话框
     */
    private Dialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    /**
     * 创建警告对话框带列表
     */
    private Dialog createAlertDialogWithList() {
        final CharSequence[] items = {"Red", "Green", "Blue"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }

    /**
     * 创建警告对话框，带单选列表或者多选列表
     */
    private Dialog createAlertDialogWithChoice() {
        final CharSequence[] items = {"Red", "Green", "Black"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        // 创建单选把这里替换为 setSingleChoiceItems
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Toast.makeText(getApplicationContext(), items[which] + " isChecked = " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    /**
     * 显示一个进度对话框，旋转框
     */
    private Dialog createProgressDialog() {
        // 最后一个参数表示进度条是否是进度不可知的
        return ProgressDialog.show(DialogActivity.this, "", "Loading. Please wait...", true);
    }

    /**
     * 自定义 dialog 实现  Dialog 完全定义布局
     * @return
     */
    private Dialog createCustomDialog() {
        Dialog dialog = new Dialog(DialogActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Custom dialog");   // 设置 title 不会显示
        return dialog;
    }

    /**
     * 自定义 Dialog 使用 AlertDialog setView
     * @return
     */
    private Dialog createCustomDialogWithTitle() {
        AlertDialog.Builder builder;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog,
                (ViewGroup) findViewById(R.id.root_layout));
        builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setView(layout);
        builder.setTitle("Custom dialog");
        return builder.create();
    }

    /**
     * 显示一个进度对话框，带进度
     */
    private Dialog createProgressDialogWithNumber() {
        progressDialog = new ProgressDialog(DialogActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressThread = new ProgressThread(handler);
        progressThread.start();
        return progressDialog;
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int total = msg.getData().getInt("total");
            progressDialog.setProgress(total);
            if (total >= 10) {
                dismissDialog(PROGRESS_DIALOG_WITH_NUMBER);
                progressThread.setState(ProgressThread.STATE_DONE);
            }
        }
    };

    private static class ProgressThread extends Thread {
        Handler mHandler;
        final static int STATE_DONE = 0;
        final static int STATE_RUNNING = 1;

        int mState;
        int total;
        ProgressThread(Handler h) {
            mHandler = h;
        }

        @Override
        public void run() {
            mState = STATE_RUNNING;
            total = 0;
            while (mState == STATE_RUNNING) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "thread interrupted");
                }

                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", total);
                msg.setData(b);
                mHandler.sendMessage(msg);
                total++;
            }
        }

        /**
         * set state to stop the thread
         * @param state
         */
        public void setState(int state) {
            mState = state;
        }
    }
}