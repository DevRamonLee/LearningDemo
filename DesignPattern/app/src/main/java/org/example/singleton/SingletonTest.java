/*
 * @Author: Ramon
 * @Date: 2025-03-26 11:32:38
 * @LastEditTime: 2025-04-07 18:57:58
 * @FilePath: /DesignPattern/app/src/main/java/org/example/singleton/SingletonTest.java
 * @Description: 
 */
package org.example.singleton;

import java.util.concurrent.CountDownLatch;

public class SingletonTest {

    /**
     * 测试各种单例模式创建
     * @throws InterruptedException
     */
    public static void testSingleton() throws InterruptedException {
        int threadCount = 1000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        long startTime = System.currentTimeMillis(); // 记录开始时间
        Runnable task = () -> {
            Singleton2 instance = Singleton2.getInstance();
            System.out.println(Thread.currentThread().getName() + " -> " + instance);
            latch.countDown();
        };

        for (int i = 0; i < threadCount; i++) {
            new Thread(task).start();
        }

        latch.await(); // 等待所有线程执行完

        long endTime = System.currentTimeMillis(); // 记录结束时间

        System.out.println("测试完成！耗时: " + (endTime - startTime) + " ms");
    }

    /**
     * 测试登记式单例
     */
    public static void testSingletonRegistry() {
        A a1 = SingletonRegistry.getInstance(A.class);
        A a2 = SingletonRegistry.getInstance(A.class);
        B b1 = SingletonRegistry.getInstance(B.class);
        B b2 = SingletonRegistry.getInstance(B.class);

        System.out.println(a1 == a2); // true
        System.out.println(b1 == b2); // true
        System.out.println((Object) a1 == (Object) b1); // false
    }
}

class A {
    public A() {
        System.out.println("A 实例创建");
    }
}

class B {
    public B() {
        System.out.println("B 实例创建");
    }
}

