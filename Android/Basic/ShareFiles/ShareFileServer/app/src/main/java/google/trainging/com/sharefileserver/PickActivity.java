package google.trainging.com.sharefileserver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PickActivity extends AppCompatActivity {

    private File mPrivateRootDir;       // 内部存储的根目录
    private File mImagesDir;            // 内部存储 images 子目录
    File[] mImageFiles;                 // images 目录下文件对象数组

    // 保存图片文件名和文件数据
    List<String> mImageNames;
    List<Bitmap> mImageBitmaps;

    private ListView mFileListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        createImages();     // 将 mipmap 下的图片资源存储到 files/myimages/ 下
        getData();          // 从 files/myimages 下获取文件名和文件,初始化 listview 适配器资源文件
        initView();
    }

    // 初始化 ListView
    protected void initView(){
        mFileListView = (ListView)findViewById(R.id.pick_list);
        ImagesListAdapter imagesListAdapter = new ImagesListAdapter(PickActivity.this,mImageNames,mImageBitmaps);
        mFileListView.setAdapter(imagesListAdapter);

        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 当一个文件名在 listView 中被点击时，获取它的 content URI 并发送给请求的 app
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PickActivity.this,"posion "+ position,Toast.LENGTH_SHORT).show();
                Uri fileUri = null;
                Intent mResultIntent = new Intent("com.example.myapp.ACTION_RETURN_FILE");
                File requestFile = new File(mImagesDir,mImageNames.get(position));

                try {
                    fileUri = FileProvider.getUriForFile(
                            PickActivity.this,
                            "google.trainging.com.sharefileserver.fileprovider",
                            requestFile);
                } catch (IllegalArgumentException e) {
                    Log.e("File Selector",
                            "The selected file can't be shared");
                }
                if (fileUri != null) {
                    mResultIntent.addFlags(
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    // Put the Uri and MIME type in the result Intent
                    mResultIntent.setDataAndType(
                            fileUri,
                            getContentResolver().getType(fileUri));
                    // Set the result
                    PickActivity.this.setResult(Activity.RESULT_OK,mResultIntent);

                }else{
                    mResultIntent.setDataAndType(null, "");
                    PickActivity.this.setResult(RESULT_CANCELED,mResultIntent);
                }
            }
        });
    }

    // 从 mipmap 中读取图片文件
    protected  void createImages(){
        mPrivateRootDir = getFilesDir();                              // 获取内部存储根路径
        mImagesDir = new File(mPrivateRootDir, "images");       // 如果 images 文件夹不存在则创建
        if (!mImagesDir.exists()) {
            mImagesDir.mkdirs();
        }
        String[] fileNames = new String[]{"test1.jpg","test2.jpg","test3.jpg"};
        int[] imageIds = new int[]{R.mipmap.test1,R.mipmap.test2,R.mipmap.test3};
        for(int i = 0; i < fileNames.length; i++) {
            File test = new File(mImagesDir, fileNames[i]);
            Bitmap testBitmap = BitmapFactory.decodeResource(getResources(), imageIds[i]);
            saveImages(test, testBitmap);
        }
    }

    // 保存文件到内部目录
    protected  void saveImages(File testFile,Bitmap bitmap){
        FileOutputStream fos = null;
        if (bitmap != null) {
            try {
                fos = new FileOutputStream(testFile);
                // save images to internal storage
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // 从内部存储读取 image 信息
    protected void getData(){
        mImageFiles = mImagesDir.listFiles();
        mImageNames = new ArrayList<>();
        mImageBitmaps = new ArrayList<>();
        for(int i = 0; i < mImageFiles.length; i++){
            try {
                File image = mImageFiles[i];
                if(image.isFile()) {
                    // 获取文件的名字
                    mImageNames.add(image.getName());
                    // 获取文件的 bitmap 对象
                    FileInputStream fis = new FileInputStream(image);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mImageBitmaps.add(bitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class  ImagesListAdapter extends BaseAdapter {
        // 得到一个 LayoutInflater 对象用来导入布局
        private LayoutInflater mInflater;

        List<String> names;
        List<Bitmap> bitmaps;

        public ImagesListAdapter(Context context ,List<String> names,List<Bitmap> bitmaps) {
            this.mInflater = LayoutInflater.from(context);
            this.names = names;
            this.bitmaps = bitmaps;
        }
        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.activity_pick_list_item,null);
                holder = new ViewHolder();

                holder.image_title = (TextView) convertView.findViewById(R.id.image_title);
                holder.image_view = (ImageView) convertView.findViewById(R.id.image_view);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.image_title.setText(names.get(position));
            holder.image_view.setImageBitmap(bitmaps.get(position));
            return convertView;
        }
    }

    public final class ViewHolder{
        public TextView image_title;
        public ImageView image_view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done_select:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

