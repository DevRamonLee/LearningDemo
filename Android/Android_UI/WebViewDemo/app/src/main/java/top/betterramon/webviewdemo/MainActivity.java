package top.betterramon.webviewdemo;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "WebViewDemo";

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configWebView();
        loadUrl();
    }

    // 对 WebView 进行配置
    private void configWebView() {
        // 触摸焦点起作用
        webView.requestFocus();
        // 不显示滚动条
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings settings = webView.getSettings();
        // 页面含有 JavaScript，设置支持 javascript
        settings.setJavaScriptEnabled(true);
        // 将一个 java 对象绑定到 javascript
        webView.addJavascriptInterface(new AppInterface(), "test"); // 对应 js 的 test.xxx

        // 加了这行后 js 的 alert 方法才能起作用
        webView.setWebChromeClient(new WebChromeClient() {
            // 设置响应 JS 的 alert 函数
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
                // return false;   // 返回 false 会先显示网页的弹框，然后再显示我们自定义的弹框
            }

            // 设置响应 js 的 prompt 函数
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                final View v = View.inflate(MainActivity.this, R.layout.prompt_dialog, null);
                ButterKnife.bind(this, v);
                TextView textView = v.findViewById(R.id.prompt_message_text);
                EditText editText = v.findViewById(R.id.prompt_input_field);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Prompt");
                builder.setView(v);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String value = editText.getText().toString();
                        result.confirm(value);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.cancel();
                    }
                });
                builder.create().show();
                return true;
            }

            // 设置响应 js 的 confirm 函数
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.confirm();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.cancel();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            /**
             * 网页调用内部跳转链接的时候调用，返回 true 表示 Android 处理，返回 false webView 自己处理
             * @param view
             * @param request
             * @return
             */
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "url is " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            /**
             * 加载资源的时候，返回 null 调用默认资源，部位 null 调用返回的资源
             * @param view
             * @param request
             * @return
             */
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                handler.proceed();  // 等待证书响应
                handler.cancel();   // 挂起连接
                handler.handleMessage(null);    // 做其他处理
            }

            /**
             * 接收 Http 请求事件
             * @param view
             * @param handler
             * @param host
             * @param realm
             */
            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            /**
             * 载入页面开始事件
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 载入页面完成事件
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });


    }

    // 加载 URL
    private void loadUrl() {
        // 加载网页
//        webView.loadUrl("https://www.baidu.com");
        // 加载本地 Html 文件
        webView.loadUrl("file:///android_asset/test.html"); // 本地存放在 assets 文件夹下
    }

    /**
     * 默认如果不做处理，点击 back 就会直接退出整个页面，
     * 这里进行判断，如果网页可回退则回退历史记录
     */
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    class AppInterface {
        @JavascriptInterface
        public void hello(String msg) { // 对应 js 中的 xxx.hello("");
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  android 调用 js 方法，这里使用了 post 方法
     *  直接调用 webView.loadUrl("javascript:callJsAlert()"); 也生效了，why？
     */
    @OnClick(R.id.call_js_alert_btn)
    void alertClick() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:callJsAlert()");
            }
        });
    }

    @OnClick(R.id.call_js_prompt_btn)
    void promptClick() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:callJsPrompt()");
            }
        });
    }

    @OnClick(R.id.call_js_confirm_btn)
    void confirmClick() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:callJsConfirm()");
            }
        });
    }

}
