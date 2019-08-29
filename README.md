# 大前端学习笔记

![logo](./assets/betterramon.png)

## Android

#### Android 基础

- [Intent 与 IntentFilter](Android/Basic/Intent-and-IntentFilter.md)
- [数据存储(SharedPreference、文件、数据库)](Android/Basic/SavingData)
- [分享操作 - FileProvider-ActionProvider 的使用](Android/Basic/ShareFiles/ShareAction-FileProvider-ActionProvider.md)
- [Camera 的使用](Android/Basic/Camera)
- [高效加载大图](Android/Basic/DisplayBitmaps)

- [Android 四大组件](Android/Android_Component)
    - Components(Activity、Service)

- [Android UI](Android/Android_UI)
    - AndroidUI(动画、自定义视图、fragment、layout、openGL、style、与用户交互)
	- WebView 基本使用总结:[WebViewDemo](Android/Android_UI/WebViewDemo)
		- [WebView 基本使用方法总结](http://betterramon.top/android-ui/2019/05/09/WebView%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8.html)

- [Frameworks](Android/Frameworks)
    - BinderDemo (手写实现 AIDL)
    - BinderDemo2 (AIDL 实现并扩展,跨进程接口回调)
    - MessengerDemo (Messenger 的使用)
    - BinderPoolDemo (Binder 连接池，存在多个 AIDL 调用时使用)
    - ContentProviderDemo (自定义 ContentProvider 实现跨进程使用)
    - SocketDemo (Socket 跨进程通信，简单自动回复功能)

- [Android_advanced](Android/Android_advanced)
	- [Surface SurfaceView SurfaceHolder SurfaceHolder.Callback关系](Android/Android_advanced/TestSurfaceView)
    - [Android Studio 中使用 OpenCV 方法一: 使用 Android Module 库,需安装 OpenCvManager.apk](Android/Android_advanced/OpenCVDemo1)
    - [Android Studio 中使用 OpenCV 方法二：NDK 使用 JNI 调用 C++ 实现](Android/Android_advanced/OpenCVDemo2)
        - 注意：由于 native 文件夹太大，请自行下载拷贝进入根目录 [OpenCV android SDK下载地址](https://sourceforge.net/projects/opencvlibrary/files/opencv-android/)
    - [Android Studio 中使用 OpenCV 方法三：使用 java 代码调用，不需要安装 OpenCvManager.apk](Android/Android_advanced/OpenCVDemo3)
    - [Android Studio 中使用 OpenCV 方法四：Cmake 使用 JNI 调用 C++ 实现](Android/Android_advanced/OpenCVDemo4)
    - [AS 中使用OpenCV (二)二维码提取](Android/Android_advanced/OpenCVDemo)
    

- [Android_training](Android/Android_training)
	- 4.3 [添加动画](Android/Android_training/AddAnimation)
	- 5.1 [无线连接设备](Android/Android_training/NetDeviceConnect)
	- 5.2 [执行网络操作](Android/Android_training/AccessInternet)
	- 10.2 [实现高效导航](Android/Android_training/EffectiveNavigation)
    - 10.3 [通知提示用户](Android/Android_training/NotifyUser)
	- 10.4 [增加搜索功能](Android/Android_training/Search)
    - 11.2 [创建自定义View](Android/Android_training/CustomView)
    - 11.5 [管理系统UI](Android/Android_training/ManageUi)
    - 13.2 [使用CursorLoader在后台加载数据](Android/Android_training/CursorLoader)
    - 14.5 [多线程操作(此代码只可做参考)](Android/Android_training/ThreadSample)


#### Android 流行库	

- [EventBus](Android/Libraries/EventBus)
	- [EventBus(一) 初步认识](Android/Libraries/EventBus/EventBusDemo)

- [ButterKnife](Android/Libraries/ButterKnife)
	- [ButterKnife(一)初步认识](Android/Libraries/ButterKnife/ButterKnifeDemo)

- [OkHttp](Android/Libraries/OkHttp/)
	- [OkHttp(一)初步认识与简单封装](Android/Libraries/OkHttp/OkHttpDemo)
	- [OKHttp(二)在项目中封装使用 OkHttp](Android/Libraries/OkHttp/OkHttpDemo2)

- [Retrofit](Android/Libraries/Retrofit/)
	- [Retrofit(一)初步认识](Android/Libraries/Retrofit/RetrofitDemo)

- [RxJava](Android/Libraries/RxJava)
	- [RxJava(一)初步认识](Android/Libraries/RxJava/RxJavaDemo)

- [Glide](Android/Libraries/Glide)
	- [Glide(一)初步认识](Android/Libraries/Glide/GlideDemo)
	
- [Retrofit+RxJava+Okhttp 封装网络请求框架](Android/Libraries/RroDemo)

## Vue

## React

## Flutter

## ReactNative

## 数据结构与算法

- [DataStructure](DataStructureAndArithmetic/DataStructure)
    - src\com\li\ramon\list
        - 顺序表(基于数组) ArrayList.java
        - 单向链表 SinglelyLinkedList.java
        - 双向链表 DoublyLinkedList.java
        - 循环链表 CircularLinkedList.java 应用：约瑟夫问题 YueSeFu.java
        - 顺序栈 ArrayStack.java 应用：1.任意进制转换 2.行编辑 3.括号匹配
        - 链式栈 LinkStack.java
        - 顺序队列 ArrayQueue.java
        - 链式队列 LinkQueue.java
        - 循环队列 LoopQueue.java
    - src\com\li\ramon\graph
        - 无向带权图的邻接矩阵表示 AdjMatrix.java
    - src\com\li\ramon\sort
        - 冒泡排序 BubbleSort.java
        - 插入排序 InsertSort.java
        - 快速排序 QuickSort.java
        - 选择排序 SelectSort.java
    - src\com\li\ramon\tree
        - 二叉搜索树 BSTree.java

- [Arithmetic](DataStructureAndArithmetic/Arithmetic)
    - src\com\betterramon\arithmetic
        - 谁是小偷(推理题) WhoIsThief.java
        - 多项式求解(n 次二项式) Binomial.java
        - 0 1 背包问题，贪心算法 KnapsackProblem.java
        - 字符串全排列，分治法 Permutation.java
        - 二分查找，分治法 BinarySearch.java
        - 迭代求一个数的平方根 CalculateRoot.java
        - 动态规划法求最长公共子序列 DpLcs.java
        - 百钱买鸡问题，穷举法 BuyChickens.java
        - 趴楼梯问题，一次走 1 步或 2 步（斐波那契数列变形。递归法和非递归法） GoStairs.java
		
		
## Developed By

Ramon Lee - [http://betterramon.top](http://betterramon.top/)

## License

Copyright (C) 2019 Ramon Lee `<`[betterramon@gmail.com](betterramon@gmail.com)`>`

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.