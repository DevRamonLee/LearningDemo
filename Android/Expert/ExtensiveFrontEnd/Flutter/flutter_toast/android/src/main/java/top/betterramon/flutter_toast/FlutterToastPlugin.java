package top.betterramon.flutter_toast;

import android.content.Context;
import android.widget.Toast;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterToastPlugin */
public class FlutterToastPlugin implements MethodCallHandler {
  private final Context mContext;

  public FlutterToastPlugin(Context context) {
    mContext = context;
  }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    // flutter_toast 是我们 MethodChannel 的名字，Dart 文件里会用到它
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_toast");
    channel.setMethodCallHandler(new FlutterToastPlugin(registrar.context()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    // call.method 是方法名，这里我们就叫它 toast
    if (call.method.equals("toast")) {
      // 调用本地代码的时候，只能传递一个参数。为了传递多个，可以把参数放在一个 map 里面。
      // call.arguemnt() 方法支持 Map 和 JSONObject
      String content = call.argument("content");
      String duration = call.argument("duration");
      Toast.makeText(mContext, content,
              "short".equals(duration) ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)
              .show();
      // 执行成功
      result.success(true);
    } else {
      result.notImplemented();
    }
  }
}
