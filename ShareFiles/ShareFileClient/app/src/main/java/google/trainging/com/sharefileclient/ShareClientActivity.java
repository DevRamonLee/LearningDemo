package google.trainging.com.sharefileclient;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class ShareClientActivity extends AppCompatActivity implements View.OnClickListener {
    /*发送一段简单文本信息*/
    private Button sendTextBtn;
    /*向服务端请求读取图片信息*/
    private Button requestImageBtn;
    private ImageView showImage;
    private TextView nameTv;
    private TextView sizeTv;
    /*发送图片文件*/
    private Button sendImageBtn;

    /*内部存储的根目录*/
    private File mPrivateRootDir;
    /*内部存储images 子目录*/
    private File mImagesDir;
    /*待分享的图片文件对象*/
    File shareImage;

    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_client);
        init();
        /*保存一个文件到内部存储目录，用于分享*/
        createImages();
    }
    private void init(){
        sendTextBtn = (Button)findViewById(R.id.send_text);
        requestImageBtn = (Button)findViewById(R.id.request_image);
        showImage = (ImageView)findViewById(R.id.imageView);
        nameTv = (TextView)findViewById(R.id.name);
        sizeTv = (TextView)findViewById(R.id.size);
        sendImageBtn = (Button)findViewById(R.id.send_image);
        sendImageBtn.setOnClickListener(this);
        sendTextBtn.setOnClickListener(this);
        requestImageBtn.setOnClickListener(this);
    }
    /*分享文本信息*/
    private void sendText(String text){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        //startActivity(sendIntent);
        /*使用createChooser创建一个选择窗口*/
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }
    /*分享图片信息*/
    private void sendImage(){
        if(shareImage.exists()){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.addCategory("android.intent.category.DEFAULT");
            Uri imageUri;
            //imageUri = Uri.fromFile(shareImage);
            imageUri = FileProvider.getUriForFile(
                    ShareClientActivity.this,
                    "google.trainging.com.sharefileclient.fileprovider",
                    shareImage);
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.setType("image/jpeg");
            startActivity(Intent.createChooser(intent,"share image"));
        }
    }

    /*请求图片信息（打开图片）*/
    private void requestImage(){
        Intent mRequestFileIntent = new Intent(Intent.ACTION_PICK);
        mRequestFileIntent.setType("image/jpg");
        startActivityForResult(mRequestFileIntent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ParcelFileDescriptor mInputPFD = null;
        // If the selection didn't work
        if (resultCode != RESULT_OK) {
            // Exit without doing anything else
            return;
        } else {
            Uri returnUri = data.getData();
            try {
                mInputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("ShareClientActivity", "File not found.");
                return;
            }
            // Get a regular file descriptor for the file
            FileDescriptor fd = mInputPFD.getFileDescriptor();
            Bitmap bm = BitmapFactory.decodeFileDescriptor(fd);
            showImage.setImageBitmap(bm);
            /*获取文件的名字和size*/
            showNameAndSize(returnUri);
        }
    }

    /*获取文件的名字和size*/
    protected void showNameAndSize(Uri returnUri){
        Cursor returnCursor =
                getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        nameTv.setText(returnCursor.getString(nameIndex));
        sizeTv.setText(Long.toString(returnCursor.getLong(sizeIndex)));
    }

    /*使用 ShareActionProvider 创建菜单，分享内容*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.main, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        /*使用v7包时要使用下面的这个方法，使用item.getActionProvider()会报错*/
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent sendIntent =new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"http://blog.csdn.net/qq_21482865/article/details/51578880");
        sendIntent.setType("text/plain");
        setShareIntent(sendIntent);
        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    /*从mipmap中读取图片文件*/
    protected  void createImages(){
        /*获取内部文件目录根路径*/
        mPrivateRootDir = getFilesDir();
        /*如果images 文件夹不存在则创建*/
        mImagesDir = new File(mPrivateRootDir, "images");
        if (!mImagesDir.exists()) {
            mImagesDir.mkdirs();
        }
        shareImage = new File(mImagesDir, "test1.jpg");
        Bitmap testBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test1);
        saveImages(shareImage, testBitmap);
    }
    /*保存文件到内部目录*/
    protected  void saveImages(File testFile,Bitmap bitmap){
        FileOutputStream fos = null;
        if (bitmap != null) {
            try {
                fos = new FileOutputStream(testFile);
                /* save images to internal storage*/
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_text:
                sendText("This is my text to send");
                break;
            case R.id.request_image:
                requestImage();
                break;
            case R.id.send_image:
                sendImage();
                break;
        }
    }
}
