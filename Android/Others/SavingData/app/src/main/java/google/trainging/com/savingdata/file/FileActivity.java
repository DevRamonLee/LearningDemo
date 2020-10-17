package google.trainging.com.savingdata.file;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import google.trainging.com.savingdata.R;

public class FileActivity extends AppCompatActivity {

    private static final  String LOG_TAG = "FileActivity";

    private TextView internalFileDir;
    private TextView internalCacheDir;
    private TextView readInternalFile;
    private TextView externalStorageState;
    private TextView spaceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView();
        testApi();
    }

    public void initView(){
        internalFileDir = (TextView) findViewById(R.id.internal_file_dir);
        internalCacheDir = (TextView) findViewById(R.id.internal_cache_dir);
        readInternalFile = (TextView) findViewById(R.id.read_internal_file);
        externalStorageState = (TextView) findViewById(R.id.external_storage_state);
        spaceInfo = (TextView) findViewById(R.id.space_info);
    }

    public void testApi(){
        // 使用 getFileDir() 获取应用的内部 file 存储目录
        String internal_file_path = getFilesDir().getAbsolutePath();
        internalFileDir.setText("getFileDir(): \n "+ internal_file_path);

        // 使用 getCacheDir() 获取应用的内部 cache 存储目录
        String internal_cache_path = getCacheDir().getAbsolutePath();
        internalCacheDir.setText("getCacheDir(): \n"+internal_cache_path);


        // 向内部文件写内容
        String fileName = "myfile"; // 文件名
        String message = "Hello World！"; // 写入的信息
        writeToInternalFile(fileName,message);

        // 读取上面写入的内容
        readInternalFile.setText("读取内部文件内容 : " + readFile(fileName));

        // 在缓存目录创建文件
        try {
            File.createTempFile(fileName,null, getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 检查外部存储是否可用
        boolean writeAble = isExternalStorageWritable();

        // 是否至少可读
        boolean readAble = isExternalStorageReadable();
        externalStorageState.setText("外部存储是否可读写： "+ writeAble +" 外部存储是否可读： "+readAble);

        // 创建外部公共文件,不会随应用卸载被删除
        String external_file_name = "external_file";
        File publicExternalFile = getAlbumStorageDir(external_file_name);
        Toast.makeText(FileActivity.this,"外部公共文件路径：" + publicExternalFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();

        // 创建外部私有文件，会随着应用卸载被删除
        File privateExternalFile = getAlbumStorageDir(FileActivity.this,external_file_name);
        Toast.makeText(FileActivity.this,"外部私有文件路径 " + privateExternalFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();

        // 获取总空间和可用空间
        long totalSpace = Environment.getExternalStorageDirectory().getTotalSpace();
        long freeSpace = Environment.getExternalStorageDirectory().getFreeSpace();

        String total = Formatter.formatFileSize(FileActivity.this,totalSpace);
        String free = Formatter.formatFileSize(FileActivity.this,freeSpace);
        spaceInfo.setText("总空间："+ total +"剩余空间：" + free);
    }

    public void writeToInternalFile(String fileName,String message){
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(message.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFile(String fileName){
        FileInputStream inputStream;
        StringBuffer buffer = new StringBuffer();
        byte [] tt=new byte[1024];
        try{
            inputStream = openFileInput(fileName);
            int len = -1;
            while ((len = inputStream.read(tt)) != -1){
                   buffer.append(new String(tt,"utf-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    // getExternalStoragePublicDirectory() 获取外部公共文件，不会随app卸载被删除
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    // getExternalFilesDir() 获取外部私有文件，卸载app会删除这个文件
    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }
}
