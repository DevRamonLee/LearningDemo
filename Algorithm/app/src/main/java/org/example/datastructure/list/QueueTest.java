/*
 * @Author: Ramon
 * @Date: 2025-03-31 08:50:50
 * @LastEditTime: 2025-03-31 12:02:06
 * @FilePath: /Algorithm/app/src/main/java/org/example/datastructure/list/QueueTest.java
 * @Description: 
 */
package org.example.datastructure.list;

public class QueueTest {

	public static void main(String[] args) {
		// 顺序队列
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
		
		// 链式队列
		LinkQueue<Integer> linkQueue = new LinkQueue<Integer>();
		linkQueue.join(1);
		linkQueue.join(2);
		linkQueue.join(3);
		System.out.println(linkQueue.toString());
		System.out.println("poll: " + linkQueue.poll()); // 先进先出
		System.out.println(linkQueue.toString());
		
		// 循环队列
		LoopQueue<String> queue = new LoopQueue<String>("aaa", 3);
		// 添加两个元素
		queue.add("bbb");
		queue.add("ccc");
		System.out.println(queue.toString());
		// 删除一个元素
		queue.remove();
		System.out.println(queue.toString());
		
	}

}
