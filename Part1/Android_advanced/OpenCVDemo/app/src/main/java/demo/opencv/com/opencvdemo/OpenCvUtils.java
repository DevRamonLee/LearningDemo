package demo.opencv.com.opencvdemo;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by meng.li on 2018/6/26.
 */

public class OpenCvUtils {
    /*利用高斯模糊图像相减获取边缘*
        1. 将图像转换为灰度图
        2. 用两个不同模糊半径对灰度图执行高斯模糊得到两幅高斯模糊后的图
        3. 将上一步得到的两幅图执行算术相减
     */
    protected static Mat differenceOfGaussian(Mat originalMat) {
        Mat grayMat = new Mat();
        Mat blur1 = new Mat();
        Mat blur2 = new Mat();

        //将图像转换为灰度图
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        //以两个不同的模糊半径对图像做模糊处理
        Imgproc.GaussianBlur(grayMat, blur1, new Size(15, 15), 5);
        Imgproc.GaussianBlur(grayMat, blur2, new Size(21, 21), 5);

        //将两幅模糊后的图相减
        Mat DoG = new Mat();
        Core.absdiff(blur1, blur2, DoG);

        //反转二值阈值化，将边缘点转换为白色
        Core.multiply(DoG, new Scalar(100), DoG);
        Imgproc.threshold(DoG, DoG, 50, 255, Imgproc.THRESH_BINARY_INV);

        return  DoG;
    }

    //Canny 边缘检测
    protected static Mat Canny(Mat originalMat) {
        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        //将图像转化为灰度
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);
        //进行Canny 边缘检测
        Imgproc.Canny(grayMat, cannyEdges, 10, 100);
        return cannyEdges;
    }

    //Sobel 算子
    protected static Mat sobel(Mat originalMat) {
        Mat grayMat = new Mat();
        Mat sobel = new Mat();//用来保存结果的Mat

        //分别用来保存梯度和绝对值的Mat
        Mat grad_x = new Mat();
        Mat abs_grad_x = new Mat();

        Mat grad_y = new Mat();
        Mat abs_grad_y = new Mat();

        //将图像转化为灰度
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        //计算水平方向梯度
        Imgproc.Sobel(grayMat, grad_x, CvType.CV_16S, 1, 0, 3, 1, 0);

        //计算垂直方向的梯度
        Imgproc.Sobel(grayMat, grad_y, CvType.CV_16S, 0, 1, 3, 1, 0);

        //计算两个方向上的梯度绝对值
        Core.convertScaleAbs(grad_x, abs_grad_x);
        Core.convertScaleAbs(grad_y, abs_grad_y);

        //计算结果梯度
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 1, sobel);
        return sobel;
    }

    //Harris 角点检测,两条直线的交点
    protected static Mat HarrisCorner(Mat originalMat) {
        Mat grayMat = new Mat();
        Mat corners = new Mat();

        //将图像转化为灰度图
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        Mat tempDst = new Mat();
        //找出角点
        Imgproc.cornerHarris(grayMat, tempDst, 2, 3, 0.04);

        //归一化Harris角点的输出
        Mat tempDstNorm = new Mat();
        Core.normalize(tempDst, tempDstNorm, 0, 255, Core.NORM_MINMAX);
        Core.convertScaleAbs(tempDstNorm, corners);

        //在新的图像上绘制角点
        Random r = new Random();
        for (int i = 0; i < tempDstNorm.cols(); i++) {
            for (int j = 0; j < tempDstNorm.rows(); j++) {
                double[] values = tempDstNorm.get(j, i);
                if (values[0] > 150)
                    Imgproc.circle(corners, new Point(i, j), 5, new Scalar(255), 2);
            }
        }
        return  corners;
    }

    //霍夫直线检测
    protected static Mat HoughLines(Mat originalMat) {
        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        Mat lines = new Mat();
        //将图像转换成灰度
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);
        //这里使用Canny边缘检测技术计算图像中的边缘，使用其他的也可以
        Imgproc.Canny(grayMat, cannyEdges, 10, 100);
        Imgproc.HoughLinesP(cannyEdges, lines, 1, Math.PI / 180, 50, 20, 20);
        Mat houghLines = new Mat();
        houghLines.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);
        //在图像上绘制直线
        for (int i = 0; i < lines.cols(); i++) {
            double[] points = lines.get(0, i);
            double x1, y1, x2, y2;
            x1 = points[0];
            y1 = points[1];
            x2 = points[2];
            y2 = points[3];
            Point pt1 = new Point(x1, y1);
            Point pt2 = new Point(x2, y2);
            //在一副图像上绘制直线
            Imgproc.line(houghLines, pt1, pt2, new Scalar(255, 0, 0), 1);
        }
        return  houghLines;
    }

    //霍夫圆
    protected static Mat HoughCircles(Mat originalMat) {
        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        Mat circles = new Mat();

        //将图像转化成灰度
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(grayMat, cannyEdges, 10, 100);

        Imgproc.HoughCircles(cannyEdges, circles, Imgproc.CV_HOUGH_GRADIENT, 1, cannyEdges.rows() / 15);
        //,grayMat.rows() / 8);

        Mat houghCircles = new Mat();
        houghCircles.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);

        //在图像上画圆
        for (int i = 0; i < circles.cols(); i++) {
            double[] parameters = circles.get(0, i);
            double x, y;
            int r;

            x = parameters[0];
            y = parameters[1];
            r = (int) parameters[2];

            Point center = new Point(x, y);
            Imgproc.circle(houghCircles, center, r, new Scalar(255, 0, 0), 1);
        }
        return  houghCircles;
    }

    //轮廓检测
    protected static Mat contours(Mat originalMat) {
        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        Mat hierarchy = new Mat();

        //保存所有轮廓的列表
        List<MatOfPoint> contourList = new ArrayList<MatOfPoint>();

        //将图像转换为灰度
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        Imgproc.Canny(grayMat, cannyEdges, 10, 100);

        //找出轮廓
        Imgproc.findContours(cannyEdges, contourList, hierarchy
                , Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);

        //在新的图像上绘制轮廓
        Mat contours = new Mat();
        contours.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);

        Random r = new Random();
        for (int i = 0; i < contourList.size(); i++) {
            Imgproc.drawContours(contours, contourList, i,
                    new Scalar(r.nextInt(255), r.nextInt(255)
                            , r.nextInt(255)), 1);
        }
       return contours;
    }
}
