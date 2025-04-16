/*
 * @Author: Ramon
 * @Date: 2025-03-29 11:06:12
 * @LastEditTime: 2025-04-07 18:55:50
 * @FilePath: /DesignPattern/app/src/main/java/org/example/singleton/Singleton2.java
 * @Description: 使用 double check 的方式来创建单例对象，相比 synchronized 提高性能
 */
package org.example.singleton;

public class Singleton2 {
    // volatile 是为了避免指令重排产生为赋值的对象
    private static volatile Singleton2 instance = null;
    private Singleton2(){}

    public static Singleton2 getInstance() {
        // 这个 if 是为了保证性能，如果已经创建了，就不再进入内部代码块
        if(instance == null) {
            // 1
            synchronized(Singleton.class) {
                // 2
                if(instance==null) {
                    instance = new Singleton2();
                }
          }
        }
        return instance; // 3
    }
}
