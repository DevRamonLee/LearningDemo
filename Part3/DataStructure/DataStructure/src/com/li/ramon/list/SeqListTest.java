package com.li.ramon.list;

import java.util.Random;

import com.li.ramon.list.impls.SeqList;

/**
 * 测试线性表的顺序表示与实现
 * @author meng.li
 *
 */
public class SeqListTest {
	final int MAX = 25;
	Random random = new Random();
	SeqList<Integer> seqList;
	
	public SeqListTest(){
		initSeqList();
	}
	
	/*
	 *采用随机数初始化数组 
	 */
	public void initSeqList(){
		seqList = new SeqList<Integer>();
		int length = random.nextInt(MAX);
		System.out.println("差生的数据随机长度为 "+length);
		if(length > SeqList.MAXSIZE){
			System.out.println("长度不合法");
		}
		for(int i = 1;i <= length; i++ ){
			int value = random.nextInt(MAX);
			System.out.print(value+" ");
			if(!seqList.insertElem(i, value)){
				System.exit(0);
			}
		}
		System.out.println("\n原始数组为： ");
		displayList(seqList);
	}
	/*
	 * 随机获取数据
	 */
	public void getElemTest(){
		int i = random.nextInt(MAX);
		System.out.println("\n获取数据的随机下标为: " + i);
		System.out.println("获取到的数据为: " + seqList.getElem(i));
	}
	/*
	 * 随机插入数据
	 */
	public void insertElemTest(){
		int i = random.nextInt(MAX);
		System.out.println("\n随机插入的位置为： " + i);
		int value = random.nextInt(MAX);
		System.out.println("随机插入的数据为：" + value);
		seqList.insertElem(i, value);
		System.out.println("插入数据后的列表为：");
		displayList(seqList);
	}
	/*
	 * 随机删除数据
	 */
	public void deleteElemTest(){
		int i = random.nextInt(MAX);
		System.out.println("\n随机删除的位置为：" + i);
		Integer value = seqList.deleteElem(i);
		if(value == null){
			System.exit(0);
		}else{
			System.out.println("删除的数据为： " + value);
			System.out.println("删除数据后的列表为: " );
			displayList(seqList);
		}
	}
	
	/*
	 * 展示数据
	 */
	public void displayList(SeqList seqList){
		for(int i = 1;i <= seqList.getLength();i++){
			if(seqList.getElem(i) != null){
				System.out.print(seqList.getElem(i) + " ");
			}
		}
		System.out.println("\n数组的长度为： "+ seqList.getLength());
	}
	
	public static void main(String args[]){
		SeqListTest seqListTest = new SeqListTest();
		seqListTest.insertElemTest();
		seqListTest.getElemTest();
		seqListTest.deleteElemTest();
	}
}
