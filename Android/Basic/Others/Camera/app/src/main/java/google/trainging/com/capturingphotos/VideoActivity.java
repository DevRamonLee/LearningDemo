package google.trainging.com.capturingphotos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_VIDEO_CAPTURE = 1;

    private Button takeVideoBtn;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
    }

    protected void init() {
        takeVideoBtn = (Button) findViewById(R.id.take_video);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        takeVideoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_video:
                dispatchTakeVideoIntent();
                break;
        }
    }

    // 使用 VideoView 查看视频
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            mVideoView.setVideoURI(videoUri);
            /*play the video*/
            if (!mVideoView.isPlaying()) {
                mVideoView.start();
            }
            /*pause the video*/
            //mVideoView.pause();
            /*stop the video*/
            //mVideoView.stopPlayback();
            /*replay the video*/
            //mVideoView.resume();
            /*release the resource*/
            //mVideoView.suspend();
        }
    }

    // 拍摄视频的 intent
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
}
