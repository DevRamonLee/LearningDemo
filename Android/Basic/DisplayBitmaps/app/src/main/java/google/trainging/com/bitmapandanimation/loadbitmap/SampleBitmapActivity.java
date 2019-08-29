package google.trainging.com.bitmapandanimation.loadbitmap;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import google.trainging.com.bitmapandanimation.R;
import google.trainging.com.bitmapandanimation.util.BitmapUtil;

// 加载压缩的图片
public class SampleBitmapActivity extends AppCompatActivity {
    ImageView sampleImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_bitmap);
        sampleImg = (ImageView) findViewById(R.id.sample_img);

        // UI 线程中直接设置图片
        /*sampleImg.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.img, 368, 495));*/

        // 调用异步加载图片
        loadBitmap(R.drawable.img, sampleImg);
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Nullable, Bitmap> {
        private final WeakReference imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // 使用一个弱引用来确保 ImageView 可以被回收
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            // 在非 UI 线程中加载图片
            return BitmapUtil.decodeSampledBitmapFromResource(getResources(), data, 368, 495);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    // 异步加载图片
    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resId);
    }
}
