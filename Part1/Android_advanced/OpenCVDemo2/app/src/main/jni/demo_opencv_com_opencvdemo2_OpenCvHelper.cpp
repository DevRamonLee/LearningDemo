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
    JNIEXPORT jstring JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_getStringFromJni
      (JNIEnv *, jclass);
    JNIEXPORT jint JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_addNum
      (JNIEnv *, jobject);
    JNIEXPORT void JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessStaticField
      (JNIEnv *, jobject);
    JNIEXPORT void JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessPrivateField
      (JNIEnv *, jobject);
    JNIEXPORT void JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessPublicMethod
      (JNIEnv *, jobject);
    JNIEXPORT jint JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessStaticMethod
      (JNIEnv *, jobject);
    JNIEXPORT jstring JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessSuperMethod
      (JNIEnv *, jobject);
    JNIEXPORT jint JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_intArrayMethod
      (JNIEnv *, jobject, jintArray);
    JNIEXPORT jobject JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_objectMethod
      (JNIEnv *, jobject, jobject);


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
    /*简单的返回一个字符串*/
    JNIEXPORT jstring JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_getStringFromJni
          (JNIEnv * env, jclass obj){
        char* str = "hello from jni";
        jstring jstr = env->NewStringUTF(str);
        return jstr;
    }

    /*jni 使用 java 中的变量*/
    JNIEXPORT jint JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_addNum
          (JNIEnv * env, jobject jobj){
           //获取实例对应的 class
          jclass jclazz = env->GetObjectClass(jobj);
          /*通过class获取相应的变量的 field id
            jclass 表示 实例对应的class类型
            num 表示变量名字
            I 代表类型描述符，基本数据类型 int
            jfieldID 代表该实例变量的 id
          */
          jfieldID fid = env->GetFieldID(jclazz, "num", "I");
          //通过 field id 获取对应的值
          //env->Get{type}Field(jobject, fieldId)
          jint num = env->GetIntField(jobj, fid);  //注意，不是用 jclazz, 使用 jobj
          num++;
          return num;
    }

    JNIEXPORT void JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessStaticField
      (JNIEnv * env, jobject jobj){
          jclass jclazz = env->GetObjectClass(jobj);
          //注意是用GetStaticFieldID，不是GetFieldID  String 是引用类型，需要使用引用类型的描述符
          jfieldID fid = env->GetStaticFieldID(jclazz, "name", "Ljava/lang/String;");
          jstring name = (jstring) env->GetStaticObjectField(jclazz, fid);
          /*如果为JNI_TRUE则表示开辟内存，然后把Java中的String拷贝到这个内存中，然后返回指向这个内存地址的指针。
            如果为JNI_FALSE，则直接返回指向Java中String的内存指针。这时不要改变这个内存中的内容，这将破坏String在Java中始终是常量的规则。
            如果是NULL，则表示不关心是否拷贝字符串。
          */
          const char* str = env->GetStringUTFChars(name, JNI_FALSE);
          /*
           * 不要用 == 比较字符串
           * name == (jstring) "cfanr"
           * 或用 = 直接赋值
           * name = (jstring) "navy"
           * 警告：warning: result of comparison against a string literal is unspecified (use strncmp instead) [-Wstring-compare]
           */
          char ch[30] = "hello, ";
          //将两个类型链接，把str放入ch中，ch要有足够的空间来保存str
          strcat(ch, str);
          jstring new_str = env->NewStringUTF(ch);
          // 将jstring类型的变量，设置到java
          env->SetStaticObjectField(jclazz, fid, new_str);
    }

    JNIEXPORT void JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessPrivateField
      (JNIEnv * env, jobject jobj){
        jclass clazz = env->GetObjectClass(jobj);

        jfieldID fid = env->GetFieldID(clazz, "age", "I");

        jint age = env->GetIntField(jobj, fid);

          if(age > 18) {
              age = 18;
          } else {
              age--;
          }
          env->SetIntField(jobj, fid, age);
    }

    /*调用 java 的公有方法*/
    JNIEXPORT void JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessPublicMethod
      (JNIEnv * env , jobject jobj){
           //1.获取对应 class 的实体类
          jclass jclazz = env->GetObjectClass(jobj);
          //2.获取方法的 id
          jmethodID mid = env->GetMethodID(jclazz, "setSex", "(Ljava/lang/String;)V");
          //3.字符数组转换为字符串
          char c[10] = "male";
          jstring jsex = env->NewStringUTF(c);
          //4.通过该 class 调用对应的 public 方法
          env->CallVoidMethod(jobj, mid, jsex);
    }

    JNIEXPORT jint JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessStaticMethod
      (JNIEnv * env, jobject jobj){
            //1.获取对应 class 实体类
            jclass jclazz = env->GetObjectClass(jobj);
            //2.通过 class 类找到对应的方法 id
            jmethodID mid = env->GetStaticMethodID(jclazz, "getHeight", "()I");  //注意静态方法是调用GetStaticMethodID, 不是GetMethodID
            //3.通过 class 调用对应的静态方法
            return env->CallStaticIntMethod(jclazz, mid);
    }

    JNIEXPORT jstring JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_accessSuperMethod
      (JNIEnv * env, jobject jobj){
            //1.通过反射获取 class 实体类
            jclass jclazz = env-> FindClass("demo/opencv/com/opencvdemo2/BaseOpenCvHelper");  //注意 FindClass 不要 L和;
            if(jclazz == NULL) {
                char c[10] = "error";
                return env->NewStringUTF(c);
            }
            //通过 class 找到对应的方法 id
            jmethodID mid = env->GetMethodID(jclazz, "hello", "(Ljava/lang/String;)Ljava/lang/String;");
            char ch[10] = "Ramon";
            jstring jstr = env->NewStringUTF(ch);
            return (jstring) env->CallNonvirtualObjectMethod(jobj, jclazz, mid, jstr);
    }

    JNIEXPORT jint JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_intArrayMethod
      (JNIEnv * env, jobject jobj, jintArray arr_){
        jint len = 0, sum = 0;
        //数组转换为对应类型的指针
        jint *arr = env->GetIntArrayElements(arr_, 0);
        //获取数组长度
        len = env->GetArrayLength(arr_);
        //由于一些版本不兼容，i不定义在for循环中
        jint i=0;
        for(; i < len; i++) {
            sum += arr[i];
        }
        env->ReleaseIntArrayElements(arr_, arr, 0);  //释放内存
        return sum;
    }

    //java 传递自定义对象类型的参数给 jni
    JNIEXPORT jobject JNICALL Java_demo_opencv_com_opencvdemo2_OpenCvHelper_objectMethod
      (JNIEnv * env, jobject jobj, jobject person){
        jclass clazz = env->GetObjectClass(person);  //注意是用 person，不是 jobj
        //jclass jclazz = env->FindClass("demo/opencv/com/opencvdemo2/Person;");  //或者通过反射获取
        if(clazz == NULL) {
            return env->NewStringUTF("cannot find class");
        }
        //获取方法构造方法 id
        jmethodID constructorMid = env->GetMethodID(clazz, "<init>", "(ILjava/lang/String;)V");
        if(constructorMid == NULL) {
            return env->NewStringUTF("not find constructor method");
        }
        jstring name = env->NewStringUTF("Ramon");

        return env->NewObject(clazz, constructorMid, 26, name);
    }
}

