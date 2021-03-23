package ramon.lee.httpsample;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.httplib.AppException;
import ramon.lee.httplib.OnGlobalExceptionListener;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/23 1:35
 */
class BaseActivity extends AppCompatActivity implements OnGlobalExceptionListener {

    @Override
    public boolean handleException(AppException e) {
        // 处理全局的 App 异常
        if (e.statusCode == 403) {
            if ("token invalid".equals(e.responseMsg)) {
                // TODO: reLogin
                return true;
            }
        } else if ("Not Found".equals(e.responseMsg)) {
            Log.i("BaseActivity", "handle global exception");
            return true;
        }
        return false;
    }
}
