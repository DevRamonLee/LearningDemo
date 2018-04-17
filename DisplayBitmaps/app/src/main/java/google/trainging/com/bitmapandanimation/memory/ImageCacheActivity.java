package google.trainging.com.bitmapandanimation.memory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.lang.ref.WeakReference;
import google.trainging.com.bitmapandanimation.R;
import google.trainging.com.bitmapandanimation.util.BitmapUtil;

/**
 * Created by meng.li on 2018/4/9.
 */

public class ImageCacheActivity extends AppCompatActivity {
    ListView imgList;
    ImageCache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_bitmap);

        /*初始化缓存类*/
        cache = new ImageCache();
        cache.init();


        imgList = (ListView)findViewById(R.id.img_list);
        ImageCacheActivity.ListAdapter listAdapter = new ImageCacheActivity.ListAdapter(ImageCacheActivity.this);
        imgList.setAdapter(listAdapter);
    }

    /*向缓存中添加图片*/
    public void addBitmapToMemoryCache(String key, BitmapDrawable bitmapDrawable) {
        cache.setmMemoryCache(key,bitmapDrawable);
    }

    /*从缓存中读取图片*/
    public BitmapDrawable getBitmapFromMemCache(String key) {
        return cache.getmMemoryCache(key);
    }

    class ListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public ListAdapter(Context context){
            mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return BitmapUtil.imgLists2.length;
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
            ImageCacheActivity.ListAdapter.ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new ImageCacheActivity.ListAdapter.ViewHolder();
                convertView = mInflater.inflate(R.layout.listview_item_layout, null);
                viewHolder.itemImg = (ImageView)convertView.findViewById(R.id.item_img);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ImageCacheActivity.ListAdapter.ViewHolder) convertView.getTag();
            }
            loadBitmap(BitmapUtil.imgLists2[position],viewHolder.itemImg);
            return convertView;
        }
        class ViewHolder{
            public ImageView itemImg;
        }
    }

    /*在执行BitmapWorkerTask 之前，你需要创建一个AsyncDrawable并且将它绑定到目标控件ImageView中*/
    public void loadBitmap(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            /*从缓存读取图片,如果找到了则直接加载，没有找到则执行异步任务加载*/
            final String imageKey = String.valueOf(resId);
            final BitmapDrawable bitmap = getBitmapFromMemCache(imageKey);
            if(bitmap != null){
                Log.i("meng","set from cache memory");
                imageView.setImageDrawable(bitmap);
            }else {
                final ImageCacheActivity.BitmapWorkerTask task = new ImageCacheActivity.BitmapWorkerTask(imageView);
                /*创建一个AsyncDrawable 并绑定到 imageView上*/
                final ImageCacheActivity.AsyncDrawable asyncDrawable =
                        new ImageCacheActivity.AsyncDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(resId);
            }
        }
    }


    /*在非UI线程处理加载图片*/
    class BitmapWorkerTask extends AsyncTask<Integer,Void,BitmapDrawable> {
        private final WeakReference imageViewReference;
        private int data = 0;
        public BitmapWorkerTask(ImageView imageView) {
            /*使用一个弱引用来确保ImageView可以被回收*/
            imageViewReference = new WeakReference(imageView);
        }

        /*在非UI线程中加载图片*/
        @Override
        protected BitmapDrawable doInBackground(Integer... params) {
            data = params[0];
            // Check disk cache in background thread
            BitmapDrawable bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), data, 200, 150,cache);

            /*将解析出的图片加载到缓存中*/
            addBitmapToMemoryCache(String.valueOf(data), bitmap);
            return bitmap;
        }
        @Override
        protected void onPostExecute(BitmapDrawable bitmap) {
            /*判断任务是否被取消了*/
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                final ImageCacheActivity.BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageDrawable(bitmap);
                }
            }
        }
    }

    /*创建一个Drawable的子类来存储任务的引用，在任务执行过程中，一个占位图片会显示在ImageView中*/
    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference bitmapWorkerTaskReference;
        public AsyncDrawable(Resources res, Bitmap bitmap,
                             ImageCacheActivity.BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            /*存储任务的引用*/
            bitmapWorkerTaskReference =
                    new WeakReference(bitmapWorkerTask);
        }
        public ImageCacheActivity.BitmapWorkerTask getBitmapWorkerTask() {
            return (ImageCacheActivity.BitmapWorkerTask)bitmapWorkerTaskReference.get();
        }
    }


    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final ImageCacheActivity.BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

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
    private static ImageCacheActivity.BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof ImageCacheActivity.AsyncDrawable) {
                final ImageCacheActivity.AsyncDrawable asyncDrawable = (ImageCacheActivity.AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}
