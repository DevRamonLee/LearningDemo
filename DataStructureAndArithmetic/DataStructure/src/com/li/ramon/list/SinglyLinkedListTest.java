package com.li.ramon.list;

import java.util.Random;

public class SinglyLinkedListTest {
	final int MAX = 20;
	Random random = new Random();
	SinglyLinkedList<Integer> mySinglyLinkedList;

	public SinglyLinkedListTest() {
		initMySinglyLinkedList();
	}

	/*
	 * 采用随机数初始化数组
	 */
	public void initMySinglyLinkedList() {
		mySinglyLinkedList = new SinglyLinkedList<Integer>();
		// 随机产生一个我们要插入的数据量的数值
		int size = random.nextInt(MAX);
		System.out.println("数据随机长度为 " + size);
		for (int i = 0; i < size; i++) {
			int value = random.nextInt(MAX);
			// 添加一个数据
			mySinglyLinkedList.add(value);
		}
		mySinglyLinkedList.display();
		System.out.println();
	}

	/*
	 * 随机获取数据
	 */
	public void getElemTest() {
		int i = random.nextInt(MAX);
		System.out.println("\n获取数据的随机下标为: " + i);
		System.out.println("获取到的数据为: " + mySinglyLinkedList.get(i));
	}

	/*
	 * 随机位置插入数据
	 */
	public void addElemTest() {
		int i = random.nextInt(MAX);
		System.out.println("\n随机插入的位置为： " + i);
		int value = random.nextInt(MAX);
		System.out.println("随机插入的数据为：" + value);
		mySinglyLinkedList.add(i, value);
		System.out.println("插入数据后的列表为：");
		mySinglyLinkedList.display();
		System.out.println();
	}

	/*
	 * 随机删除数据
	 */
	public void deleteElemTest() {
		int i = random.nextInt(MAX);
		System.out.println("\n随机删除的位置为：" + i);
		mySinglyLinkedList.delete(i);
		System.out.println("删除数据后的列表为: ");
		mySinglyLinkedList.display();
	}

	public static void main(String[] args) {
		SinglyLinkedListTest mySinglyLinkedListTest = new SinglyLinkedListTest();
		mySinglyLinkedListTest.addElemTest();
		mySinglyLinkedListTest.getElemTest();
		mySinglyLinkedListTest.deleteElemTest();
	}
}
