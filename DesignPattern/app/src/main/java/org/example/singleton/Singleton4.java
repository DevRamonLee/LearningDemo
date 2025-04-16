/*
 * @Author: Ramon
 * @Date: 2025-03-26 11:38:31
 * @LastEditTime: 2025-03-26 11:40:54
 * @FilePath: /DesignPattern/src/Singleton/Singleton2.java
 * @Description: 使用静态内部类来实现懒汉式单例，线程安全，懒加载
 */

package org.example.singleton;

public class Singleton4 {
   
    private Singleton4() {}

    private static class SingletonHolder {
    
        private static final Singleton4 INSTANCE = new Singleton4();
    }
    
    public static Singleton4 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
