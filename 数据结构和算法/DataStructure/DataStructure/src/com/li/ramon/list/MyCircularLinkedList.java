package com.li.ramon.list;

/**
 * 循环单链表
 * @author limeng
 *
 */
public class MyCircularLinkedList<E> {
	private Entry<E> header = new Entry<E>(null); //头结点
	public int size = 0; // 当前尺寸
	
	//构造函数，初始化单链表
	public MyCircularLinkedList() {
		// 初始化循环链表：头结点的 next 指针指向 header
		header.next = header;
	}
	
	/**
	 * 添加元素
	 * @param e
	 */
	public void add(E e) {
		Entry temp = header;
	    while (temp.next != header) {
	        temp = temp.next;
	    }
	    Entry addEntry = new Entry(e); 
	    temp.next = addEntry;
	    addEntry.next = header;
	    size++;
	}
	
	/**
	 * 删除元素
	 * @param ind
	 */
	public void delete(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Delete element,the index is out of bounds. index = " + index);
        } else {
            Entry temp = header;
            int counter = 0;
            while (temp.next != header) {
                //将 index 前面的节点指向 index 后面的节点
                if (index == counter) {
                    temp.next = temp.next.next;
                    size--;
                }
                counter++;
                temp = temp.next;
            }
        }
    }
	
	/**
	 * 获取元素的值
	 * @param index
	 * @return
	 */
	public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("Get element failed,the index is out of bounds. index = " + index);
        } else {
            //使用 temp 结点遍历,第 0 个元素应该是 header 的下一个元素
            Entry temp = header.next;
            //counter 用来计数
            int counter = 0;
            while (temp != header) {
                if (counter == index) {
                    return (E) temp.element;
                }
                temp = temp.next;
                counter++;
            }
        }
        return null;
    }
	

	/**
	 * 遍历单链表
	 */
	public void display() {
		Entry temp = header.next;
		while(temp.next != header) {
			System.out.print(temp.element + " " );
			temp = temp.next;
		}
	}

	// 单链表的结点 Node，这里使用泛型，使其具有扩展性
	private static class Entry<E> {
		E element; // data 元素
		Entry<E> next; // 指向下一个结点的指针

		@SuppressWarnings("unused")
		Entry(E element) {
			this.element = element;
		}
	}
}
