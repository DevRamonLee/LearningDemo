package google.trainging.com.savingdata.file;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import google.trainging.com.savingdata.R;

public class FileActivity extends AppCompatActivity {

    private static final  String LOG_TAG = "FileActivity";
    private TextView internal_file_dir;
    private TextView internal_cache_dir;
    private TextView read_internal_file;
    private TextView external_storage_state;
    private TextView space_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView();
        testApi();
    }

    public void initView(){
        internal_file_dir = (TextView) findViewById(R.id.textView3);
        internal_cache_dir = (TextView) findViewById(R.id.textView4);
        read_internal_file = (TextView) findViewById(R.id.textView5);
        external_storage_state = (TextView) findViewById(R.id.textView6);
        space_info = (TextView) findViewById(R.id.textView7);
    }

    public void testApi(){
        String internal_file_path = getFilesDir().getAbsolutePath();
        /*使用getFileDir() 获取应用的内部存储目录*/
        internal_file_dir.setText("getFileDir(): \n "+ internal_file_path);

        String internal_cache_path = getCacheDir().getAbsolutePath();
        internal_cache_dir.setText("getCacheDir(): \n"+internal_cache_path);

        /*使用File的构造方法创建文件*/
        //File file = new File(getFilesDir(),"file_name");

        /*向内部文件中写入信息*/
        String fileName = "myfile";//文件名
        String message = "Hello World！";//写入的信息
        writeToInternalFile(fileName,message);
        /*读取内部文件数据*/
        read_internal_file.setText("read file Message: "+readFile(fileName));

        /*在缓存目录创建文件*/
        try {
            File.createTempFile(fileName,null,getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*检查外部存储是否可用*/
        boolean writeAble = isExternalStorageWritable();
        /*是否至少可读*/
        boolean readAble = isExternalStorageReadable();
        external_storage_state.setText("writeable is "+ writeAble +" readable is "+readAble);

        /*创建外部公共文件,不会随应用卸载被删除*/
        String external_file_name = "external_file";
        File public_external_file = getAlbumStorageDir(external_file_name);
        Toast.makeText(FileActivity.this,"public external file created "+public_external_file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        /*创建外部私有文件，会随着应用卸载被删除*/
        File private_external_file = getAlbumStorageDir(FileActivity.this,external_file_name);
        Toast.makeText(FileActivity.this,"private external file created "+private_external_file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        /*获取总空间和可用空间*/
        long total_space = Environment.getExternalStorageDirectory().getTotalSpace();
        long free_space = Environment.getExternalStorageDirectory().getFreeSpace();
        String total = Formatter.formatFileSize(FileActivity.this,total_space);
        String free = Formatter.formatFileSize(FileActivity.this,free_space);
        space_info.setText("total space is "+ total +" free space is "+free);
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
        Toast.makeText(FileActivity.this,"save data to internal file ",Toast.LENGTH_SHORT).show();
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
    /*getExternalStoragePublicDirectory() 获取外部公共文件，不会随app卸载被删除*/
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    /*getExternalFilesDir（） 获取外部私有文件，卸载app会删除这个文件*/
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
