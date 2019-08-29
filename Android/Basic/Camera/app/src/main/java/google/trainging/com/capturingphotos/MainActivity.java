package google.trainging.com.capturingphotos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button photoBtn;
    private Button videoBtn;
    private Button cameraBtn;
    private Activity mActivity;
    private static final  int MY_PERMISSION_MUTI_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = MainActivity.this;
        checkPermission();
    }

    protected void checkPermission(){
        if (ContextCompat.checkSelfPermission(mActivity, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mActivity, CAMERA)
                != PackageManager.PERMISSION_GRANTED ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    CAMERA)) {
                Toast.makeText(MainActivity.this, "Some Permission was Denied last time", Toast.LENGTH_SHORT)
                        .show();
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE ,CAMERA }, MY_PERMISSION_MUTI_REQUEST);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE ,CAMERA }, MY_PERMISSION_MUTI_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_MUTI_REQUEST: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++) perms.put(permissions[i], grantResults[i]);

                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // 所有权限都通过了，初始化视图
                    init();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
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
