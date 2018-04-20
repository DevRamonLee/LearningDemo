package google.trainging.com.sharefileserver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
*  Android training 2.0  应用程序内容共享
*  响应 intent 请求
* */

/*接收处理文字和图片intent数据*/
public class ShareServerActivity extends AppCompatActivity {
    private TextView receiveText;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_server);
        initView();

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
        /*处理从其他地方启动，例如从launcher*/
        }
    }
    private void initView(){
        receiveText = (TextView) findViewById(R.id.receive_text);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
    private void handleSendText(Intent intent){
        String receivedStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        receiveText.setText(receiveText.getText().toString()+ " : "+ receivedStr);
    }
    private  void handleSendImage(Intent intent){
        Uri returnUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        /*7.0 以前可以使用这种方法*/
        //FileInputStream fileInputStream=new FileInputStream(uri.getPath());
        ParcelFileDescriptor mInputPFD = null;
        try {
            mInputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Get a regular file descriptor for the file
        FileDescriptor fd = mInputPFD.getFileDescriptor();
        Bitmap bm = BitmapFactory.decodeFileDescriptor(fd);
        imageView.setImageBitmap(bm);
    }
    private  void handleSendMultipleImages(Intent intent){
        //接收多张图片
        //ArrayList<Uri> uris=intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
    }
}
