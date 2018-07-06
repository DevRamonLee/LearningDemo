package demo.opencv.com.opencvdemo2;

/**
 * Created by meng.li on 2018/6/12.
 */

public class OpenCvHelper extends BaseOpenCvHelper{
    static{
        System.loadLibrary("OpenCV");
    }

    public static native int[] gray(int[] buf,int w,int h);
    public static native String getStringFromJni();

    public int num = 10;
    public native int addNum();

    public static String name = "java";
    public native void accessStaticField();

    private int age = 21;
    public native void accessPrivateField();
    public int getAge() {
        return age;
    }

    private String sex = "female";
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex(){
        return sex;
    }
    public native void accessPublicMethod();

    private static int height = 170;
    public static int getHeight() {
        return height;
    }
    public native int accessStaticMethod();

    public native String accessSuperMethod();


    public native int intArrayMethod(int[] arr);

    public native Person objectMethod(Person person);
}
