package org.example.datastructure.list;

import java.io.Serializable;

import org.w3c.dom.Node;

/**
 * 循环队列
 * @author meng.li
 *
 */
public class LoopQueue<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int DEFAULT_SIZE = 10;
	private int capacity;// 保存数组的长度
	private Object[] elementData;// 定义一个数组用于保存循环队列的元素
	private int front = 0;// 队头
	private int rear = 0;// 队尾
	
	/**
	 * 以默认数组长度构建空循环队列
	 */
	public LoopQueue() {
		capacity = DEFAULT_SIZE;
		elementData = new Object[capacity];
	}
	
	/**
	 * 以一个元素来构建循环队列
	 * @param element
	 */
	public LoopQueue(T element) {
		this();
		elementData[0] = element;
		rear++;
	}
	
	/**
	 * 已指定长度来创建循环队列
	 * @param element
	 * @param initSize
	 */
	public LoopQueue(T element, int initSize) {
		this.capacity = initSize;
		elementData = new Object[capacity];
		elementData[0] = element;
		rear++;
	}
	
	public int size() {
		if (isEmpty()) {
			return 0;
		}
		// 循环队列可能 front 大于 rear
		return rear > front ? rear - front : capacity - (front - rear);
	}
	
	/**
	 * 插入队列
	 * @param element
	 */
	public void add(T element) {
		if (rear == front && elementData[front] != null) {
			throw new IndexOutOfBoundsException("the queue is full");
		}
		elementData[rear++] = element;
		// 如果 rear 已经到头，就转头
		rear = rear == capacity ? 0 : rear;
	}
	
	/**
	 * 移除队列
	 * @return
	 */
	public T remove() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("empty queue exception.");
		}
		// 保留队列 front 端元素的值
		T value = (T)elementData[front];
		// 释放队列 front 端的元素
		elementData[front++] = null;
		// 如果 front 已经到头，那就转头
		front = front == capacity ? 0 : front;
		return value;
	}
	
	/**
	 * 返回队列栈顶元素，不删除元素
	 * @return
	 */
	public T element() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("empty queue exception");
		}
		return (T)elementData[front];
	}

	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return rear == front && elementData[rear] == null;// rear 等于 front 可能是满队列
	}
	
	/**
	 * 清空循环队列
	 */
	public String toString() {
		if (isEmpty()) {
			return "[]";
		} else {
			// 如果 front < rear,有效元素就是 front 到 rear 之间的元素
			if (front < rear) {
				StringBuilder sb = new StringBuilder("[");
				for (int i = front; i < rear; i ++) {
					sb.append(elementData[i] + ", ");
				}
				int len = sb.length();
				return sb.delete(len -2, len).append("]").toString();
			}
			// 如果 front >= rear，有效元素为 front 到 capacity 之间， 0 - rear 的元素
			else {
				StringBuilder sb = new StringBuilder("[");
				for (int i = front; i < capacity; i ++) {
					sb.append(elementData[i] + ", ");
				}
				for (int j = 0; j < rear; j++) {
					sb.append(elementData[j] + ", ");
				}
				int len = sb.length();
				return sb.delete(len -2, len).append("]").toString();
			}
		}
	}
}
