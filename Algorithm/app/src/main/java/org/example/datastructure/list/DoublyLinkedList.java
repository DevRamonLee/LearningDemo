package org.example.datastructure.list;

/**
 * 双向链表
 * @author limeng
 *
 */
public class DoublyLinkedList<E> {
	private Entry<E> head = new Entry<E>(null); //头结点
	private Entry<E> tail = new Entry<E>(null); //头结点
	private int size = 0;
	
	// 初始化双向链表
	public DoublyLinkedList() {
		// 这一步很关键，把 head 的 prior 指针指向空， tail 的 next 指针指向空
		head.prior = null;
		tail.next = null;
		// 让头结点指向尾结点
		head.next = tail;
		// 让尾结点指向头结点
		tail.prior = head;
	}
	
	// 判断链表是否为空
	public boolean isDoublyLinedListEmpty() {
		if(head.next == tail) {
			return true;
		}
		return false;
	}
	
	// 尾插法，插入一个结点
	public void insertFromTail(E e) {
		// 创建结点
		Entry<E> data = new Entry<E>(e);

		tail.prior.next = data;
		data.prior = tail.prior;
		data.next = tail;
		tail.prior = data;
		size++;
	}
	
	// 头插入，从头部插入一个结点
	public void insertFromHead(E e) {
		// 创建结点
		Entry<E> data = new Entry<E>(e);
		
		head.next.prior = data;
		data.next = head.next;
		data.prior = head;
		head.next = data;
		size++;
	}
	
	// 删除 index 位置的元素
	public E deleteAtIndex(int index) {
		if(index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException("Delete element,the index is out of bounds. index = " + index);
		} else {
			int counter = 0;
			Entry<E> temp = head.next;
			while(temp != tail) {
				if(index == counter) {
					// 删除元素
					temp.prior.next = temp.next;
					temp.next.prior = temp.prior;
					E data = temp.element;
					temp = null;
					return data;
				}
				counter ++;
				temp = temp.next;
			}
			return null;
		}
	}
	
	// 打印双向链表
	public void print() {
		if(isDoublyLinedListEmpty()) {
			System.out.println("双向链表为空！");
		}
		Entry<E> temp = head.next;
		while(temp != tail) {
			System.out.print(temp.element + " ");
			temp = temp.next;
		}
		System.out.println();
	}
	
	// 获取指定 index 的元素
	public E getElementByIndex(int index) {
		if(index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException("Delete element,the index is out of bounds. index = " + index);
		}
		int counter = 0;
		Entry<E> temp = head.next;
		while(counter < index) {
			temp = temp.next;
			counter++;
		}
		return temp.element;
	}
	
	// 获取链表的 size
	public int getSize() {
		return size;
	}
	
	// 双向链表的结点 Node，这里使用泛型，使其具有扩展性
	private static class Entry<E> {
		E element; // data 元素
		Entry<E> prior;// 指向前一个结点的指针
		Entry<E> next; // 指向下一个结点的指针

		@SuppressWarnings("unused")
		Entry(E element) {
			this.element = element;
		}
	}
}
