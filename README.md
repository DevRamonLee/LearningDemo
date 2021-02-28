## Android

#### Android 基础

- [开源库使用](Android/OpenSourceLibUsing/ReadMe.md)

- 四大组件
    - [Activity 的生命周期](Android/FourComponents/Activity-life-cycle.md)
    - [Activity 启动模式](Android/FourComponents/Activity-launch-mode.md)
    - [Intent 与 IntentFilter](Android/FourComponents/Intent-and-IntentFilter.md)

- IPC
	- [IPC 第一话：Binder 与 序列化](Android/IPC/IPC-one.md)
	- [IPC 第二话：手写实现 Binder](Android/IPC/IPC-two.md)
	- [IPC 第三话：AIDL 方式实现 Binder,跨进程接口回调](Android/IPC/IPC-three.md)
	- [IPC 第四话：Bundle-文件共享-Messenger实现 IPC](Android/IPC/IPC-four.md)
	- [IPC 第五话：ContentProvider 实现 IPC](Android/IPC/IPC-five.md)
	- [IPC 第六话：Socket 实现 IPC](Android/IPC/IPC-six.md)
	- [IPC 第七话：Binder 连接池](Android/IPC/IPC-seven.md)

- View 事件体系
	- [View 第一话：View 的基础知识](Android/View/View-one-basic.md)
	- [View 第二话：View 滑动](Android/View/View-two-scroll.md)
	- [View 第三话：View 的事件分发](Android/View/View-three-dispacth-event.md)
	- [View 第四话：View 滑动冲突](Android/View/View-four-scroll-conflict.md)
	- [View 第五话：View 的绘制流程](Android/View/View-five-paint-progress.md)
    - [View 第六话：RemoteView](Android/View/View-six-RemoteView.md)

- 其他
    - [数据存储(SharedPreference、文件、数据库)](Android/Others/SavingData.md)
    - [分享操作 - FileProvider-ActionProvider 的使用](Android/Others/ShareFiles/ShareAction-FileProvider-ActionProvider.md)
    - [Camera 的使用](Android/Others/Camera.md)
    - [高效加载大图](Android/Others/DispalyBitmaps.md)

## Flutter

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

Ramon Lee

## License

Copyright (C) 2020 Ramon Lee `<`[betterramon@gmail.com](betterramon@gmail.com)`>`

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.