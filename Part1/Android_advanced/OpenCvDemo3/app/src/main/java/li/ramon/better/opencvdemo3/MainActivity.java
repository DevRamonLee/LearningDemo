package li.ramon.better.opencvdemo3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button grayBtn;
    private Button originalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    protected  void initView(){
        imageView = findViewById(R.id.img);
        grayBtn = findViewById(R.id.gray_button);
        originalBtn = findViewById(R.id.original_button);

        grayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greyImage();
            }
        });

        originalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalImage();
            }
        });
    }

    /*显示原图*/
    protected  void originalImage(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.food);
        imageView.setImageBitmap(bitmap);
    }

    /*灰度化显示图片*/
    protected  void greyImage(){
        /*使用opencv包进行灰度化处理*/
        OpenCVLoader.initDebug();

        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.food);
        Bitmap grayBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.RGB_565);

        Utils.bitmapToMat(bitmap,rgbMat);
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);
        Utils.matToBitmap(grayMat,grayBitmap);
        imageView.setImageBitmap(grayBitmap);
    }
}
