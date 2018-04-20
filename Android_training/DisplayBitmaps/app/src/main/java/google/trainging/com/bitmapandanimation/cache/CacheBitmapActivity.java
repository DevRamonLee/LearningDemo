package google.trainging.com.bitmapandanimation.cache;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import google.trainging.com.bitmapandanimation.R;
import google.trainging.com.bitmapandanimation.util.BitmapUtil;

import static android.os.Environment.isExternalStorageRemovable;

/*使用内存缓存和硬盘缓存来缓存图片*/
public class CacheBitmapActivity extends AppCompatActivity {
    private LruCache<String, Bitmap> mMemoryCache;
    ListView imgList;

    /*disk cache begin@{*/
    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";
    /*disk cache end@}*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_bitmap);

        /*获取VM 最大可用内存*/
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        /*使用最大可用内存的 1/8 作为缓存大小*/
        /*这里有个问题，如果设置的缓存过小而单个图片过大的话，会看不到缓存的效果*/
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                /*计算每次添加图片的大小*/
                return bitmap.getByteCount() / 1024;
            }
        };

        /*初始化硬盘缓存 begin @{*/
        File cacheDir = getDiskCacheDir(this, DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);
        /*end@}*/

        imgList = (ListView)findViewById(R.id.img_list);
        CacheBitmapActivity.ListAdapter listAdapter = new CacheBitmapActivity.ListAdapter(CacheBitmapActivity.this);
        imgList.setAdapter(listAdapter);
    }

    /*初始化DiskLruCache */
    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                File cacheDir = params[0];
                try {
                    mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE);
                    mDiskCacheStarting = false; // Finished initialization
                    mDiskCacheLock.notifyAll(); // Wake any waiting threads
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /*向缓存中添加图片*/
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }

        /*同时添加到硬盘缓存*/
        try {
            synchronized (mDiskCacheLock) {
                if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if(editor != null){
                        OutputStream out = editor.newOutputStream(0);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                        editor.commit();
                    }
                    mDiskLruCache.flush();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Bitmap getBitmapFromDiskCache(String key){
        synchronized (mDiskCacheLock) {
            // Wait while disk cache is started from background thread
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {}
            }
            try {
                DiskLruCache.Snapshot snap = mDiskLruCache.get(key);
                if (mDiskLruCache != null && snap != null) {
                    InputStream in = snap.getInputStream(0);
                    return BitmapFactory.decodeStream(in);
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
    // but if not mounted, falls back on internal storage.
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();
        Log.i("meng","cache path is "+cachePath);
        return new File(cachePath + File.separator + uniqueName);
    }


    /*从缓存中读取图片*/
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    class ListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public ListAdapter(Context context){
            mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return BitmapUtil.imgLists.length;
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
            CacheBitmapActivity.ListAdapter.ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new CacheBitmapActivity.ListAdapter.ViewHolder();
                convertView = mInflater.inflate(R.layout.listview_item_layout, null);
                viewHolder.itemImg = (ImageView)convertView.findViewById(R.id.item_img);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (CacheBitmapActivity.ListAdapter.ViewHolder) convertView.getTag();
            }
            loadBitmap(BitmapUtil.imgLists[position],viewHolder.itemImg);
            return convertView;
        }
        class ViewHolder{
            public ImageView itemImg;
        }
    }


    /*在非UI线程处理加载图片*/
    class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap> {
        private final WeakReference imageViewReference;
        private int data = 0;
        public BitmapWorkerTask(ImageView imageView) {
            /*使用一个弱引用来确保ImageView可以被回收*/
            imageViewReference = new WeakReference(imageView);
        }

        /*在非UI线程中加载图片*/
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            final String imageKey = String.valueOf(params[0]);

            // Check disk cache in background thread
            Bitmap bitmap = getBitmapFromDiskCache(imageKey);
            Log.i("meng","bitmap is null "+(bitmap == null));

            if (bitmap == null) { // Not found in disk cache
                bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), data, 200, 150);
            }
            /*将解析出的图片加载到缓存中*/
            addBitmapToMemoryCache(String.valueOf(data), bitmap);
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            /*判断任务是否被取消了*/
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                final CacheBitmapActivity.BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    /*创建一个Drawable的子类来存储任务的引用，在任务执行过程中，一个占位图片会显示在ImageView中*/
    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference bitmapWorkerTaskReference;
        public AsyncDrawable(Resources res, Bitmap bitmap,
                             CacheBitmapActivity.BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            /*存储任务的引用*/
            bitmapWorkerTaskReference =
                    new WeakReference(bitmapWorkerTask);
        }
        public CacheBitmapActivity.BitmapWorkerTask getBitmapWorkerTask() {
            return (CacheBitmapActivity.BitmapWorkerTask)bitmapWorkerTaskReference.get();
        }
    }
    /*在执行BitmapWorkerTask 之前，你需要创建一个AsyncDrawable并且将它绑定到目标控件ImageView中*/
    public void loadBitmap(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            /*从缓存读取图片,如果找到了则直接加载，没有找到则执行异步任务加载*/
            final String imageKey = String.valueOf(resId);
            final Bitmap bitmap = getBitmapFromMemCache(imageKey);
            if(bitmap != null){
                Log.i("meng","set from cache memory");
                imageView.setImageBitmap(bitmap);
            }else {
                final CacheBitmapActivity.BitmapWorkerTask task = new CacheBitmapActivity.BitmapWorkerTask(imageView);
                /*创建一个AsyncDrawable 并绑定到 imageView上*/
                final CacheBitmapActivity.AsyncDrawable asyncDrawable =
                        new CacheBitmapActivity.AsyncDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(resId);
            }
        }
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final CacheBitmapActivity.BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData == 0 || bitmapData != data) {
                /*调用cancel()其实是给AsyncTask设置一个"canceled"状态*/
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }
    /*获取imageView绑定的异步任务*/
    private static CacheBitmapActivity.BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof CacheBitmapActivity.AsyncDrawable) {
                final CacheBitmapActivity.AsyncDrawable asyncDrawable = (CacheBitmapActivity.AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}
