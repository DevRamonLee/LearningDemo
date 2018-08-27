package li.ramon.better.opencvdemo1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity {

    Bitmap originalBitmap;
    Bitmap tempBitmap;
    Bitmap currentBitmap;
    Mat originalMat;

    ImageView imageOrigional;
    ImageView imageCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imageOrigional = findViewById(R.id.original);
        imageCurrent = findViewById(R.id.current);
        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        //copy 方法第一个参数表示结构，第二个参数如果为true，表示产生的图片可变（如像素可变）
        tempBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        currentBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, false);
    }

    //opencv 加载回调接口
    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    //原始图的矩阵
                    originalMat = new Mat(tempBitmap.getHeight(), tempBitmap.getWidth(), CvType.CV_8U);
                    Utils.bitmapToMat(tempBitmap, originalMat);//bitmap 转换为Mat
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //实例化opencv 加载环境
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0,
                this, mOpenCVCallBack);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opencv_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadImage(Mat img) {
        Utils.matToBitmap(img, currentBitmap);
        imageCurrent.setImageBitmap(currentBitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Mat result = null;
        switch (item.getItemId()) {
            case R.id.gaussian:
                result = OpenCvUtils.differenceOfGaussian(originalMat);
                break;
            case R.id.canny:
                result = OpenCvUtils.Canny(originalMat);
                break;
            case R.id.sobel:
                result = OpenCvUtils.sobel(originalMat);
                break;
            case R.id.harrisCorner:
                result = OpenCvUtils.HarrisCorner(originalMat);
                break;
            case R.id.houghLines:
                result = OpenCvUtils.HoughLines(originalMat);
                break;
            case R.id.houghCircles:
                result = OpenCvUtils.HoughCircles(originalMat);
                break;
            case R.id.contours:
                result = OpenCvUtils.contours(originalMat);
                break;
            case R.id.hist:
                result = OpenCvUtils.hist(originalMat);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        if (result != null) {
            loadImage(result);
        }
        return true;
    }
}
