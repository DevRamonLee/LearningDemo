# Android

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
	- 1.6 [数据保存](Android/Android_training/SavingData)
	- 1.7 [与其他应用交互](Android/Android_training/InteractingWithApp)
	- 2.0 [Android 分享操作](Android/Android_training/ShareFiles)
	- 3.2 [拍照](Android/Android_training/Camera)
	- 4.1 [高效显示Bitmap](Android/Android_training/DisplayBitmaps)
	- 4.2 [使用 OpenGL ES 显示图像](Android/Android_training/OpenGlEs)
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

- [Android 第三方开源库](Android/Libraries)
	- EventBus 初步使用:[EventBusDemo](Android/Libraries/EventBusDemo)
		- 相关文章：[EventBus(一)初步认识](http://betterramon.top/eventbus/2019/05/05/EventBus(%E4%B8%80)%E5%88%9D%E6%AD%A5%E8%AE%A4%E8%AF%86.html)
	- ButterKnife 初步使用：[ButterKnifeDemo](Android/Libraries/ButterKnifeDemo)
		- 相关文章：[ButterKnife(一)初步认识](http://betterramon.top/butterknife/2019/05/08/ButterKnife(%E4%B8%80)%E5%88%9D%E6%AD%A5%E8%AE%A4%E8%AF%86.html)

# 数据结构和算法

- [DataStructure](数据结构和算法/DataStructure)
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

- [Arithmetic](数据结构和算法/Arithmetic)
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
    