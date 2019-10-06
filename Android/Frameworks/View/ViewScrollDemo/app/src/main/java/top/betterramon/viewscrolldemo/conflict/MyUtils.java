package top.betterramon.viewscrolldemo.conflict;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MyUtils {
    public static DisplayMetrics getScreenMetrics(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }
}
