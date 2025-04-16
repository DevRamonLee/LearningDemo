/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:49:12
 * @LastEditTime: 2025-03-31 12:03:54
 * @FilePath: /Algorithm/app/src/main/java/org/example/common/model/Merchandise.java
 * @Description: 
 */
package org.example.common.model;

/**
 * 0-1 背包，描述物品的数据结构
 * @author meng.li
 *
 */
public class Merchandise {
	public int weight;	//重量
	public int price;	//价值
	public int status;	//0:未选中；1:已选中；2:已经不可选
	
	public Merchandise(int weight, int price, int status){
		this.weight = weight;
		this.price = price;
		this.status = status;
	}
}
