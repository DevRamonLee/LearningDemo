package top.betterramon.activitydemo.util;
import android.widget.Toast;

import top.betterramon.activitydemo.Program;

public class ToastUtils {
    public static void showToast(String msg) {
        Toast.makeText(Program.applicationContext, msg, Toast.LENGTH_SHORT).show();
    }
}
