/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:50:50
 * @LastEditTime: 2025-03-31 12:01:21
 * @FilePath: /Algorithm/app/src/main/java/org/example/datastructure/list/ArrayQueueTest.java
 * @Description: 
 */
package org.example.datastructure.list;

public class ArrayQueueTest {

	public static void main(String[] args) {
		// Ë³Ðò¶ÓÁÐ
		ArrayQueue<Integer> arrayQueue= new ArrayQueue<Integer>();
		arrayQueue.join(3);
		arrayQueue.join(4);
		arrayQueue.join(5);
		arrayQueue.join(6);
		
		System.out.println(arrayQueue.toString());
		arrayQueue.poll();
		arrayQueue.poll();
		System.out.println(arrayQueue.toString());
		System.out.println("invoke peek : " + arrayQueue.peek());

	}

}
