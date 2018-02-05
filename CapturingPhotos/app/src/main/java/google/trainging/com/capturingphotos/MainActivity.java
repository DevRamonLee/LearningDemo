package google.trainging.com.capturingphotos;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button photoBtn;
    private Button videoBtn;
    private Button cameraBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    protected  void init(){
        photoBtn = (Button)findViewById(R.id.photo_activity);
        videoBtn = (Button)findViewById(R.id.video_activity);
        cameraBtn = (Button)findViewById(R.id.control_camera_activity);
        cameraBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_activity:
                Intent photoIntent = new Intent(MainActivity.this,PhotoActivity.class);
                startActivity(photoIntent);
                break;
            case R.id.video_activity:
                Intent videoIntent = new Intent(MainActivity.this,VideoActivity.class);
                startActivity(videoIntent);
                break;
            case R.id.control_camera_activity:
                Intent cameraIntent = new Intent(MainActivity.this,ControlCameraActivity.class);
                startActivity(cameraIntent);
        }
    }
}
