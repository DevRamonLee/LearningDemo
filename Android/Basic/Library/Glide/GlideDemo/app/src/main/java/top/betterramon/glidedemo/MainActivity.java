package top.betterramon.glidedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button btnLoad;
    private ImageView imageBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndloadData();
    }

    private void initViewAndloadData () {
        btnLoad = findViewById(R.id.btn_load);
        imageBook = findViewById(R.id.image_book);




        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                    String url = "http://www.guolin.tech/book.png";
                                    final Context context = getApplicationContext();

                                    RequestOptions options = new RequestOptions()
                                            .circleCrop();

                                    Glide.with(context)
                                            .load(url)
                                            .apply(options)
                                            .into(imageBook);

            }
        });
    }
}
