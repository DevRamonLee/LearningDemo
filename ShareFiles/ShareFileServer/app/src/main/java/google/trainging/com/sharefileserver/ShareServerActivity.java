package google.trainging.com.sharefileserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShareServerActivity extends AppCompatActivity {
    private TextView receiveText;
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
    }
    private void handleSendText(Intent intent){
        String receivedStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        receiveText.setText(receiveText.getText().toString()+ " : "+ receivedStr);
    }
    private  void handleSendImage(Intent intent){

    }
    private  void handleSendMultipleImages(Intent intent){

    }
}
