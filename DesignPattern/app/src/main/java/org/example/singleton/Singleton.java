/*
 * @Author: Ramon
 * @Date: 2021-12-11 11:45:03
 * @LastEditTime: 2025-03-29 11:09:26
 * @FilePath: /DesignPattern/app/src/main/java/org/example/singleton/Singleton.java
 * @Description: 线程不安全的单例模式，错误示例
 */
package org.example.singleton;


public class Singleton {
    private static Singleton instance = null;
    private Singleton(){}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}