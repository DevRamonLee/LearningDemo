package ramon.lee.androidui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class MatrixBasic extends View {
    private static final String TAG = "MatrixBasic";
    private Paint mPaint;
    public MatrixBasic(Context context) {
        this(context, null);
    }

    public MatrixBasic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();                   // 创建画笔
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * mapPoints 重载方法一
         */
        /*// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
        float[] pts = new float[]{0, 0, 80, 100, 400, 300};
        // 构造一个matrix，x坐标缩放0.5
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);
        // 输出pts计算之前数据
        Log.i(TAG, "before: "+ Arrays.toString(pts));
        // 调用map方法计算
        matrix.mapPoints(pts);
        // 输出pts计算之后数据
        Log.i(TAG, "after : "+ Arrays.toString(pts));*/

        /**
         * mapPoints (float[] dst, float[] src) ，src作为参数传递原始数值，计算结果存放在dst中，src不变。
         */
        /*// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
        float[] src = new float[]{0, 0, 80, 100, 400, 300};
        float[] dst = new float[6];

        // 构造一个matrix，x坐标缩放0.5
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);

        // 输出计算之前数据
        Log.i(TAG, "before: src="+ Arrays.toString(src));
        Log.i(TAG, "before: dst="+ Arrays.toString(dst));

        // 调用map方法计算
        matrix.mapPoints(dst,src);

        // 输出计算之后数据
        Log.i(TAG, "after : src="+ Arrays.toString(src));
        Log.i(TAG, "after : dst="+ Arrays.toString(dst));*/

        /**
         * 指定计算一部分数值
         * void mapPoints (float[] dst, int dstIndex,float[] src, int srcIndex, int pointCount)
         */
        /*// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
        float[] src = new float[]{0, 0, 80, 100, 400, 300};
        float[] dst = new float[6];

        // 构造一个matrix，x坐标缩放0.5
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);

        // 输出计算之前数据
        Log.i(TAG, "before: src="+ Arrays.toString(src));
        Log.i(TAG, "before: dst="+ Arrays.toString(dst));

        // 调用map方法计算(最后一个2表示两个点，即四个数值,并非两个数值)
        matrix.mapPoints(dst, 0, src, 2, 2);

        // 输出计算之后数据
        Log.i(TAG, "after : src="+ Arrays.toString(src));
        Log.i(TAG, "after : dst="+ Arrays.toString(dst));*/

        /**
         * mapRadius 测量平均半径
         */
        /*float radius = 100;
        float result = 0;

        // 构造一个matrix，x坐标缩放0.5
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);

        Log.i(TAG, "mapRadius: "+radius);
        result = matrix.mapRadius(radius);
        Log.i(TAG, "mapRadius: "+result);*/

        /**
         * 测量矩形变换后的位置
         */
        /*RectF rect = new RectF(400, 400, 1000, 800);

        // 构造一个matrix
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);
        matrix.postSkew(1,0);

        Log.i(TAG, "mapRadius: "+rect.toString());

        boolean result = matrix.mapRect(rect);

        Log.i(TAG, "mapRadius: "+rect.toString());
        // 由于使用了错切，所以返回 false
        Log.e(TAG, "isRect: "+ result);*/

        /**
         * 测量向量，向量不受位移的影响
         */
        /*float[] src = new float[]{1000, 800};
        float[] dst = new float[2];

        // 构造一个matrix
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);
        matrix.postTranslate(100,100);

        // 计算向量, 不受位移影响
        matrix.mapVectors(dst, src);
        Log.i(TAG, "mapVectors: "+Arrays.toString(dst));

        // 计算点
        matrix.mapPoints(dst, src);
        Log.i(TAG, "mapPoints: "+Arrays.toString(dst));*/

        /**
         * 获取 View 在屏幕中的位置
         */
        float[] values = new float[9];
        int[] location1 = new int[2];

        Matrix matrix = canvas.getMatrix();
        matrix.getValues(values);

        location1[0] = (int) values[2];
        location1[1] = (int) values[5];
        Log.i(TAG, "location1 = " + Arrays.toString(location1));

        int[] location2 = new int[2];
        this.getLocationOnScreen(location2);
        Log.i(TAG, "location2 = " + Arrays.toString(location2));
    }
}