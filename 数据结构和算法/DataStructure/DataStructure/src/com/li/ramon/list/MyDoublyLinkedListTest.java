package com.li.ramon.list;

import java.util.Random;

public class MyDoublyLinkedListTest {

	final int MAX = 10;
	Random random = new Random();
	MyDoublyLinkedList<Integer> myDoublyLinkedList;

	public MyDoublyLinkedListTest() {
		initMyDoublyLinkedList();
	}

	/*
	 * 采用随机数初始化数组
	 */
	public void initMyDoublyLinkedList() {
		myDoublyLinkedList = new MyDoublyLinkedList<Integer>();
		for (int i = 0; i < MAX; i++) {
			int value = random.nextInt(MAX);
			// 添加一个数据
			myDoublyLinkedList.insertFromHead(value);
		}
		System.out.println("生成的双向链表数据为：");
		myDoublyLinkedList.print();
	}

	/*
	 * 随机获取数据
	 */
	public void getElemTest() {
		int i = random.nextInt(MAX);
		System.out.println("\n获取数据的随机下标为: " + i);
		System.out.println("获取到的数据为: " + myDoublyLinkedList.getElementByIndex(i));
	}

	/*
	 * 随机删除数据
	 */
	public void deleteElemTest() {
		int i = random.nextInt(MAX);
		System.out.println("\n随机删除的位置为：" + i);
		int data = myDoublyLinkedList.deleteAtIndex(i);
		System.out.println("删除的数据为: " + data + " 删除数据后的列表为: ");
		myDoublyLinkedList.print();
	}

	public static void main(String[] args) {
		MyDoublyLinkedListTest myDoublyLinkedListTest = new MyDoublyLinkedListTest();
		myDoublyLinkedListTest.getElemTest();
		myDoublyLinkedListTest.deleteElemTest();
	}
}
