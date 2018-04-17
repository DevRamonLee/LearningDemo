package google.trainging.com.bitmapandanimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import google.trainging.com.bitmapandanimation.cache.CacheBitmapActivity;
import google.trainging.com.bitmapandanimation.config.HandleCfgChangesActivity;
import google.trainging.com.bitmapandanimation.loadbitmap.HandleConcurrencyActivity;
import google.trainging.com.bitmapandanimation.loadbitmap.SampleBitmapActivity;
import google.trainging.com.bitmapandanimation.memory.ImageCacheActivity;
import google.trainging.com.bitmapandanimation.memory.RecycleBitmapActivity;
import google.trainging.com.bitmapandanimation.ui.ImageDetailActivity;
import google.trainging.com.bitmapandanimation.ui.ImageGridActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button loadSampleBitmap;/*简单加载图片*/
    private Button handleConcurency;/*处理多并发问题*/
    private Button cacheBitmaps;/*缓存图片*/
    private Button cfgChange;/*处理配置改变*/
    private Button manageMemory;/*控制图片内存,Android 2.3 以下，调用recycle方法*/
    private Button optionInBitmap;/*Android 3.0 以上，使用BitmapFactory.Option.inBitmap 属性来重复利用一个Bitmap分配的内存，避免频繁创建和销毁*/
    private Button viewPagerUi;/*使用ViewPager 和 ImageView显示图片*/
    private Button gridView;/*使用GridView 显示图片*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSampleBitmap = (Button) findViewById(R.id.load_sample_bitmap);
        handleConcurency = (Button) findViewById(R.id.handle_concurency);
        cacheBitmaps = (Button) findViewById(R.id.bitmap_cache);
        cfgChange = (Button) findViewById(R.id.cfg_change);
        manageMemory = (Button) findViewById(R.id.manage_memory);
        optionInBitmap = (Button) findViewById(R.id.inBitmap_option);
        viewPagerUi = (Button) findViewById(R.id.view_pager);
        gridView = (Button) findViewById(R.id.grid_view);
        cfgChange.setOnClickListener(this);
        cacheBitmaps.setOnClickListener(this);
        handleConcurency.setOnClickListener(this);
        loadSampleBitmap.setOnClickListener(this);
        manageMemory.setOnClickListener(this);
        optionInBitmap.setOnClickListener(this);
        viewPagerUi.setOnClickListener(this);
        gridView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()){
            case R.id.load_sample_bitmap:
                mIntent = new Intent(MainActivity.this,SampleBitmapActivity.class);
                startActivity(mIntent);
                break;
            case R.id.handle_concurency:
                mIntent = new Intent(MainActivity.this,HandleConcurrencyActivity.class);
                startActivity(mIntent);
                break;
            case R.id.bitmap_cache:
                mIntent = new Intent(MainActivity.this,CacheBitmapActivity.class);
                startActivity(mIntent);
                break;
            case R.id.cfg_change:
                mIntent = new Intent(MainActivity.this,HandleCfgChangesActivity.class);
                startActivity(mIntent);
                break;
            case R.id.manage_memory:
                mIntent = new Intent(MainActivity.this, RecycleBitmapActivity.class);
                startActivity(mIntent);
                break;
            case R.id.inBitmap_option:
                mIntent = new Intent(MainActivity.this, ImageCacheActivity.class);
                startActivity(mIntent);
                break;
            case R.id.view_pager:
                mIntent = new Intent(MainActivity.this, ImageDetailActivity.class);
                startActivity(mIntent);
                break;
            case R.id.grid_view:
                mIntent = new Intent(MainActivity.this, ImageGridActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
