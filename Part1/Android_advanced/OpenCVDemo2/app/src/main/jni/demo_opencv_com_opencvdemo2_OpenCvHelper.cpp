//
// Created by meng.li on 2018/6/12.
//
#include "demo_opencv_com_opencvdemo2_OpenCvHelper.h"
#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>

using namespace cv;

extern "C" {

    JNIEXPORT jintArray JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_gray
      (JNIEnv *, jclass, jintArray, jint, jint);

    JNIEXPORT jintArray JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_gray(JNIEnv *env,jclass obj,jintArray buf,int w,int h)
    {
        jint *cbuf;
        cbuf = env->GetIntArrayElements(buf,JNI_FALSE);
        if (NULL == cbuf)
        {
            return 0;
        }

        Mat imgData(h,w,CV_8UC4,(unsigned char*) cbuf);

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
        env->SetIntArrayRegion(result,0,size,cbuf);
        env->ReleaseIntArrayElements(buf,cbuf,0);
        return result;
    }
}

