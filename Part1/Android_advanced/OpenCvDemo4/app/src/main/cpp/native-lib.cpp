#include <jni.h>
#include <string>
#include <opencv/cv.h>
#include <opencv2/opencv.hpp>

using namespace cv;

extern "C"
JNIEXPORT jintArray JNICALL
Java_li_ramon_better_opencvdemo4_MainActivity_gray(JNIEnv *env, jobject instance, jintArray buf_,
                                                   jint w, jint h) {
    jint *buf = env->GetIntArrayElements(buf_, NULL);
    if (NULL == buf)
    {
        return 0;
    }

    Mat imgData(h,w,CV_8UC4,(unsigned char*) buf);

    u_char *ptr = imgData.ptr(0);
    for (int i = 0; i < w*h; ++i)
    {
        //图像存储方式为：BGRA
        int grayScale = (int)(ptr[4*i+2]*0.299 + ptr[4*i+1]*0.587 + ptr[4*i+0]*0.144 );
        ptr[4*i+0] = grayScale;
        ptr[4*i+1] = grayScale;
        ptr[4*i+2] = grayScale;
    }

    int size = w * h;
    jintArray  result = env->NewIntArray(size);
    env->SetIntArrayRegion(result,0,size,buf);
    env->ReleaseIntArrayElements(buf_,buf,0);
    return result;
}