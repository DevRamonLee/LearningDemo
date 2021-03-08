package com.li.ramon.list;

import java.io.Serializable;
/**
 * 链式队列
 * @author meng.li
 *
 * @param <T>
 */
public class LinkQueue<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 定义一个内部类 Node，Node 实例代表链式队列的节点
	
	private class Node {
		private T data;// 保存节点数据
		private Node next;// 指向下个节点的引用
		
		// 无参构造函数
		public Node() {
			
		}
		
		// 初始化所有属性的构造器
		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private Node front;// 保存该队列的头节点
	private Node rear;// 保存该队列的尾节点
	private int size;// 保存该队列中已包含的节点数
	
	// 创建链队列
	public LinkQueue() {
		front = null;
		rear = null;
	}
	
	/**
	 * 已指定数据元素初始化链队列，该链队列只有一个元素
	 * @param element
	 */
	public LinkQueue(T element) {
		front = new Node(element, null);
		// 只有一个节点， front 和 rear 都执向该节点
		rear = front;
		size++;
	}
	
	/**
	 * 
	 * @return 返回链队的大小
	 */
	public int size() {
		return size;
	}
	
	public void join(T element) {
		// 如果链队是空队列
		if (front == null) {
			front = new Node(element, null);
			rear = front;// 只有一个节点，指向同一个
		} else {
			Node newNode = new Node(element, null);// 创建新节点
			rear.next = newNode;// 让尾节点的 next 指向新增的节点
			rear = newNode;// 以新节点作为新的尾节点
		}
		size++;
	}
	
	/**
	 * 出队操作，并删除元素
	 * @return
	 */
	public T poll() {
		Node oldFront = front;
		front = front.next;
		oldFront.next = null;
		size--;
		return oldFront.data;
	}
	
	/**
	 * 返回队列顶部元素，但是不删除节点
	 * @return
	 */
	public T peek() {
		return front.data;
	}
	
	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * 清空队列
	 */
	public void clear() {
		// 将 front、rear 两个节点置为空
		front = null;
		rear = null;
		size = 0;
	}
	
	public String toString() {
		// 链式队列为空时
		if (isEmpty()) {
			return "[]";
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (Node current = front; current != null; current = current.next) {
				sb.append(current.data + ", ");
			}
			int len = sb.length();
			return sb.delete(len -2, len).append("]").toString();
		}
	}

}
