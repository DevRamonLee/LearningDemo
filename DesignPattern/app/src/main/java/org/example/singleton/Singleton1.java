/*
 * @Author: Ramon
 * @Date: 2025-03-29 11:03:30
 * @LastEditTime: 2025-03-29 11:05:25
 * @FilePath: /DesignPattern/app/src/main/java/org/example/singleton/Singleton1.java
 * @Description: 使用 synchronize 创建线程安全的单例模式，但是效率较低
 */
package org.example.singleton;

public class Singleton1 {
    private static Singleton1 instance = null;
    private Singleton1(){}

    public static synchronized Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }
}
