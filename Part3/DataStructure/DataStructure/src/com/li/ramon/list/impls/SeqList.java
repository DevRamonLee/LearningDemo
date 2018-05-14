package com.li.ramon.list.impls;

import com.li.ramon.list.interfaces.ISeqList;
/**
 * 线性表的顺序实现
 * @author meng.li
 *
 * @param <T>
 */
public class SeqList<T> implements ISeqList<T> {
	public static final int MAXSIZE = 30;//初始化数组大小
	
	private T[] data;//数组存储数据
	private int length;//当前数据长度
	
	public SeqList(){
		data = (T[]) new Object[MAXSIZE];
	}
	/**
	 *	插入元素
	 * @param i 需要插入的元素的位置
	 * @param value 需要插入的元素的值
	 * @return
	 */
	public boolean insertElem(int i, T value) {
		//线性表已满
		if(length == MAXSIZE){
			System.out.println("该线性表已经满了");
			return false;
		}
		//插入位置不在范围内
		if(i < 1 || i > MAXSIZE){
			System.out.println("插入的位置不在范围内");
			return false;
		}
		//插入的位置不在表尾,需要把元素后移腾出位置
		if(i < length){
			for(int j = length;j >= i;j--){
				data[j] = data[j-1];
			}
		}
		//插入的位置大于当前 length 长度，插入到末尾
		if(i >= length){
			i = length +1;
		}
		//线性表是从1开始，而数组是从0开始，所以插入数据位置为i-1
		data[i-1] = value;
		length ++;
		return true;
	}
	
	/**
	 *  获取元素
	 * @param i 需要获得的元素的下标
	 * @return
	 */
	public T getElem(int i) {
		if(i < 1 || i > MAXSIZE){
			return null;
		}
		return data[i-1];
	}

	/**
	 * 删除元素
	 * @param i 需要删除的元素的下标
	 * @return
	 */
	public T deleteElem(int i) {
		//线性表为空
		if(length == 0){
			System.out.println("线性表为空");
			return null;
		}
		//删除的数据不在范围内
		if(i < 1 || i> length){
			System.out.println("删除的数据不在范围内");
			return null;
		}
		T t = data[i-1];
		//数据向删除位置前移
		for(int j = i;j < length;j++){
			data[j-1] = data[j];
		}
		length--;
		return t;
	}
	public T[] getData() {
		return data;
	}
	public void setData(T[] data) {
		this.data = data;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		if(length < 0 || length > MAXSIZE){
			System.out.println("长度不合法");
		}
		this.length = length;
	}

}
