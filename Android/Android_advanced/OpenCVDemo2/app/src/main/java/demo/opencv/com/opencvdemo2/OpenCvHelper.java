package demo.opencv.com.opencvdemo2;

/**
 * Created by meng.li on 2018/6/12.
 */

public class OpenCvHelper{
    static{
        System.loadLibrary("OpenCV");
    }
    public static native int[] gray(int[] buf,int w,int h);
}
