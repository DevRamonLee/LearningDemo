package google.trainging.com.bitmapandanimation.config;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.lang.ref.WeakReference;

import google.trainging.com.bitmapandanimation.util.BitmapUtil;
import google.trainging.com.bitmapandanimation.R;

/*处理配置改变，如横竖屏旋转*/
public class HandleCfgChangesActivity extends AppCompatActivity {

    private LruCache<String, Bitmap> mMemoryCache;
    ListView imgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_bitmap);

        RetainFragment retainFragment =
                RetainFragment.findOrCreateRetainFragment(getFragmentManager());
        mMemoryCache = retainFragment.mRetainedCache;
        if (mMemoryCache == null) {
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
            retainFragment.mRetainedCache = mMemoryCache;
        }

        imgList = (ListView)findViewById(R.id.img_list);
        HandleCfgChangesActivity.ListAdapter listAdapter = new HandleCfgChangesActivity.ListAdapter(HandleCfgChangesActivity.this);
        imgList.setAdapter(listAdapter);
    }

    /*向缓存中添加图片*/
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
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
            HandleCfgChangesActivity.ListAdapter.ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new HandleCfgChangesActivity.ListAdapter.ViewHolder();
                convertView = mInflater.inflate(R.layout.listview_item_layout, null);
                viewHolder.itemImg = (ImageView)convertView.findViewById(R.id.item_img);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (HandleCfgChangesActivity.ListAdapter.ViewHolder) convertView.getTag();
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
            // Check disk cache in background thread
            Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), data, 300, 250);
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
                final HandleCfgChangesActivity.BitmapWorkerTask bitmapWorkerTask =
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
                             HandleCfgChangesActivity.BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            /*存储任务的引用*/
            bitmapWorkerTaskReference =
                    new WeakReference(bitmapWorkerTask);
        }
        public HandleCfgChangesActivity.BitmapWorkerTask getBitmapWorkerTask() {
            return (HandleCfgChangesActivity.BitmapWorkerTask)bitmapWorkerTaskReference.get();
        }
    }
    /*在执行BitmapWorkerTask 之前，你需要创建一个AsyncDrawable并且将它绑定到目标控件ImageView中*/
    public void loadBitmap(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            /*从缓存读取图片,如果找到了则直接加载，没有找到则执行异步任务加载*/
            final String imageKey = String.valueOf(resId);
            final Bitmap bitmap = getBitmapFromMemCache(imageKey);
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }else {
                final HandleCfgChangesActivity.BitmapWorkerTask task = new HandleCfgChangesActivity.BitmapWorkerTask(imageView);
                /*创建一个AsyncDrawable 并绑定到 imageView上*/
                final HandleCfgChangesActivity.AsyncDrawable asyncDrawable =
                        new HandleCfgChangesActivity.AsyncDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(resId);
            }
        }
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final HandleCfgChangesActivity.BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

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
    private static HandleCfgChangesActivity.BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof HandleCfgChangesActivity.AsyncDrawable) {
                final HandleCfgChangesActivity.AsyncDrawable asyncDrawable = (HandleCfgChangesActivity.AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}
