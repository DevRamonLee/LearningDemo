package com.li.ramon.list.interfaces;
/**
 * 线性表的顺序存储结构接口
 * @author meng.li
 * 包含三个方法
 * 插入一个元素
 * 读取一个元素
 * 删除一个元素
 */
public interface ISeqList<T> {
	/**
	 * 插入元素
	 * @param i 需要插入的元素的位置
	 * @param value 需要插入的元素的值
	 * @return
	 */
	public boolean insertElem(int i,T value);
	
	/**
	 * 获取元素
	 * @param i 需要获得的元素的下标
	 * @return
	 */
	public T getElem(int i);
	
	/**
	 * 删除元素
	 * @param i 需要删除的元素的下标
	 * @return
	 */
	public T deleteElem(int i);
}
