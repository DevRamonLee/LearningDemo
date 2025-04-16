/*
 * @Author: Ramon
 * @Date: 2025-03-29 10:37:49
 * @LastEditTime: 2025-03-29 11:08:05
 * @FilePath: /DesignPattern/app/src/main/java/org/example/singleton/Singleton3.java
 * @Description: 饿汉式单例，使用前已经创建好了
 */
package org.example.singleton;

public class Singleton3 {
    private static final Singleton3 instance = new Singleton3();
    private Singleton3(){} 

    public static Singleton3 getInstance() {
        return instance;
    }
}
