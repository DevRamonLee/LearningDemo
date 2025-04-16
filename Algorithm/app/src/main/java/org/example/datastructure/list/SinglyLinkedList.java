package org.example.datastructure.list;

/***
 * 单链表的基本操作
 * @author limeng
 * @param <E>
 */
public class SinglyLinkedList<E> {
	private Entry<E> header = new Entry<E>(null); //头结点
	private int size = 0; // 当前尺寸
	
	//构造函数，初始化单链表
	public SinglyLinkedList() {

	}
	
	/**
	 * 添加元素
	 * @param e
	 */
	public void add(E e) {
		Entry temp = header;
	    while (temp.next != null) {
	        temp = temp.next;
	    }
	    temp.next = new Entry(e);
	    size++;
	}

	/**
	 * 在指定位置插入元素
	 * @param index
	 * @param e
	 */
	public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Add element failed,the index is out of bounds. index = " + index);
        } else {
        	Entry temp = header; // temp 临时结点，保存 header 头结点
            int counter = 0; //记录我们遍历到哪个位置了
            while (temp != null) {
            	/* index 等于 counter，说明遍历到了要插入的位置 
            	 * 例： 当前 index 为 0 第一次循环 index 等于 counter
            	 *    则此时需要把元素 e 插入到 0 位置
            	 *    1. 首先我们需要保存当前元素的下一个结点，也就是  back 对象，
            	 *    2. 把当前结点的 next 指向 使用 e 创建的对象 entry，新结点
            	 *    3. 新结点 entry 的 next 指向我们刚才保存的 back 对象，这样链表就重新连接起来了
            	 *    4. size ++
            	 */
                if (index == counter) {
                    Entry entry = new Entry(e);
                    Entry back = temp.next;
                    temp.next = entry;
                    entry.next = back;
                    size++;
                }
                temp = temp.next;
                counter++;
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
            while (temp != null) {
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
	 * 删除元素
	 * @param ind
	 */
	public void delete(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Delete element,the index is out of bounds. index = " + index);
        } else {
            Entry temp = header;
            int counter = 0;
            while (temp != null) {
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
	 * 遍历单链表
	 */
	public void display() {
		Entry temp = header.next;
		while(temp != null) {
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
