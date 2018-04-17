package google.trainging.com.bitmapandanimation.memory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import google.trainging.com.bitmapandanimation.R;
import google.trainging.com.bitmapandanimation.util.BitmapUtil;

public class RecycleBitmapActivity extends AppCompatActivity
                                    implements View.OnClickListener{

    private Button nextBtn;
    private Button previousBtn;
    private ImageView mImg;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_bitmap);
        init();
    }
    protected void init(){
        nextBtn = (Button) findViewById(R.id.next);
        previousBtn = (Button) findViewById(R.id.previous);
        mImg = (ImageView) findViewById(R.id.recycleImageView);
        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        loadBitmap(BitmapUtil.imgLists[0],mImg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous:
                index--;
                if(index >= 0) {
                    loadBitmap(BitmapUtil.imgLists[index], mImg);
                }else {
                    index = 0;
                }
                break;
            case R.id.next:
                index++;
                if(index < BitmapUtil.imgLists.length) {
                    loadBitmap(BitmapUtil.imgLists[index], mImg);
                }else{
                    index = BitmapUtil.imgLists.length - 1;
                }
                break;
        }
    }

    /*在非UI线程处理加载图片*/
    class BitmapWorkerTask extends AsyncTask<Integer,Nullable,Bitmap> {
        private final WeakReference imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            /*使用一个弱引用来确保ImageView可以被回收*/
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            /*在非UI线程中加载图片*/
            return BitmapUtil.decodeSampledBitmapFromResource(getResources(), data , 368, 495);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = (ImageView) imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageDrawable(new RecycleBitmapDrawable(getResources(),bitmap));
                }
            }
        }
    }

    /*开始异步加载位图*/
    public void loadBitmap(int resId, ImageView imageView) {
        RecycleBitmapActivity.BitmapWorkerTask task = new RecycleBitmapActivity.BitmapWorkerTask(imageView);
        task.execute(resId);
    }
}
