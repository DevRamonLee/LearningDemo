package google.trainging.com.capturingphotos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;     // 拍摄缩略图
    private static final int REQUEST_TAKE_PHOTO = 2;        // 拍摄全尺寸并保存在外部存储上

    private Button captureBtn;
    private ImageView captureImg;
    private Button fullSizeBtn;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        init();
    }

    private void init() {
        captureBtn = (Button) findViewById(R.id.capture_btn);
        captureImg = (ImageView) findViewById(R.id.capture_img);
        fullSizeBtn = (Button) findViewById(R.id.take_full_size);
        fullSizeBtn.setOnClickListener(this);
        captureBtn.setOnClickListener(this);
    }

    // 调用系统应用拍摄照片
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有可执行意图的活动
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // 拍摄全尺寸图片并保存在外部存储上
    private void dispatchTakeFullSizePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "google.trainging.com.capturingphotos.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);      // 提供给相机应用可以读写的文件
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    // 获取拍摄的照片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            captureImg.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Toast.makeText(PhotoActivity.this, "scanning files", Toast.LENGTH_SHORT).show();

            mCurrentPhotoPath = PhotoUtil.amendRotatePhoto(mCurrentPhotoPath, captureImg, this);    // 处理图片的旋转问题
            galleryAddPic();    // 更新图库
            setPic();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_btn:
                dispatchTakePictureIntent();            // 拍摄一个缩略图
                break;
            case R.id.take_full_size:
                dispatchTakeFullSizePictureIntent();    // 拍摄全尺寸图片
                break;
        }
    }

    // 将拍摄的照片扫描到媒体库
    private void galleryAddPic() {
        File f = new File(mCurrentPhotoPath);
        try {
            // 把文件插入到系统图库
            MediaStore.Images.Media.insertImage(PhotoActivity.this.getContentResolver(),
                    f.getAbsolutePath(), f.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        PhotoActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));
    }

    // 创建一个保存照片内容的文件
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,         // prefix
                ".jpg",         // suffix
                storageDir             // directory
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // 根据 imageView 大小来压缩图片大小，控制内存，防止内存溢出
    private void setPic() {
        // Get the dimensions of the View
        int targetW = captureImg.getWidth();
        int targetH = captureImg.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        // inJustDecodeBounds 设置为true 表示 bmOptions 只加载图片的宽和高信息
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // 上面改为了 true，这里需要加载图片，需要改为 false
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;   // 设置缩放比例

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        captureImg.setImageBitmap(bitmap);
    }
}
