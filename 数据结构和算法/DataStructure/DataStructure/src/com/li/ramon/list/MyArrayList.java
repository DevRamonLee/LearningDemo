package com.li.ramon.list;

/**
 * 线性表的顺序表示及实现
 * 
 * @author meng.li
 * @param <T>
 */
public class MyArrayList<T> {

	private static final int DEFAULT_CAPACITY = 10;// 默认容量

	private Object[] elementData;// 真正存储数据的数组
	private int size;// 线性表长度
	private int maxSize;// 数组的大小

	// 无参构造函数
	public MyArrayList() {
		this(DEFAULT_CAPACITY);
	}

	// 带有初始容量的构造函数
	public MyArrayList(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: "
					+ initialCapacity);
		this.maxSize = initialCapacity;
		this.elementData = new Object[maxSize];
	}

	// 根据索引获取对应元素
	public T getElem(int index) {
		if (index < 0 || index >= size)
			return null;
		// 数组取值
		return (T) elementData[index];
	}

	// 根据索引插入数据
	public boolean insertElem(int index, T element) {
		if (index < 0) {
			System.out.println("插入下标不能小于 0 ");
			return false;
		}
		if (index > size) {
			System.out.println("当前线性表大小为： " + size + " 下标不能超过 " + size);
			return false;
		}
		// 确认数组容量
		ensureCapacity();
		// 插入到末尾的处理
		if (index == size) {
			elementData[index] = element;
			size++;
			return true;
		}
		// 插入到中间，需要移动元素
		for (int j = size; j > index; j--) {
			elementData[j] = elementData[j - 1];
		}

		// 插入数据
		elementData[index] = element;
		size++;
		return true;
	}

	// 删除对应索引的元素
	public T removeElem(int index) {
		if (size == 0) {
			System.out.println("当前线性表为空，不能删除");
			return null;
		}
		if (index < 0 || index >= size) {
			System.out.println("删除位置不在范围内");
			return null;
		}
		T delVal = (T) elementData[index];
		// 移除数据
		elementData[index] = null;
		// 末尾的处理,删除的是最后一位
		if (index == size - 1) {
			size--;
			return delVal;
		}
		// 一般情况的处理,删除的是中间元素，数组需要前移
		for (int j = index; j < size - 1; j++) {
			elementData[j] = elementData[j + 1];
		}
		// 最后一位置空
		elementData[size - 1] = null;
		size--;
		return delVal;
	}

	// 确认容量是否够用
	private void ensureCapacity() {
		Object[] newArrs;
		if (size == maxSize) {
			System.out.println("\n数组已经满了,正在扩容中");
			// 扩大为原来的 1.5 倍
			maxSize = maxSize + (maxSize >> 1);
			newArrs = new Object[maxSize];
			for (int j = 0; j < size; j++) {
				newArrs[j] = elementData[j];
			}
			elementData = newArrs;
			System.out.println("扩容后的大小为：" + maxSize);
		}
	}

	// 展示我们的线性表数据
	public void display() {
		for (int i = 0; i < size; i++) {
			System.out.print(" " + elementData[i]);
		}
		System.out.println();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
