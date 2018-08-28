package demo.opencv.com.opencvdemo;

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
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qr);
        tempBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        currentBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, false);
    }

    //提取二维码轮廓
    private void myQrDetect() {
        Map<Integer, MatOfPoint2f> pointLists = new HashMap<>();//保存矩形轮廓的键和坐标信息
        Map<Integer, Double> areaLists = new LinkedHashMap<>();//保存矩形轮廓的键和面积
        List<MatOfPoint> contours = new ArrayList<>();//保存轮廓

        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();

        //将图像转换为灰度
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(grayMat, cannyEdges, 10, 100);
        //找出所有的轮廓
        Imgproc.findContours(cannyEdges, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            //检测contour是否是四边形
            MatOfPoint2f new_mat = new MatOfPoint2f(contours.get(contourIdx).toArray());
            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();

            //对图像轮廓点进行多边形拟合
            Imgproc.approxPolyDP(new_mat, approxCurve_temp, 0.01 * new_mat.total(), true);
            if (approxCurve_temp.total() == 4) {
                double itemArea = Imgproc.contourArea(contours.get(contourIdx));//计算矩形轮廓的面积
                areaLists.put(contourIdx, itemArea);
                pointLists.put(contourIdx, approxCurve_temp);
            }
        }

        //对矩形面积进行排序，由大到小排序
        areaLists = sortMapByValue(areaLists);

        List<List<Point>> pointList = getPositionedRectangles(pointLists, areaLists);

        //计算下面两个点的坐标确定二维码的范围
        Point leftBottom = new Point(Integer.MAX_VALUE, -1); //左下角的点
        Point rightTop = new Point(-1, Integer.MAX_VALUE);//右上角的点

        for (int i = 0; i < pointList.size(); i++) {
            List<Point> points = pointList.get(i);
            for (int j = 0; j < points.size(); j++) {
                if (points.get(j).x <= leftBottom.x && points.get(j).y >= leftBottom.y) {
                    leftBottom = points.get(j);
                }
                if (points.get(j).x >= rightTop.x && points.get(j).y <= rightTop.y) {
                    rightTop = points.get(j);
                }
            }
        }

        int width = (int) (rightTop.x - leftBottom.x);
        int height = (int) (leftBottom.y - rightTop.y);

        /*提取出二维码区域,并保存为 bitmap*/
        Rect qrRect = new Rect((int)leftBottom.x,(int)rightTop.y,width,height);//二维码区域
        Mat qrMat = originalMat.submat(qrRect);
        /* qrMat 与 bitmap 宽高要一致，不然 matToBitmap()会抛出 generator/src/cpp/utils.cpp:97: error: (-215)*/
        Bitmap qrBitmap = Bitmap.createBitmap(qrMat.cols(), qrMat.rows(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(qrMat, qrBitmap);
        imageCurrent.setImageBitmap(qrBitmap);
    }

    int num = 0;
    //计算得出三个定位二维码的矩形坐标
    public List<List<Point>> getPositionedRectangles(Map<Integer, MatOfPoint2f> pointLists, Map<Integer, Double> areaLists) {
        List<List<Point>> result = new ArrayList<List<Point>>();
        List<Integer> keys = new ArrayList<>();
        for (int key : areaLists.keySet()) {
            keys.add(key);
        }
        boolean flag;
        for (int i = 0; i < keys.size(); i++) {
            flag = true;
            if (areaLists.get(keys.get(i)) > 0) {
                //判断里面是否包含一个小矩形，面积比为1.96左右
                for (int j = i +1; j < keys.size()&& flag; j++) {
                    //判断这个小矩形是否包含在特征矩形内
                    if (checkIsInner(pointLists.get(keys.get(i)), pointLists.get(keys.get(j)))) {
                        //计算两个矩形的面积比,大矩形面积是否是小矩形面积的 1.9 倍左右
                        if (areaLists.get(keys.get(j)) != 0) {
                            double divideResult = areaLists.get(keys.get(i)) / areaLists.get(keys.get(j));
                            if (divideResult > 1.8 && divideResult < 2.1) {
                                for(int k = j + 1; k < keys.size() && flag; k++) {
                                    if(checkIsInner(pointLists.get(keys.get(j)),pointLists.get(keys.get(k)))) {
                                        //判断小矩形内部是否还包含一个更小的矩形，面积比为 2.88 倍左右
                                        double divideResult2 = areaLists.get(keys.get(j)) / areaLists.get(keys.get(k));
                                        if(divideResult2 > 2.5 && divideResult2 < 3.0) {
                                            List<Point> tempPoint = calculatePoints(pointLists.get(keys.get(i)));
                                            if (tempPoint != null && tempPoint.size() > 0)
                                                result.add(tempPoint);
                                            flag = false;
                                        }
                                    }
                                }
                                //没有检测到小矩形内部还包含一个更小的矩形
                                if(result.size() == num ){
                                    num ++;
                                    List<Point> tempPoint = calculatePoints(pointLists.get(keys.get(i)));
                                    if (tempPoint != null && tempPoint.size() > 0)
                                        result.add(tempPoint);
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }


    public Map<Integer, Double> sortMapByValue(Map<Integer, Double> oriMap) {
        Map<Integer, Double> sortedMap = new LinkedHashMap<>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Map.Entry<Integer, Double>> entryList = new ArrayList<Map.Entry<Integer, Double>>(oriMap.entrySet());
            Collections.sort(entryList,
                    new Comparator<Map.Entry<Integer, Double>>() {
                        public int compare(Map.Entry<Integer, Double> entry1,
                                           Map.Entry<Integer, Double> entry2) {
                            int value1 = 0, value2 = 0;
                            try {
                                value1 = (int) entry1.getValue().doubleValue();
                                value2 = (int) entry2.getValue().doubleValue();
                            } catch (NumberFormatException e) {
                                value1 = 0;
                                value2 = 0;
                            }
                            return value2 - value1;
                        }
                    });
            Iterator<Map.Entry<Integer, Double>> iter = entryList.iterator();
            Map.Entry<Integer, Double> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }

    //判断小矩阵是否在大矩阵内
    private boolean checkIsInner(MatOfPoint2f bigRectangle, MatOfPoint2f smallRectangle) {
        boolean isInner = false;
        List<Point> bigPoint = calculatePoints(bigRectangle);
        List<Point> smallPoint = calculatePoints(smallRectangle);

        if ((smallPoint.get(0).x > bigPoint.get(0).x && smallPoint.get(0).y > bigPoint.get(0).y)
                && (smallPoint.get(1).x < bigPoint.get(1).x && smallPoint.get(1).y > bigPoint.get(1).y)
                && (smallPoint.get(2).x > bigPoint.get(2).x && smallPoint.get(2).y < bigPoint.get(2).y)
                && (smallPoint.get(3).x < bigPoint.get(3).x && smallPoint.get(3).y < bigPoint.get(3).y)) {
            //满足上面这个条件，说明小矩形在大矩形内部
            isInner = true;
        }
        return isInner;
    }

    private List<Point> calculatePoints(MatOfPoint2f approxCurve) {
        double[] temp_double = approxCurve.get(0, 0);
        Point point1 = new Point(temp_double[0], temp_double[1]);

        temp_double = approxCurve.get(1, 0);
        Point point2 = new Point(temp_double[0], temp_double[1]);

        temp_double = approxCurve.get(2, 0);
        Point point3 = new Point(temp_double[0], temp_double[1]);
        temp_double = approxCurve.get(3, 0);

        Point point4 = new Point(temp_double[0], temp_double[1]);

        List<Point> source = new ArrayList<>();
        source.add(point1);
        source.add(point2);
        source.add(point3);
        source.add(point4);
        //对4个点进行排序
        Point centerPoint = new Point(0, 0);//质心
        for (Point corner : source) {
            centerPoint.x += corner.x;
            centerPoint.y += corner.y;
        }
        centerPoint.x = centerPoint.x / source.size();
        centerPoint.y = centerPoint.y / source.size();
        Point lefttop = new Point();
        Point righttop = new Point();
        Point leftbottom = new Point();
        Point rightbottom = new Point();
        for (int i = 0; i < source.size(); i++) {
            if (source.get(i).x < centerPoint.x && source.get(i).y < centerPoint.y) {
                lefttop = source.get(i);
            } else if (source.get(i).x > centerPoint.x && source.get(i).y < centerPoint.y) {
                righttop = source.get(i);
            } else if (source.get(i).x < centerPoint.x && source.get(i).y > centerPoint.y) {
                leftbottom = source.get(i);
            } else if (source.get(i).x > centerPoint.x && source.get(i).y > centerPoint.y) {
                rightbottom = source.get(i);
            }
        }
        source.clear();
        source.add(lefttop);
        source.add(righttop);
        source.add(leftbottom);
        source.add(rightbottom);

        return source;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_detect:
                myQrDetect();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
