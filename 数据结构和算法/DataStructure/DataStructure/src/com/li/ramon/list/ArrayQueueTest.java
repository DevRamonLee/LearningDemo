package com.li.ramon.list;

public class ArrayQueueTest {

	public static void main(String[] args) {
		// À≥–Ú∂”¡–
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
