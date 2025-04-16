package org.example.datastructure.list;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 顺序队列
 * @author limeng
 *
 */
public class ArrayQueue<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int DEFAULT_SIZE = 10;
	
	private int capacity;// 保存数组长度
	
	private Object[] elementData;// 定义一个数组用于保存顺序队列元素
	
	private int count = 0;// 队列大小
	
	// 以默认数组长度创建空顺序队列
	public ArrayQueue() {
		capacity = DEFAULT_SIZE;
		elementData = new Object[capacity];
	}
	
	// 以一个初始化元素创建队列
	public ArrayQueue(T element) {
		this();
		elementData[0] = element;
		count++;
	}
	
	// 以指定长度初始化队列
	public ArrayQueue(int initSize) {
		elementData = new Object[initSize];
	}
	
	/**
	 * 以指定长度的数组来创建顺序队列
	 * @param element 指定顺序队列的第一个元素
	 * @param initSize 指定顺序队列底层数组的长度
	 */
	public ArrayQueue(T element, int initSize) {
		this.capacity = initSize;
		elementData = new Object[capacity];
		elementData[0] = element;
		count++;
	}
	
	/**
	 * size
	 * @return 返回顺序队列的大小
	 */
	public int size() {
		return count;
	}
	
	/**
	 * 入队
	 * @param element
	 */
	public void join(T element) {
		ensureCapacity(count + 1);
		elementData[count++] = element; // 先使用 count 赋值，然后再执行 ++ 操作
	}
	
	private void ensureCapacity(int minCapacity) {
		// 如果数组的原有长度小于目前所需的长度
		int oldCapacity = elementData.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
	}
	
	/**
	 * poll
	 * @return 出队并删除元素
	 */
	public T poll() {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("the queue is empty");
		}
		// 取出返回值
		T value = (T) elementData[0];
		count--;
		for (int i = 1; i <= count; i++) {
			elementData[i-1] = elementData[i];// 所有元素向前移动一位
		}
		return value;
	}
	
	/**
	 * peek
	 * @return 返回队首元素，但是不删除队首元素
	 */
	public T peek() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("the queue is empty");
		}
		return (T)elementData[0];
	}
	
	/**
	 * 顺序队列为空
	 * @return
	 */
	public boolean isEmpty() {
		return 0 == count;
	}
	
	public void clear() {
		// 将所有元素赋值为 null
		Arrays.fill(elementData, null);
		count = 0;
	}
	
	public String toString() {
		if (isEmpty()) {
			return "[]";
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (int i = 0; i < count; i++) {
				sb.append((T)elementData[i] + ", ");
			}
			int len = sb.length();
			return sb.delete(len -2, len).append("]").toString();
		}
	}
}
