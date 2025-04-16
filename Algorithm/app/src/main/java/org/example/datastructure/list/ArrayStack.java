/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:50:50
 * @LastEditTime: 2025-03-31 12:01:26
 * @FilePath: /Algorithm/app/src/main/java/org/example/datastructure/list/ArrayStack.java
 * @Description: 
 */
package org.example.datastructure.list;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
/**
 * @author limeng
 *	栈的顺序实现，利用数组
 */
public class ArrayStack<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_SIZE = 12;
	
	private T[] mArray;// 定义一个数组用于保存顺序栈的元素
	
	private int count;// 记录栈中元素的个数
	
	public ArrayStack(Class<T> type) {
		this(type, DEFAULT_SIZE);
	}
	
	public ArrayStack(Class<T> type, int size) {
		// 不能直接调用 mArray = new T[DEFAULT_SIZE]
		mArray = (T[]) Array.newInstance(type, size);
		count = 0;
	}
	
	// 将 val 添加到栈
	public void push(T val) {
		// 当前长度加 1，判断长度是否足够
		ensureCapacity(count + 1);
		mArray[count++] = val;
	}
	
	// 返回栈顶元素值
	public T peek() {
		if(!isEmpty()) {
			return mArray[count -1];
		} else {
			return null;
		}
	}
	
	// 返回栈顶元素，并删除栈顶元素
	public T pop() {
		if(!isEmpty()) {
			T ret = mArray[count -1];
			// 释放栈顶元素
			mArray[--count] = null;
			return ret;
		} else {
			return null;
		}
	}
	
	// 返回栈的大小
	public int size() {
		return count;
	}
	
	// 返回栈是否为空
	public boolean isEmpty() {
		return size() ==0;
	}
	
	// 打印栈
	public void printArrayStack() {
		if(isEmpty()) {
			System.out.println("Stack is empty");
		}
		System.out.printf("stack size() = %d",size());
		
		int i = size() -1;
		while (i >= 0) {
			System.out.println(mArray[i]);
			i--;
		}
	}
	
	// 清空顺序栈
	public void clear() {
		// 将底层数组所有元素赋为 null
		Arrays.fill(mArray, null);
		count = 0;
	}
	private void ensureCapacity(int minCapcity) {
		// 如果数组的原有长度小于目前所需的长度
		int oldCapcity = mArray.length;
		if (minCapcity > oldCapcity) {
			int newCapcity = (oldCapcity * 3) / 2 +1;
			// 创建新数组并把原来的数据复制过去
			mArray = Arrays.copyOf(mArray, newCapcity);
		}
	}
}
