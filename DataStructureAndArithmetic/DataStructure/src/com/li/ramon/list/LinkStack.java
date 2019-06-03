package com.li.ramon.list;

import java.io.Serializable;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

public class LinkStack<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private class Node {
		private T data;// 保存节点的数据
		private Node next; // 指向下个结点的引用
		
		public Node() {
			
		}
		
		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private Node top;// 保存链栈的栈顶元素
	private int size = 0;// 保存链栈中已包含的节点数，即栈的长度
	
	public LinkStack() {
		top = null;
	}
	
	public LinkStack(T element) {
		top = new Node(element, null);
		size++;
	}
	
	// 栈的长度
	public int size() {
		return size;
	}
	
	// 入栈
	public void push(T element) {
		// 注意这里的参数 top，相当于把当前的 top.next 指向上一个 top
		top = new Node(element, top);
		size++;
	}
	
	// 出栈
	public T pop() {
		if( top != null) {
			Node oldTop = top;
			top = top.next;
			oldTop.next = null;// 释放它引用的元素
			size--;
			return oldTop.data;
		} else {
			return null;
		}
	}
	
	// 访问栈顶元素
	public T peek() {
		return top.data;
	}
	
	// 判断顺序栈是否为空
	public boolean isEmpty() {
		return size == 0;
	}
	
	// 打印栈信息
	public String toString() {
		// 当链栈为空链栈时
		if (isEmpty()) {
			return "[]";
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (Node current = top; current != null; current = current.next) {
				sb.append(current.data + ", ");
			}
			int len = sb.length();
			return sb.delete(len -2 , len).append("]").toString();
		}
	}
}
