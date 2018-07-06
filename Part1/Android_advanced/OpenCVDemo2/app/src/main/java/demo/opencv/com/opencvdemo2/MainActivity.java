package demo.opencv.com.opencvdemo2;

import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button grayBtn;
    private Button originalBtn;
    private Button binaryBtn;

    OpenCvHelper helper = new OpenCvHelper();

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
        binaryBtn = findViewById(R.id.binary_button);

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

        binaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binaryImage();
            }
        });
    }

    /*显示原图*/
    protected  void originalImage(){
        toastMessage("显示原图");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.food);
        imageView.setImageBitmap(bitmap);
    }

    /*灰度化显示图片*/
    protected  void greyImage(){
        toastMessage("显示灰度图片");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.food);

        /*以下进行灰度化处理*/
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int[] resultPixels = OpenCvHelper.gray(pix, w, h);
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        result.setPixels(resultPixels,0,w,0,0,w,h);
        imageView.setImageBitmap(result);
    }

    /*显示二值化的图片*/
    protected void binaryImage(){
        //从 jni 获取一个字符串
        //toastMessage(OpenCvHelper.getStringFromJni());

        /*jni 获取java实例变量的值*/

        //toastMessage("调用前 num = " + helper.num);
        //toastMessage("调用后 num = " + helper.addNum());

        /*jni 获取java静态变量的值*/
        /*toastMessage("调用前 name = " + OpenCvHelper.name);
        helper.accessStaticField();
        toastMessage("调用后 name = " + OpenCvHelper.name);*/

        /*jni 获取private类型的变量*/
        /*toastMessage("调用前 age = " + helper.getAge());
        helper.accessPrivateField();
        toastMessage("调用后 age = " + helper.getAge());*/

        /*jni 调用 public 类型的方法*/
        /*toastMessage("调用前sex =  " + helper.getSex());
        helper.accessPublicMethod();
        toastMessage("调用后sex = " + helper.getSex());*/

        /*jni调用静态方法*/
        //toastMessage("调用静态方法 height = " + OpenCvHelper.getHeight());

        /*jni调用父类的方法*/
        //toastMessage("调用父类方法 " + helper.accessSuperMethod());

        /*java 向jni传递数组类型的参数*/
        //toastMessage("数组的和为： " + helper.intArrayMethod(new int[]{1,2,3}));

        /*java 想jni传递自定义对象类型的参数*/
        toastMessage("Person is " + helper.objectMethod(new Person()).toString());
    }

    private void toastMessage(String message){
        Toast.makeText(MainActivity.this,message,
                Toast.LENGTH_SHORT).show();
    }
}
