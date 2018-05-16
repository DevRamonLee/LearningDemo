package com.li.ramon.list;

import java.util.Random;

/**
 * 测试线性表的顺序表示与实现
 * @author meng.li
 *
 */
public class MyArrayListTest {
	final int MAX = 60;
	Random random = new Random();
	MyArrayList<Integer> myArrayList;
	
	public MyArrayListTest(){
		initmyArrayList();
	}
	
	/*
	 *采用随机数初始化数组 
	 */
	public void initmyArrayList(){
		myArrayList = new MyArrayList<Integer>();
		//随机产生一个我们要插入的数据量的数值
		int size = random.nextInt(MAX);
		System.out.println("数据随机长度为 "+size);
		for(int i = 0;i < size; i++ ){
			int value = random.nextInt(MAX);
			if(!myArrayList.insertElem(i, value)){
				System.exit(0);
			}
		}
		System.out.println("\n初始化线性表为： ");
		myArrayList.display();
	}
	/*
	 * 随机获取数据
	 */
	public void getElemTest(){
		int i = random.nextInt(MAX);
		System.out.println("\n获取数据的随机下标为: " + i);
		System.out.println("获取到的数据为: " + myArrayList.getElem(i));
	}
	/*
	 * 随机插入数据
	 */
	public void insertElemTest(){
		int i = random.nextInt(MAX);
		System.out.println("\n随机插入的位置为： " + i);
		int value = random.nextInt(MAX);
		System.out.println("随机插入的数据为：" + value);
		myArrayList.insertElem(i, value);
		System.out.println("插入数据后的列表为：");
		myArrayList.display();
	}
	/*
	 * 随机删除数据
	 */
	public void deleteElemTest(){
		int i = random.nextInt(MAX);
		System.out.println("\n随机删除的位置为：" + i);
		Integer value = myArrayList.removeElem(i);
		if(value == null){
			System.exit(0);
		}else{
			System.out.println("删除的数据为： " + value);
			System.out.println("删除数据后的列表为: " );
			myArrayList.display();
		}
	}
	
	
	public static void main(String args[]){
		MyArrayListTest myArrayListTest = new MyArrayListTest();
		myArrayListTest.insertElemTest();
		myArrayListTest.getElemTest();
		myArrayListTest.deleteElemTest();
	}
}
