package google.trainging.com.capturingphotos;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/*Android  调用Camera API 拍照*/
public class ControlCameraActivity extends AppCompatActivity {
    private static final String TAG = "ControlCameraActivity";
    Camera mCamera;
    private CameraPreview mPreview;
    private FrameLayout mCameralayout;
    private Button mTakePictureBtn;
    private Button mSwitchCamera;
    /*指定打开哪一个相机
      0 后置
      1 前置
     */
    private int mCameraId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_control_camera);
        if (!checkCameraHardware(this)) {
            Toast.makeText(ControlCameraActivity.this, getResources().getString(R.string.prompt_support), Toast.LENGTH_SHORT)
                    .show();
        } else {
            openCamera();
            mTakePictureBtn = (Button) findViewById(R.id.button_capture);
            mTakePictureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mCamera.takePicture(null, null, mPictureCallback);
                    /*为拍照按钮添加聚焦*/
                    mCamera.autoFocus(mAutoFocusCallback);
                }
            });
            mSwitchCamera = (Button) findViewById(R.id.button_switch);
            mSwitchCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchCamera();
                }
            });

            setCameraDisplayOrientation(this, mCameraId, mCamera);
        }

    }

    /* 聚焦回调,聚焦成功后拍照*/
    private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                mCamera.takePicture(null, null, mPictureCallback);
            }
        }
    };

    // 拍照回调
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            File pictureDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            final String picturePath = pictureDir
                    + File.separator
                    + new DateFormat().format("yyyyMMddHHmmss", new Date())
                    .toString() + ".jpg";
            /*处理照片放在线程中处理，避免卡顿*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(picturePath);
                    try {
                        // 获取当前旋转角度, 并旋转图片
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                            bitmap = rotateBitmapByDegree(bitmap, 90);
                        } else {
                            bitmap = rotateBitmapByDegree(bitmap, -90);
                        }
                        BufferedOutputStream bos = new BufferedOutputStream(
                                new FileOutputStream(file));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        bos.flush();
                        bos.close();
                        bitmap.recycle();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            /*继续进行拍照*/
            mCamera.startPreview();
        }
    };
    // 切换前置和后置摄像头
    public void switchCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, cameraInfo);
        if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
            mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else{
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        mCameralayout.removeView(mPreview);
        releaseCamera();
        openCamera();
        setCameraDisplayOrientation(ControlCameraActivity.this, mCameraId, mCamera);
    }

    // 判断相机是否支持
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    // 获取相机实例
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(mCameraId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    // 开始预览相机
    public void openCamera() {
        if (mCamera == null) {
            mCamera = getCameraInstance();
            mPreview = new CameraPreview(ControlCameraActivity.this, mCamera);
            mPreview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    /*点击预览页面，只聚焦不拍照*/
                    mCamera.autoFocus(null);
                    return false;
                }
            });
            mCameralayout = (FrameLayout) findViewById(R.id.camera_preview);
            mCameralayout.addView(mPreview);
            mCamera.startPreview();
        }
    }

    // 释放相机
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    // 设置相机横竖屏
    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        Log.i(TAG,"rotation is "+ rotation);
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /*旋转图片,默认保存的照片是横向的，需要根据相机当前方向来保存*/
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
}
